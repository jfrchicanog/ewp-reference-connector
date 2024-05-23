package eu.erasmuswithoutpaper.iia.control;

import eu.erasmuswithoutpaper.api.iias.endpoints.*;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.RecommendedLanguageSkill;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.iia.entity.*;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;
import eu.erasmuswithoutpaper.organization.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class IiaConverter {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    public List<IiasGetResponse.Iia> convertToIias(String hei_id, List<Iia> iiaList) {
        return iiaList.stream().map((Iia iia) -> {
            IiasGetResponse.Iia converted = new IiasGetResponse.Iia();
            HashMap<String, IiaPartner> uniquePartners = new HashMap<>();
            for (CooperationCondition condition : iia.getCooperationConditions()) {
                if (condition.getSendingPartner() != null && condition.getSendingPartner().getInstitutionId() != null) {
                    uniquePartners.put(condition.getSendingPartner().getInstitutionId(), condition.getSendingPartner());
                }
                if (condition.getReceivingPartner() != null && condition.getReceivingPartner().getInstitutionId() != null) {
                    uniquePartners.put(condition.getReceivingPartner().getInstitutionId(), condition.getReceivingPartner());
                }
            }

            Set<String> partnerKeys = uniquePartners.keySet();
            for (String partnerKey : partnerKeys) {
                IiaPartner partner = uniquePartners.get(partnerKey);
                converted.getPartner().add(convertToPartner(iia, partner));
            }


            // The value of `hei-id` of the first `partner` MUST match the value
            // passed in the `hei_id` request parameter,

            Comparator<? super IiasGetResponse.Iia.Partner> heiIdComparator = new Comparator<IiasGetResponse.Iia.Partner>() {
                //     return 1 if rhs should be before lhs
                //     return -1 if lhs should be before rhs
                //     return 0 otherwise
                @Override
                public int compare(IiasGetResponse.Iia.Partner lhs, IiasGetResponse.Iia.Partner rhs) {
                    if (rhs.getHeiId() != null && rhs.getHeiId().equals(hei_id)) {
                        return 1;
                    }
                    if (lhs.getHeiId() != null && lhs.getHeiId().equals(hei_id)) {
                        return -1;
                    }
                    return 0;
                }
            };
            converted.getPartner().sort(heiIdComparator);

            converted.setCooperationConditions(convertToCooperationConditions(iia.getCooperationConditions(), iia.getConditionsTerminatedAsAWhole()));
            converted.setInEffect(iia.isInEfect());

            //try {
            //    JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.Iia.CooperationConditions.class);
            //    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            //    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            //    StringWriter sw = new StringWriter();

            //Create a copy off CooperationConditions to be used in calculateSha256 function
            //   CooperationConditions cc = new CooperationConditions();
            //    cc.getStaffTeacherMobilitySpec().addAll(converted.getCooperationConditions().getStaffTeacherMobilitySpec());
            //    cc.getStaffTrainingMobilitySpec().addAll(converted.getCooperationConditions().getStaffTrainingMobilitySpec());
            //    cc.getStudentStudiesMobilitySpec().addAll(converted.getCooperationConditions().getStudentStudiesMobilitySpec());
            //    cc.getStudentTraineeshipMobilitySpec().addAll(converted.getCooperationConditions().getStudentTraineeshipMobilitySpec());

            //    cc = removeContactInfo(cc);

            //    QName qName = new QName("cooperation_conditions");
            //    JAXBElement<IiasGetResponse.Iia.CooperationConditions> root = new JAXBElement<IiasGetResponse.Iia.CooperationConditions>(qName, IiasGetResponse.Iia.CooperationConditions.class, cc);

            //    jaxbMarshaller.marshal(root, sw);
            //    String xmlString = sw.toString();

            //    converted.setConditionsHash(HashCalculationUtility.calculateSha256(xmlString));
            //} catch (InvalidCanonicalizerException | CanonicalizationException | NoSuchAlgorithmException | SAXException
            //        | IOException | ParserConfigurationException | TransformerException | JAXBException e) {
            //    logger.error("Can't calculate sha256", e);
            //}
            converted.setIiaHash(iia.getConditionsHash());
            return converted;
        }).collect(Collectors.toList());
    }

    public CooperationConditions removeContactInfo(CooperationConditions cc) {
        cc.getStaffTeacherMobilitySpec().forEach(t -> {
            t.getReceivingContact().clear();
            t.getSendingContact().clear();
        });

        cc.getStaffTrainingMobilitySpec().forEach(t -> {
            t.getReceivingContact().clear();
            t.getSendingContact().clear();

        });

        cc.getStudentStudiesMobilitySpec().forEach(t -> {
            t.getReceivingContact().clear();
            t.getSendingContact().clear();
        });

        cc.getStudentTraineeshipMobilitySpec().forEach(t -> {
            t.getReceivingContact().clear();
            t.getSendingContact().clear();
        });

        return cc;
    }

    public CooperationConditions convertToCooperationConditions(List<CooperationCondition> cooperationConditions, Boolean terminatedAsAWhole) {
        // TODO: Add this
        Map<String, List<CooperationCondition>> ccMap = cooperationConditions
                .stream()
                .collect(Collectors.groupingBy(cc -> {
                    String rtn = "";
                    if (cc.getMobilityType() == null) {
                        return rtn;
                    }
                    if (cc.getMobilityType().getMobilityGroup() != null) {
                        rtn += cc.getMobilityType().getMobilityGroup() + "-";
                    }
                    if (cc.getMobilityType().getMobilityCategory() != null) {
                        rtn += cc.getMobilityType().getMobilityCategory();
                    }
                    return rtn;
                }));

        CooperationConditions converted = new CooperationConditions();

        if (ccMap.containsKey("Staff-Teaching")) {
            converted.getStaffTeacherMobilitySpec().addAll(
                    ccMap.get("Staff-Teaching")
                            .stream()
                            .map(this::convertToStaffTeacherMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Staff-Training")) {
            converted.getStaffTrainingMobilitySpec().addAll(
                    ccMap.get("Staff-Training")
                            .stream()
                            .map(this::convertToStaffTrainingMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Student-Studies")) {
            converted.getStudentStudiesMobilitySpec().addAll(
                    ccMap.get("Student-Studies")
                            .stream()
                            .map(this::convertToStudentStudiesMobilitySpec)
                            .collect(Collectors.toList()));
        }
        if (ccMap.containsKey("Student-Training")) {
            converted.getStudentTraineeshipMobilitySpec().addAll(
                    ccMap.get("Student-Training")
                            .stream()
                            .map(this::convertToStudentTraineeshipMobilitySpec)
                            .collect(Collectors.toList()));
        }
        converted.getStudentTraineeshipMobilitySpec();

        converted.setTerminatedAsAWhole(terminatedAsAWhole);

        return converted;
    }

    private IiasGetResponse.Iia.Partner convertToPartner(Iia iia, IiaPartner partner) {
        IiasGetResponse.Iia.Partner converted = new IiasGetResponse.Iia.Partner();

        if (partner == null) {
            return converted;
        }

        converted.setHeiId(partner.getInstitutionId());
        converted.setOunitId(partner.getOrganizationUnitId());

        converted.setIiaCode(partner.getIiaCode());
        converted.setIiaId(partner.getIiaId());

        try {
            if (iia.getSigningDate() != null) {
                converted.setSigningDate(ConverterHelper.convertToXmlGregorianCalendar(iia.getSigningDate()));
            }
        } catch (DatatypeConfigurationException e) {
            logger.error("Can't convert date", e);
        }//TODO Iia has two other properties startDate, endDate

        if (partner.getSigningContact() != null) {
            Contact contact = new Contact();

            if (partner.getSigningContact().getPerson() != null && partner.getSigningContact().getPerson().getGender() != null) {
                contact.setPersonGender(partner.getSigningContact().getPerson().getGender().value());
            }
            if (partner.getSigningContact().getContactDetails() != null) {
                contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(partner.getSigningContact().getContactDetails().getMailingAddress()));
                contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(partner.getSigningContact().getContactDetails().getStreetAddress()));
            }
            converted.setSigningContact(contact);
        }

        return converted;
    }

    private StaffTeacherMobilitySpec convertToStaffTeacherMobilitySpec(CooperationCondition cc) {
        StaffTeacherMobilitySpec conv = new StaffTeacherMobilitySpec();
        if (cc == null) {
            return conv;
        }
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }

    private StaffTrainingMobilitySpec convertToStaffTrainingMobilitySpec(CooperationCondition cc) {
        StaffTrainingMobilitySpec conv = new StaffTrainingMobilitySpec();
        if (cc == null) {
            return conv;
        }
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }

    private StudentStudiesMobilitySpec convertToStudentStudiesMobilitySpec(CooperationCondition cc) {
        StudentStudiesMobilitySpec conv = new StudentStudiesMobilitySpec();
        if (cc == null) {
            return conv;
        }
        addToStudentMobilitySpecification(conv, cc);
        return conv;
    }

    private StudentTraineeshipMobilitySpec convertToStudentTraineeshipMobilitySpec(CooperationCondition cc) {
        StudentTraineeshipMobilitySpec conv = new StudentTraineeshipMobilitySpec();
        if (cc == null) {
            return conv;
        }
        addToStudentMobilitySpecification(conv, cc);
        return conv;
    }

    private void addToMobilitySpecification(MobilitySpecification conv, CooperationCondition cc) {

        if (cc.getRecommendedLanguageSkill() != null) {
            List<RecommendedLanguageSkill> recommendedSkills = cc.getRecommendedLanguageSkill().stream().map((langskill) -> {

                RecommendedLanguageSkill recommendedLangSkill = new RecommendedLanguageSkill();

                recommendedLangSkill.setCefrLevel(langskill.getCefrLevel());
                recommendedLangSkill.setLanguage(langskill.getLanguage());

                if (langskill.getSubjectArea() != null) {
                    SubjectArea subjectArea = new SubjectArea();
                    subjectArea.setIscedClarification(langskill.getSubjectArea().getIscedClarification());
                    SubjectArea.IscedFCode iscedFCode = new SubjectArea.IscedFCode();
                    iscedFCode.setValue(langskill.getSubjectArea().getIscedFCode());
                    subjectArea.setIscedFCode(iscedFCode);

                    recommendedLangSkill.setSubjectArea(subjectArea);
                }

                return recommendedLangSkill;
            }).collect(Collectors.toList());

            conv.getRecommendedLanguageSkill().addAll(recommendedSkills);
        }

        if (cc.getReceivingAcademicYearId() != null && !cc.getReceivingAcademicYearId().isEmpty()) {
            cc.getReceivingAcademicYearId().sort(Comparator.naturalOrder());
            conv.setReceivingFirstAcademicYearId(cc.getReceivingAcademicYearId().get(0));
            if (cc.getReceivingAcademicYearId().size() > 1) {
                conv.setReceivingLastAcademicYearId(cc.getReceivingAcademicYearId().get(1));
            }
        }

        if (cc.getReceivingPartner().getOrganizationUnitId() != null) {
            conv.setReceivingOunitId(cc.getReceivingPartner().getOrganizationUnitId());
        }

        if (cc.getSubjectAreas() != null && !cc.getSubjectAreas().isEmpty()) {
            List<SubjectArea> subjectAreas = cc.getSubjectAreas().stream().map(subject -> {
                SubjectArea subjectArea = new SubjectArea();

                subjectArea.setIscedClarification(subject.getIscedClarification());
                SubjectArea.IscedFCode iscedFCode = new SubjectArea.IscedFCode();
                iscedFCode.setValue(subject.getIscedFCode());
                subjectArea.setIscedFCode(iscedFCode);

                return subjectArea;
            }).collect(Collectors.toList());

            conv.getSubjectArea().addAll(subjectAreas);
        }


        List<Contact> contactReceivings = new ArrayList<>();
        if (cc.getReceivingPartner() != null && cc.getReceivingPartner().getContacts() != null) {
            contactReceivings = cc.getReceivingPartner().getContacts().stream().map(recContact -> {
                Contact contact = new Contact();

                if (recContact.getPerson() != null && recContact.getPerson().getGender() != null) {
                    contact.setPersonGender(recContact.getPerson().getGender().value());
                }

                if (recContact.getContactDetails() != null) {
                    contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(recContact.getContactDetails().getMailingAddress()));
                    contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(recContact.getContactDetails().getStreetAddress()));
                }

                return contact;
            }).collect(Collectors.toList());
        }

        conv.getReceivingContact().addAll(contactReceivings);

        List<Contact> contactsSending = new ArrayList<>();
        if (cc.getSendingPartner() != null && cc.getSendingPartner().getContacts() != null) {
            contactsSending = cc.getSendingPartner().getContacts().stream().map(sendContact -> {
                Contact contact = new Contact();

                if (sendContact.getPerson() != null && sendContact.getPerson().getGender() != null) {
                    contact.setPersonGender(sendContact.getPerson().getGender().value());
                }

                if (sendContact.getContactDetails() != null) {
                    contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(sendContact.getContactDetails().getMailingAddress()));
                    contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(sendContact.getContactDetails().getStreetAddress()));
                }

                return contact;
            }).collect(Collectors.toList());
        }

        conv.getSendingContact().addAll(contactsSending);

        if (cc.getSendingPartner() != null && cc.getSendingPartner().getOrganizationUnitId() != null) {
            conv.setSendingOunitId(cc.getSendingPartner().getOrganizationUnitId());
        }

        MobilitySpecification.MobilitiesPerYear mobilitiesPerYear = new MobilitySpecification.MobilitiesPerYear();
        if (cc.getMobilityNumber() != null) {
            mobilitiesPerYear.setValue(BigInteger.valueOf(cc.getMobilityNumber().getNumber()));
        } else {
            mobilitiesPerYear.setNotYetDefined(true);
        }
        conv.setMobilitiesPerYear(mobilitiesPerYear);
        if (cc.getReceivingPartner() != null) {
            conv.setReceivingHeiId(cc.getReceivingPartner().getInstitutionId());
        }
        if (cc.getSendingPartner() != null) {
            conv.setSendingHeiId(cc.getSendingPartner().getInstitutionId());
        }
        conv.setOtherInfoTerms(cc.getOtherInfoTerms());
    }

    private void addToStudentMobilitySpecification(StudentMobilitySpecification conv, CooperationCondition cc) {
        //conv.setAvgMonths(BigInteger.ONE);
        if (conv == null) {
            return;
        }
        if (cc == null) {
            return;
        }
        if (cc.getDuration() != null && cc.getDuration().getNumber() != null) {
            conv.setTotalMonthsPerYear(cc.getDuration().getNumber().setScale(2, RoundingMode.HALF_EVEN));
        }

        List<Byte> eqfLevels = new ArrayList<Byte>();
        byte[] arrEqfLevel = cc.getEqfLevel();
        if (arrEqfLevel == null) {
            arrEqfLevel = new byte[0];
        }
        for (int i = 0; i < arrEqfLevel.length; i++) {
            eqfLevels.add(new Byte(arrEqfLevel[i]));
        }

        conv.getEqfLevel().addAll(eqfLevels);

        conv.setBlended(cc.isBlended());

        addToMobilitySpecification(conv, cc);
    }

    private void addToStaffMobilitySpecification(StaffMobilitySpecification conv, CooperationCondition cc) {
        if (conv == null) {
            return;
        }
        if (cc == null) {
            return;
        }
        //conv.setAvgDays(BigInteger.ONE);
        if (cc.getDuration() != null && cc.getDuration().getNumber() != null) {
            conv.setTotalDaysPerYear(cc.getDuration().getNumber().setScale(2, RoundingMode.HALF_EVEN));
        }

        addToMobilitySpecification(conv, cc);
    }


    public void convertToIia(IiasGetResponse.Iia iia, Iia iiaInternal, List<Institution> institutions) {
        try {
            logger.info("Converting IIA");
            if (iia.getIiaHash() != null) {
                iiaInternal.setConditionsHash(iia.getIiaHash());
            }

            logger.info("CONVERTING IIA: after conditions hash");

            iiaInternal.setInEfect(iia.isInEffect());

            logger.info("CONVERTING IIA: after in effect");

            if (iia.getCooperationConditions() != null) {
                iiaInternal.setConditionsTerminatedAsAWhole(iia.getCooperationConditions().isTerminatedAsAWhole());
            }

            logger.info("CONVERTING IIA: after conditions terminated as a whole");

            List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());

            logger.info("CONVERTING IIA: after cooperation conditions 1");

            if (iia.getPartner() != null) {
                iia.getPartner().forEach((IiasGetResponse.Iia.Partner partner) -> {

                    logger.info("CONVERTING IIA: before partner");

                    IiaPartner partnerInternal = new IiaPartner();

                    if (institutions != null) {
                        for (Institution institution : institutions) {
                            if (institution.getInstitutionId() != null && partner.getHeiId() != null) {
                                if (institution.getInstitutionId().equals(partner.getHeiId())) {
                    /*System.out.println("Found Parter:" + partner.getHeiId());
                    System.out.println("IiaCode:" + iiaInternal.getIiaCode());
                    System.out.println("IiaCode Parter:" + partner.getIiaCode());*/

                                    //iiaInternal.setEndDate(null);
                                    //iiaInternal.setStartDate(null);
                                    iiaInternal.setIiaCode(partner.getIiaCode());

                                    logger.info("CONVERTING IIA: after iia code 2");

                                    iiaInternal.setId(partner.getIiaId());

                                    logger.info("CONVERTING IIA: after iia id 2");

                                    if (partner.getSigningDate() != null) {
                                        iiaInternal.setSigningDate(partner.getSigningDate().toGregorianCalendar().getTime());
                                    }

                                    logger.info("CONVERTING IIA: after signing date");

                                    break;
                                }
                            }
                        }
                    }

                    logger.info("CONVERTING IIA: after institutions");

                    if (partner.getIiaCode() != null) {
                        partnerInternal.setIiaCode(partner.getIiaCode());
                    }

                    logger.info("CONVERTING IIA: after iia code");

                    if (partner.getIiaId() != null) {
                        partnerInternal.setIiaId(partner.getIiaId());
                    }

                    logger.info("CONVERTING IIA: after iia id");

                    partnerInternal.setInstitutionId(partner.getHeiId());

                    logger.info("CONVERTING IIA: after institution id");

                    if (partner.getOunitId() != null) {
                        partnerInternal.setOrganizationUnitId(partner.getOunitId());
                    }

                    logger.info("CONVERTING IIA: after organization unit id");

                    if (partner.getSigningContact() != null) {
                        Contact signingContact = partner.getSigningContact();

                        eu.erasmuswithoutpaper.organization.entity.Contact signingContactInternal = convertToContact(signingContact);
                        partnerInternal.setSigningContact(signingContactInternal);
                    }

                    logger.info("CONVERTING IIA: after signing contact");

                    if (partner.getContact() != null) {
                        List<Contact> contacts = partner.getContact();

                        List<eu.erasmuswithoutpaper.organization.entity.Contact> internalContacts = new ArrayList<>();
                        if (contacts != null) {
                            for (Contact contact : contacts) {
                                eu.erasmuswithoutpaper.organization.entity.Contact internalContact = convertToContact(contact);

                                if (internalContact != null) {
                                    internalContacts.add(internalContact);
                                }
                            }
                        }

                        partnerInternal.setContacts(internalContacts);
                    }

                    logger.info("CONVERTING IIA: after contacts");

                    if (iiaIternalCooperationConditions != null) {
                        for (CooperationCondition cooperationConditionInternal : iiaIternalCooperationConditions) {

                            if (cooperationConditionInternal.getReceivingPartner() != null && cooperationConditionInternal.getReceivingPartner().getInstitutionId() != null &&
                                    cooperationConditionInternal.getReceivingPartner().getInstitutionId().equals(partner.getHeiId())) {

                                cooperationConditionInternal.setReceivingPartner(partnerInternal);

                            } else if (cooperationConditionInternal.getSendingPartner() != null && cooperationConditionInternal.getSendingPartner().getInstitutionId() != null &&
                                    cooperationConditionInternal.getSendingPartner().getInstitutionId().equals(partner.getHeiId())) {

                                cooperationConditionInternal.setSendingPartner(partnerInternal);

                            }
                        }
                    }

                    logger.info("CONVERTING IIA: after cooperation conditions 2");

                });
            }

            logger.info("CONVERTING IIA: after partners");

            iiaInternal.setCooperationConditions(iiaIternalCooperationConditions);
            logger.info("CONVERTING IIA: after cooperation conditions 3");
        } catch (Exception e) {
            logger.error("Error converting IIA", e);
            e.printStackTrace();
            String stackTrace = Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n"));
            logger.error(stackTrace);
            throw e;
        }
    }

    private eu.erasmuswithoutpaper.organization.entity.Contact convertToContact(Contact pContact) {
        eu.erasmuswithoutpaper.organization.entity.Contact internalContact = new eu.erasmuswithoutpaper.organization.entity.Contact();

//		List<StringWithOptionalLang> contactNames = pContact.getContactName();
//		for (StringWithOptionalLang stringWithOptionalLang : contactNames) {
//
//		}
        //internalContact.set

        if (pContact == null) {
            return null;
        }

        Person personInternal = new Person();
        if (pContact.getPersonGender() != null) {
            personInternal.setGender(Gender.getById(pContact.getPersonGender()));
        }
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

        if (flexible == null) {
            return null;
        }

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

        if (flexible.getDeliveryPointCode() != null) {
            List<String> codes = flexible.getDeliveryPointCode().stream().map(String::valueOf).collect(Collectors.toList());
            flexibleAddressInternal.setDeliveryPointCode(new ArrayList<>());
            flexibleAddressInternal.getDeliveryPointCode().addAll(codes);
        }
        return flexibleAddressInternal;
    }

    private List<CooperationCondition> getCooperationConditions(CooperationConditions cooperationConditions) {
        List<CooperationCondition> cooperationConditionsInternals = new ArrayList<>();

        if (cooperationConditions == null) {
            return null;
        }

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

        if (studentStudies == null) {
            return null;
        }

        cc.setMobilityType(mobType);

        Duration duration = new Duration();
        duration.setNumber(studentStudies.getTotalMonthsPerYear());
        cc.setDuration(duration);

        if (studentStudies.getEqfLevel() != null) {
            List<Byte> eqfLevels = studentStudies.getEqfLevel();
            byte[] arrEqfLevel = new byte[eqfLevels.size()];
            for (int i = 0; i < eqfLevels.size(); i++) {
                arrEqfLevel[i] = eqfLevels.get(i);
            }
            cc.setEqfLevel(arrEqfLevel);
        }

        cc.setBlended(studentStudies.isBlended());
        return cc;
    }

    private CooperationCondition convertFromStaffToCooperationCondition(MobilityType mobType,
                                                                        StaffMobilitySpecification staffTeacher) {
        CooperationCondition cc = new CooperationCondition();

        if (staffTeacher == null) {
            return null;
        }

        cc.setMobilityType(mobType);

        Duration duration = new Duration();
        duration.setNumber(staffTeacher.getTotalDaysPerYear());
        cc.setDuration(duration);

        convertFromMobilitySpecification(staffTeacher, cc);
        return cc;
    }

    private void convertFromMobilitySpecification(MobilitySpecification mobilitySpec, CooperationCondition cc) {
        List<eu.erasmuswithoutpaper.iia.entity.LanguageSkill> langskills = new ArrayList<>();

        if (mobilitySpec == null) {
            return;
        }
        if (cc == null) {
            return;
        }

        if (mobilitySpec.getRecommendedLanguageSkill() != null) {
            List<RecommendedLanguageSkill> recommendedSkills = mobilitySpec.getRecommendedLanguageSkill();
            for (RecommendedLanguageSkill recommendedSkill : recommendedSkills) {
                eu.erasmuswithoutpaper.iia.entity.LanguageSkill langskill = new eu.erasmuswithoutpaper.iia.entity.LanguageSkill();

                if (recommendedSkill.getCefrLevel() != null) {
                    langskill.setCefrLevel(recommendedSkill.getCefrLevel());
                }

                langskill.setLanguage(recommendedSkill.getLanguage());

                if (recommendedSkill.getSubjectArea() != null) {
                    eu.erasmuswithoutpaper.iia.entity.SubjectArea subjectArea = new eu.erasmuswithoutpaper.iia.entity.SubjectArea();
                    subjectArea.setIscedClarification(recommendedSkill.getSubjectArea().getIscedClarification());
                    if (recommendedSkill.getSubjectArea().getIscedFCode() != null) {
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
            if (mobilitySpec.getMobilitiesPerYear().isNotYetDefined() == null || !mobilitySpec.getMobilitiesPerYear().isNotYetDefined()) {
                if (mobilitySpec.getMobilitiesPerYear().getValue() != null) {
                    mobNumber.setNumber(mobilitySpec.getMobilitiesPerYear().getValue().intValue());
                }
            }
            cc.setMobilityNumber(mobNumber);
        }

        List<eu.erasmuswithoutpaper.iia.entity.SubjectArea> subjectAreasInt = new ArrayList<>();
        List<eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea> subjectAreas = mobilitySpec.getSubjectArea();
        if (subjectAreas != null) {
            for (eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea subjectArea : subjectAreas) {
                eu.erasmuswithoutpaper.iia.entity.SubjectArea subjectAreaInt = new eu.erasmuswithoutpaper.iia.entity.SubjectArea();

                if (subjectArea.getIscedClarification() != null) {
                    subjectAreaInt.setIscedClarification(subjectArea.getIscedClarification());
                }
                if (subjectArea.getIscedFCode() != null) {
                    subjectAreaInt.setIscedFCode(subjectArea.getIscedFCode().getValue());
                }

                subjectAreasInt.add(subjectAreaInt);
            }
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

        if (contact == null) {
            return null;
        }

        FlexibleAddress address = convertFlexibleAddress(contact.getMailingAddress());
        FlexibleAddress streetAdd = convertFlexibleAddress(contact.getMailingAddress());

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setMailingAddress(address);
        contactDetails.setStreetAddress(streetAdd);

        contactRec.setContactDetails(contactDetails);

        Person person = new Person();
        if (contact.getPersonGender() != null) {
            person.setGender(Gender.getById(contact.getPersonGender()));
        }
        return contactRec;
    }
}
