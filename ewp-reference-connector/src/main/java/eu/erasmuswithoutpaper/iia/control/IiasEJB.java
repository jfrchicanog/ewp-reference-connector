package eu.erasmuswithoutpaper.iia.control;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.omobility.las.boundary.TestEndpointsOLAS;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import org.xml.sax.SAXException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    public void updateIia(Iia iiaInternal, Iia foundIia) {
        em.merge(foundIia);

        //localIia.setConditionsHash(iiaInternal.getConditionsHash());
        foundIia.setInEfect(iiaInternal.isInEfect());
        foundIia.setHashPartner(iiaInternal.getHashPartner());
        //localIia.setIiaCode(iiaInternal.getIiaCode());

        List<CooperationCondition> cooperationConditions = iiaInternal.getCooperationConditions();
        List<CooperationCondition> cooperationConditionsCurrent = foundIia.getCooperationConditions();//cc in database
        for (CooperationCondition cc : cooperationConditions) {
            for (CooperationCondition ccCurrent : cooperationConditionsCurrent) {//cc in database
                if (cc.getSendingPartner().getInstitutionId().equals(ccCurrent.getSendingPartner().getInstitutionId())) {
                    if (cc.getReceivingPartner().getInstitutionId().equals(ccCurrent.getReceivingPartner().getInstitutionId())) {
                        ccCurrent.setBlended(cc.isBlended());
                        ccCurrent.setDuration(cc.getDuration());
                        ccCurrent.setEndDate(cc.getEndDate());
                        ccCurrent.setEqfLevel(cc.getEqfLevel());
                        ccCurrent.setMobilityNumber(cc.getMobilityNumber());
                        ccCurrent.setMobilityType(cc.getMobilityType());
                        ccCurrent.setOtherInfoTerms(cc.getOtherInfoTerms());
                        ccCurrent.setReceivingAcademicYearId(cc.getReceivingAcademicYearId());
                        ccCurrent.setRecommendedLanguageSkill(cc.getRecommendedLanguageSkill());
                        ccCurrent.setStartDate(cc.getStartDate());
                        ccCurrent.setSubjectAreas(cc.getSubjectAreas());

                        IiaPartner sendingPartnerC = ccCurrent.getSendingPartner();//partner in database
                        IiaPartner sendingPartner = cc.getSendingPartner();//updated partner

                        sendingPartnerC.setContacts(sendingPartner.getContacts());
                        sendingPartnerC.setSigningContact(sendingPartner.getSigningContact());
                        sendingPartnerC.setOrganizationUnitId(sendingPartner.getOrganizationUnitId());
                        sendingPartnerC.setIiaCode(sendingPartner.getIiaCode());

                        IiaPartner receivingPartnerC = ccCurrent.getReceivingPartner();//partner in database
                        IiaPartner receivingPartner = cc.getReceivingPartner();//updated partner

                        receivingPartnerC.setContacts(receivingPartner.getContacts());
                        receivingPartnerC.setSigningContact(receivingPartner.getSigningContact());
                        receivingPartnerC.setOrganizationUnitId(receivingPartner.getOrganizationUnitId());

                        ccCurrent.setSendingPartner(sendingPartnerC);
                        ccCurrent.setReceivingPartner(receivingPartnerC);
                    }
                }
            }
        }

        foundIia.setCooperationConditions(cooperationConditionsCurrent);

        em.merge(foundIia);
        em.flush();

        String localHeiId = getHeiId();

        LOG.fine("UPDATE: CALC HASH");
        try {
            foundIia.setConditionsHash(HashCalculationUtility.calculateSha256(iiaConverter.convertToIias(localHeiId, Arrays.asList(em.find(Iia.class, foundIia.getId()))).get(0)));
        } catch (Exception e) {
            LOG.fine("UPDATE: HASH ERROR, Can't calculate sha256 updating iia");
            LOG.fine(e.getMessage());
        }

        em.merge(foundIia);
        em.flush();
    }


    public List<Institution> findAllInstitutions() {
        return em.createNamedQuery(Institution.findAll).getResultList();
    }

    public String getHeiId() {
        List<Institution> institutions = em.createNamedQuery(Institution.findAll).getResultList();
        return institutions.get(0).getInstitutionId();
    }
}