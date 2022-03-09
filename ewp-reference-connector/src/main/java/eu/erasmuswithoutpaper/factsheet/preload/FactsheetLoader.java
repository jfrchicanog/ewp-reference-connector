package eu.erasmuswithoutpaper.factsheet.preload;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.erasmuswithoutpaper.factsheet.entity.Accessibility;
import eu.erasmuswithoutpaper.factsheet.entity.AdditionalRequirement;
import eu.erasmuswithoutpaper.factsheet.entity.CalendarEntry;
import eu.erasmuswithoutpaper.factsheet.entity.ContactInfo;
import eu.erasmuswithoutpaper.factsheet.entity.FactsheetAdditionalInfo;
import eu.erasmuswithoutpaper.factsheet.entity.LanguageItem;
import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;
import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.PhoneNumber;

public class FactsheetLoader extends AbstractStartupLoader{

	@Override
	public void createDemoDataIkea() throws IOException {
		
		String factsheetJSON = "{'id':'F01', 'heiId': 'IK-IKEA-02'}";
		MobilityFactsheet factSheet = JsonHelper.mapToObject(MobilityFactsheet.class, factsheetJSON);
		
		completeInformation(factSheet);
		
		em.persist(factSheet);
	}

	private void completeInformation(MobilityFactsheet factSheet) throws IOException {
		CalendarEntry calendar = getStudentNominationCalendar();
		factSheet.setStudentNominationTerm(calendar);
		factSheet.setStudentApplicationTerm(calendar);
		
		ContactInfo ctcInfo = getContactInfo();
		factSheet.setApplicationInfo(ctcInfo);
		factSheet.setHousingInfo(ctcInfo);
		factSheet.setVisaInfo(ctcInfo);
		factSheet.setInsuranceInfo(ctcInfo);
		factSheet.setVisaInfo(ctcInfo);
		
		List<AdditionalRequirement> addReqs = new ArrayList<>();
		addReqs.add(getAdditionalReq());
		factSheet.setAdditionalRequirements(addReqs);
		
		factSheet.setDecisionWeeksLimit(new BigInteger("4"));
		
		factSheet.setTorWeeksLimit(new BigInteger("3"));
		
		List<Accessibility> accessibilities = new ArrayList<>();
		accessibilities.add(getAccessibility());
		factSheet.setAccessibility(accessibilities);
		
		List<FactsheetAdditionalInfo> addInfoList = new ArrayList<>();
		addInfoList.add(getAdditionalInfo());
		factSheet.setAdditionalInfo(addInfoList);
	}
	
	private void completeInformationPOM(MobilityFactsheet factSheet) throws IOException {
		CalendarEntry calendar = getStudentNominationCalendarPOM();
		factSheet.setStudentNominationTerm(calendar);
		factSheet.setStudentApplicationTerm(calendar);
		
		ContactInfo ctcInfo = getContactInfoPOM();
		factSheet.setApplicationInfo(ctcInfo);
		factSheet.setHousingInfo(ctcInfo);
		factSheet.setVisaInfo(ctcInfo);
		factSheet.setInsuranceInfo(ctcInfo);
		factSheet.setVisaInfo(ctcInfo);
		
		List<AdditionalRequirement> addReqs = new ArrayList<>();
		addReqs.add(getAdditionalReqPOM());
		factSheet.setAdditionalRequirements(addReqs);
		
		factSheet.setDecisionWeeksLimit(new BigInteger("4"));
		
		factSheet.setTorWeeksLimit(new BigInteger("3"));
		
		List<Accessibility> accessibilities = new ArrayList<>();
		accessibilities.add(getAccessibilityPOM());
		factSheet.setAccessibility(accessibilities);
		
		List<FactsheetAdditionalInfo> addInfoList = new ArrayList<>();
		addInfoList.add(getAdditionalInfoPOM());
		factSheet.setAdditionalInfo(addInfoList);
	}
	
	private void completeInformationUMA(MobilityFactsheet factSheet) throws IOException {
		CalendarEntry calendar = getStudentNominationCalendarUMA();
		factSheet.setStudentNominationTerm(calendar);
		factSheet.setStudentApplicationTerm(calendar);
		
		ContactInfo ctcInfo = getContactInfoUMA();
		factSheet.setApplicationInfo(ctcInfo);
		factSheet.setHousingInfo(ctcInfo);
		factSheet.setVisaInfo(ctcInfo);
		factSheet.setInsuranceInfo(ctcInfo);
		
		List<AdditionalRequirement> addReqs = new ArrayList<>();
		addReqs.add(getAdditionalReqUMA());
		factSheet.setAdditionalRequirements(addReqs);
		
		factSheet.setDecisionWeeksLimit(new BigInteger("4"));
		
		factSheet.setTorWeeksLimit(new BigInteger("3"));
		
		List<Accessibility> accessibilities = new ArrayList<>();
		accessibilities.add(getAccessibilityUMA());
		factSheet.setAccessibility(accessibilities);
		
		List<FactsheetAdditionalInfo> addInfoList = new ArrayList<>();
		addInfoList.add(getAdditionalInfoUMA());
		factSheet.setAdditionalInfo(addInfoList);
	}

	private FactsheetAdditionalInfo getAdditionalInfo() throws IOException {
		String json = "{'type':'info'}";
		
		FactsheetAdditionalInfo addInfo = JsonHelper.mapToObject(FactsheetAdditionalInfo.class, json);
		addInfo.setInfo(getContactInfo());
		return addInfo;
	}
	
	private FactsheetAdditionalInfo getAdditionalInfoPOM() throws IOException {
		String json = "{'type':'info'}";
		
		FactsheetAdditionalInfo addInfo = JsonHelper.mapToObject(FactsheetAdditionalInfo.class, json);
		addInfo.setInfo(getContactInfo());
		return addInfo;
	}
	
	private FactsheetAdditionalInfo getAdditionalInfoUMA() throws IOException {
		String json = "{'type':'info'}";
		
		FactsheetAdditionalInfo addInfo = JsonHelper.mapToObject(FactsheetAdditionalInfo.class, json);
		addInfo.setInfo(getContactInfo());
		return addInfo;
	}

	private ContactInfo getContactInfo() throws IOException {
		String json = "{'email':'test@uma.es'}";
		
		ContactInfo info = JsonHelper.mapToObject(ContactInfo.class, json);
		info.setPhoneNumber(getPhoneNumber());
		
		info.setUrl(getURLInfo());
		return info;
	}
	
	private ContactInfo getContactInfoUMA() throws IOException {
		String json = "{'email':'test@uma.es'}";
		
		ContactInfo info = JsonHelper.mapToObject(ContactInfo.class, json);
		info.setPhoneNumber(getPhoneNumber());
		info.setUrl(getURLInfo());
		return info;
	}
	
	private ContactInfo getContactInfoPOM() throws IOException {
		String json = "{'email':'test@uma.es'}";
		
		ContactInfo info = JsonHelper.mapToObject(ContactInfo.class, json);
		info.setPhoneNumber(getPhoneNumber());
		info.setUrl(getURLInfo());
		return info;
	}

	private CalendarEntry getStudentNominationCalendar() throws IOException {
		String json = "{'factsheetId':'F01'}";
		CalendarEntry calendarEntry = JsonHelper.mapToObject(CalendarEntry.class, json);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			calendarEntry.setAutumnTerm(simpleDateFormat.parse("01/03/2022"));
			calendarEntry.setSpringTerm(simpleDateFormat.parse("31/05/2022"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return calendarEntry;
	}
	
	private CalendarEntry getStudentNominationCalendarUMA() throws IOException {
		String json = "{'factsheetId':'F03'}";
		CalendarEntry calendarEntry = JsonHelper.mapToObject(CalendarEntry.class, json);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			calendarEntry.setAutumnTerm(simpleDateFormat.parse("01/03/2022"));
			calendarEntry.setSpringTerm(simpleDateFormat.parse("31/05/2022"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return calendarEntry;
	}
	
	private CalendarEntry getStudentNominationCalendarPOM() throws IOException {
		String json = "{'factsheetId':'F02'}";
		CalendarEntry calendarEntry = JsonHelper.mapToObject(CalendarEntry.class, json);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			calendarEntry.setAutumnTerm(simpleDateFormat.parse("01/03/2022"));
			calendarEntry.setSpringTerm(simpleDateFormat.parse("31/05/2022"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return calendarEntry;
	}

	private AdditionalRequirement getAdditionalReq() throws IOException {
		String json = "{'name':'Requirement01'}";
		
		AdditionalRequirement addReq = JsonHelper.mapToObject(AdditionalRequirement.class, json);
		
		addReq.setInformationWebsite(getURLInfo()); 		
		
		return addReq;
	}

	private List<LanguageItem> getURLInfo() {
		List<LanguageItem> langItems = new ArrayList<>();
		LanguageItem item = new LanguageItem();
		item.setLang("es");
		item.setText("");
		langItems.add(item);
		
		return langItems;
	}
	
	private AdditionalRequirement getAdditionalReqPOM() throws IOException {
		String json = "{'name':'Requirement02'}";
		
		AdditionalRequirement addReq = JsonHelper.mapToObject(AdditionalRequirement.class, json);
		addReq.setInformationWebsite(getURLInfo()); 	
		return addReq;
	}
	
	private AdditionalRequirement getAdditionalReqUMA() throws IOException {
		String json = "{'name':'Requirement03'}";
		
		AdditionalRequirement addReq = JsonHelper.mapToObject(AdditionalRequirement.class, json);
		addReq.setInformationWebsite(getURLInfo()); 	
		return addReq;
	}

	private Accessibility getAccessibility() throws IOException {
		String json = "{'name': 'ACC01'}";
		
		Accessibility accessibility = JsonHelper.mapToObject(Accessibility.class, json);
		accessibility.setInformation(getContactInfo());
		return accessibility;
	}
	
	private Accessibility getAccessibilityPOM() throws IOException {
		String json = "{'name': 'ACC02'}";
		
		Accessibility accessibility = JsonHelper.mapToObject(Accessibility.class, json);
		accessibility.setInformation(getContactInfo());
		return accessibility;
	}
	
	private Accessibility getAccessibilityUMA() throws IOException {
		String json = "{'name': 'ACC03'}";
		
		Accessibility accessibility = JsonHelper.mapToObject(Accessibility.class, json);
		accessibility.setInformation(getContactInfo());
		return accessibility;
	}

	private PhoneNumber getPhoneNumber() throws IOException {
		String jsonPhoneNumber = "{'e164':'222-222-222','extensionNumber':'788', 'otherFormat':'88888'}";
		PhoneNumber phoneNumber = JsonHelper.mapToObject(PhoneNumber.class, jsonPhoneNumber);
		return phoneNumber;
	}

	@Override
	public void createDemoDataPomodoro() throws IOException {

		String factsheetJSON = "{'heiId': 'IK-POM-01'}";
		MobilityFactsheet factSheet = JsonHelper.mapToObject(MobilityFactsheet.class, factsheetJSON);
		
		completeInformationPOM(factSheet);
		
		em.persist(factSheet);
		
	}

	@Override
	public void createDemoDataUma() throws IOException {
		String factsheetJSON = "{'heiId': 'uma.es'}";
		MobilityFactsheet factSheet = JsonHelper.mapToObject(MobilityFactsheet.class, factsheetJSON);
		
		completeInformationUMA(factSheet);
		
		em.persist(factSheet);
		
	}

}
