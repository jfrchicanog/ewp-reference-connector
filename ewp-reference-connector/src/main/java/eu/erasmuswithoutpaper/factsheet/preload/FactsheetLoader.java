package eu.erasmuswithoutpaper.factsheet.preload;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.erasmuswithoutpaper.factsheet.entity.Accessibility;
import eu.erasmuswithoutpaper.factsheet.entity.AdditionalRequirement;
import eu.erasmuswithoutpaper.factsheet.entity.CalendarEntry;
import eu.erasmuswithoutpaper.factsheet.entity.ContactInfo;
import eu.erasmuswithoutpaper.factsheet.entity.FactsheetAdditionalInfo;
import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;
import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.PhoneNumber;

public class FactsheetLoader extends AbstractStartupLoader{

	@Override
	public void createDemoDataIkea() throws IOException {
		
		String factsheetJSON = "{'id':'F01', 'heiId': 'IK-IKEA-01'}";
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

	private FactsheetAdditionalInfo getAdditionalInfo() throws IOException {
		String json = "{'id':'001','type':'info'}";
		
		FactsheetAdditionalInfo addInfo = JsonHelper.mapToObject(FactsheetAdditionalInfo.class, json);
		return addInfo;
	}

	private ContactInfo getContactInfo() throws IOException {
		String json = "{'id':'01','email':'test@uma.es'}";
		
		ContactInfo info = JsonHelper.mapToObject(ContactInfo.class, json);
		info.setPhoneNumber(getPhoneNumber());
		return null;
	}

	private CalendarEntry getStudentNominationCalendar() throws IOException {
		String json = "{'id':'01','factsheetId':'F01'}";
		CalendarEntry calendarEntry = JsonHelper.mapToObject(CalendarEntry.class, json);
		
		calendarEntry.setAutumnTerm(new Date());
		calendarEntry.setSpringTerm(new Date());
		
		return calendarEntry;
	}

	private AdditionalRequirement getAdditionalReq() throws IOException {
		String json = "{'id':'Add01','name':'Requirement01'}";
		
		AdditionalRequirement addReq = JsonHelper.mapToObject(AdditionalRequirement.class, json);
		return addReq;
	}

	private Accessibility getAccessibility() throws IOException {
		String json = "{'id':'01', 'name': 'ACC01'}";
		
		Accessibility accessibility = JsonHelper.mapToObject(Accessibility.class, json);
		return accessibility;
	}

	private PhoneNumber getPhoneNumber() throws IOException {
		String jsonPhoneNumber = "{'id':'PHONE01','e164':'222-222-222','extensionNumber':'788', 'otherFormat':'88888'}";
		PhoneNumber phoneNumber = JsonHelper.mapToObject(PhoneNumber.class, jsonPhoneNumber);
		return phoneNumber;
	}

	@Override
	public void createDemoDataPomodoro() throws IOException {

		String factsheetJSON = "{'id':'F02', 'heiId': 'IK-POM-01'}";
		MobilityFactsheet factSheet = JsonHelper.mapToObject(MobilityFactsheet.class, factsheetJSON);
		
		completeInformation(factSheet);
		
		em.persist(factSheet);
		
	}

	@Override
	public void createDemoDataUma() throws IOException {
		String factsheetJSON = "{'id':'F01', 'heiId': 'IK-UMA-01'}";
		MobilityFactsheet factSheet = JsonHelper.mapToObject(MobilityFactsheet.class, factsheetJSON);
		
		completeInformation(factSheet);
		
		em.persist(factSheet);
		
	}

}
