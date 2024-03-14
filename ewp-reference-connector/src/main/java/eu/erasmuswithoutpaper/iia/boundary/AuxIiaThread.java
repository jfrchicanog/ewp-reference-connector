/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.iia.boundary;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.RecommendedLanguageSkill;
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
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
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
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * @author Moritz Baader
 */
public class AuxIiaThread {

    @EJB
    IiasEJB iiasEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    private static final Logger LOG = Logger.getLogger(AuxIiaThread.class.getCanonicalName());

    public void addEditIia(String heiId, String iiaId) throws InterruptedException {
        String localHeiId = iiasEJB.getHeiId();

        LOG.fine("AuxIiaThread_ADDEDIT: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Url encontrada: " + url);

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

        LOG.fine("AuxIiaThread_ADDEDIT: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        LOG.fine("AuxIiaThread_ADDEDIT: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            //TODO: Handle error, notify monitoring
            return;
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Respuesta raw: " + clientResponse.getRawResponse());

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        Iia localIia = null;

        IiasGetResponse.Iia sendIia = responseEnity.getIia().get(0);

        LOG.fine("AuxIiaThread_ADDEDIT: SendIia found HEIID: " + sendIia.getPartner().stream().map(p -> (p.getHeiId() == null ? "" : p.getHeiId())).collect(Collectors.toList()));
        LOG.fine("AuxIiaThread_ADDEDIT: SendIia found IIAID: " + sendIia.getPartner().stream().map(p -> (p.getIiaId() == null ? "" : p.getIiaId())).collect(Collectors.toList()));

        for (IiasGetResponse.Iia.Partner partner : sendIia.getPartner()) {
            LOG.fine("AuxIiaThread_ADDEDIT: Partner heiID: " + partner.getHeiId());
            LOG.fine("AuxIiaThread_ADDEDIT: Partner ID: " + partner.getIiaId());
            if (localHeiId.equals(partner.getHeiId())) {
                LOG.fine("AuxIiaThread_ADDEDIT: Own localeId found: " + (partner.getIiaId() == null ? "" : partner.getIiaId()));
                if (partner.getIiaId() != null) {
                    List<Iia> iia = iiasEJB.findByIdList(partner.getIiaId());
                    LOG.fine("AuxIiaThread_ADDEDIT: Find local iias: " + (iia == null ? "0" : iia.size()));
                    if (iia != null && !iia.isEmpty()) {
                        localIia = iia.get(0);
                        LOG.fine("AuxIiaThread_ADDEDIT: Foind local iia: " + localIia.getId());
                        break;
                    }
                }
            }
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Busqueda en bbdd " + (localIia != null));
        if (localIia == null) {
            Iia newIia = new Iia();
            convertToIia(sendIia, newIia);

            LOG.fine("AuxIiaThread_ADDEDIT: Iia convertsed with conditions: " + newIia.getCooperationConditions().size());

            iiasEJB.insertReceivedIia(sendIia, newIia);

            /*LOG.fine("AuxIiaThread_ADDEDIT: After seting id");

            map = registryClient.getIiaCnrHeiUrls(heiId);

            if (map == null) {
                return;
            }

            LOG.fine("AuxIiaThread_ADDEDIT: MAP 2 ENCONTRADO");

            url = (new ArrayList<>(map.values())).get(0);
            if (url == null) {
                return;
            }

            LOG.fine("AuxIiaThread_ADDEDIT: CNR URL: " + url);

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

            LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + cnrResponse.getStatusCode());*/

        } else {
            LOG.fine("AuxIiaThread_ADDEDIT: Found existing iia");
            boolean containOtherId = false;
            for (CooperationCondition cc : localIia.getCooperationConditions()) {
                LOG.fine("AuxIiaThread_ADDEDIT: Sending HeiId" + cc.getSendingPartner().getInstitutionId());
                LOG.fine("AuxIiaThread_ADDEDIT: Reciving HeiId" + cc.getReceivingPartner().getInstitutionId());
                if (heiId.equals(cc.getSendingPartner().getInstitutionId())) {
                    containOtherId = cc.getSendingPartner().getIiaId() != null;
                }
                if (heiId.equals(cc.getReceivingPartner().getInstitutionId())) {
                    containOtherId = cc.getReceivingPartner().getIiaId() != null;
                }
            }
            if (!containOtherId) {
                LOG.fine("AuxIiaThread_ADDEDIT: Not containing other ID");
                for (CooperationCondition condition : localIia.getCooperationConditions()) {
                    if (condition.getSendingPartner().getInstitutionId().equals(heiId)) {
                        LOG.fine("AuxIiaThread_ADDEDIT: Partner " + condition.getSendingPartner().getInstitutionId() + " ID set to: " + iiaId);
                        condition.getSendingPartner().setIiaId(iiaId);
                    }

                    if (condition.getReceivingPartner().getInstitutionId().equals(heiId)) {
                        LOG.fine("AuxIiaThread_ADDEDIT: Partner " + condition.getReceivingPartner().getInstitutionId() + " ID set to: " + iiaId);
                        condition.getReceivingPartner().setIiaId(iiaId);
                    }
                }
                LOG.fine("AuxIiaThread_ADDEDIT: Partners hash set to: " + sendIia.getIiaHash());
                localIia.setHashPartner(sendIia.getIiaHash());
                iiasEJB.merge(localIia);
                LOG.fine("AuxIiaThread_ADDEDIT: Merged");
            } else {
                String beforeHash = localIia.getConditionsHash();
                LOG.fine("AuxIiaThread_ADDEDIT: Before hash: " + beforeHash);
                Iia modifIia = new Iia();
                convertToIia(sendIia, modifIia);

                iiasEJB.updateIia(modifIia, localIia);

                LOG.fine("AuxIiaThread_ADDEDIT: After merging changes");

                LOG.fine("AuxIiaThread_ADDEDIT: Compare hashes: " + beforeHash + " " + localIia.getConditionsHash());

                if (!beforeHash.equals(localIia.getConditionsHash())) {

                    map = registryClient.getIiaCnrHeiUrls(heiId);

                    if (map == null) {
                        return;
                    }

                    LOG.fine("AuxIiaThread_ADDEDIT: MAP CNR ENCONTRADO");

                    url = (new ArrayList<>(map.values())).get(0);
                    if (url == null) {
                        return;
                    }

                    LOG.fine("AuxIiaThread_ADDEDIT: CNR URL: " + url);

                    ClientRequest cnrRequest = new ClientRequest();
                    cnrRequest.setUrl(url);
                    cnrRequest.setHeiId(heiId);
                    cnrRequest.setMethod(HttpMethodEnum.POST);
                    cnrRequest.setHttpsec(true);

                    Map<String, List<String>> paramsMapCNR = new HashMap<>();
                    paramsMapCNR.put("notifier_hei_id", Arrays.asList(localHeiId));
                    paramsMapCNR.put("iia_id", Arrays.asList(localIia.getId()));
                    ParamsClass paramsClassCNR = new ParamsClass();
                    paramsClassCNR.setUnknownFields(paramsMapCNR);
                    cnrRequest.setParams(paramsClassCNR);

                    ClientResponse cnrResponse = restClient.sendRequest(cnrRequest, Empty.class);

                    LOG.fine("AuxIiaThread_ADDEDIT: After CNR with code: " + cnrResponse.getStatusCode());
                }
            }
        }

    }

    public void aprovals(String approving_heiId, String owner_heiId, String iiaId) {
        LOG.fine("AuxIiaThread_APROVALS: Sending aproval request");
        Map<String, String> map = registryClient.getIiaApprovalHeiUrls(approving_heiId);
        if (map == null) {
            return;
        }

        LOG.fine("AuxIiaThread_APROVALS: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return;
        }

        LOG.fine("AuxIiaThread_APROVALS: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(approving_heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("approving_hei", Arrays.asList(approving_heiId));
        paramsMap.put("owner_hei", Arrays.asList(owner_heiId));
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("AuxIiaThread_APROVALS: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasApprovalResponse.class);

        LOG.fine("AuxIiaThread_APROVALS: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            //TODO: Handle error, notify monitoring
            return;
        }

        LOG.fine("AuxIiaThread_APROVALS: Respuesta raw: " + clientResponse.getRawResponse());

        IiasApprovalResponse responseEnity = (IiasApprovalResponse) clientResponse.getResult();

        IiasApprovalResponse.Approval iiaApproval = responseEnity.getApproval().get(0);

        Iia localIia = null;
        List<Iia> iia = iiasEJB.findByIdList(iiaId);
        LOG.fine("AuxIiaThread_APROVALS: Find local iias: " + (iia == null ? "0" : iia.size()));
        if (iia != null && !iia.isEmpty()) {
            localIia = iia.get(0);
            LOG.fine("AuxIiaThread_APROVALS: Found local iia: " + localIia.getId());
        }


        LOG.fine("AuxIiaThread_APROVALS: Busqueda en bbdd " + (localIia != null));
        LOG.fine("AuxIiaThread_APROVALS: Found HASH: " + (localIia != null ? localIia.getConditionsHash(): ""));
        LOG.fine("AuxIiaThread_APROVALS: Send HASH: " + iiaApproval.getConditionsHash());
        if (localIia != null && localIia.getConditionsHash().equals(iiaApproval.getConditionsHash())) {
            LOG.fine("AuxIiaThread_APROVALS: Found existing iia and hash is the same");
            //localIia.setInEfect(iiaApproval.isApproved());
            iiasEJB.merge(localIia);
        }
    }

    private void convertToIia(IiasGetResponse.Iia iia, Iia iiaInternal) {

        if (iia.getIiaHash() != null) {
            iiaInternal.setConditionsHash(iia.getIiaHash());
        }

        iiaInternal.setInEfect(iia.isInEffect());

        List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());

        List<Institution> institutions = iiasEJB.findAllInstitutions();
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
            List<RecommendedLanguageSkill> recommendedSkills = mobilitySpec.getRecommendedLanguageSkill();
            for (RecommendedLanguageSkill recommendedSkill : recommendedSkills) {
                eu.erasmuswithoutpaper.iia.entity.LanguageSkill langskill = new eu.erasmuswithoutpaper.iia.entity.LanguageSkill();

                if (recommendedSkill.getCefrLevel() != null) {
                    langskill.setCefrLevel(recommendedSkill.getCefrLevel());
                }

                langskill.setLanguage(recommendedSkill.getLanguage());

                if (recommendedSkill.getSubjectArea() != null) {
                    SubjectArea subjectArea = new SubjectArea();
                    subjectArea.setIscedClarification(recommendedSkill.getSubjectArea().getIscedClarification());
                    if(recommendedSkill.getSubjectArea().getIscedFCode() != null) {
                        subjectArea.setIscedFCode(recommendedSkill.getSubjectArea().getIscedFCode().getValue());
                    }
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
        cc.getReceivingAcademicYearId().add(mobilitySpec.getReceivingFirstAcademicYearId());
        cc.getReceivingAcademicYearId().add(mobilitySpec.getReceivingLastAcademicYearId());

        //Require academic years to be ordered and without gaps
        if (cc.getReceivingAcademicYearId() != null) {
            cc.getReceivingAcademicYearId().sort(String::compareTo);
        }

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
            if(mobilitySpec.getMobilitiesPerYear().isNotYetDefined() == null || !mobilitySpec.getMobilitiesPerYear().isNotYetDefined()) {
                if(mobilitySpec.getMobilitiesPerYear().getValue() != null) {
                    mobNumber.setNumber(mobilitySpec.getMobilitiesPerYear().getValue().intValue());
                }
            }
            cc.setMobilityNumber(mobNumber);
        }

        List<SubjectArea> subjectAreasInt = new ArrayList<>();
        List<eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea> subjectAreas = mobilitySpec.getSubjectArea();
        for (eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea subjectArea : subjectAreas) {
            SubjectArea subjectAreaInt = new SubjectArea();

            if (subjectArea.getIscedClarification() != null) {
                subjectAreaInt.setIscedClarification(subjectArea.getIscedClarification());
            }
            if(subjectArea.getIscedFCode() != null) {
                subjectAreaInt.setIscedFCode(subjectArea.getIscedFCode().getValue());
            }

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
