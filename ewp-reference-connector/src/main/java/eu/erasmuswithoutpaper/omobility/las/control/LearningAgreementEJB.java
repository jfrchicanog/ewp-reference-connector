package eu.erasmuswithoutpaper.omobility.las.control;

import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.imobility.entity.IMobility;
import eu.erasmuswithoutpaper.omobility.las.boundary.TestEndpointsOLAS;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LearningAgreementEJB {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(TestEndpointsOLAS.class.getCanonicalName());

    public String insert(OlearningAgreement olearningAgreement) {
        em.persist(olearningAgreement);
        em.flush();
        return olearningAgreement.getId();
    }

    public void merge(OlearningAgreement olearningAgreement) {
        em.merge(olearningAgreement);
        em.flush();
    }

    public void update(OlearningAgreement olearningAgreement, OlearningAgreement olearningAgreementDB) {
        ChangesProposal changesProposal = olearningAgreement.getChangesProposal();
        LOG.fine("CHANGE: changesProposal: " + changesProposal);

        em.persist(changesProposal);
        em.flush();

        LOG.fine("CHANGE: changesProposal: " + changesProposal.getId());

        olearningAgreementDB.setChangesProposal(changesProposal);
        em.merge(olearningAgreementDB);
        em.flush();
    }

    public List<OlearningAgreement> findAll() {
        return em.createNamedQuery(OlearningAgreement.findAll).getResultList();
    }

    public List<IMobility> findIMobilityByOmobilityId(String omobilityId) {
        return em.createNamedQuery(IMobility.findByOmobilityId).setParameter("omobilityId", omobilityId).getResultList();
    }

    public OlearningAgreement findById(String id) {
        return em.find(OlearningAgreement.class, id);
    }

    public List<OlearningAgreement> findBySendingHeiId(String sendingHeiId) {
        return em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHei", sendingHeiId).getResultList();
    }

    public List<OlearningAgreement> findBySendingHeiIdFilterd(String sendingHeiId) {
        return em.createNamedQuery(OlearningAgreement.findBySendingHeiIdFilterd).setParameter("sendingHei", sendingHeiId).getResultList();
    }

    public List<OlearningAgreement> findByReceivingHeiId(String receivingHeiId) {
        return em.createNamedQuery(OlearningAgreement.findByReceivingHeiId).setParameter("receivingHei", receivingHeiId).getResultList();
    }

    public List<Institution> getInternalInstitution() {
        return em.createNamedQuery(Institution.findAll).getResultList();
    }

    public String getHeiId() {
        List<Institution> internalInstitution = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        return internalInstitution.get(0).getInstitutionId();
    }

    public ChangesProposal findChangesProposalById(String id) {
        return em.find(ChangesProposal.class, id);
    }

    public void approveChangesProposal(OmobilityLasUpdateRequest request) {
        ApprovedProposal appCmp = approveCmpStudiedDraft(request);
        em.persist(appCmp);
        em.flush();

        ChangesProposal changesProposal = em.find(ChangesProposal.class, request.getApproveProposalV1().getChangesProposalId());
        String idLAS = changesProposal.getOlearningAgreement().getId();
        OlearningAgreement omobility = em.find(OlearningAgreement.class, idLAS);
        changesProposal = omobility.getChangesProposal();

        omobility.setChangesProposal(null);
        em.remove(changesProposal);

        em.merge(omobility);
        em.flush();

        if (omobility.getFirstVersion() != null) {

            if (omobility.getApprovedChanges() == null) {
                ListOfComponents cmp = getListOfComponents(changesProposal, appCmp);

                em.persist(cmp);
                omobility.setApprovedChanges(cmp);

            } else {
                ListOfComponents cmpBD = omobility.getApprovedChanges();

                if (cmpBD.getBlendedMobilityComponents() == null || cmpBD.getBlendedMobilityComponents().isEmpty()) {
                    cmpBD.setBlendedMobilityComponents(changesProposal.getBlendedMobilityComponents());
                } else {
                    cmpBD.getBlendedMobilityComponents().addAll(changesProposal.getBlendedMobilityComponents());
                }

                if (cmpBD.getComponentsStudied() == null || cmpBD.getComponentsStudied().isEmpty()) {
                    cmpBD.setComponentsStudied(changesProposal.getComponentsStudied());
                } else {
                    cmpBD.getComponentsStudied().addAll(changesProposal.getComponentsStudied());
                }

                if (cmpBD.getComponentsRecognized() == null || cmpBD.getComponentsRecognized().isEmpty()) {
                    cmpBD.setComponentsRecognized(changesProposal.getComponentsRecognized());
                } else {
                    cmpBD.getComponentsRecognized().addAll(changesProposal.getComponentsRecognized());
                }

                if (cmpBD.getVirtualComponents() == null || cmpBD.getVirtualComponents().isEmpty()) {
                    cmpBD.setVirtualComponents(changesProposal.getVirtualComponents());
                } else {
                    cmpBD.getVirtualComponents().addAll(changesProposal.getVirtualComponents());
                }

                if (cmpBD.getShortTermDoctoralComponents() == null || cmpBD.getShortTermDoctoralComponents().isEmpty()) {
                    cmpBD.setShortTermDoctoralComponents(changesProposal.getShortTermDoctoralComponents());
                } else {
                    cmpBD.getShortTermDoctoralComponents().addAll(changesProposal.getShortTermDoctoralComponents());
                }

                cmpBD.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
                if (appCmp.getSignature() != null) {
                    cmpBD.setReceivingHeiSignature(appCmp.getSignature());
                }
                cmpBD.setStudentSignature(changesProposal.getStudentSignature());

                em.merge(cmpBD);
                em.flush();
            }

        } else {
            ListOfComponents cmp = getListOfComponents(changesProposal, appCmp);

            em.persist(cmp);

            omobility.setFirstVersion(cmp);
        }

        em.merge(omobility);
        em.flush();
    }

    public void rejectChangesProposal(OmobilityLasUpdateRequest request) {
        CommentProposal updateComponentsStudied = updateComponentsStudied(request);
        em.persist(updateComponentsStudied);
        em.flush();

        ChangesProposal changesProposal = em.find(ChangesProposal.class, request.getCommentProposalV1().getChangesProposalId());
        if (changesProposal.getOlearningAgreement().getFirstVersion() == null) {
            changesProposal.setCommentProposal(updateComponentsStudied);
            if (updateComponentsStudied.getSignature() != null) {
                changesProposal.setReceivingHeiSignature(updateComponentsStudied.getSignature());
            }
            em.merge(changesProposal);
            em.flush();
        } else {
            em.remove(changesProposal);

            OlearningAgreement omobility = em.find(OlearningAgreement.class, changesProposal.getOlearningAgreement().getId());
            omobility.setChangesProposal(null);

            em.merge(omobility);
            em.flush();
        }
    }

    private ApprovedProposal approveCmpStudiedDraft(OmobilityLasUpdateRequest request) {
        ApprovedProposal appCmp = new ApprovedProposal();

        if (request == null || request.getApproveProposalV1() == null) {
            return null;
        }

        String changesProposal = request.getApproveProposalV1().getChangesProposalId();
        appCmp.setChangesProposalId(changesProposal);

        appCmp.setOmobilityId(request.getApproveProposalV1().getOmobilityId());

        Signature signature = new Signature();
        signature.setSignerApp(request.getApproveProposalV1().getSignature().getSignerApp());
        signature.setSignerEmail(request.getApproveProposalV1().getSignature().getSignerEmail());
        signature.setSignerName(request.getApproveProposalV1().getSignature().getSignerName());
        signature.setSignerPosition(request.getApproveProposalV1().getSignature().getSignerPosition());
        if (request.getApproveProposalV1().getSignature().getTimestamp() != null) {
            signature.setTimestamp(request.getApproveProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
        }

        appCmp.setSignature(signature);

        return appCmp;
    }

    private static ListOfComponents getListOfComponents(ChangesProposal changesProposal, ApprovedProposal appCmp) {
        ListOfComponents cmp = new ListOfComponents();

        cmp.setBlendedMobilityComponents(changesProposal.getBlendedMobilityComponents());
        cmp.setComponentsStudied(changesProposal.getComponentsStudied());
        cmp.setComponentsRecognized(changesProposal.getComponentsRecognized());
        cmp.setVirtualComponents(changesProposal.getVirtualComponents());
        cmp.setShortTermDoctoralComponents(changesProposal.getShortTermDoctoralComponents());
        cmp.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
        if (appCmp.getSignature() != null) {
            cmp.setReceivingHeiSignature(appCmp.getSignature());
        }
        cmp.setStudentSignature(changesProposal.getStudentSignature());
        return cmp;
    }

    private CommentProposal updateComponentsStudied(OmobilityLasUpdateRequest request) {
        CommentProposal commentProposal = new CommentProposal();

        commentProposal.setChangesProposalId(request.getCommentProposalV1().getChangesProposalId());
        commentProposal.setComment(request.getCommentProposalV1().getComment());
        commentProposal.setOmobilityId(request.getCommentProposalV1().getOmobilityId());

        Signature signature = new Signature();
        signature.setSignerApp(request.getCommentProposalV1().getSignature().getSignerApp());
        signature.setSignerEmail(request.getCommentProposalV1().getSignature().getSignerEmail());
        signature.setSignerName(request.getCommentProposalV1().getSignature().getSignerName());
        signature.setSignerPosition(request.getCommentProposalV1().getSignature().getSignerPosition());
        if (request.getCommentProposalV1().getSignature().getTimestamp() != null) {
            signature.setTimestamp(request.getCommentProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
        }
        commentProposal.setSignature(signature);

        return commentProposal;
    }
}
