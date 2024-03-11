package eu.erasmuswithoutpaper.iia.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;

import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.architecture.StringWithOptionalLang;
import eu.erasmuswithoutpaper.api.iias.endpoints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class IiaConverter {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    public List<IiasGetResponse.Iia> convertToIias(String hei_id, List<Iia> iiaList) {
        return iiaList.stream().map((Iia iia) -> {
            IiasGetResponse.Iia iiaResponse = new IiasGetResponse.Iia();
            iiaResponse.setIiaHash(iia.getIiaHash());
            iiaResponse.setInEffect(iia.isInEfect());
            iiaResponse.setPdfFile(iia.getPdfFile());

            if(iia.getPartner() != null) {
                iiaResponse.getPartner().addAll(iia.getPartner().stream().map(this::convertToPartner).collect(Collectors.toList()));
            }
            if (iia.getCooperationConditions() != null) {
                iiaResponse.setCooperationConditions(convertToCooperationConditions(iia.getCooperationConditions()));
            }
            return iiaResponse;
        }).collect(Collectors.toList());
    }

    private IiasGetResponse.Iia.Partner convertToPartner(IiaPartner partner) {
        if(partner == null) {
            return null;
        }
        IiasGetResponse.Iia.Partner partnerResponse = new IiasGetResponse.Iia.Partner();
        partnerResponse.setHeiId(partner.getHeiId());
        partnerResponse.setIiaId(partner.getIiaId());
        partnerResponse.setIiaCode(partner.getIiaCode());
        partnerResponse.setOunitId(partner.getOunitId());
        try {
            partnerResponse.setSigningDate(ConverterHelper.convertToXmlGregorianCalendar(partner.getSigningDate()));
        } catch (DatatypeConfigurationException e) {
            logger.error("Error converting OunitName to MultilineStringWithOptionalLang", e);
        }
        partnerResponse.setSigningContact(convertToContact(partner.getSigningContact()));
        if (partner.getContact() != null) {
            partnerResponse.getContact().addAll(partner.getContact().stream().map(this::convertToContact).collect(Collectors.toList()));
        }
        return partnerResponse;
    }

    private Contact convertToContact(eu.erasmuswithoutpaper.iia.entity.IiaContact iiaContact) {
        if(iiaContact == null) {
            return null;
        }
        Contact contact = new Contact();

        contact.setPersonGender(iiaContact.getPersonGender());
        contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(iiaContact.getStreetAddress()));
        contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(iiaContact.getMailingAddress()));
        contact.getEmail().addAll(iiaContact.getEmail());
        if (iiaContact.getContactName() != null) {
            contact.getContactName().addAll(iiaContact.getContactName().stream().map(str -> {
                StringWithOptionalLang stringWithOptionalLang = new StringWithOptionalLang();
                stringWithOptionalLang.setValue(str);
                return stringWithOptionalLang;
            }).collect(Collectors.toList()));
        }
        if (iiaContact.getPersonFamilyName() != null) {
            contact.getPersonFamilyName().addAll(iiaContact.getPersonFamilyName().stream().map(str -> {
                StringWithOptionalLang stringWithOptionalLang = new StringWithOptionalLang();
                stringWithOptionalLang.setValue(str);
                return stringWithOptionalLang;
            }).collect(Collectors.toList()));
        }
        if (iiaContact.getPersonGivenName() != null) {
            contact.getPersonGivenNames().addAll(iiaContact.getPersonGivenName().stream().map(str -> {
                StringWithOptionalLang stringWithOptionalLang = new StringWithOptionalLang();
                stringWithOptionalLang.setValue(str);
                return stringWithOptionalLang;
            }).collect(Collectors.toList()));
        }
        if (iiaContact.getPhoneNumber() != null) {
            contact.getPhoneNumber().addAll(iiaContact.getPhoneNumber().stream().map(ConverterHelper::convertToPhoneNumber).collect(Collectors.toList()));
        }
        if (iiaContact.getFaxNumber() != null) {
            contact.getFaxNumber().addAll(iiaContact.getFaxNumber().stream().map(ConverterHelper::convertToPhoneNumber).collect(Collectors.toList()));
        }
        if (iiaContact.getRoleDescription() != null) {
            contact.getRoleDescription().addAll(iiaContact.getRoleDescription().stream().map(str -> {
                MultilineStringWithOptionalLang stringWithOptionalLang = new MultilineStringWithOptionalLang();
                stringWithOptionalLang.setValue(str);
                return stringWithOptionalLang;
            }).collect(Collectors.toList()));
        }

        return contact;
    }

    private IiasGetResponse.Iia.CooperationConditions convertToCooperationConditions(eu.erasmuswithoutpaper.iia.entity.CooperationCondition cooperationCondition) {
        if (cooperationCondition == null) {
            return null;
        }
        IiasGetResponse.Iia.CooperationConditions cooperationConditionResponse = new IiasGetResponse.Iia.CooperationConditions();

        cooperationConditionResponse.setTerminatedAsAWhole(cooperationCondition.getTerminatedAsAWhole());
        if (cooperationCondition.getStaffTeacherMobilitySpec() != null) {
            cooperationConditionResponse.getStaffTeacherMobilitySpec().addAll(cooperationCondition.getStaffTeacherMobilitySpec().stream().map(this::convertToStaffTeacherMobilitySpec).collect(Collectors.toList()));
        }
        if (cooperationCondition.getStaffTrainingMobilitySpec() != null) {
            cooperationConditionResponse.getStaffTrainingMobilitySpec().addAll(cooperationCondition.getStaffTrainingMobilitySpec().stream().map(this::convertToStaffTrainingMobilitySpec).collect(Collectors.toList()));
        }
        if (cooperationCondition.getStudentStudiesMobilitySpec() != null) {
            cooperationConditionResponse.getStudentStudiesMobilitySpec().addAll(cooperationCondition.getStudentStudiesMobilitySpec().stream().map(this::convertToStudentMobilitySpecification).collect(Collectors.toList()));
        }
        if (cooperationCondition.getStudentTraineeshipMobilitySpec() != null) {
            cooperationConditionResponse.getStudentTraineeshipMobilitySpec().addAll(cooperationCondition.getStudentTraineeshipMobilitySpec().stream().map(this::convertToStudentTraineeshipMobilitySpec).collect(Collectors.toList()));
        }
        return cooperationConditionResponse;
    }

    private <T extends MobilitySpecification, K extends eu.erasmuswithoutpaper.iia.entity.MobilitySpecification> T convertToMobilitySpecification(K mobilitySpecification, T mobilitySpecificationResponse) {
        if (mobilitySpecification == null) {
            return null;
        }

        mobilitySpecificationResponse.setSendingHeiId(mobilitySpecification.getSendingHeiId());
        mobilitySpecificationResponse.setSendingOunitId(mobilitySpecification.getSendingOunitId());
        if(mobilitySpecification.getSendingContact() != null) {
            mobilitySpecificationResponse.getSendingContact().addAll(mobilitySpecification.getSendingContact().stream().map(this::convertToContact).collect(Collectors.toList()));
        }
        mobilitySpecificationResponse.setReceivingHeiId(mobilitySpecification.getReceivingHeiId());
        mobilitySpecificationResponse.setReceivingOunitId(mobilitySpecification.getReceivingOunitId());
        if(mobilitySpecification.getReceivingContact() != null) {
            mobilitySpecificationResponse.getReceivingContact().addAll(mobilitySpecification.getReceivingContact().stream().map(this::convertToContact).collect(Collectors.toList()));
        }
        mobilitySpecificationResponse.setReceivingFirstAcademicYearId(mobilitySpecification.getReceivingFirstAcademicYearId());
        mobilitySpecificationResponse.setReceivingLastAcademicYearId(mobilitySpecification.getReceivingLastAcademicYearId());
        mobilitySpecificationResponse.setMobilitiesPerYear(convertToMobilityPerYear(mobilitySpecification.getMobilitiesPerYear()));
        if(mobilitySpecification.getRecommendedLanguageSkill() != null) {
            mobilitySpecificationResponse.getRecommendedLanguageSkill().addAll(mobilitySpecification.getRecommendedLanguageSkill().stream().map(this::convertToRecommendedLanguageSkill).collect(Collectors.toList()));
        }
        if(mobilitySpecification.getSubjectArea() != null) {
            mobilitySpecificationResponse.getSubjectArea().addAll(mobilitySpecification.getSubjectArea().stream().map(this::convertToSubjectArea).collect(Collectors.toList()));
        }
        mobilitySpecificationResponse.setOtherInfoTerms(mobilitySpecification.getOtherInfoTerms());

        return mobilitySpecificationResponse;
    }

    private MobilitySpecification.MobilitiesPerYear convertToMobilityPerYear(eu.erasmuswithoutpaper.iia.entity.MobilitiesPerYear mobilitiesPerYear) {
        if (mobilitiesPerYear == null) {
            return null;
        }
        MobilitySpecification.MobilitiesPerYear mobilitiesPerYearResponse = new MobilitySpecification.MobilitiesPerYear();
        mobilitiesPerYearResponse.setValue(mobilitiesPerYear.getValue());
        mobilitiesPerYearResponse.setNotYetDefined(mobilitiesPerYear.getNotYetDefined());

        return mobilitiesPerYearResponse;
    }

    private RecommendedLanguageSkill convertToRecommendedLanguageSkill(eu.erasmuswithoutpaper.iia.entity.RecommendedLanguageSkill recommendedLanguageSkill) {
        if (recommendedLanguageSkill == null) {
            return null;
        }
        RecommendedLanguageSkill recommendedLanguageSkillResponse = new RecommendedLanguageSkill();
        recommendedLanguageSkillResponse.setCefrLevel(recommendedLanguageSkill.getCefrLevel());
        recommendedLanguageSkillResponse.setLanguage(recommendedLanguageSkill.getLanguage());
        recommendedLanguageSkillResponse.setNotYetDefined(recommendedLanguageSkill.getNotYetDefined());
        recommendedLanguageSkillResponse.setSubjectArea(convertToSubjectArea(recommendedLanguageSkill.getSubjectArea()));

        return recommendedLanguageSkillResponse;
    }

    private SubjectArea convertToSubjectArea(eu.erasmuswithoutpaper.iia.entity.SubjectArea subjectArea) {
        if (subjectArea == null) {
            return null;
        }
        SubjectArea subjectAreaResponse = new SubjectArea();
        subjectAreaResponse.setIscedClarification(subjectArea.getIscedClarification());

        SubjectArea.IscedFCode iscedFCode = new SubjectArea.IscedFCode();
        iscedFCode.setValue(subjectArea.getIscedFCode());
        subjectAreaResponse.setIscedFCode(iscedFCode);

        return subjectAreaResponse;
    }

    private StaffTeacherMobilitySpec convertToStaffTeacherMobilitySpec(eu.erasmuswithoutpaper.iia.entity.StaffMobilitySpecification staffMobilitySpecification) {
        if (staffMobilitySpecification == null) {
            return null;
        }

        StaffTeacherMobilitySpec staffTeacherMobilitySpec = convertToMobilitySpecification(staffMobilitySpecification, new StaffTeacherMobilitySpec());

        staffTeacherMobilitySpec.setTotalDaysPerYear(staffMobilitySpecification.getTotalDaysPerYear());

        return staffTeacherMobilitySpec;
    }

    private StaffTrainingMobilitySpec convertToStaffTrainingMobilitySpec(eu.erasmuswithoutpaper.iia.entity.StaffMobilitySpecification staffMobilitySpecification) {
        if (staffMobilitySpecification == null) {
            return null;
        }

        StaffTrainingMobilitySpec staffTeacherMobilitySpec = convertToMobilitySpecification(staffMobilitySpecification, new StaffTrainingMobilitySpec());

        staffTeacherMobilitySpec.setTotalDaysPerYear(staffMobilitySpecification.getTotalDaysPerYear());

        return staffTeacherMobilitySpec;
    }

    private StudentStudiesMobilitySpec convertToStudentMobilitySpecification(eu.erasmuswithoutpaper.iia.entity.StudentMobilitySpecification staffMobilitySpecification) {
        if (staffMobilitySpecification == null) {
            return null;
        }

        StudentStudiesMobilitySpec staffTeacherMobilitySpec = convertToMobilitySpecification(staffMobilitySpecification, new StudentStudiesMobilitySpec());

        staffTeacherMobilitySpec.setTotalMonthsPerYear(staffMobilitySpecification.getTotalMonthsPerYear());
        staffTeacherMobilitySpec.setBlended(staffMobilitySpecification.getBlended());
        staffTeacherMobilitySpec.getEqfLevel().addAll(Arrays.asList(staffMobilitySpecification.getEqfLevel()));

        return staffTeacherMobilitySpec;
    }

    private StudentTraineeshipMobilitySpec convertToStudentTraineeshipMobilitySpec(eu.erasmuswithoutpaper.iia.entity.StudentMobilitySpecification staffMobilitySpecification) {
        if (staffMobilitySpecification == null) {
            return null;
        }

        StudentTraineeshipMobilitySpec staffTeacherMobilitySpec = convertToMobilitySpecification(staffMobilitySpecification, new StudentTraineeshipMobilitySpec());

        staffTeacherMobilitySpec.setTotalMonthsPerYear(staffMobilitySpecification.getTotalMonthsPerYear());
        staffTeacherMobilitySpec.setBlended(staffMobilitySpecification.getBlended());
        staffTeacherMobilitySpec.getEqfLevel().addAll(Arrays.asList(staffMobilitySpecification.getEqfLevel()));

        return staffTeacherMobilitySpec;
    }
}