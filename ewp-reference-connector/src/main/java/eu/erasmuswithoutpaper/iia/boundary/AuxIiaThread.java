/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.iia.boundary;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTeacherMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTrainingMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentStudiesMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentTraineeshipMobilitySpec;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Duration;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumber;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.iia.entity.SubjectArea;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Moritz Baader
 */
@Stateless
public class AuxIiaThread {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    IiaConverter iiaConverter;

    @Inject
    RestClient restClient;

    private static final Logger LOG = Logger.getLogger(AuxIiaThread.class.getCanonicalName());

    public void run(String heiId, String iiaId) {
        String localHeiId = "";
        List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        localHeiId = institutions.get(0).getInstitutionId();

        LOG.fine("CNRGetFirst: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return;
        }

        LOG.fine("CNRGetFirst: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return;
        }

        LOG.fine("CNRGetFirst: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        LOG.fine("CNRGetFirst: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            //NOTIFY
            return;
        }

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        boolean foundLocalIia = false;
        Iia localIia = null;

        if (responseEnity.getIia() != null && !responseEnity.getIia().isEmpty()) {
            IiasGetResponse.Iia responseEnityIia = responseEnity.getIia().get(0);
            for (IiasGetResponse.Iia.Partner partner : responseEnityIia.getPartner()) {
                if (localHeiId.equals(partner.getHeiId())) {
                    if (partner.getIiaId() != null) {
                        List<Iia> iia = em.createNamedQuery(Iia.findById, Iia.class).setParameter("idPartner", iiaId).getResultList();
                        foundLocalIia = iia != null && !iia.isEmpty();
                        if (foundLocalIia) {
                            localIia = iia.get(0);
                        }
                    }
                }
            }
        }

        LOG.fine("CNRGetFirst: Busqueda en bbdd " + foundLocalIia);
        if (!foundLocalIia) {
            eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia sendIia = responseEnity.getIia().get(0);
            Iia newIia = new Iia();
            convertToIia(sendIia, newIia);

            LOG.fine("CNRGetFirst: Iia convertsed with conditions: " + newIia.getCooperationConditions().size());

            try {

                JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.Iia.CooperationConditions.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                //Create a copy off CooperationConditions to be used in calculateSha256 function
                IiasGetResponse.Iia.CooperationConditions cc = new IiasGetResponse.Iia.CooperationConditions();
                cc.getStaffTeacherMobilitySpec().addAll(sendIia.getCooperationConditions().getStaffTeacherMobilitySpec());
                cc.getStaffTrainingMobilitySpec().addAll(sendIia.getCooperationConditions().getStaffTrainingMobilitySpec());
                cc.getStudentStudiesMobilitySpec().addAll(sendIia.getCooperationConditions().getStudentStudiesMobilitySpec());
                cc.getStudentTraineeshipMobilitySpec().addAll(sendIia.getCooperationConditions().getStudentTraineeshipMobilitySpec());

                cc = iiaConverter.removeContactInfo(cc);

                QName qName = new QName("cooperation_conditions");
                JAXBElement<IiasGetResponse.Iia.CooperationConditions> root = new JAXBElement<IiasGetResponse.Iia.CooperationConditions>(qName, IiasGetResponse.Iia.CooperationConditions.class, cc);

                jaxbMarshaller.marshal(root, sw);
                String xmlString = sw.toString();

                String calculatedHash = HashCalculationUtility.calculateSha256(xmlString);

                newIia.setConditionsHash(calculatedHash);
            } catch (InvalidCanonicalizerException | CanonicalizationException | NoSuchAlgorithmException | SAXException
                    | IOException | ParserConfigurationException | TransformerException | JAXBException e) {
            }
            for (CooperationCondition cc : newIia.getCooperationConditions()) {
                for (IiasGetResponse.Iia.Partner partner : sendIia.getPartner()) {
                    if (partner.getHeiId().equals(cc.getSendingPartner().getInstitutionId())) {
                        cc.getSendingPartner().setIiaCode(partner.getIiaCode());
                        cc.getSendingPartner().setIiaId(partner.getIiaId());
                    }
                }

            }
            LOG.fine("CNRGetFirst: After setting partner id");
            newIia.setHashPartner(sendIia.getConditionsHash());

            LOG.fine("CNRGetFirst: Iia hash calculated: " + newIia.getConditionsHash());

            em.persist(newIia);
            em.flush();

            LOG.fine("CNRGetFirst: Iia persisted: " + newIia.getId());

            for (CooperationCondition cc : newIia.getCooperationConditions()) {
                cc.getReceivingPartner().setIiaId(newIia.getId());
            }

            em.merge(newIia);
            em.flush();

            LOG.fine("CNRGetFirst: After seting id");

            map = registryClient.getIiaCnrHeiUrls(heiId);

            if (map == null) {
                return;
            }

            LOG.fine("CNRGetFirst: MAP 2 ENCONTRADO");

            url = (new ArrayList<>(map.values())).get(0);
            if (url == null) {
                return;
            }

            LOG.fine("CNRGetFirst: CNR URL: " + url);

            ClientRequest cnrRequest = new ClientRequest();
            cnrRequest.setUrl(url);
            cnrRequest.setHeiId(heiId);
            cnrRequest.setMethod(HttpMethodEnum.POST);
            cnrRequest.setHttpsec(true);

            Map<String, List<String>> paramsMapCNR = new HashMap<>();
            paramsMapCNR.put("notifier_hei_id", Arrays.asList(localHeiId));
            paramsMapCNR.put("iia_id", Arrays.asList(newIia.getId()));
            ParamsClass paramsClassCNR = new ParamsClass();
            paramsClassCNR.setUnknownFields(paramsMapCNR);
            cnrRequest.setParams(paramsClassCNR);

            ClientResponse cnrResponse = restClient.sendRequest(cnrRequest, Empty.class);

            LOG.fine("CNRGetFirst: After CNR with code: " + cnrResponse.getStatusCode());

        } else {
            LOG.fine("CNRGetFirst: Found existing iia");
            localIia.getCooperationConditions().forEach(cc -> {
                LOG.fine("CNRGetFirst: Sending HeiId" + cc.getSendingPartner().getInstitutionId());
                LOG.fine("CNRGetFirst: Reciving HeiId" + cc.getReceivingPartner().getInstitutionId());
            });
            /*if (localIia.getCooperationConditions().stream().allMatch((cc) -> cc.get)) {

            }*/
        }

    }

    private void convertToIia(IiasGetResponse.Iia iia, Iia iiaInternal) {

        if (iia.getConditionsHash() != null) {
            iiaInternal.setConditionsHash(iia.getConditionsHash());
        }

        iiaInternal.setInEfect(iia.isInEffect());

        List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());

        List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();
        iia.getPartner().stream().forEach((IiasGetResponse.Iia.Partner partner) -> {

            IiaPartner partnerInternal = new IiaPartner();

            for (Institution institution : institutions) {
                if (institution.getInstitutionId().equals(partner.getHeiId())) {
                    System.out.println("Found Parter:" + partner.getHeiId());
                    System.out.println("IiaCode:" + iiaInternal.getIiaCode());
                    System.out.println("IiaCode Parter:" + partner.getIiaCode());

                    //iiaInternal.setEndDate(null);
                    //iiaInternal.setStartDate(null);
                    iiaInternal.setIiaCode(partner.getIiaCode());
                    iiaInternal.setId(partner.getIiaId());

                    if (partner.getSigningDate() != null) {
                        iiaInternal.setSigningDate(partner.getSigningDate().toGregorianCalendar().getTime());
                    }

                    break;
                }
            }

            if (partner.getIiaCode() != null) {
                partnerInternal.setIiaCode(partner.getIiaCode());
            }

            if (partner.getIiaId() != null) {
                partnerInternal.setIiaId(partner.getIiaId());
            }

            partnerInternal.setInstitutionId(partner.getHeiId());

            if (partner.getOunitId() != null) {
                partnerInternal.setOrganizationUnitId(partner.getOunitId());
            }

            if (partner.getSigningContact() != null) {
                Contact signingContact = partner.getSigningContact();

                eu.erasmuswithoutpaper.organization.entity.Contact signingContactInternal = convertToContact(signingContact);
                partnerInternal.setSigningContact(signingContactInternal);
            }

            if (partner.getContact() != null) {
                List<Contact> contacts = partner.getContact();

                List<eu.erasmuswithoutpaper.organization.entity.Contact> internalContacts = new ArrayList<>();
                for (Contact contact : contacts) {
                    eu.erasmuswithoutpaper.organization.entity.Contact internalContact = convertToContact(contact);

                    internalContacts.add(internalContact);
                }

                partnerInternal.setContacts(internalContacts);
            }

            for (CooperationCondition cooperationConditionInternal : iiaIternalCooperationConditions) {

                if (cooperationConditionInternal.getReceivingPartner().getInstitutionId().equals(partner.getHeiId())) {

                    cooperationConditionInternal.setReceivingPartner(partnerInternal);

                } else if (cooperationConditionInternal.getSendingPartner().getInstitutionId().equals(partner.getHeiId())) {

                    cooperationConditionInternal.setSendingPartner(partnerInternal);

                }
            }

        });

        iiaInternal.setCooperationConditions(iiaIternalCooperationConditions);
    }

    private eu.erasmuswithoutpaper.organization.entity.Contact convertToContact(Contact pContact) {
        eu.erasmuswithoutpaper.organization.entity.Contact internalContact = new eu.erasmuswithoutpaper.organization.entity.Contact();

//		List<StringWithOptionalLang> contactNames = pContact.getContactName();
//		for (StringWithOptionalLang stringWithOptionalLang : contactNames) {
//			
//		}
        //internalContact.set
        Person personInternal = new Person();
        personInternal.setGender(Gender.getById(pContact.getPersonGender()));
        internalContact.setPerson(personInternal);

        ContactDetails contactDetails = new ContactDetails();

        FlexibleAddress flexibleAddressInternal = convertFlexibleAddress(pContact.getMailingAddress());
        FlexibleAddress streetAddressInternal = convertFlexibleAddress(pContact.getStreetAddress());

        contactDetails.setMailingAddress(flexibleAddressInternal);
        contactDetails.setStreetAddress(streetAddressInternal);

        internalContact.setContactDetails(contactDetails);

        return internalContact;
    }

    private FlexibleAddress convertFlexibleAddress(eu.erasmuswithoutpaper.api.types.address.FlexibleAddress flexible) {
        FlexibleAddress flexibleAddressInternal = new FlexibleAddress();

        flexibleAddressInternal.setBuildingName(flexible.getBuildingName());
        flexibleAddressInternal.setBuildingNumber(flexible.getBuildingNumber());
        flexibleAddressInternal.setCountry(flexible.getCountry());
        flexibleAddressInternal.setFloor(flexible.getFloor());
        flexibleAddressInternal.setLocality(flexible.getLocality());
        flexibleAddressInternal.setPostalCode(flexible.getPostalCode());
        flexibleAddressInternal.setPostOfficeBox(flexible.getPostOfficeBox());
        flexibleAddressInternal.setRegion(flexible.getRegion());
        flexibleAddressInternal.setStreetName(flexible.getStreetName());
        flexibleAddressInternal.setUnit(flexible.getUnit());
        Optional.ofNullable(flexible.getAddressLine()).ifPresent(flexibleAddressInternal.getAddressLine()::addAll);
        Optional.ofNullable(flexible.getRecipientName()).ifPresent(flexibleAddressInternal.getRecipientName()::addAll);

        List<String> codes = flexible.getDeliveryPointCode().stream().map(obj -> {
            String deliveryCode = String.valueOf(obj);
            return deliveryCode;
        }).collect(Collectors.toList());
        flexibleAddressInternal.setDeliveryPointCode(new ArrayList<>());
        flexibleAddressInternal.getDeliveryPointCode().addAll(codes);

        return flexibleAddressInternal;
    }

    private List<CooperationCondition> getCooperationConditions(IiasGetResponse.Iia.CooperationConditions cooperationConditions) {
        List<CooperationCondition> cooperationConditionsInternals = new ArrayList<>();

        List<StaffTeacherMobilitySpec> staffTeacherMobs = cooperationConditions.getStaffTeacherMobilitySpec();
        if (staffTeacherMobs != null) {
            MobilityType mobType = new MobilityType();
            mobType.setMobilityGroup("Staff");
            mobType.setMobilityCategory("Teaching");

            List<CooperationCondition> ccList = staffTeacherMobs.stream().map(staffTeacher -> {
                CooperationCondition cc = convertFromStaffToCooperationCondition(mobType, staffTeacher);

                return cc;
            }).collect(Collectors.toList());

            cooperationConditionsInternals.addAll(ccList);
        }

        List<StaffTrainingMobilitySpec> stafftrainingMobs = cooperationConditions.getStaffTrainingMobilitySpec();
        if (stafftrainingMobs != null) {
            MobilityType mobType = new MobilityType();
            mobType.setMobilityGroup("Staff");
            mobType.setMobilityCategory("Training");

            List<CooperationCondition> ccList = stafftrainingMobs.stream().map(stafftraining -> {
                CooperationCondition cc = convertFromStaffToCooperationCondition(mobType, stafftraining);

                return cc;
            }).collect(Collectors.toList());

            cooperationConditionsInternals.addAll(ccList);
        }

        List<StudentStudiesMobilitySpec> studentStudiesMobs = cooperationConditions.getStudentStudiesMobilitySpec();
        if (studentStudiesMobs != null) {
            MobilityType mobType = new MobilityType();
            mobType.setMobilityGroup("Student");
            mobType.setMobilityCategory("Studies");

            List<CooperationCondition> ccList = studentStudiesMobs.stream().map(studentStudies -> {
                CooperationCondition cc = convertFromStudentToCooperationCondition(mobType, studentStudies);

                convertFromMobilitySpecification(studentStudies, cc);

                return cc;
            }).collect(Collectors.toList());

            cooperationConditionsInternals.addAll(ccList);
        }

        List<StudentTraineeshipMobilitySpec> studentTraineeshipMobs = cooperationConditions.getStudentTraineeshipMobilitySpec();
        if (studentTraineeshipMobs != null) {
            MobilityType mobType = new MobilityType();
            mobType.setMobilityGroup("Student");
            mobType.setMobilityCategory("Training");

            List<CooperationCondition> ccList = studentTraineeshipMobs.stream().map(studentTraineeship -> {
                CooperationCondition cc = convertFromStudentToCooperationCondition(mobType, studentTraineeship);

                convertFromMobilitySpecification(studentTraineeship, cc);

                return cc;
            }).collect(Collectors.toList());

            cooperationConditionsInternals.addAll(ccList);
        }

        return cooperationConditionsInternals;
    }

    private CooperationCondition convertFromStudentToCooperationCondition(MobilityType mobType,
            StudentMobilitySpecification studentStudies) {
        CooperationCondition cc = new CooperationCondition();

        cc.setMobilityType(mobType);

        Duration duration = new Duration();
        duration.setNumber(studentStudies.getTotalMonthsPerYear());
        cc.setDuration(duration);

        if (studentStudies.getEqfLevel() != null) {
            List<Byte> eqfLevels = studentStudies.getEqfLevel();
            byte[] arrEqfLevel = new byte[eqfLevels.size()];
            for (int i = 0; i < eqfLevels.size(); i++) {
                arrEqfLevel[i] = eqfLevels.get(i).byteValue();
            }
            cc.setEqfLevel(arrEqfLevel);
        }

        cc.setBlended(studentStudies.isBlended());
        return cc;
    }

    private CooperationCondition convertFromStaffToCooperationCondition(MobilityType mobType,
            StaffMobilitySpecification staffTeacher) {
        CooperationCondition cc = new CooperationCondition();

        cc.setMobilityType(mobType);

        Duration duration = new Duration();
        duration.setNumber(staffTeacher.getTotalDaysPerYear());
        cc.setDuration(duration);

        convertFromMobilitySpecification(staffTeacher, cc);
        return cc;
    }

    private void convertFromMobilitySpecification(MobilitySpecification mobilitySpec, CooperationCondition cc) {
        List<eu.erasmuswithoutpaper.iia.entity.LanguageSkill> langskills = new ArrayList<>();

        if (mobilitySpec.getRecommendedLanguageSkill() != null) {
            List<MobilitySpecification.RecommendedLanguageSkill> recommendedSkills = mobilitySpec.getRecommendedLanguageSkill();
            for (MobilitySpecification.RecommendedLanguageSkill recommendedSkill : recommendedSkills) {
                eu.erasmuswithoutpaper.iia.entity.LanguageSkill langskill = new eu.erasmuswithoutpaper.iia.entity.LanguageSkill();

                if (recommendedSkill.getCefrLevel() != null) {
                    langskill.setCefrLevel(recommendedSkill.getCefrLevel());
                }

                langskill.setLanguage(recommendedSkill.getLanguage());

                if (recommendedSkill.getSubjectArea() != null) {
                    SubjectArea subjectArea = new SubjectArea();
                    subjectArea.setIscedClarification(recommendedSkill.getSubjectArea().getIscedClarification());
                    subjectArea.setIscedFCode(recommendedSkill.getSubjectArea().getIscedFCode());
                    langskill.setSubjectArea(subjectArea);
                }

                langskills.add(langskill);
            }
            if (cc.getRecommendedLanguageSkill() == null) {
                cc.setRecommendedLanguageSkill(new ArrayList<>());
            }

            cc.getRecommendedLanguageSkill().addAll(langskills);
        }

        if (cc.getReceivingAcademicYearId() == null) {
            cc.setReceivingAcademicYearId(new ArrayList<>());
        }
        cc.getReceivingAcademicYearId().addAll(mobilitySpec.getReceivingAcademicYearId());

        //Require academic years to be ordered and without gaps
        cc.getReceivingAcademicYearId().sort((c1, c2) -> {
            return c1.compareTo(c2);
        });

        if (mobilitySpec.getOtherInfoTerms() != null) {
            cc.setOtherInfoTerms(mobilitySpec.getOtherInfoTerms());
        }

        IiaPartner receivingPartnerInternal = new IiaPartner();
        if (mobilitySpec.getReceivingHeiId() != null) {
            receivingPartnerInternal.setInstitutionId(mobilitySpec.getReceivingHeiId());
        }

        if (mobilitySpec.getReceivingOunitId() != null) {
            receivingPartnerInternal.setOrganizationUnitId(mobilitySpec.getReceivingOunitId());
        }
        cc.setReceivingPartner(receivingPartnerInternal);

        IiaPartner sendingPartnerInternal = new IiaPartner();
        if (mobilitySpec.getSendingOunitId() != null) {
            sendingPartnerInternal.setOrganizationUnitId(mobilitySpec.getSendingOunitId());
        }

        if (mobilitySpec.getSendingHeiId() != null) {
            sendingPartnerInternal.setInstitutionId(mobilitySpec.getSendingHeiId());
        }
        cc.setSendingPartner(sendingPartnerInternal);

        if (mobilitySpec.getMobilitiesPerYear() != null) {
            MobilityNumber mobNumber = new MobilityNumber();
            mobNumber.setNumber(mobilitySpec.getMobilitiesPerYear().intValue());
            cc.setMobilityNumber(mobNumber);
        }

        List<SubjectArea> subjectAreasInt = new ArrayList<>();
        List<eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea> subjectAreas = mobilitySpec.getSubjectArea();
        for (eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea subjectArea : subjectAreas) {
            SubjectArea subjectAreaInt = new SubjectArea();

            if (subjectArea.getIscedClarification() != null) {
                subjectAreaInt.setIscedClarification(subjectArea.getIscedClarification());
            }
            subjectAreaInt.setIscedFCode(subjectArea.getIscedFCode());

            subjectAreasInt.add(subjectAreaInt);
        }
        cc.setSubjectAreas(subjectAreasInt);

        if (mobilitySpec.getReceivingContact() != null) {
            List<Contact> receivingContacts = mobilitySpec.getReceivingContact();
            List<eu.erasmuswithoutpaper.organization.entity.Contact> contactsReceivigInternal = new ArrayList<>();
            for (Contact contact : receivingContacts) {
                eu.erasmuswithoutpaper.organization.entity.Contact contactRec = convertToContactDetails(contact);

                contactsReceivigInternal.add(contactRec);
            }

            if (cc.getReceivingPartner().getContacts() == null) {
                cc.getReceivingPartner().setContacts(new ArrayList<>());
            }

            cc.getReceivingPartner().getContacts().addAll(contactsReceivigInternal);
        }

        if (mobilitySpec.getSendingContact() != null) {
            List<Contact> sendingContacts = mobilitySpec.getSendingContact();
            List<eu.erasmuswithoutpaper.organization.entity.Contact> contactsSendinginInternal = new ArrayList<>();
            for (Contact contact : sendingContacts) {
                eu.erasmuswithoutpaper.organization.entity.Contact contactRec = convertToContactDetails(contact);

                contactsSendinginInternal.add(contactRec);
            }

            if (cc.getSendingPartner().getContacts() == null) {
                cc.getSendingPartner().setContacts(new ArrayList<>());
            }
            cc.getSendingPartner().getContacts().addAll(contactsSendinginInternal);
        }

    }

    private eu.erasmuswithoutpaper.organization.entity.Contact convertToContactDetails(Contact contact) {
        eu.erasmuswithoutpaper.organization.entity.Contact contactRec = new eu.erasmuswithoutpaper.organization.entity.Contact();

        FlexibleAddress address = convertFlexibleAddress(contact.getMailingAddress());
        FlexibleAddress streetAdd = convertFlexibleAddress(contact.getMailingAddress());

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setMailingAddress(address);
        contactDetails.setStreetAddress(streetAdd);

        contactRec.setContactDetails(contactDetails);

        Person person = new Person();
        person.setGender(Gender.getById(contact.getPersonGender()));
        return contactRec;
    }
}
