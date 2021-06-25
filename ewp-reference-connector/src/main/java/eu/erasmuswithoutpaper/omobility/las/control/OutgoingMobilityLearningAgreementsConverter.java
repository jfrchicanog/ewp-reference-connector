package eu.erasmuswithoutpaper.omobility.las.control;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.api.architecture.MultilineString;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentRecognized;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.ComponentsRecognized;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.ComponentsStudied;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement.StudentLanguageSkill;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfChangesToComponentsRecognized;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfChangesToComponentsStudied;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfChangesToComponentsStudied.InsertComponentStudied;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfChangesToComponentsStudied.RemoveComponentStudied;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.SingleChangeToAList;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.SingleChangeToAList.Reason;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.SnapshotOfComponentsRecognized;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.omobility.las.entity.OlasLanguageSkill;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.OmobilityComponentRecognized;
import eu.erasmuswithoutpaper.omobility.las.entity.SingleChange;
import eu.erasmuswithoutpaper.omobility.las.entity.Student;

public class OutgoingMobilityLearningAgreementsConverter {
    private static final Logger logger = LoggerFactory.getLogger(OutgoingMobilityLearningAgreementsConverter.class);
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public LearningAgreement convertToLearningAgreements(OlearningAgreement olearningAgreement) {
    	LearningAgreement learningAgreement = new LearningAgreement();
        
        learningAgreement.setEqfLevelStudiedAtDeparture(olearningAgreement.getEqfLevel());
        learningAgreement.setOmobilityId(olearningAgreement.getId());
        
        learningAgreement.setReceivingAcademicYearId(olearningAgreement.getReceivingAcademicTermEwpId());
        learningAgreement.setStudentIscedFCode(olearningAgreement.getStudentIscedFCode());
        
        learningAgreement.setStudentLanguageSkill(convertToStudentLanguageSkill(olearningAgreement.getStudentLanguageSkill()));
        
        try {
        	 learningAgreement.setPlannedMobilityStart(ConverterHelper.convertToXmlGregorianCalendar(olearningAgreement.getPlannedMobilityStart()));
             learningAgreement.setPlannedMobilityEnd(ConverterHelper.convertToXmlGregorianCalendar(olearningAgreement.getPlannedMobilityEnd()));
        } catch(DatatypeConfigurationException ex) {
        	logger.error("Can't convert date", ex);
        }
       
        learningAgreement.setReceivingHei(convertToMobilityInstitution(olearningAgreement.getReceivingHei()));
        learningAgreement.setSendingHei(convertToMobilityInstitution(olearningAgreement.getSendingHei()));
        
        learningAgreement.setStudent(convertToStudent(olearningAgreement.getStudent()));
        
        LearningAgreement.ComponentsRecognized theComponentRecognized = new LearningAgreement.ComponentsRecognized();
        ComponentsStudied theComponentStudied = new ComponentsStudied();
        
        fillingChanges(olearningAgreement,theComponentRecognized);
    	fillingSnapShot(olearningAgreement, theComponentRecognized);
    	
    	fillingStudiedChanges(olearningAgreement, theComponentStudied);
    	fillingStudiedSnapshot(olearningAgreement, theComponentRecognized);
    	
    	learningAgreement.setComponentsRecognized(theComponentRecognized);
        learningAgreement.setComponentsStudied(theComponentStudied);
        
        return learningAgreement;
    }

	private void fillingStudiedSnapshot(OlearningAgreement olearningAgreement,
			LearningAgreement.ComponentsRecognized theComponentRecognized) {
		SnapshotOfComponentsRecognized beforeMobilitySnapshot = new SnapshotOfComponentsRecognized();
    	beforeMobilitySnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getStudiedBeforeMobilitySnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(beforeMobilitySnapshot);
    	
    	SnapshotOfComponentsRecognized latestApprovedSnapshot = new SnapshotOfComponentsRecognized();
    	latestApprovedSnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getStudiedLatestApprovedSnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(latestApprovedSnapshot);
    	
    	SnapshotOfComponentsRecognized latestDraftSnapshot = new SnapshotOfComponentsRecognized();
    	latestDraftSnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getStudiedLatestDraftSnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(latestDraftSnapshot);
	}

	private void fillingStudiedChanges(OlearningAgreement olearningAgreement, ComponentsStudied theComponentStudied) {
		ListOfChangesToComponentsStudied beforeMobility = new ListOfChangesToComponentsStudied();
        beforeMobility.getInsertComponentStudiedOrRemoveComponentStudied().addAll(convertToSingleChangeToAList(olearningAgreement.getStudiedbeforeMobilityChanges()));
        theComponentStudied.setBeforeMobilityChanges(beforeMobility);
        
        ListOfChangesToComponentsStudied latestApproved = new ListOfChangesToComponentsStudied();
        latestApproved.getInsertComponentStudiedOrRemoveComponentStudied().addAll(convertToSingleChangeToAList(olearningAgreement.getStudiedlatestApprovedChanges()));
        theComponentStudied.setBeforeMobilityChanges(latestApproved);
        
        ListOfChangesToComponentsStudied latestDraft = new ListOfChangesToComponentsStudied();
        latestDraft.getInsertComponentStudiedOrRemoveComponentStudied().addAll(convertToSingleChangeToAList(olearningAgreement.getStudiedLatestDraftChanges()));
        theComponentStudied.setBeforeMobilityChanges(latestDraft);
	}

	private void fillingSnapShot(OlearningAgreement olearningAgreement,
			LearningAgreement.ComponentsRecognized theComponentRecognized) {
		
		SnapshotOfComponentsRecognized beforeMobilitySnapshot = new SnapshotOfComponentsRecognized();
    	beforeMobilitySnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getCmpRecognizedBeforeMobilitySnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(beforeMobilitySnapshot);
    	
    	SnapshotOfComponentsRecognized latestApprovedSnapshot = new SnapshotOfComponentsRecognized();
    	latestApprovedSnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getCmpRecognizedLatestApprovedSnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(latestApprovedSnapshot);
    	
    	SnapshotOfComponentsRecognized latestDraftSnapshot = new SnapshotOfComponentsRecognized();
    	latestDraftSnapshot.getComponentRecognized().addAll(convertToComponentRecognized(olearningAgreement.getCmpRecognizedLatestDraftSnapshot()));
    	theComponentRecognized.setBeforeMobilitySnapshot(latestDraftSnapshot);
	}

	private LearningAgreement.ComponentsRecognized fillingChanges(OlearningAgreement olearningAgreement, ComponentsRecognized theComponentRecognized) {
    	ListOfChangesToComponentsRecognized beforeChangesCmpRecognized = new ListOfChangesToComponentsRecognized();
    	beforeChangesCmpRecognized.getInsertComponentRecognizedOrRemoveComponentRecognized().addAll(convertToSingleChangeToAList(olearningAgreement.getCmpRecognizedBeforeMobilityChanges()));
    	theComponentRecognized.setBeforeMobilityChanges(beforeChangesCmpRecognized);
    	
    	ListOfChangesToComponentsRecognized latestApprovedCmpRecognized = new ListOfChangesToComponentsRecognized();
    	latestApprovedCmpRecognized.getInsertComponentRecognizedOrRemoveComponentRecognized().addAll(convertToSingleChangeToAList(olearningAgreement.getCmpRecognizedLatestApprovedChanges()));
    	theComponentRecognized.setBeforeMobilityChanges(latestApprovedCmpRecognized);
    	
    	ListOfChangesToComponentsRecognized latestDraftCmpRecognized = new ListOfChangesToComponentsRecognized();
    	latestDraftCmpRecognized.getInsertComponentRecognizedOrRemoveComponentRecognized().addAll(convertToSingleChangeToAList(olearningAgreement.getCmpRecognizedLatestDraftChanges()));
    	theComponentRecognized.setBeforeMobilityChanges(latestDraftCmpRecognized);
		return theComponentRecognized;
	}

	private List<ComponentRecognized> convertToComponentRecognized(
			List<OmobilityComponentRecognized> cmpRecognizedList) {
		
		List<ComponentRecognized> recognizedList = cmpRecognizedList.stream().map((c) -> {
			ComponentRecognized cmpRec = new ComponentRecognized();
    		
			cmpRec.setLoiId(c.getLoiId());
			cmpRec.setLosId(c.getLosId());
			
            return cmpRec;
        }).collect(Collectors.toList());
        
        return recognizedList;
	}

	private List<SingleChangeToAList> convertToSingleChangeToAList(
			List<SingleChange> list) {
		
		List<SingleChangeToAList> sglChangeList = list.stream().map((c) -> {
    		SingleChangeToAList sglChange;
    		
    		if (c.isDelete()) {
    			RemoveComponentStudied delete = new RemoveComponentStudied();
    			
    			delete.setIndex(c.getIndex());
    			
    			MultilineString multiLineStr = new MultilineString();
    			multiLineStr.setValue(c.getDisplayText());
    			
    			Reason reason = new Reason();
    			reason.setDisplayText(multiLineStr);
    			reason.setEwpReasonCode(c.getEwpReasonCode());
    			
    			delete.setReason(reason);
    			
    			sglChange = delete;
    		} else {
    			InsertComponentStudied insert = new InsertComponentStudied();
    			
    			insert.setIndex(c.getIndex());
    			
    			MultilineString multiLineStr = new MultilineString();
    			multiLineStr.setValue(c.getDisplayText());
    			
    			Reason reason = new Reason();
    			reason.setDisplayText(multiLineStr);
    			reason.setEwpReasonCode(c.getEwpReasonCode());
    			
    			insert.setReason(reason);
    			
    			eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied cmpStudied = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied();
    			cmpStudied.setLoiId(c.getComponentStudied().getLoiId());
    			cmpStudied.setLosId(c.getComponentStudied().getLosId());
    			cmpStudied.setAcademicTermDisplayName(c.getComponentStudied().getAcademicTermDisplayName());
    			cmpStudied.setTitle(c.getComponentStudied().getTitle());
    			
    			List<eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied.Credit> studiedCreditList = cmpStudied.getCredit().stream().map(credit -> {
    				eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied.Credit studiedCredit = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied.Credit();
    				
    				studiedCredit.setScheme(credit.getScheme());
    				studiedCredit.setValue(credit.getValue());
    				
    				return studiedCredit;
    			}).collect(Collectors.toList());
    			
    			cmpStudied.getCredit().addAll(studiedCreditList);
    			
    			insert.setComponentStudied(cmpStudied);
    			
    			sglChange = insert;
    		}
    		
            return sglChange;
        }).collect(Collectors.toList());
        
        return sglChangeList;
	}

	private StudentLanguageSkill convertToStudentLanguageSkill(OlasLanguageSkill studentLanguageSkill) {
		StudentLanguageSkill studentlangSkill = new StudentLanguageSkill();
		
		studentlangSkill.setLanguage(studentLanguageSkill.getLanguage());
		studentlangSkill.setCefrLevel(studentLanguageSkill.getCefrLevel());
		
		return studentlangSkill;
	}

    private MobilityInstitution convertToMobilityInstitution(eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution mobilityInstitution) {
    	MobilityInstitution mobilityInst = new MobilityInstitution();
    	
        mobilityInst.setHeiId(mobilityInstitution.getHeiId());
        mobilityInst.setOunitId(mobilityInstitution.getOunitId());
        mobilityInst.setOunitName(mobilityInstitution.getOunitName());
        
        return mobilityInst;
    }

    private LearningAgreement.Student convertToStudent(Student student) {
    	LearningAgreement.Student omobilityLasStudent = new LearningAgreement.Student();
    	
    	try {
			omobilityLasStudent.setBirthDate(ConverterHelper.convertToXmlGregorianCalendar(student.getBirthDate()));
		} catch (DatatypeConfigurationException e) {
			logger.error("Can't convert date", e);
		}
    	
    	omobilityLasStudent.setCitizenship(student.getCitizenship());
    	omobilityLasStudent.setEmail(student.getEmail());
    	omobilityLasStudent.setFamilyName(student.getFamilyName());
    	omobilityLasStudent.setGender(student.getGender());
    	omobilityLasStudent.setGivenNames(student.getGivenName());
    	
    	List<PhoneNumber> phoneNumbers = student.getPhoneNumber().stream().map(phone -> {
    		PhoneNumber phoneNumber = new PhoneNumber();
    		
    		phoneNumber.setE164(phone.getE164());
    		phoneNumber.setExt(phone.getExtensionNumber());
    		phoneNumber.setOtherFormat(phone.getOtherFormat());
    		
    		return phoneNumber;
    	}).collect(Collectors.toList());
    	
    	omobilityLasStudent.getPhoneNumber().addAll(phoneNumbers);
    	
        return omobilityLasStudent;
    }
}
