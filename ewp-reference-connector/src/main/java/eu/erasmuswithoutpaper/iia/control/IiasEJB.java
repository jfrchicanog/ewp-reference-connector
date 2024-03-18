package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class IiasEJB {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    IiaConverter iiaConverter;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(IiasEJB.class.getCanonicalName());

    public List<Iia> findAll() {
        return em.createNamedQuery(Iia.findAll).getResultList();
    }

    public Iia findById(String iiaId) {
        return em.find(Iia.class, iiaId);
    }

    public List<Iia> findByIdList(String iiaId) {
        return em.createNamedQuery(Iia.findById).setParameter("id", iiaId).getResultList();
    }

    public List<Iia> findByIiaCode(String iiaCode) {
        return em.createNamedQuery(Iia.findByIiaCode, Iia.class).setParameter("iiaCode", iiaCode).getResultList();
    }

    public void merge(Iia iia) {
        em.merge(iia);
        em.flush();
    }

    public List<Notification> findNotifications() {
        return em.createNamedQuery(Notification.findAll).getResultList();
    }

    public void insertNotification(Notification notification) {
        em.persist(notification);
    }

    public List<IiaApproval> findIiaApprovals() {
        return em.createNamedQuery(IiaApproval.findAll).getResultList();
    }

    public List<MobilityType> findMobilityTypes() {
        return em.createNamedQuery(MobilityType.findAll).getResultList();
    }

    public void insertIia(Iia iiaInternal) throws Exception {
        iiaInternal.setModifyDate(new Date());

        em.persist(iiaInternal);
        em.flush();

        //Update generated iiaId for the partner owner of the iia
        String localHeiId = getHeiId();

        String iiaIdGenerated = iiaInternal.getId();
        for (CooperationCondition condition : iiaInternal.getCooperationConditions()) {
            if (condition.getSendingPartner().getInstitutionId().equals(localHeiId)) {
                condition.getSendingPartner().setIiaId(iiaInternal.getId());
            }

            if (condition.getReceivingPartner().getInstitutionId().equals(localHeiId)) {
                condition.getReceivingPartner().setIiaId(iiaInternal.getId());
            }
        }

        LOG.fine("ADD: CALC HASH");
        try {
            iiaInternal.setConditionsHash(HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(em.find(Iia.class, iiaIdGenerated))).get(0)));
        } catch (Exception e) {
            LOG.fine("ADD: HASH ERROR, Can't calculate sha256 adding new iia");
            LOG.fine(e.getMessage());
            throw e;
        }
        LOG.fine("ADD: AFTER HASH");

        em.merge(iiaInternal);
    }

    public void insertReceivedIia(IiasGetResponse.Iia sendIia, Iia newIia) {
        String localHeiId = getHeiId();
        for (CooperationCondition cc : newIia.getCooperationConditions()) {
            for (IiasGetResponse.Iia.Partner partner : sendIia.getPartner()) {
                if (!partner.getHeiId().equals(localHeiId)) {
                    cc.getSendingPartner().setIiaCode(partner.getIiaCode());
                    cc.getSendingPartner().setIiaId(partner.getIiaId());
                }
            }
        }

        LOG.fine("AuxIiaThread_ADDEDIT: After setting partner id");
        newIia.setHashPartner(sendIia.getIiaHash());

        newIia.setModifyDate(new Date());

        em.persist(newIia);
        em.flush();

        LOG.fine("AuxIiaThread_ADDEDIT: Iia persisted: " + newIia.getId());

        for (CooperationCondition condition : newIia.getCooperationConditions()) {
            if (condition.getSendingPartner().getInstitutionId().equals(localHeiId)) {
                condition.getSendingPartner().setIiaId(newIia.getId());
            }

            if (condition.getReceivingPartner().getInstitutionId().equals(localHeiId)) {
                condition.getReceivingPartner().setIiaId(newIia.getId());
            }
        }

        em.merge(newIia);
        em.flush();

        LOG.fine("AuxIiaThread_ADDEDIT: Calculate hash");
        try {
            newIia.setConditionsHash(HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(em.find(Iia.class, newIia.getId()))).get(0)));
            LOG.fine("AuxIiaThread_ADDEDIT: HASH CALCULATED: " + newIia.getConditionsHash());
        } catch (Exception e) {
            LOG.fine("AuxIiaThread_ADDEDIT: HASH ERROR, Can't calculate sha256 adding new iia");
            LOG.fine(e.getMessage());
        }
        LOG.fine("AuxIiaThread_ADDEDIT: After hash");

        em.merge(newIia);
        em.flush();
    }

    public void updateWithPartnerIDs(Iia localIia, IiasGetResponse.Iia sendIia, String iiaId, String heiId) {
        String otherIiaCode = sendIia.getPartner().stream().filter(p -> p.getHeiId().equals(heiId)).map(IiasGetResponse.Iia.Partner::getIiaCode).findFirst().orElse(null);
        for (CooperationCondition condition : localIia.getCooperationConditions()) {
            if (condition.getSendingPartner().getInstitutionId().equals(heiId)) {
                LOG.fine("AuxIiaThread_ADDEDIT: Partner " + condition.getSendingPartner().getInstitutionId() + " ID set to: " + iiaId);
                condition.getSendingPartner().setIiaId(iiaId);
                condition.getSendingPartner().setIiaCode(otherIiaCode);
            }

            if (condition.getReceivingPartner().getInstitutionId().equals(heiId)) {
                LOG.fine("AuxIiaThread_ADDEDIT: Partner " + condition.getReceivingPartner().getInstitutionId() + " ID set to: " + iiaId);
                condition.getReceivingPartner().setIiaId(iiaId);
                condition.getReceivingPartner().setIiaCode(otherIiaCode);
            }
        }
        LOG.fine("AuxIiaThread_ADDEDIT: Partners hash set to: " + sendIia.getIiaHash());
        localIia.setHashPartner(sendIia.getIiaHash());
        em.merge(localIia);
        em.flush();

        String localHeiId = getHeiId();

        LOG.fine("UPDATE: CALC HASH");
        try {
            localIia.setConditionsHash(HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(em.find(Iia.class, localIia.getId()))).get(0)));
        } catch (Exception e) {
            LOG.fine("UPDATE: HASH ERROR, Can't calculate sha256 updating iia");
            LOG.fine(e.getMessage());
        }

        em.merge(localIia);
        em.flush();
    }

    public void updateIia(Iia iiaInternal, String iiaId, String partnerHash) {
        em.getTransaction().begin();
        Iia foundIia = em.find(Iia.class, iiaId);

        foundIia.setModifyDate(new Date());
        //em.merge(foundIia);

        //foundIia.setConditionsHash(iiaInternal.getConditionsHash());
        foundIia.setInEfect(iiaInternal.isInEfect());
        foundIia.setHashPartner(iiaInternal.getHashPartner());
        foundIia.setIiaCode(iiaInternal.getIiaCode());

        List<CooperationCondition> cooperationConditions = iiaInternal.getCooperationConditions();
        List<CooperationCondition> cooperationConditionsCurrent = foundIia.getCooperationConditions();//cc in database
        for (CooperationCondition cc : cooperationConditions) {
            for (CooperationCondition ccCurrent : cooperationConditionsCurrent) {//cc in database
                if (cc.getSendingPartner().getInstitutionId().equals(ccCurrent.getSendingPartner().getInstitutionId())) {
                    if (cc.getReceivingPartner().getInstitutionId().equals(ccCurrent.getReceivingPartner().getInstitutionId())) {
                        LOG.fine("UPDATE: FOUND COOPERATION CONDITION");
                        ccCurrent.setBlended(cc.isBlended());
                        ccCurrent.setDuration(cc.getDuration()); //
                        ccCurrent.setEndDate(cc.getEndDate());
                        ccCurrent.setEqfLevel(cc.getEqfLevel());
                        ccCurrent.setMobilityNumber(cc.getMobilityNumber()); //
                        ccCurrent.setMobilityType(cc.getMobilityType()); //
                        ccCurrent.setOtherInfoTerms(cc.getOtherInfoTerms());
                        ccCurrent.setReceivingAcademicYearId(cc.getReceivingAcademicYearId());
                        ccCurrent.setRecommendedLanguageSkill(cc.getRecommendedLanguageSkill()); //
                        ccCurrent.setStartDate(cc.getStartDate());
                        ccCurrent.setSubjectAreas(cc.getSubjectAreas()); //

                        IiaPartner sendingPartnerC = ccCurrent.getSendingPartner();//partner in database
                        IiaPartner sendingPartner = cc.getSendingPartner();//updated partner

                        sendingPartnerC.setContacts(sendingPartner.getContacts());
                        sendingPartnerC.setSigningContact(sendingPartner.getSigningContact());
                        sendingPartnerC.setOrganizationUnitId(sendingPartner.getOrganizationUnitId());
                        sendingPartnerC.setIiaCode(sendingPartner.getIiaCode());
                        sendingPartnerC.setIiaId(sendingPartner.getIiaId());

                        IiaPartner receivingPartnerC = ccCurrent.getReceivingPartner();//partner in database
                        IiaPartner receivingPartner = cc.getReceivingPartner();//updated partner

                        receivingPartnerC.setContacts(receivingPartner.getContacts());
                        receivingPartnerC.setSigningContact(receivingPartner.getSigningContact());
                        receivingPartnerC.setOrganizationUnitId(receivingPartner.getOrganizationUnitId());
                        receivingPartnerC.setIiaCode(receivingPartner.getIiaCode());
                        receivingPartnerC.setIiaId(receivingPartner.getIiaId());

                        ccCurrent.setSendingPartner(sendingPartnerC);
                        ccCurrent.setReceivingPartner(receivingPartnerC);
                    }
                }
            }
        }

        foundIia.setCooperationConditions(cooperationConditionsCurrent);

        //em.merge(foundIia);
        //em.flush();

        String localHeiId = getHeiId();

        LOG.fine("UPDATE: CALC HASH");
        try {
            foundIia.setConditionsHash(HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(em.find(Iia.class, foundIia.getId()))).get(0)));
        } catch (Exception e) {
            LOG.fine("UPDATE: HASH ERROR, Can't calculate sha256 updating iia");
            LOG.fine(e.getMessage());
        }

        //em.merge(foundIia);
        //em.flush();

        if (partnerHash != null) {
            LOG.fine("UPDATE *ESPECIAL*: PARTNER HASH SET TO: " + partnerHash);
            foundIia.setHashPartner(partnerHash);
            //em.merge(foundIia);
            //em.flush();
        }
        em.getTransaction().commit();
    }

    public List<Iia> getByPartnerId(String heiId, String partnerId) {
        return em.createNamedQuery(Iia.findByPartnerAndId).setParameter("heiId", heiId).setParameter("iiaId", partnerId).getResultList();
    }

    public void insertIiaApproval(IiaApproval iiaApproval) {
        em.persist(iiaApproval);
        em.flush();

        Iia iia = em.find(Iia.class, iiaApproval.getIia().getId());

        List<String> heiIds = new ArrayList<>();
        if (iia != null) {
            heiIds.addAll(iia.getCooperationConditions().stream().map(c -> c.getSendingPartner().getInstitutionId()).collect(java.util.stream.Collectors.toList()));
            heiIds.addAll(iia.getCooperationConditions().stream().map(c -> c.getReceivingPartner().getInstitutionId()).collect(java.util.stream.Collectors.toList()));

            if (heiIds.stream().allMatch(heiId -> {
                List<IiaApproval> list = findIiaApproval(heiId, iiaApproval.getIia().getId());
                return (list != null && !list.isEmpty());
            })) {
                iia.setInEfect(true);
                em.merge(iia);
                em.flush();
            }
        }
    }

    public List<IiaApproval> findIiaApproval(String heiId, String iiaId) {
        return em.createNamedQuery(IiaApproval.findByIiaIdAndHeiId, IiaApproval.class).setParameter("heiId", heiId).setParameter("iiaId", iiaId).getResultList();
    }


    public List<Institution> findAllInstitutions() {
        return em.createNamedQuery(Institution.findAll).getResultList();
    }

    public String getHeiId() {
        List<Institution> institutions = em.createNamedQuery(Institution.findAll).getResultList();
        return institutions.get(0).getInstitutionId();
    }
}
