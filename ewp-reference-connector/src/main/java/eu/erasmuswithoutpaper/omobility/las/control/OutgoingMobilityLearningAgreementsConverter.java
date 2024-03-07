package eu.erasmuswithoutpaper.omobility.las.control;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

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
import eu.erasmuswithoutpaper.omobility.las.entity.OlasLanguageSkill;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.Signature;
import eu.erasmuswithoutpaper.omobility.las.entity.Student;

public class OutgoingMobilityLearningAgreementsConverter {
    private static final Logger logger = LoggerFactory.getLogger(OutgoingMobilityLearningAgreementsConverter.class);
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public LearningAgreement convertToLearningAgreements(OlearningAgreement olearningAgreement) {
    	LearningAgreement learningAgreement = new LearningAgreement();

		if (olearningAgreement == null) {
			return null;
		}

    	try {
    		ListOfComponents approvalChanges = convertToListOfComponents(olearningAgreement.getApprovedChanges());
        	learningAgreement.setApprovedChanges(approvalChanges);

			eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal ochangesProposal = olearningAgreement.getChangesProposal();
			ChangesProposal changesProposal = convertToChangesProposal(ochangesProposal);
			learningAgreement.setChangesProposal(changesProposal);

			ListOfComponents firstVersion = convertToListOfComponents(olearningAgreement.getFirstVersion());
			learningAgreement.setApprovedChanges(firstVersion);

			Calendar calendar = Calendar.getInstance();

			if(olearningAgreement.getEndDate() != null) {
				learningAgreement.setEndDate(ConverterHelper.convertToXmlGregorianCalendar(olearningAgreement.getEndDate()));
			}
			if(olearningAgreement.getEndYearMonth() != null) {
				calendar.clear();
				calendar.set(Calendar.MONTH, olearningAgreement.getEndYearMonth().getMonthValue());
				calendar.set(Calendar.YEAR, olearningAgreement.getEndYearMonth().getYear());
				Date date = calendar.getTime();
				learningAgreement.setEndYearMonth(ConverterHelper.convertToXmlGregorianCalendar(date));
			}

            learningAgreement.setEqfLevelStudiedAtDeparture(olearningAgreement.getEqfLevelStudiedAtDeparture());
            learningAgreement.setIscedClarification(olearningAgreement.getIscedClarification());
            learningAgreement.setIscedFCode(olearningAgreement.getIscedFCode());
            learningAgreement.setLearningOutcomesUrl(olearningAgreement.getLearningOutcomesUrl());
            learningAgreement.setOmobilityId(olearningAgreement.getId());
            learningAgreement.setProvisionsUrl(olearningAgreement.getProvisionsUrl());
            learningAgreement.setReceivingAcademicYearId(olearningAgreement.getReceivingAcademicTermEwpId());
            learningAgreement.setReceivingHei(convertToMobilityInstitution(olearningAgreement.getReceivingHei()));
            learningAgreement.setSendingHei(convertToMobilityInstitution(olearningAgreement.getSendingHei()));

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
            learningAgreement.setStudent(convertToStudent(olearningAgreement.getStudent()));
            learningAgreement.setStudentLanguageSkill(convertToStudentLanguageSkill(olearningAgreement.getStudentLanguageSkill()));
            
    	} catch(DatatypeConfigurationException e) {
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
    	changesProposal.setBlendedMobilityComponents(tmpChangesProposal.getBlendedMobilityComponents());
    	changesProposal.setComponentsRecognized(tmpChangesProposal.getComponentsRecognized());
    	changesProposal.setComponentsStudied(tmpChangesProposal.getComponentsStudied());
    	changesProposal.setReceivingHeiSignature(tmpChangesProposal.getReceivingHeiSignature());
    	changesProposal.setSendingHeiSignature(tmpChangesProposal.getSendingHeiSignature());
    	changesProposal.setShortTermDoctoralComponents(tmpChangesProposal.getShortTermDoctoralComponents());
    	changesProposal.setStudentSignature(tmpChangesProposal.getStudentSignature());
    	changesProposal.setVirtualComponents(tmpChangesProposal.getVirtualComponents());

    	Student oStudent = ochangesProposal.getStudent();
    	eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student student = convertToStudent(oStudent);
    	
    	changesProposal.setStudent(student);
		return changesProposal;
	}

	private eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student convertToStudent(Student oStudent) {
		eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student student = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student();

		if(oStudent == null) {
			return null;
		}

		try {
			if(oStudent.getBirthDate() != null) {
				student.setBirthDate(ConverterHelper.convertToXmlGregorianCalendar(oStudent.getBirthDate()));
			}
		} catch (DatatypeConfigurationException e) {
			logger.error("Can't convert date", e);
		}
    	
    	student.setCitizenship(oStudent.getCitizenship());
    	student.setEmail(oStudent.getEmail());
    	student.setFamilyName(oStudent.getFamilyName());
    	student.setGender(oStudent.getGender());
    	student.setGivenNames(oStudent.getGivenNames());
    	student.setGlobalId(oStudent.getGlobalId());
		return student;
	}

	private ListOfComponents convertToListOfComponents(eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents olistOfCmp)
			throws DatatypeConfigurationException {
		ListOfComponents approvalChanges = new ListOfComponents();
    	if (olistOfCmp == null) {
			return null;
		}
    	List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oBlendedLasComponents = olistOfCmp.getBlendedMobilityComponents();
    	ComponentList blendedMobility = convertToComponentsList(oBlendedLasComponents);
    	approvalChanges.setBlendedMobilityComponents(blendedMobility);
    	
    	List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oComponentsLasComponents = olistOfCmp.getComponentsRecognized();
    	ComponentList componentsRecognized = convertToComponentsList(oComponentsLasComponents);
    	approvalChanges.setComponentsRecognized(componentsRecognized);
    	
    	List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oComponentsStudiedLasComponents = olistOfCmp.getComponentsStudied();
    	ComponentList componentsStudied = convertToComponentsList(oComponentsStudiedLasComponents);
    	approvalChanges.setComponentsStudied(componentsStudied);
    	
    	Signature localReceivingHeiSig = olistOfCmp.getReceivingHeiSignature();
    	eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature receivingHeiSig = convertToSignature(localReceivingHeiSig);
    	
    	approvalChanges.setReceivingHeiSignature(receivingHeiSig);
    	
    	Signature localSendingHeiSig = olistOfCmp.getSendingHeiSignature();
    	eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature sendingHeiSig = convertToSignature(localSendingHeiSig);
    	
    	approvalChanges.setSendingHeiSignature(sendingHeiSig);
    	
    	List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oShortTermComponents = olistOfCmp.getShortTermDoctoralComponents();
    	ComponentList shortTemDoctoral = convertToComponentsList(oShortTermComponents);
    	
    	approvalChanges.setShortTermDoctoralComponents(shortTemDoctoral);
    	
    	List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oVirtualComponents = olistOfCmp.getVirtualComponents();
    	ComponentList virtualComponents = convertToComponentsList(oVirtualComponents);
    	
    	approvalChanges.setVirtualComponents(virtualComponents);
    	
    	Signature studentSig = olistOfCmp.getStudentSignature();
    	eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature studentSignature = convertToSignature(studentSig);
    	
    	approvalChanges.setStudentSignature(studentSignature);
		return approvalChanges;
	}

	private eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature convertToSignature(Signature localReceivingHeiSig)
			throws DatatypeConfigurationException {
		eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature receivingHeiSig = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();

		if(localReceivingHeiSig == null) {
			return null;
		}

		receivingHeiSig.setSignerApp(localReceivingHeiSig.getSignerApp());
    	receivingHeiSig.setSignerEmail(localReceivingHeiSig.getSignerEmail());
    	receivingHeiSig.setSignerName(localReceivingHeiSig.getSignerName());
    	receivingHeiSig.setSignerPosition(localReceivingHeiSig.getSignerPosition());
		if(localReceivingHeiSig.getTimestamp() != null) {
			receivingHeiSig.setTimestamp(ConverterHelper.convertToXmlGregorianCalendar(localReceivingHeiSig.getTimestamp()));
		}
		return receivingHeiSig;
	}

	private ComponentList convertToComponentsList(List<eu.erasmuswithoutpaper.omobility.las.entity.Component> oLasComponents) {
		ComponentList componentList = new ComponentList();

		if (oLasComponents == null) {
			return null;
		}
    	
    	List<Component> components = oLasComponents.stream().map((cmp) -> {
    		Component component = new Component();
    		
    		component.setLoiId(cmp.getLoiId());
    		component.setLosCode(cmp.getLosCode());
    		component.setLosId(cmp.getLosId());
    		component.setReasonCode(cmp.getReasonCode());
    		component.setReasonText(cmp.getReasonText());
    		component.setRecognitionConditions(cmp.getRecognitionConditions());
    		component.setShortDescription(cmp.getShortDescription());
    		component.setStatus(cmp.getStatus());
    		
    		https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId termId = new https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId();
    		if (cmp.getTermId() != null) {
				termId.setTermNumber(cmp.getTermId().getTermNumber());
				termId.setTotalTerms(cmp.getTermId().getTotalTerms());
				component.setTermId(termId);
			}else {
				component.setTermId(null);
			}
    		
    		component.setTitle(cmp.getTitle());
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
        mobilityInst.setOunitId(mobilityInstitution.getOunitId());
        mobilityInst.setOunitName(mobilityInstitution.getOunitName());
        
        return mobilityInst;
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

}
