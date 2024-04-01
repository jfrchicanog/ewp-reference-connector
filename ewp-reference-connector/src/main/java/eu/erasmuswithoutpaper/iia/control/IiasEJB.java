package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.entity.*;
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

    public List<Iia> findByPartnerId(String id) {
        return em.createNamedQuery(Iia.findByPartnerId).setParameter("iiaId", id).getResultList();
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

    public String deletCC(Iia iiaInternal, Iia foundIia, String partnerHash) {
        foundIia.setModifyDate(new Date());
        //em.merge(foundIia);

        foundIia.setInEfect(iiaInternal.isInEfect());
        foundIia.setHashPartner(iiaInternal.getHashPartner());
        foundIia.setIiaCode(iiaInternal.getIiaCode());

        foundIia.setCooperationConditions(new ArrayList<>());
        em.merge(foundIia);
        em.flush();

        if(foundIia.getCooperationConditions() != null) {
            for (CooperationCondition cc : foundIia.getCooperationConditions()) {
                CooperationCondition ccFound = em.find(CooperationCondition.class, cc.getId());
                em.remove(ccFound);
                em.flush();
            }
        }
        return "";
    }

    public String updateIia(Iia iiaInternal, Iia foundIia, String partnerHash) {
        foundIia.setModifyDate(new Date());
        //em.merge(foundIia);

        foundIia.setInEfect(iiaInternal.isInEfect());
        foundIia.setHashPartner(iiaInternal.getHashPartner());
        foundIia.setIiaCode(iiaInternal.getIiaCode());

        foundIia.setCooperationConditions(new ArrayList<>());
        em.merge(foundIia);
        em.flush();

        if(foundIia.getCooperationConditions() != null) {
            for (CooperationCondition cc : foundIia.getCooperationConditions()) {
                CooperationCondition ccFound = em.find(CooperationCondition.class, cc.getId());
                em.remove(ccFound);
                em.flush();
            }
        }

        if (iiaInternal.getCooperationConditions() != null) {
            for (CooperationCondition cc : iiaInternal.getCooperationConditions()) {
                em.persist(cc);
                em.flush();
                foundIia.getCooperationConditions().add(cc);
            }
        }


        /*List<CooperationCondition> cooperationConditions = iiaInternal.getCooperationConditions();
        //List<CooperationCondition> cooperationConditionsCurrent = foundIia.getCooperationConditions();//cc in database
        for (CooperationCondition cc : cooperationConditions) {
            String ccId = foundIia.getCooperationConditions().stream().filter(c -> c.getSendingPartner().getInstitutionId().equals(cc.getSendingPartner().getInstitutionId()) && c.getReceivingPartner().getInstitutionId().equals(cc.getReceivingPartner().getInstitutionId())).map(CooperationCondition::getId).findFirst().orElse(null);
            //for (CooperationCondition ccCurrentB : cooperationConditionsCurrent) {//cc in database
                //if (cc.getSendingPartner().getInstitutionId().equals(ccCurrentB.getSendingPartner().getInstitutionId())) {
                    //if (cc.getReceivingPartner().getInstitutionId().equals(ccCurrentB.getReceivingPartner().getInstitutionId())) {
                        //String ccId = ccCurrentB.getId();
                        CooperationCondition ccCurrent = em.find(CooperationCondition.class, ccId);
                        ccCurrent.setBlended(cc.isBlended());
                        if(cc.getDuration() != null) {
                            if (ccCurrent.getDuration() != null) {
                                Duration duration = em.find(Duration.class, ccCurrent.getDuration().getId());
                                duration.setUnit(cc.getDuration().getUnit());
                                duration.setUnit(cc.getDuration().getUnit());

                                ccCurrent.setDuration(duration);
                            }else {
                                em.persist(cc.getDuration());
                                em.flush();

                                ccCurrent.setDuration(cc.getDuration());
                            }
                        }else {
                            ccCurrent.setDuration(null);
                        }
                        ccCurrent.setEndDate(cc.getEndDate());
                        ccCurrent.setEqfLevel(cc.getEqfLevel());
                        if (cc.getMobilityNumber() != null) {
                            if (ccCurrent.getMobilityNumber() != null) {
                                MobilityNumber mobilityNumber = em.find(MobilityNumber.class, ccCurrent.getMobilityNumber().getId());
                                mobilityNumber.setNumber(cc.getMobilityNumber().getNumber());
                                mobilityNumber.setVariant(cc.getMobilityNumber().getVariant());

                                ccCurrent.setMobilityNumber(mobilityNumber);
                            } else {
                                em.persist(cc.getMobilityNumber());
                                em.flush();

                                ccCurrent.setMobilityNumber(cc.getMobilityNumber());
                            }
                        } else {
                            ccCurrent.setMobilityNumber(null);
                        }
                        if(cc.getMobilityType() != null) {
                            if (ccCurrent.getMobilityType() != null) {
                                MobilityType mobilityType = em.find(MobilityType.class, ccCurrent.getMobilityType().getId());
                                mobilityType.setMobilityCategory(cc.getMobilityType().getMobilityCategory());
                                mobilityType.setMobilityGroup(cc.getMobilityType().getMobilityGroup());

                                ccCurrent.setMobilityType(mobilityType);
                            } else {
                                em.persist(cc.getMobilityType());
                                em.flush();

                                ccCurrent.setMobilityType(cc.getMobilityType());
                            }
                        } else {
                            ccCurrent.setMobilityType(null);
                        }
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
                        em.merge(ccCurrent);
                    //}
                //}
            //}
        }*/

        //foundIia.setCooperationConditions(cooperationConditionsCurrent);

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

        em.merge(foundIia);
        em.flush();

        return foundIia.getConditionsHash();
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

    public void deleteAssociatedIiaApprovals(String iiaId) {
        List<IiaApproval> iiaApprovals = em.createNamedQuery(IiaApproval.findByIiaId, IiaApproval.class).setParameter("iiaId", iiaId).getResultList();
        for (IiaApproval iiaApproval : iiaApprovals) {
            em.remove(iiaApproval);
        }
    }


    public List<Institution> findAllInstitutions() {
        return em.createNamedQuery(Institution.findAll).getResultList();
    }

    public String getHeiId() {
        List<Institution> institutions = em.createNamedQuery(Institution.findAll).getResultList();
        return institutions.get(0).getInstitutionId();
    }
}
