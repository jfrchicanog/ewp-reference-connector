package eu.erasmuswithoutpaper.iia.boundary;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import eu.erasmuswithoutpaper.common.control.*;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.Partner;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.RecommendedLanguageSkill;
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
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Duration;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.IiaResponse;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumber;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumberVariants;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.iia.entity.SubjectArea;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Path("iia")
public class GuiIiaResource {

    @EJB
    IiasEJB iiasEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    IiaConverter iiaConverter;

    @Inject
    GlobalProperties properties;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final Logger logger = LoggerFactory.getLogger(GuiIiaResource.class);
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(AuxIiaThread.class.getCanonicalName());

    @GET
    @Path("get_all")
    @InternalAuthenticate
    // TODO: fix the default value
    public Response getAll(@QueryParam("hei_id") @DefaultValue("uma.es") String hei_id) {
        List<Iia> iiaList = iiasEJB.findAll();
        List<IiasGetResponse.Iia> result = iiaConverter.convertToIias(hei_id, iiaList);//It was required to use IiaConverter to avoid a recursive problem when the iia object was converted to json

        GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(result) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("get_heiid")
    @InternalAuthenticate
    public Response getHei(@QueryParam("hei_id") String heiId) {
        List<Iia> iiaList = iiasEJB.findAll();

        Predicate<Iia> condition = new Predicate<Iia>() {
            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();

                List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> heiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty();
            }
        };

        if (!iiaList.isEmpty()) {
            List<Iia> filteredList = iiaList.stream().filter(condition).collect(Collectors.toList());

            if (!filteredList.isEmpty()) {
                List<IiasGetResponse.Iia> iiasGetResponseList = iiaConverter.convertToIias(heiId, iiaList);

                GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(iiasGetResponseList) {
                };
                return Response.ok(entity).build();
            }
        }

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("mobility_types")
    @InternalAuthenticate
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = iiasEJB.findMobilityTypes();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("mobility_unit_variants")
    @InternalAuthenticate
    public Response getMobilityNumberVariants() {
        String[] statuses = MobilityNumberVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("duration_unit_variants")
    @InternalAuthenticate
    public Response getDurationUnitVariants() {
        String[] statuses = DurationUnitVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {
        };

        return Response.ok(entity).build();
    }

    @POST
    @Path("add")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(IiasGetResponse.Iia iia) throws Exception {
        LOG.fine("ADD: Add start");
        Iia iiaInternal = new Iia();

        convertToIia(iia, iiaInternal);

        iiasEJB.insertIia(iiaInternal);

        System.out.println("ADD: Created Iia Id:" + iiaInternal.getId());

        List<ClientResponse> iiasResponse = notifyPartner(iiaInternal);

        LOG.fine("ADD: Notification send");

        IiaResponse response = new IiaResponse(iiaInternal.getId(), iiaInternal.getConditionsHash());

        return Response.ok(response).build();
    }

    private void convertToIia(IiasGetResponse.Iia iia, Iia iiaInternal) {

        if (iia.getIiaHash() != null) {
            iiaInternal.setConditionsHash(iia.getIiaHash());
        }

        iiaInternal.setInEfect(iia.isInEffect());

        List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());

        List<Institution> institutions = iiasEJB.findAllInstitutions();
        iia.getPartner().stream().forEach((Partner partner) -> {

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

    private List<CooperationCondition> getCooperationConditions(CooperationConditions cooperationConditions) {
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
        if(cc.getReceivingAcademicYearId() != null) {
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

    @GET
    @Path("heis")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaHeisWithUrls();

        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {
        };
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("iias-index")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasIndex(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "index", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "index", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse> entity = null;
        try {
            eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse index = (eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse) iiaResponse.getResult();
            entity = new GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse>(index) {
            };
        } catch (Exception e) {
            return javax.ws.rs.core.Response.serverError().entity(iiaResponse.getErrorMessage()).build();
        }

        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("iias")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iias(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }

    @POST
    @Path("update")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response update(IiasGetResponse.Iia iia) {
        LOG.fine("UPDATE: Start Update");
        Iia iiaInternal = new Iia();

        convertToIia(iia, iiaInternal);

        //Find the iia by code
        List<Iia> foundIias = iiasEJB.findByIiaCode(iiaInternal.getIiaCode());

        Iia foundIia = (foundIias != null && !foundIias.isEmpty()) ? foundIias.get(0) : null;

        //Check if the iia exists
        if (foundIia == null) {
            //Find the iia by id
            foundIias = iiasEJB.findByIdList(iiaInternal.getId());

            foundIia = (foundIias != null && !foundIias.isEmpty()) ? foundIias.get(0) : null;

            if (foundIia == null) {
                return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        //check if the iia is a draft or proposal
        if (foundIia.isInEfect()) {
            System.out.println("Is draft");
            return javax.ws.rs.core.Response.status(Response.Status.NOT_MODIFIED).build();
        }

        if (iiaInternal.getIiaCode() == null || iiaInternal.getIiaCode().isEmpty()) {
            System.err.println("Update Algoria: Mising iiaCode");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (iiaInternal.getCooperationConditions().size() == 0) {
            System.err.println("Update Algoria: Mising Cooperation Conditions");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();

        } else if (!validateConditions(iiaInternal.getCooperationConditions())) {
            System.err.println("Update Algoria: Invalids Cooperation Conditions");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        iiasEJB.updateIia(iiaInternal, foundIia);

        //Notify the partner about the modification using the API GUI IIA CNR 
        List<ClientResponse> iiasResponse = notifyPartner(iiaInternal);

        //ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
        IiaResponse response = new IiaResponse(foundIia.getId(), foundIia.getConditionsHash());
        return javax.ws.rs.core.Response.ok(response).build();
    }

    private List<ClientResponse> notifyPartner(Iia iia) {
        LOG.fine("NOTIFY: Send notification");

        String localHeiId = iiasEJB.getHeiId();

        List<ClientResponse> partnersResponseList = new ArrayList<>();

        //Getting agreement partners
        IiaPartner partnerSending = null;
        IiaPartner partnerReceiving = null;

        Set<NotifyAux> cnrUrls = new HashSet<>();

        List<Institution> institutions = iiasEJB.findAllInstitutions();
        for (CooperationCondition condition : iia.getCooperationConditions()) {
            partnerSending = condition.getSendingPartner();
            partnerReceiving = condition.getReceivingPartner();

            LOG.fine("NOTIFY: partnerSending: " + partnerSending.getInstitutionId());
            LOG.fine("NOTIFY: partnerReceiving: " + partnerReceiving.getInstitutionId());

            Map<String, String> urls = null;
            for (Institution institution : institutions) {

                if (!institution.getInstitutionId().equals(partnerSending.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaCnrHeiUrls(partnerSending.getInstitutionId());

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerSending.getInstitutionId(), entry.getValue()));
                        }
                    }

                } else if (!institution.getInstitutionId().equals(partnerReceiving.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaCnrHeiUrls(partnerReceiving.getInstitutionId());

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerReceiving.getInstitutionId(), entry.getValue()));
                        }

                    }
                }

            }
        }

        String finalLocalHeiId = localHeiId;
        cnrUrls.forEach(url -> {
            LOG.fine("NOTIFY: url: " + url.getUrl());
            LOG.fine("NOTIFY: heiId: " + url.getHeiId());
            //Notify the other institution about the modification
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setUrl(url.getUrl());//get the first and only one url
            clientRequest.setHeiId(url.getHeiId());
            clientRequest.setMethod(HttpMethodEnum.POST);
            clientRequest.setHttpsec(true);

            Map<String, List<String>> paramsMap = new HashMap<>();
            paramsMap.put("notifier_hei_id", Collections.singletonList(finalLocalHeiId));
            paramsMap.put("iia_id", Collections.singletonList(iia.getId()));
            ParamsClass paramsClass = new ParamsClass();
            paramsClass.setUnknownFields(paramsMap);
            clientRequest.setParams(paramsClass);

            ClientResponse iiaResponse = restClient.sendRequest(clientRequest, Empty.class);

            try {
                if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
                } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
                }
            } catch (Exception e) {

            }

            partnersResponseList.add(iiaResponse);
        });

        return partnersResponseList;

    }

    @POST
    @Path("iias-approve")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApprove(@FormParam("hei_id") String heiId, @FormParam("iia_code") String
            iiaCode) {
        if (heiId == null || heiId.isEmpty() || iiaCode == null || iiaCode.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        //seek the iia by code and by the ouid of the sending institution
        List<Iia> foundIia = iiasEJB.findByIiaCode(iiaCode);

        Predicate<Iia> condition = new Predicate<Iia>() {
            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();

                List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> heiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty();
            }
        };

        foundIia.stream().filter(condition).collect(Collectors.toList());

        if (foundIia.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        //get the first one found
        Iia theIia = foundIia.get(0);

        //Getting agreement partners
        IiaPartner partnerReceiving = null;

        for (CooperationCondition cooCondition : theIia.getCooperationConditions()) {
            partnerReceiving = cooCondition.getReceivingPartner();
        }

        //Verify if the agreement is approved by the other institution.
        Map<String, String> urlsGet = registryClient.getIiaApprovalHeiUrls(heiId);
        List<String> urlGetValues = new ArrayList<String>(urlsGet.values());

        ClientRequest clientRequestGetIia = new ClientRequest();
        clientRequestGetIia.setUrl(urlGetValues.get(0));//get the first and only one url
        clientRequestGetIia.setHeiId(partnerReceiving.getInstitutionId());
        clientRequestGetIia.setMethod(HttpMethodEnum.GET);
        clientRequestGetIia.setHttpsec(true);

        List<String> iiaIds = new ArrayList<>();
        iiaIds.add(theIia.getId());

        Map<String, List<String>> params = new HashMap<>();
        params.put("approving_hei_id", Arrays.asList(partnerReceiving.getInstitutionId()));
        params.put("owner_hei_id", Arrays.asList(heiId));
        params.put("iia_id", iiaIds);

        ParamsClass pc = new ParamsClass();
        pc.setUnknownFields(params);

        clientRequestGetIia.setParams(pc);

        ClientResponse iiaApprovalResponse = restClient.sendRequest(clientRequestGetIia, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);

        try {
            if (iiaApprovalResponse.getStatusCode() <= 599 && iiaApprovalResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequestGetIia.getHeiId(), "iias-approval", null, Integer.toString(iiaApprovalResponse.getStatusCode()), iiaApprovalResponse.getErrorMessage(), null);
            } else if (iiaApprovalResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequestGetIia.getHeiId(), "iias-approval", null, Integer.toString(iiaApprovalResponse.getStatusCode()), iiaApprovalResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse response = (IiasApprovalResponse) iiaApprovalResponse.getResult();

        Approval approval = response.getApproval().stream()
                .filter(app -> theIia.getIiaCode().equals(app.getIiaId()))
                .findAny()
                .orElse(null);

        if (approval != null) {
            theIia.setInEfect(true);//it was approved
            //em.persist(theIia); TODO: arreglar
        }

        //Get the url for notify the institute
        Map<String, String> urls = registryClient.getIiaApprovalCnrHeiUrls(heiId);
        List<String> urlValues = new ArrayList<String>(urls.values());

        //Notify the other institution about the approval
        ClientRequest clientRequestNotifyApproval = new ClientRequest();
        clientRequestNotifyApproval.setUrl(urlValues.get(0));//get the first and only one url
        clientRequestNotifyApproval.setHeiId(partnerReceiving.getInstitutionId());
        clientRequestNotifyApproval.setMethod(HttpMethodEnum.POST);
        clientRequestNotifyApproval.setHttpsec(true);

        Map<String, List<String>> paramsCnr = new HashMap<>();
        paramsCnr.put("approving_hei_id", Arrays.asList(partnerReceiving.getInstitutionId()));
        paramsCnr.put("owner_hei_id", Arrays.asList(heiId));
        paramsCnr.put("iia_id", iiaIds);

        ParamsClass pc2 = new ParamsClass();
        pc2.setUnknownFields(paramsCnr);

        clientRequestNotifyApproval.setParams(pc2);

        ClientResponse iiaResponse = restClient.sendRequest(clientRequestNotifyApproval, Object.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequestNotifyApproval.getHeiId(), "iia-approval-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequestNotifyApproval.getHeiId(), "iia-approval-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }

    private boolean validateConditions(List<CooperationCondition> conditions) {

        Predicate<CooperationCondition> condition = new Predicate<CooperationCondition>() {
            @Override
            public boolean test(CooperationCondition c) {
                if (c.getSendingPartner() == null || c.getReceivingPartner() == null) {
                    return true;
                } else if (c.getSendingPartner().getInstitutionId() == null || c.getSendingPartner().getInstitutionId().isEmpty()) {
                    return true;
                } else if (c.getReceivingPartner().getInstitutionId() == null || c.getReceivingPartner().getInstitutionId().isEmpty()) {
                    return true;
                }
                return false;
            }

        };

        List<CooperationCondition> wrongConditions = conditions.stream().filter(condition).collect(Collectors.toList());
        return wrongConditions.isEmpty();
    }

}
