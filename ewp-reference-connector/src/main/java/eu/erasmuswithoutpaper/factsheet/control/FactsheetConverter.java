package eu.erasmuswithoutpaper.factsheet.control;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.api.architecture.HTTPWithOptionalLang;
import eu.erasmuswithoutpaper.api.factsheet.CalendarEntry;
import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse;
import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.Accessibility;
import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.AdditionalInfo;
import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.Calendar;
import eu.erasmuswithoutpaper.api.factsheet.InformationEntry;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.factsheet.entity.AdditionalRequirement;
import eu.erasmuswithoutpaper.factsheet.entity.FactsheetAdditionalInfo;
import eu.erasmuswithoutpaper.factsheet.entity.LanguageItem;
import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class FactsheetConverter {
	private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
	
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<FactsheetResponse.Factsheet> convertToFactsheet(List<eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet> factsheetList) {
    	return factsheetList.stream().map(factsheet -> {
    		FactsheetResponse.Factsheet converted = new FactsheetResponse.Factsheet();
    		
    		converted.setHeiId(factsheet.getHeiId());
    		converted.setTorWeeksLimit(factsheet.getTorWeeksLimit());
    		converted.setDecisionWeeksLimit(factsheet.getDecisionWeeksLimit());
    		
    		 try {
    			 
    			 Calendar factsheetCalendar = convertToCalendar(factsheet);
    			 converted.setCalendar(factsheetCalendar);
    			 
    			 InformationEntry visaInfo = convertToVisaInfo(factsheet);
    			 converted.setVisaInfo(visaInfo);
    			 
    			 InformationEntry housingInfo = convertToHousingInfo(factsheet);
    			 converted.setHousingInfo(housingInfo);
    			 
    			 InformationEntry applicationInfo = convertToApplicationInfo(factsheet);
    			 converted.setApplicationInfo(applicationInfo);
    			 
    			 InformationEntry insuranceInfo = convertToInsuranceInfo(factsheet);
    			 converted.setInsuranceInfo(insuranceInfo);
    			 
    			 converted.getAdditionalInfo().addAll(convertToAdditionalInfo(factsheet.getAdditionalInfo()));
    			 converted.getAdditionalRequirement().addAll(convertToAdditionalRequirement(factsheet.getAdditionalRequirements()));
    			 converted.getAccessibility().addAll(convertToAccessibility(factsheet.getAccessibility()));
    			 
    	        } catch (DatatypeConfigurationException ex) {
    	            logger.error("Can't convert date", ex);
    	        }
    		
    		return converted;
    	}).collect(Collectors.toList());
    }

	private InformationEntry convertToInsuranceInfo(MobilityFactsheet factsheet) {
		InformationEntry insuranceInfo = new InformationEntry();
		 
		 insuranceInfo.setEmail(factsheet.getVisaInfo().getEmail());
		 insuranceInfo.setPhoneNumber(ConverterHelper.convertToPhoneNumber(factsheet.getInsuranceInfo().getPhoneNumber()));
		 
		 insuranceInfo.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(factsheet.getInsuranceInfo().getUrl()));
		return insuranceInfo;
	}

	private InformationEntry convertToApplicationInfo(MobilityFactsheet factsheet) {
		InformationEntry applicationInfo = new InformationEntry();
		 
		 applicationInfo.setEmail(factsheet.getVisaInfo().getEmail());
		 applicationInfo.setPhoneNumber(ConverterHelper.convertToPhoneNumber(factsheet.getApplicationInfo().getPhoneNumber()));
		 
		 applicationInfo.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(factsheet.getApplicationInfo().getUrl()));
		return applicationInfo;
	}

	private InformationEntry convertToHousingInfo(MobilityFactsheet factsheet) {
		InformationEntry housingInfo = new InformationEntry();
		 
		 housingInfo.setEmail(factsheet.getVisaInfo().getEmail());
		 housingInfo.setPhoneNumber(ConverterHelper.convertToPhoneNumber(factsheet.getHousingInfo().getPhoneNumber()));
		 
		 housingInfo.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(factsheet.getHousingInfo().getUrl()));
		return housingInfo;
	}

	private InformationEntry convertToVisaInfo(MobilityFactsheet factsheet) {
		InformationEntry visaInfo = new InformationEntry();
		 
		 visaInfo.setEmail(factsheet.getVisaInfo().getEmail());
		 visaInfo.setPhoneNumber(ConverterHelper.convertToPhoneNumber(factsheet.getVisaInfo().getPhoneNumber()));
		 
		 visaInfo.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(factsheet.getVisaInfo().getUrl()));
		 return visaInfo;
	}

	private Calendar convertToCalendar(MobilityFactsheet factsheet) throws DatatypeConfigurationException {
		Calendar factsheetCalendar = new Calendar();
		 
		 CalendarEntry studentAppCalendar = new CalendarEntry();
		 
		 Date springTerm  = factsheet.getStudentApplicationTerm().getSpringTerm();
		 Date autumnTerm  = factsheet.getStudentApplicationTerm().getAutumnTerm();
		 studentAppCalendar.setAutumnTerm(ConverterHelper.convertToXmlGregorianCalendar(autumnTerm));
		 studentAppCalendar.setSpringTerm(ConverterHelper.convertToXmlGregorianCalendar(springTerm));
		 
		 CalendarEntry studentNominationCalendar = new CalendarEntry();
		 
		 Date nominationSpringTerm  = factsheet.getStudentApplicationTerm().getSpringTerm();
		 Date nominationAutumnTerm  = factsheet.getStudentApplicationTerm().getAutumnTerm();
		 studentNominationCalendar.setAutumnTerm(ConverterHelper.convertToXmlGregorianCalendar(nominationAutumnTerm));
		 studentNominationCalendar.setSpringTerm(ConverterHelper.convertToXmlGregorianCalendar(nominationSpringTerm));
		 
		 factsheetCalendar.setStudentApplications(studentAppCalendar);
		 factsheetCalendar.setStudentNominations(studentNominationCalendar);
		return factsheetCalendar;
	}
    
    public static List<HTTPWithOptionalLang> convertToHttpWithOptionalLang(List<LanguageItem> languageItems) {
        return languageItems.stream().map((languageItem) -> {
            HTTPWithOptionalLang httpWithOptionalLang = new HTTPWithOptionalLang();
            httpWithOptionalLang.setLang(languageItem.getLang());
            httpWithOptionalLang.setValue(languageItem.getText());
            return httpWithOptionalLang;
        }).collect(Collectors.toList());
    }
    
    public static List<AdditionalInfo> convertToAdditionalInfo(List<FactsheetAdditionalInfo> addInfoItems) {
        return addInfoItems.stream().map((addInfo) -> {
        	AdditionalInfo addInfomation = new AdditionalInfo();
            addInfomation.setType(addInfo.getType());
            
            InformationEntry info = new InformationEntry();
            info.setEmail(addInfo.getInfo().getEmail());
            info.setPhoneNumber(ConverterHelper.convertToPhoneNumber(addInfo.getInfo().getPhoneNumber()));
            info.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(addInfo.getInfo().getUrl()));
            
            addInfomation.setInfo(info);
            return addInfomation;
        }).collect(Collectors.toList());
    }
    
    public static List<eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.AdditionalRequirement> convertToAdditionalRequirement(List<AdditionalRequirement> addReqItems) {
        return addReqItems.stream().map((addReq) -> {
        	eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.AdditionalRequirement addRequirement = new eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse.Factsheet.AdditionalRequirement();
            addRequirement.setDetails(addReq.getDetails());
            addRequirement.setName(addReq.getName());
            
            addRequirement.getInformationWebsite().addAll(convertToHttpWithOptionalLang(addReq.getInformationWebsite()));
            
            return addRequirement;
        }).collect(Collectors.toList());
    }
    
    public static List<Accessibility> convertToAccessibility(List<eu.erasmuswithoutpaper.factsheet.entity.Accessibility> accItems) {
        return accItems.stream().map((accessibilityItem) -> {
        	Accessibility accessibility = new Accessibility();
            accessibility.setDescription(accessibilityItem.getDescription());
            accessibility.setName(accessibilityItem.getName());
            accessibility.setType(accessibilityItem.getType().name().toLowerCase());
            
            InformationEntry info = new InformationEntry();
            info.setEmail(accessibilityItem.getInformation().getEmail());
            info.setPhoneNumber(ConverterHelper.convertToPhoneNumber(accessibilityItem.getInformation().getPhoneNumber()));
            info.getWebsiteUrl().addAll(convertToHttpWithOptionalLang(accessibilityItem.getInformation().getUrl()));
            
            accessibility.setInformation(info);
            
            return accessibility;
        }).collect(Collectors.toList());
    }
}
