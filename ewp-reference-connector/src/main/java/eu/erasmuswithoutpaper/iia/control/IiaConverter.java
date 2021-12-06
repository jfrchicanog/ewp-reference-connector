package eu.erasmuswithoutpaper.iia.control;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification.RecommendedLanguageSkill;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTeacherMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTrainingMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentStudiesMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentTraineeshipMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class IiaConverter {
	private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
		 
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<IiasGetResponse.Iia> convertToIias(String hei_id, List<Iia> iiaList) {
        return iiaList.stream().map((Iia iia) -> {
            IiasGetResponse.Iia converted = new IiasGetResponse.Iia();
            HashMap<String, IiaPartner> uniquePartners = new HashMap<>();
            for (CooperationCondition condition : iia.getCooperationConditions()) {
                uniquePartners.put(condition.getSendingPartner().getInstitutionId(), condition.getSendingPartner());
                uniquePartners.put(condition.getReceivingPartner().getInstitutionId(), condition.getReceivingPartner());
            }

            Set<String> partnerKeys = uniquePartners.keySet();
            for (String partnerKey : partnerKeys) {
                IiaPartner partner = uniquePartners.get(partnerKey);
                converted.getPartner().add(convertToPartner(iia, partner));
            }

            /**
             * The value of `hei-id` of the first `partner` MUST match the value passed in
             * the `hei_id` request parameter,
             */
            Comparator<? super IiasGetResponse.Iia.Partner> heiIdComparator = new Comparator<IiasGetResponse.Iia.Partner>() {
                //     return 1 if rhs should be before lhs
                //     return -1 if lhs should be before rhs
                //     return 0 otherwise
                @Override
                public int compare(IiasGetResponse.Iia.Partner lhs, IiasGetResponse.Iia.Partner rhs) {
                    if (rhs.getHeiId().equals(hei_id)) {
                        return 1;
                    }
                    if (lhs.getHeiId().equals(hei_id)) {
                        return -1;
                    }
                    return 0;
                }
            };
            converted.getPartner().sort(heiIdComparator);

            converted.setCooperationConditions(convertToCooperationConditions(iia.getCooperationConditions()));
            converted.setInEffect(iia.isInEfect());
            
            try {
            	JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.Iia.CooperationConditions.class);
            	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            	
            	StringWriter sw = new StringWriter();
            	
            	//Create a copy off CooperationConditions to be used in calculateSha256 function
            	CooperationConditions cc = new CooperationConditions();
            	cc.getStaffTeacherMobilitySpec().addAll(converted.getCooperationConditions().getStaffTeacherMobilitySpec());
            	cc.getStaffTrainingMobilitySpec().addAll(converted.getCooperationConditions().getStaffTrainingMobilitySpec());
            	cc.getStudentStudiesMobilitySpec().addAll(converted.getCooperationConditions().getStudentStudiesMobilitySpec());
            	cc.getStudentTraineeshipMobilitySpec().addAll(converted.getCooperationConditions().getStudentTraineeshipMobilitySpec());
            	
            	cc = removeContactInfo(cc);
            	
            	QName qName = new QName("http//www.erasmuswithoupaper.com", "cooperation_conditions");
            	JAXBElement<IiasGetResponse.Iia.CooperationConditions> root = new JAXBElement<IiasGetResponse.Iia.CooperationConditions>(qName, IiasGetResponse.Iia.CooperationConditions.class, cc);
            	
            	jaxbMarshaller.marshal(root, sw);
            	String xmlString = sw.toString();
            	
				converted.setConditionsHash(HashCalculationUtility.calculateSha256(xmlString));
			} catch (InvalidCanonicalizerException | CanonicalizationException | NoSuchAlgorithmException | SAXException
					| IOException | ParserConfigurationException | TransformerException | JAXBException e) {
				logger.error("Can't calculate sha256", e);
			}
            return converted;
        }).collect(Collectors.toList());
    }
    
	private CooperationConditions removeContactInfo(CooperationConditions cc) {
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

	private IiasGetResponse.Iia.CooperationConditions convertToCooperationConditions(List<CooperationCondition> cooperationConditions) {
        // TODO: Add this
        Map<String, List<CooperationCondition>> ccMap = cooperationConditions
                .stream()
                .collect(Collectors.groupingBy(cc -> cc.getMobilityType().getMobilityGroup() + "-" + cc.getMobilityType().getMobilityCategory()));
                
        IiasGetResponse.Iia.CooperationConditions converted = new IiasGetResponse.Iia.CooperationConditions();

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
                    ccMap.get("Student-Studies")
                            .stream()
                            .map(this::convertToStudentTraineeshipMobilitySpec)
                            .collect(Collectors.toList()));
        }
        converted.getStudentTraineeshipMobilitySpec();

        return converted;
    }

    private IiasGetResponse.Iia.Partner convertToPartner(Iia iia, IiaPartner partner) {
        IiasGetResponse.Iia.Partner converted = new IiasGetResponse.Iia.Partner();
        converted.setHeiId(partner.getInstitutionId());
        converted.setOunitId(partner.getOrganizationUnitId());
        converted.setIiaCode(iia.getIiaCode());
        
        converted.setIiaId(iia.getId());//TODO Let me know if it is ok
        
        try {
        	if (iia.getSigningDate() != null) {
        		System.out.println(iia.getSigningDate());
        		converted.setSigningDate(ConverterHelper.convertToXmlGregorianCalendar(iia.getSigningDate()));
        	}
		} catch (DatatypeConfigurationException e) {
			 logger.error("Can't convert date", e);
		}//TODO Iia has two other properties startDate, endDate
        
        if (partner.getSigningContact() != null) {
        	Contact contact = new Contact();
        	
        	contact.setPersonGender(partner.getSigningContact().getPerson().getGender().value());
        	contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(partner.getSigningContact().getContactDetails().getMailingAddress()));
        	contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(partner.getSigningContact().getContactDetails().getStreetAddress()));
            
            converted.setSigningContact(contact);
        }
    	
        return converted;
    }      

    private StaffTeacherMobilitySpec convertToStaffTeacherMobilitySpec(CooperationCondition cc) {
        StaffTeacherMobilitySpec conv = new StaffTeacherMobilitySpec();
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }
    private StaffTrainingMobilitySpec convertToStaffTrainingMobilitySpec(CooperationCondition cc) {
        StaffTrainingMobilitySpec conv = new StaffTrainingMobilitySpec();
        addToStaffMobilitySpecification(conv, cc);
        return conv;
    }
    private StudentStudiesMobilitySpec convertToStudentStudiesMobilitySpec(CooperationCondition cc) {
        StudentStudiesMobilitySpec conv = new StudentStudiesMobilitySpec();
        addToStudentMobilitySpecification(conv, cc);
        return conv;
    }
    private StudentTraineeshipMobilitySpec convertToStudentTraineeshipMobilitySpec(CooperationCondition cc) {
        StudentTraineeshipMobilitySpec conv = new StudentTraineeshipMobilitySpec();
        addToStudentMobilitySpecification(conv, cc);
        return conv;
    }
    
    private void addToMobilitySpecification(MobilitySpecification conv , CooperationCondition cc) {
    	List<RecommendedLanguageSkill> recommendedSkills = cc.getRecommendedLanguageSkill().stream().map((langskill) ->{
    		
    		RecommendedLanguageSkill recommendedLangSkill = new RecommendedLanguageSkill();
    		
    		recommendedLangSkill.setCefrLevel(langskill.getCefrLevel());
    		recommendedLangSkill.setLanguage(langskill.getLanguage());
    		
    		if (langskill.getSubjectArea() != null) {
    			SubjectArea subjectArea= new SubjectArea();
        		subjectArea.setIscedClarification(langskill.getSubjectArea().getIscedClarification());
        		subjectArea.setIscedFCode(langskill.getSubjectArea().getIscedFCode());
        		
        		recommendedLangSkill.setSubjectArea(subjectArea);
    		}
    		
    		return recommendedLangSkill;
    	}).collect(Collectors.toList());
    	
    	conv.getReceivingAcademicYearId().addAll(cc.getReceivingAcademicYearId());
        
    	if (cc.getReceivingPartner().getOrganizationUnitId() != null) {
            conv.setReceivingOunitId(cc.getReceivingPartner().getOrganizationUnitId());
        }
        
        conv.getRecommendedLanguageSkill().addAll(recommendedSkills);
        
        if (cc.getSubjectAreas() != null && !cc.getSubjectAreas().isEmpty()) {
        	 List<SubjectArea> subjectAreas = cc.getSubjectAreas().stream().map(subject -> {
             	SubjectArea subjectArea= new SubjectArea();
             	
          		subjectArea.setIscedClarification(subject.getIscedClarification());
          		subjectArea.setIscedFCode(subject.getIscedFCode());
          		
             	return subjectArea;
             }).collect(Collectors.toList());
            
             conv.getSubjectArea().addAll(subjectAreas);
        }
       
        List<Contact> contactReceivings = cc.getReceivingPartner().getContacts().stream().map(recContact -> {
        	Contact contact = new Contact();
        	
        	contact.setPersonGender(recContact.getPerson().getGender().value());
        	
        	if (recContact.getContactDetails() != null) {
        		contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(recContact.getContactDetails().getMailingAddress()));
            	contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(recContact.getContactDetails().getStreetAddress()));
        	}
        	
        	return contact;
        }).collect(Collectors.toList());
        
        conv.getReceivingContact().addAll(contactReceivings);
        
        List<Contact> contactsSending = cc.getSendingPartner().getContacts().stream().map(sendContact -> {
        	Contact contact = new Contact();
        	
        	contact.setPersonGender(sendContact.getPerson().getGender().value());
        	
        	if (sendContact.getContactDetails() != null) {
        		contact.setMailingAddress(ConverterHelper.convertToFlexibleAddress(sendContact.getContactDetails().getMailingAddress()));
            	contact.setStreetAddress(ConverterHelper.convertToFlexibleAddress(sendContact.getContactDetails().getStreetAddress()));
        	}
        	
        	return contact;
        }).collect(Collectors.toList());
        
        conv.getSendingContact().addAll(contactsSending);
        
        if (cc.getSendingPartner().getOrganizationUnitId() != null) {
            conv.setSendingOunitId(cc.getSendingPartner().getOrganizationUnitId());
        }
        
        conv.setMobilitiesPerYear(BigInteger.valueOf(cc.getMobilityNumber().getNumber()));
        conv.setReceivingHeiId(cc.getReceivingPartner().getInstitutionId());
        conv.setSendingHeiId(cc.getSendingPartner().getInstitutionId());
        conv.setOtherInfoTerms(cc.getOtherInfoTerms());
    }
    
    private void addToStudentMobilitySpecification(StudentMobilitySpecification conv, CooperationCondition cc) {
        //conv.setAvgMonths(BigInteger.ONE);
        conv.setTotalMonthsPerYear(new BigDecimal(cc.getDuration().getNumber().toBigInteger(),2));
        
        List<Byte> eqfLevels = new ArrayList<Byte>();
        byte[] arrEqfLevel = cc.getEqfLevel();
        for (int i = 0; i <arrEqfLevel.length; i++) {
        	eqfLevels.add(new Byte(arrEqfLevel[i]));
		}
        
        conv.getEqfLevel().addAll(eqfLevels);
        
        conv.setBlended(cc.isBlended());
        
        addToMobilitySpecification(conv , cc);
    }

    private void addToStaffMobilitySpecification(StaffMobilitySpecification conv, CooperationCondition cc) {
        //conv.setAvgDays(BigInteger.ONE);
        conv.setTotalDaysPerYear(new BigDecimal(cc.getDuration().getNumber().toBigInteger(), 2));
        
        addToMobilitySpecification(conv , cc);
    }
}
