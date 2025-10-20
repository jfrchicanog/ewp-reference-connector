package eu.erasmuswithoutpaper.omobility.las.control;

import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.imobility.entity.IMobility;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LearningAgreementEJB {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(LearningAgreementEJB.class.getCanonicalName());

    public String insert(OlearningAgreement olearningAgreement) {
        em.persist(olearningAgreement);
        em.flush();
        return olearningAgreement.getId();
    }

    public void merge(OlearningAgreement olearningAgreement) {
        em.merge(olearningAgreement);
        em.flush();
    }

    public String update(OlearningAgreement olearningAgreement) {
        if (olearningAgreement.getChangesProposal() != null && olearningAgreement.getChangesProposal().getId() == null) {
            em.persist(olearningAgreement.getChangesProposal());
            em.flush();
        }
        em.merge(olearningAgreement);
        em.flush();
        return olearningAgreement.getId();
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

    public OlearningAgreement findBySendingHeiIdAndOmobilityId(String sendingHeiId, String omobilityId) {
        List<OlearningAgreement> result = em.createNamedQuery(OlearningAgreement.findBySendingHeiIdAndOmobilityId, OlearningAgreement.class)
                .setParameter("sendingHei", sendingHeiId)
                .setParameter("omobilityId", omobilityId)
                .getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
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

    public ChangesProposal findByIdChangeProposal(String id) {
        return em.createNamedQuery(ChangesProposal.findByIdChangeProposal, ChangesProposal.class).setParameter("id_changeProposal", id).getSingleResult();
    }

    public void approveChangesProposal(OmobilityLasUpdateRequest request, String id) {
        ApprovedProposal appCmp = converter.approveCmpStudiedDraft(request);
        em.persist(appCmp);
        em.flush();

        OlearningAgreement omobility = findById(id);
        ChangesProposal changesProposal = omobility.getChangesProposal();

        omobility.setChangesProposal(null);

        em.remove(changesProposal);
        em.merge(omobility);
        em.flush();

        if (omobility.getFirstVersion() != null) {

            if (omobility.getApprovedChanges() == null) {
                ListOfComponents cmp = converter.getListOfComponents(changesProposal, appCmp);

                em.persist(cmp);
                omobility.setApprovedChanges(cmp);

            } else {
                ListOfComponents cmpBD = omobility.getApprovedChanges();
                ListOfComponents cmp = converter.getListOfComponents(changesProposal, appCmp);

                if ((cmpBD.getComponentsStudied() == null || cmpBD.getComponentsStudied().isEmpty()) && (cmp.getComponentsStudied() != null && !cmp.getComponentsStudied().isEmpty())) {
                    cmpBD.setComponentsStudied(cmp.getComponentsStudied());
                } else if (cmp.getComponentsStudied() != null && !cmp.getComponentsStudied().isEmpty()) {
                    cmpBD.getComponentsStudied().addAll(cmp.getComponentsStudied());
                }

                if ((cmpBD.getComponentsRecognized() == null || cmpBD.getComponentsRecognized().isEmpty()) && (cmp.getComponentsRecognized() != null && !cmp.getComponentsRecognized().isEmpty())) {
                    cmpBD.setComponentsRecognized(cmp.getComponentsRecognized());
                } else if (cmp.getComponentsRecognized() != null && !cmp.getComponentsRecognized().isEmpty()) {
                    cmpBD.getComponentsRecognized().addAll(cmp.getComponentsRecognized());
                }

                if ((cmpBD.getBlendedMobilityComponents() == null || cmpBD.getBlendedMobilityComponents().isEmpty()) && (cmp.getBlendedMobilityComponents() != null && !cmp.getBlendedMobilityComponents().isEmpty())) {
                    cmpBD.setBlendedMobilityComponents(cmp.getBlendedMobilityComponents());
                } else if (cmp.getBlendedMobilityComponents() != null && !cmp.getBlendedMobilityComponents().isEmpty()) {
                    cmpBD.getBlendedMobilityComponents().addAll(cmp.getBlendedMobilityComponents());
                }

                if ((cmpBD.getVirtualComponents() == null || cmpBD.getVirtualComponents().isEmpty()) && (cmp.getVirtualComponents() != null && !cmp.getVirtualComponents().isEmpty())) {
                    cmpBD.setVirtualComponents(cmp.getVirtualComponents());
                } else if (cmp.getVirtualComponents() != null && !cmp.getVirtualComponents().isEmpty()) {
                    cmpBD.getVirtualComponents().addAll(cmp.getVirtualComponents());
                }

                if ((cmpBD.getShortTermDoctoralComponents() == null || cmpBD.getShortTermDoctoralComponents().isEmpty()) && (cmp.getShortTermDoctoralComponents() != null && !cmp.getShortTermDoctoralComponents().isEmpty())) {
                    cmpBD.setShortTermDoctoralComponents(cmp.getShortTermDoctoralComponents());
                } else if (cmp.getShortTermDoctoralComponents() != null && !cmp.getShortTermDoctoralComponents().isEmpty()) {
                    cmpBD.getShortTermDoctoralComponents().addAll(cmp.getShortTermDoctoralComponents());
                }

                cmpBD.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
                cmpBD.setReceivingHeiSignature(cmp.getReceivingHeiSignature());
                cmpBD.setStudentSignature(changesProposal.getStudentSignature());

                em.merge(cmpBD);
                em.flush();
            }

        } else {
            ListOfComponents cmp = converter.getListOfComponents(changesProposal, appCmp);

            em.persist(cmp);

            omobility.setFirstVersion(cmp);
        }

        em.merge(omobility);
        em.flush();
    }

    public void rejectChangesProposal(OmobilityLasUpdateRequest request, String id) {
        CommentProposal rejectCmpStudiedDraft = converter.rejectCmpStudiedDraft(request);
        em.persist(rejectCmpStudiedDraft);
        em.flush();

        OlearningAgreement omobility = findById(id);
        ChangesProposal changesProposal = omobility.getChangesProposal();

        if (changesProposal.getOlearningAgreement().getFirstVersion() == null) {
            em.remove(omobility);
        } else {
            em.remove(changesProposal);

            omobility.setChangesProposal(null);

            em.merge(omobility);
            em.flush();
        }
    }

}
