package eu.erasmuswithoutpaper.omobility.las.control;

import java.sql.Timestamp;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Component;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentList;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LasOutgoingStatsResponse.AcademicYearLaStats;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.ChangesProposal;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.StudentLanguageSkill;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfComponents;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;

public class OutgoingMobilityLearningAgreementsConverter {
    private static final Logger logger = LoggerFactory.getLogger(OutgoingMobilityLearningAgreementsConverter.class);

//--------------------------------------------------------------------------------------- CONVERT TO API ---------------------------------------------------------------------------------------

    public LearningAgreement convertToLearningAgreements(OlearningAgreement olearningAgreement) {
        LearningAgreement learningAgreement = new LearningAgreement();

        if (olearningAgreement == null) {
            return null;
        }

        try {
            if (olearningAgreement.getFromPartner() != null && olearningAgreement.getFromPartner()) {
                learningAgreement.setOmobilityId(olearningAgreement.getOmobilityId());
            } else {
                learningAgreement.setOmobilityId(olearningAgreement.getId());
            }

            learningAgreement.setSendingHei(convertToMobilityInstitution(olearningAgreement.getSendingHei()));
            learningAgreement.setReceivingHei(convertToMobilityInstitution(olearningAgreement.getReceivingHei()));

            learningAgreement.setReceivingAcademicYearId(olearningAgreement.getReceivingAcademicTermEwpId());

            learningAgreement.setStudent(convertToStudent(olearningAgreement.getStudent()));

            Calendar calendar = Calendar.getInstance();
            if (olearningAgreement.getStartDate() != null) {
                learningAgreement.setStartDate(ConverterHelper.convertToXmlGregorianCalendar(olearningAgreement.getStartDate()));
            }
            if (olearningAgreement.getStartYearMonth() != null) {
                calendar.clear();
                calendar.set(Calendar.MONTH, olearningAgreement.getStartYearMonth().getMonthValue());
                calendar.set(Calendar.YEAR, olearningAgreement.getStartYearMonth().getYear());
                Date dateStart = calendar.getTime();
                learningAgreement.setStartYearMonth(ConverterHelper.convertToXmlGregorianCalendar(dateStart));
            }
            if (olearningAgreement.getEndDate() != null) {
                learningAgreement.setEndDate(ConverterHelper.convertToXmlGregorianCalendar(olearningAgreement.getEndDate()));
            }
            if (olearningAgreement.getEndYearMonth() != null) {
                calendar.clear();
                calendar.set(Calendar.MONTH, olearningAgreement.getEndYearMonth().getMonthValue());
                calendar.set(Calendar.YEAR, olearningAgreement.getEndYearMonth().getYear());
                Date date = calendar.getTime();
                learningAgreement.setEndYearMonth(ConverterHelper.convertToXmlGregorianCalendar(date));
            }

            learningAgreement.setEqfLevelStudiedAtDeparture(olearningAgreement.getEqfLevelStudiedAtDeparture());

            learningAgreement.setIscedFCode(olearningAgreement.getIscedFCode());
            learningAgreement.setIscedClarification(olearningAgreement.getIscedClarification());

            learningAgreement.setStudentLanguageSkill(convertToStudentLanguageSkill(olearningAgreement.getStudentLanguageSkill()));

            learningAgreement.setFirstVersion(convertToListOfComponents(olearningAgreement.getFirstVersion()));
            learningAgreement.setApprovedChanges(convertToListOfComponents(olearningAgreement.getApprovedChanges()));

            learningAgreement.setChangesProposal(convertToChangesProposal(olearningAgreement.getChangesProposal()));

            learningAgreement.setLearningOutcomesUrl(olearningAgreement.getLearningOutcomesUrl());
            learningAgreement.setProvisionsUrl(olearningAgreement.getProvisionsUrl());

        } catch (DatatypeConfigurationException e) {
            logger.error("Can't convert date", e);
        }

        return learningAgreement;
    }

    private ChangesProposal convertToChangesProposal(
            eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal ochangesProposal)
            throws DatatypeConfigurationException {
        ListOfComponents tmpChangesProposal = convertToListOfComponents(ochangesProposal);

        if (tmpChangesProposal == null) {
            return null;
        }

        ChangesProposal changesProposal = new ChangesProposal();
        changesProposal.setComponentsStudied(tmpChangesProposal.getComponentsStudied());
        changesProposal.setComponentsRecognized(tmpChangesProposal.getComponentsRecognized());
        changesProposal.setVirtualComponents(tmpChangesProposal.getVirtualComponents());
        changesProposal.setBlendedMobilityComponents(tmpChangesProposal.getBlendedMobilityComponents());
        changesProposal.setShortTermDoctoralComponents(tmpChangesProposal.getShortTermDoctoralComponents());
        changesProposal.setStudentSignature(tmpChangesProposal.getStudentSignature());
        changesProposal.setSendingHeiSignature(tmpChangesProposal.getSendingHeiSignature());
        changesProposal.setReceivingHeiSignature(tmpChangesProposal.getReceivingHeiSignature());

        eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student student = convertToStudent(ochangesProposal.getStudent());
        changesProposal.setStudent(student);
        changesProposal.setId(ochangesProposal.getId_changeProposal());

        return changesProposal;
    }

    private eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student convertToStudent(Student oStudent) {
        eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student student = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student();

        if (oStudent == null) {
            return null;
        }

        student.setGivenNames(oStudent.getGivenNames());
        student.setFamilyName(oStudent.getFamilyName());
        student.setGlobalId(oStudent.getGlobalId());

        try {
            if (oStudent.getBirthDate() != null) {
                student.setBirthDate(ConverterHelper.convertToXmlGregorianCalendar(oStudent.getBirthDate()));
            }
        } catch (DatatypeConfigurationException e) {
            logger.error("Can't convert date", e);
        }

        student.setCitizenship(oStudent.getCitizenship());
        student.setGender(oStudent.getGender());
        student.setEmail(oStudent.getEmail());

        return student;
    }

    private ListOfComponents convertToListOfComponents(eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents olistOfCmp)
            throws DatatypeConfigurationException {
        ListOfComponents approvalChanges = new ListOfComponents();
        if (olistOfCmp == null) {
            return null;
        }

        ComponentList componentsStudied = convertToComponentsList(olistOfCmp.getComponentsStudied());
        approvalChanges.setComponentsStudied(componentsStudied);

        ComponentList componentsRecognized = convertToComponentsList(olistOfCmp.getComponentsRecognized());
        approvalChanges.setComponentsRecognized(componentsRecognized);

        ComponentList virtualComponents = convertToComponentsList(olistOfCmp.getVirtualComponents());
        approvalChanges.setVirtualComponents(virtualComponents);

        ComponentList blendedMobility = convertToComponentsList(olistOfCmp.getBlendedMobilityComponents());
        approvalChanges.setBlendedMobilityComponents(blendedMobility);

        ComponentList shortTemDoctoral = convertToComponentsList(olistOfCmp.getShortTermDoctoralComponents());
        approvalChanges.setShortTermDoctoralComponents(shortTemDoctoral);

        approvalChanges.setStudentSignature(convertToSignature(olistOfCmp.getStudentSignature()));
        approvalChanges.setSendingHeiSignature(convertToSignature(olistOfCmp.getSendingHeiSignature()));
        approvalChanges.setReceivingHeiSignature(convertToSignature(olistOfCmp.getReceivingHeiSignature()));

        return approvalChanges;
    }

    private eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature convertToSignature(Signature localReceivingHeiSig)
            throws DatatypeConfigurationException {
        eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature receivingHeiSig = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();

        if (localReceivingHeiSig == null) {
            return null;
        }

        receivingHeiSig.setSignerName(localReceivingHeiSig.getSignerName());
        receivingHeiSig.setSignerPosition(localReceivingHeiSig.getSignerPosition());
        receivingHeiSig.setSignerEmail(localReceivingHeiSig.getSignerEmail());
        if (localReceivingHeiSig.getTimestamp() != null) {
            receivingHeiSig.setTimestamp(ConverterHelper.convertToXmlGregorianCalendar(localReceivingHeiSig.getTimestamp(), localReceivingHeiSig.getTimeZone()));
        }
        receivingHeiSig.setSignerApp(localReceivingHeiSig.getSignerApp());

        return receivingHeiSig;
    }

    private ComponentList convertToComponentsList(List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oLasComponents) {
        ComponentList componentList = new ComponentList();

        if (oLasComponents == null) {
            return null;
        }

        List<Component> components = oLasComponents.stream().map((cmp) -> {
            Component component = new Component();

            component.setLosId(cmp.getLosId());
            component.setLosCode(cmp.getLosCode());
            component.setTitle(cmp.getTitle());
            component.setLoiId(cmp.getLoiId());

            if (cmp.getTermId() != null) {
                TermId termId = new TermId();
                termId.setTermNumber(cmp.getTermId().getTermNumber());
                termId.setTotalTerms(cmp.getTermId().getTotalTerms());

                component.setTermId(termId);
            } else {
                component.setTermId(null);
            }

            if (cmp.getCredit() != null) {
                component.getCredit().addAll(cmp.getCredit().stream().map((credit) -> {
                    Component.Credit cred = new Component.Credit();
                    credit.setScheme(credit.getScheme());
                    credit.setValue(credit.getValue());
                    return cred;
                }).collect(Collectors.toList()));
            }

            component.setRecognitionConditions(cmp.getRecognitionConditions());
            component.setShortDescription(cmp.getShortDescription());
            component.setStatus(cmp.getStatus());
            component.setReasonCode(cmp.getReasonCode());
            component.setReasonText(cmp.getReasonText());

            return component;
        }).collect(Collectors.toList());

        componentList.getComponent().addAll(components);
        return componentList;
    }

    private StudentLanguageSkill convertToStudentLanguageSkill(OlasLanguageSkill studentLanguageSkill) {
        StudentLanguageSkill studentlangSkill = new StudentLanguageSkill();

        if (studentLanguageSkill == null) {
            return null;
        }

        studentlangSkill.setLanguage(studentLanguageSkill.getLanguage());
        studentlangSkill.setCefrLevel(studentLanguageSkill.getCefrLevel());

        return studentlangSkill;
    }

    private MobilityInstitution convertToMobilityInstitution(eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution mobilityInstitution) {
        MobilityInstitution mobilityInst = new MobilityInstitution();

        if (mobilityInstitution == null) {
            return null;
        }

        mobilityInst.setHeiId(mobilityInstitution.getHeiId());
        mobilityInst.setOunitName(mobilityInstitution.getOunitName());
        mobilityInst.setOunitId(mobilityInstitution.getOunitId());

        mobilityInst.setContactPerson(convertToContactPerson(mobilityInstitution.getContactPerson()));

        return mobilityInst;
    }

    private MobilityInstitution.ContactPerson convertToContactPerson(eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson contactPerson) {
        MobilityInstitution.ContactPerson contactPersonApi = new MobilityInstitution.ContactPerson();

        if (contactPerson == null) {
            return null;
        }

        contactPersonApi.setGivenNames(contactPerson.getGivenNames());
        contactPersonApi.setFamilyName(contactPerson.getFamilyName());
        contactPersonApi.setEmail(contactPerson.getEmail());
        contactPersonApi.setPhoneNumber(convertToPhoneNumber(contactPerson.getPhoneNumber()));
        return contactPersonApi;
    }

    private PhoneNumber convertToPhoneNumber(eu.erasmuswithoutpaper.omobility.las.entity.OmobilityPhoneNumber phoneNumber) {
        PhoneNumber phoneNumberApi = new PhoneNumber();

        if (phoneNumber == null) {
            return null;
        }

        phoneNumberApi.setE164(phoneNumber.getE164());
        phoneNumberApi.setExt(phoneNumber.getExtensionNumber());
        phoneNumberApi.setOtherFormat(phoneNumber.getOtherFormat());

        return phoneNumberApi;
    }

    public AcademicYearLaStats convertToLearningAgreementsStats(
            eu.erasmuswithoutpaper.omobility.las.entity.AcademicYearLaStats m) {
        AcademicYearLaStats apiAcademicYearLaStats = new AcademicYearLaStats();

        if (m == null) {
            return null;
        }

        apiAcademicYearLaStats.setLaOutgoingLatestVersionApproved(m.getLaOutgoingLatestVersionApproved());
        apiAcademicYearLaStats.setLaOutgoingLatestVersionAwaiting(m.getLaOutgoingLatestVersionAwaiting());
        apiAcademicYearLaStats.setLaOutgoingLatestVersionRejected(m.getLaOutgoingLatestVersionRejected());
        apiAcademicYearLaStats.setLaOutgoingModifiedAfterApproval(m.getLaOutgoingModifiedAfterApproval());
        apiAcademicYearLaStats.setLaOutgoingTotal(m.getLaOutgoingTotal());
        apiAcademicYearLaStats.setLaOutgoingNotModifiedAfterApproval(m.getLaOutgoingNotModifiedAfterApproval());
        apiAcademicYearLaStats.setReceivingAcademicYearId(m.getReceivingAcademicYearId());

        return apiAcademicYearLaStats;
    }

//--------------------------------------------------------------------------------------- CONVERT TO ENTITY ---------------------------------------------------------------------------------------

    public OlearningAgreement convertToOlearningAgreement(LearningAgreement la, boolean fromPartner, OlearningAgreement original) {
        if (la == null) {
            return null;
        }

        OlearningAgreement ola = new OlearningAgreement();

        if (fromPartner) {
            ola.setOmobilityId(la.getOmobilityId());
            if (original != null) {
                ola.setId(original.getId());
            }
        } else {
            ola.setId(la.getOmobilityId());
        }
        ola.setFromPartner(fromPartner);

        ola.setReceivingHei(convertToMobilityInstitution(la.getReceivingHei(), original != null ? original.getReceivingHei() : null));
        ola.setSendingHei(convertToMobilityInstitution(la.getSendingHei(), original != null ? original.getSendingHei() : null));

        ola.setReceivingAcademicTermEwpId(la.getReceivingAcademicYearId());

        ola.setStudent(convertToStudent(la.getStudent(), original != null ? original.getStudent() : null));

        if (la.getStartDate() != null) {
            ola.setStartDate(la.getStartDate().toGregorianCalendar().getTime());
        }
        if (la.getStartYearMonth() != null) {
            ola.setStartYearMonth(YearMonth.from(la.getStartYearMonth().toGregorianCalendar().getTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
        }
        if (la.getEndDate() != null) {
            ola.setEndDate(la.getEndDate().toGregorianCalendar().getTime());
        }
        if (la.getEndYearMonth() != null) {
            ola.setEndYearMonth(YearMonth.from(la.getEndYearMonth().toGregorianCalendar().getTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
        }

        ola.setEqfLevelStudiedAtDeparture(la.getEqfLevelStudiedAtDeparture());
        ola.setIscedFCode(la.getIscedFCode());
        ola.setIscedClarification(la.getIscedClarification());

        ola.setStudentLanguageSkill(convertToStudentLanguageSkill(la.getStudentLanguageSkill(), original != null ? original.getStudentLanguageSkill() : null));
        ola.setFirstVersion(convertToListOfComponents(la.getFirstVersion(), original != null ? original.getFirstVersion() : null));
        ola.setApprovedChanges(convertToListOfComponents(la.getApprovedChanges(), original != null ? original.getApprovedChanges() : null));
        ola.setChangesProposal(convertToChangesProposal(la.getChangesProposal(), original != null ? original.getChangesProposal() : null));

        ola.setLearningOutcomesUrl(la.getLearningOutcomesUrl());
        ola.setProvisionsUrl(la.getProvisionsUrl());

        return ola;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution convertToMobilityInstitution(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution mi, eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution original) {
        if (mi == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution m = new eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution();

        if (original != null) {
            m.setId(original.getId());
        }
        m.setHeiId(mi.getHeiId());
        m.setOunitId(mi.getOunitId());
        m.setOunitName(mi.getOunitName());

        m.setContactPerson(convertToContactPerson(mi.getContactPerson(), original != null ? original.getContactPerson() : null));

        return m;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson convertToContactPerson(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution.ContactPerson cp, ContactPerson original) {
        if (cp == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson c = new eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson();

        if (original != null) {
            c.setId(original.getId());
        }
        c.setGivenNames(cp.getGivenNames());
        c.setFamilyName(cp.getFamilyName());
        c.setEmail(cp.getEmail());

        c.setPhoneNumber(convertToPhoneNumber(cp.getPhoneNumber(), original != null ? original.getPhoneNumber() : null));

        return c;
    }

    private OmobilityPhoneNumber convertToPhoneNumber(PhoneNumber pn, OmobilityPhoneNumber original) {
        if (pn == null) {
            return null;
        }

        OmobilityPhoneNumber p = new OmobilityPhoneNumber();

        if (original != null) {
            p.setId(original.getId());
        }
        p.setE164(pn.getE164());
        if (pn.getExt() != null) {
            p.setExtensionNumber(pn.getExt().toString());
        }
        p.setOtherFormat(pn.getOtherFormat());

        return p;
    }

    private Student convertToStudent(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student s, Student original) {
        if (s == null) {
            return null;
        }

        Student student = new Student();

        if (original != null) {
            student.setId(original.getId());
        }
        student.setGivenNames(s.getGivenNames());
        student.setFamilyName(s.getFamilyName());
        student.setGlobalId(s.getGlobalId());
        student.setCitizenship(s.getCitizenship());
        student.setGender(s.getGender());
        student.setEmail(s.getEmail());

        if (s.getBirthDate() != null) {
            student.setBirthDate(s.getBirthDate().toGregorianCalendar().getTime());
        }

        return student;
    }

    private OlasLanguageSkill convertToStudentLanguageSkill(LearningAgreement.StudentLanguageSkill sls, OlasLanguageSkill original) {
        if (sls == null) {
            return null;
        }

        OlasLanguageSkill studentLanguageSkill = new OlasLanguageSkill();

        if (original != null) {
            studentLanguageSkill.setId(original.getId());
        }
        studentLanguageSkill.setCefrLevel(sls.getCefrLevel());
        studentLanguageSkill.setLanguage(sls.getLanguage());

        return studentLanguageSkill;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents convertToListOfComponents(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfComponents loc, eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents original) {
        if (loc == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents listOfComponents = new eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents();

        if (original != null) {
            listOfComponents.setId(original.getId());
        }

        if (loc.getComponentsStudied() != null && loc.getComponentsStudied().getComponent() != null) {
            listOfComponents.setComponentsStudied(loc.getComponentsStudied().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getComponentsRecognized() != null && loc.getComponentsRecognized().getComponent() != null) {
            listOfComponents.setComponentsRecognized(loc.getComponentsRecognized().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getVirtualComponents() != null && loc.getVirtualComponents().getComponent() != null) {
            listOfComponents.setVirtualComponents(loc.getVirtualComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getBlendedMobilityComponents() != null && loc.getBlendedMobilityComponents().getComponent() != null) {
            listOfComponents.setBlendedMobilityComponents(loc.getBlendedMobilityComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getShortTermDoctoralComponents() != null && loc.getShortTermDoctoralComponents().getComponent() != null) {
            listOfComponents.setShortTermDoctoralComponents(loc.getShortTermDoctoralComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }

        listOfComponents.setStudentSignature(convertToSignature(loc.getStudentSignature(), original != null ? original.getStudentSignature() : null));
        listOfComponents.setSendingHeiSignature(convertToSignature(loc.getSendingHeiSignature(), original != null ? original.getSendingHeiSignature() : null));
        listOfComponents.setReceivingHeiSignature(convertToSignature(loc.getReceivingHeiSignature(), original != null ? original.getReceivingHeiSignature() : null));

        return listOfComponents;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.Component convertToComponent(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Component c) {
        if (c == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.Component component = new eu.erasmuswithoutpaper.omobility.las.entity.Component();

        component.setLosId(c.getLosId());
        component.setLosCode(c.getLosCode());
        component.setTitle(c.getTitle());
        component.setLoiId(c.getLoiId());

        component.setTermId(convertToTermId(c.getTermId()));

        if (c.getCredit() != null) {
            component.setCredit(c.getCredit().stream().map(this::convertToCredit).collect(Collectors.toList()));
        }

        component.setRecognitionConditions(c.getRecognitionConditions());
        component.setShortDescription(c.getShortDescription());
        component.setStatus(c.getStatus());
        component.setReasonCode(c.getReasonCode());
        component.setReasonText(c.getReasonText());

        return component;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.TermId convertToTermId(https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId ti) {
        if (ti == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.TermId termId = new eu.erasmuswithoutpaper.omobility.las.entity.TermId();

        termId.setTermNumber(ti.getTermNumber());
        termId.setTotalTerms(ti.getTotalTerms());

        return termId;
    }

    private Credit convertToCredit(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Component.Credit c) {
        if (c == null) {
            return null;
        }

        Credit credit = new Credit();

        credit.setScheme(c.getScheme());
        credit.setValue(c.getValue());

        return credit;
    }

    private Signature convertToSignature(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature s, Signature original) {
        if (s == null) {
            return null;
        }

        Signature signature = new Signature();

        if (original != null) {
            signature.setId(original.getId());
        }
        signature.setSignerName(s.getSignerName());
        signature.setSignerPosition(s.getSignerPosition());
        signature.setSignerEmail(s.getSignerEmail());

        if (s.getTimestamp() != null) {
            try {
                GregorianCalendar gcal = new GregorianCalendar();
                XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

                // Convert XMLGregorianCalendar to java.util.Date and then to Timestamp
                signature.setTimestamp(new Timestamp(xmlCal.toGregorianCalendar().getTimeInMillis()));

                // Get TimeZone as String
                TimeZone timeZone = xmlCal.toGregorianCalendar().getTimeZone();
                signature.setTimeZone(timeZone.getID());
            } catch (Exception e) {
                logger.error("Can't convert date", e);
            }
        }

        signature.setSignerApp(s.getSignerApp());

        return signature;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal convertToChangesProposal(LearningAgreement.ChangesProposal cp, eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal original) {
        if (cp == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal changesProposal = new eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal();

        if (original != null) {
            changesProposal.setId(original.getId());
        }
        if (cp.getComponentsStudied() != null && cp.getComponentsStudied().getComponent() != null) {
            changesProposal.setComponentsStudied(cp.getComponentsStudied().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getComponentsRecognized() != null && cp.getComponentsRecognized().getComponent() != null) {
            changesProposal.setComponentsRecognized(cp.getComponentsRecognized().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getVirtualComponents() != null && cp.getVirtualComponents().getComponent() != null) {
            changesProposal.setVirtualComponents(cp.getVirtualComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getBlendedMobilityComponents() != null && cp.getBlendedMobilityComponents().getComponent() != null) {
            changesProposal.setBlendedMobilityComponents(cp.getBlendedMobilityComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getShortTermDoctoralComponents() != null && cp.getShortTermDoctoralComponents().getComponent() != null) {
            changesProposal.setShortTermDoctoralComponents(cp.getShortTermDoctoralComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }

        changesProposal.setStudentSignature(convertToSignature(cp.getStudentSignature(), original != null ? original.getStudentSignature() : null));
        changesProposal.setSendingHeiSignature(convertToSignature(cp.getSendingHeiSignature(), original != null ? original.getSendingHeiSignature() : null));
        changesProposal.setReceivingHeiSignature(convertToSignature(cp.getReceivingHeiSignature(), original != null ? original.getReceivingHeiSignature() : null));

        changesProposal.setStudent(convertToStudent(cp.getStudent(), original != null ? original.getStudent() : null));

        changesProposal.setId_changeProposal(cp.getId());

        return changesProposal;
    }

//--------------------------------------------------------------------------------------- CONVERT FOR APPROVAL ---------------------------------------------------------------------------------------

    public ApprovedProposal approveCmpStudiedDraft(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest request) {
        ApprovedProposal appCmp = new ApprovedProposal();

        if (request == null || request.getApproveProposalV1() == null) {
            return null;
        }

        appCmp.setOmobilityId(request.getApproveProposalV1().getOmobilityId());
        appCmp.setChangesProposalId(request.getApproveProposalV1().getChangesProposalId());

        appCmp.setSignature(convertToSignature(request.getApproveProposalV1().getSignature(), null));

        return appCmp;
    }

    public eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents getListOfComponents(eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal changesProposal, ApprovedProposal appCmp) {
        eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents cmp = new eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents();

        cmp.setComponentsStudied(changesProposal.getComponentsStudied());
        cmp.setComponentsRecognized(changesProposal.getComponentsRecognized());
        cmp.setBlendedMobilityComponents(changesProposal.getBlendedMobilityComponents());
        cmp.setVirtualComponents(changesProposal.getVirtualComponents());
        cmp.setShortTermDoctoralComponents(changesProposal.getShortTermDoctoralComponents());
        cmp.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
        if (appCmp.getSignature() != null) {
            cmp.setReceivingHeiSignature(appCmp.getSignature());
        }
        cmp.setStudentSignature(changesProposal.getStudentSignature());
        return cmp;
    }

//--------------------------------------------------------------------------------------- CONVERT FOR REJECTION ---------------------------------------------------------------------------------------

    public CommentProposal rejectCmpStudiedDraft(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest request) {
        CommentProposal commentProposal = new CommentProposal();

        commentProposal.setOmobilityId(request.getCommentProposalV1().getOmobilityId());
        commentProposal.setChangesProposalId(request.getCommentProposalV1().getChangesProposalId());
        commentProposal.setComment(request.getCommentProposalV1().getComment());

        commentProposal.setSignature(convertToSignature(request.getCommentProposalV1().getSignature(), null));

        return commentProposal;
    }
}
