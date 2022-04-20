package eu.erasmuswithoutpaper.iia.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.Partner;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification.RecommendedLanguageSkill;
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
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Duration;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumber;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumberVariants;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.iia.entity.SubjectArea;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Stateless
@Path("iia")
public class GuiIiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @Inject
    IiaConverter iiaConverter;
    
    @Inject
    GlobalProperties properties;

    @GET
    @Path("get_all")
    @InternalAuthenticate
    // TODO: fix the default value
    public Response getAll(@QueryParam("hei_id") @DefaultValue("uma.es") String hei_id) {
        List<Iia> iiaList = em.createNamedQuery(Iia.findAll).getResultList();
        List<IiasGetResponse.Iia> result = iiaConverter.convertToIias(hei_id, iiaList);//It was required to use IiaConverter to avoid a recursive problem when the iia object was converted to json
        
        GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(result) {};

        return Response.ok(entity).build();
    }
    
    @GET
    @Path("get_heiid")
    @InternalAuthenticate
    public Response getHei(@QueryParam("hei_id") String heiId) {
        List<Iia> iiaList = em.createNamedQuery(Iia.findAll).getResultList();
        
        Predicate<Iia> condition = new Predicate<Iia>()
        {
            @Override
            public boolean test(Iia iia) {
            	List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();
            	
            	List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> heiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty() ;
            }
        };
        
        if (!iiaList.isEmpty()) {
        	 List<Iia> filteredList = iiaList.stream().filter(condition).collect(Collectors.toList());
        	
        	if (!filteredList.isEmpty()) {
        		List<IiasGetResponse.Iia> iiasGetResponseList = iiaConverter.convertToIias(heiId, iiaList);
        		
        		GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(iiasGetResponseList) {};
        		 return Response.ok(entity).build();
        	}
        } 
        
        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("mobility_types")
    @InternalAuthenticate
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = em.createNamedQuery(MobilityType.findAll).getResultList();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("mobility_unit_variants")
    @InternalAuthenticate
    public Response getMobilityNumberVariants() {
        String[] statuses = MobilityNumberVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("duration_unit_variants")
    @InternalAuthenticate
    public Response getDurationUnitVariants() {
        String[] statuses = DurationUnitVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
    
    @POST
    @Path("add")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(IiasGetResponse.Iia iia) {
    	Iia iiaInternal = new Iia();
    	
    	if (iia.getConditionsHash() != null) {
    		iiaInternal.setConditionsHash(iia.getConditionsHash());
    	}
    	
    	iiaInternal.setInEfect(iia.isInEffect());
    	
    	List<CooperationCondition> cooperationConditionList = iia.getPartner().stream().map((Partner partner) -> {
    		CooperationCondition cooperationConditionInternal = new CooperationCondition();
    		
    		if (partner.getIiaCode() != null) {
    			iiaInternal.setIiaCode(partner.getIiaCode());
    		}
    		
    		if (partner.getIiaId() != null) {
    			iiaInternal.setId(partner.getIiaId());
    		}
        	
    		if (partner.getSigningDate() != null) {
    			iiaInternal.setSigningDate(partner.getSigningDate().toGregorianCalendar().getTime());
    		}
        	
        	//iiaInternal.setEndDate(null);
        	//iiaInternal.setStartDate(null);
        	
    		IiaPartner partnerInternal = new IiaPartner();
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
        	
    		cooperationConditionInternal.setReceivingPartner(partnerInternal);
    		return cooperationConditionInternal;
    	}).collect(Collectors.toList());
    	
    	List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());
    	iiaIternalCooperationConditions.addAll(cooperationConditionList);
    	
    	iiaInternal.setCooperationConditions(iiaIternalCooperationConditions);
    	
        em.persist(iiaInternal);
    }

	private eu.erasmuswithoutpaper.organization.entity.Contact convertToContact(Contact signingContact) {
		eu.erasmuswithoutpaper.organization.entity.Contact signingContactInternal = new eu.erasmuswithoutpaper.organization.entity.Contact();
		
		Person personInternal = new Person();
		personInternal.setGender(Gender.getById(signingContact.getPersonGender()));
		signingContactInternal.setPerson(personInternal);
		
		ContactDetails contactDetails = new ContactDetails();
		
		FlexibleAddress flexibleAddressInternal = convertFlexibleAddress(signingContact.getMailingAddress());
		FlexibleAddress streetAddressInternal = convertFlexibleAddress(signingContact.getStreetAddress());
		
		contactDetails.setMailingAddress(flexibleAddressInternal);
		contactDetails.setStreetAddress(streetAddressInternal);
		
		signingContactInternal.setContactDetails(contactDetails);
		return signingContactInternal;
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
    	List<CooperationCondition> cooperationConditionsInternals = new  ArrayList<>();
    	
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
				
				if (recommendedSkill.getCefrLevel() != null){
					langskill.setCefrLevel(recommendedSkill.getCefrLevel());
				}
				
				langskill.setLanguage(recommendedSkill.getLanguage());
				
				if (recommendedSkill.getSubjectArea() != null) {
					SubjectArea subjectArea = new SubjectArea();
					subjectArea.setIscedClarification(recommendedSkill.getSubjectArea().getIscedClarification());
					subjectArea.setIscedFCode(recommendedSkill.getSubjectArea().getIscedFCode());
					langskill.setSubjectArea(subjectArea);
				}
				
				langskills.add(langskill);
			} 
			cc.getRecommendedLanguageSkill().addAll(langskills);
		}
		
		cc.getReceivingAcademicYearId().addAll(mobilitySpec.getReceivingAcademicYearId());
		
		if (mobilitySpec.getOtherInfoTerms() != null) {
			cc.setOtherInfoTerms(mobilitySpec.getOtherInfoTerms());
		}
		
		cc.getReceivingPartner().setInstitutionId(mobilitySpec.getReceivingHeiId());
		if(mobilitySpec.getReceivingOunitId() != null) {
			cc.getReceivingPartner().setOrganizationUnitId(mobilitySpec.getReceivingOunitId());
		}
		
		cc.getSendingPartner().setInstitutionId(mobilitySpec.getSendingHeiId());
		if(mobilitySpec.getSendingOunitId() != null) {
			cc.getSendingPartner().setOrganizationUnitId(mobilitySpec.getSendingOunitId());
		}
		
		if (mobilitySpec.getMobilitiesPerYear() != null) {
			MobilityNumber mobNumber = new MobilityNumber();
			mobNumber.setNumber(mobilitySpec.getMobilitiesPerYear().intValue());
			cc.setMobilityNumber(mobNumber);
		}
		
		List<SubjectArea> subjectAreasInt = new ArrayList<>();
		List<eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea> subjectAreas = mobilitySpec.getSubjectArea();
		for (eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea subjectArea : subjectAreas) {
			SubjectArea subjectAreaInt = new SubjectArea();
			
			if (subjectArea.getIscedClarification() != null) {
				subjectAreaInt.setIscedClarification(subjectArea.getIscedClarification());
			}
			subjectAreaInt.setIscedFCode(subjectArea.getIscedFCode());
			
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
			cc.getReceivingPartner().getContacts().addAll(contactsReceivigInternal);
		}
		
		if (mobilitySpec.getSendingContact() != null) {
			List<Contact> sendingContacts = mobilitySpec.getSendingContact();
			List<eu.erasmuswithoutpaper.organization.entity.Contact> contactsSendinginInternal = new ArrayList<>();
			for (Contact contact : sendingContacts) {
				eu.erasmuswithoutpaper.organization.entity.Contact contactRec = convertToContactDetails(contact);
				
				contactsSendinginInternal.add(contactRec);
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
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias-index")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasIndex(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse.class);
        
        eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse index = (eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse)iiaResponse.getResult();
        GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse> entity = new GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse>(index) {};
        
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iias(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
    
    @POST
    @Path("update")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response update(Iia iia) {
    	//Find the iia by code
    	List<Iia> foundIias = em.createNamedQuery(Iia.findByIiaCode).setParameter("iiaCode", iia.getIiaCode()).getResultList();
    	
    	Iia foundIia = ( foundIias != null && !foundIias.isEmpty() ) ? foundIias.get(0) : null;
    	
    	//Check if the iia exists
    	if (foundIia == null) {
    		return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
    	}
		
    	//check if the iia is a draft or proposal
		if (foundIia.isInEfect()) {
			return javax.ws.rs.core.Response.status(Response.Status.NOT_MODIFIED).build();
		}
		
		if (iia.getIiaCode() == null || iia.getIiaCode().isEmpty()) {
			return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		if (iia.getStartDate() == null) {
			return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		if (iia.getCooperationConditions().size() == 0) {
			
			return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
			
		} else if (!validateConditions(iia.getCooperationConditions())) {
			
			return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		em.persist(iia);//TODO here the new instance is inserted but remains de old one

		//Notify the partner about the modification using the API GUI IIA CNR 
		ClientRequest clientRequest = notifyPartner(iia);
		ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
		
		return javax.ws.rs.core.Response.ok(iiaResponse).build();
	} 
    
    private ClientRequest notifyPartner(Iia iia) {
    	//Getting agreement partners
		IiaPartner partnerSending = null;
		IiaPartner partnerReceiving = null;
		
		for (CooperationCondition condition : iia.getCooperationConditions()) {
			partnerSending = condition.getSendingPartner();
			partnerReceiving = condition.getReceivingPartner();
        }
		
    	//Get the url for notify the institute
    	Map<String, String> urls = registryClient.getIiaCnrHeiUrls(partnerSending.getInstitutionId());
    	List<String> urlValues = new ArrayList<String>(urls.values());
    	
    	//Notify the other institution about the modification 
    	ClientRequest clientRequest = new ClientRequest();
    	clientRequest.setUrl(urls.get(urlValues.get(0)));//get the first and only one url
    	clientRequest.setHeiId(partnerReceiving.getInstitutionId());
    	clientRequest.setMethod(HttpMethodEnum.POST);
    	clientRequest.setHttpsec(true);
    	
    	return clientRequest;
	}

	@POST
    @Path("iias-approve")
	@InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApprove(@FormParam("hei_id") String heiId, @FormParam("iia_code") String iiaCode) {
    	if (heiId == null || heiId.isEmpty() || iiaCode == null || iiaCode.isEmpty()) {
    		return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }
    	
    	//seek the iia by code and by the ouid of the sending institution
    	List<Iia> foundIia = em.createNamedQuery(Iia.findByIiaCode).setParameter("iiaCode", iiaCode).getResultList();
    	
    	Predicate<Iia> condition = new Predicate<Iia>()
        {
            @Override
            public boolean test(Iia iia) {
            	List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();
            	
            	List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> heiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty() ;
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
    	
    	Map<String,List<String>> params = new HashMap<>();
    	params.put("iia_approval_id", iiaIds);
    	
    	clientRequestGetIia.setParams(params);
    	
    	ClientResponse iiaApprovalResponse = restClient.sendRequest(clientRequestGetIia, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
    	eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse response = (IiasApprovalResponse) iiaApprovalResponse.getResult();
    	
    	Approval approval = response.getApproval().stream()
    			  .filter(app -> theIia.getIiaCode().equals(app.getIiaId()))
    			  .findAny()
    			  .orElse(null);
    	
    	if(approval != null) {
    		theIia.setInEfect(true);//it was approved
        	em.persist(theIia);
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
    	
    	Map<String,List<String>> paramsCnr = new HashMap<>();
    	paramsCnr.put("iia_approval_id", iiaIds);
    	
    	clientRequestGetIia.setParams(paramsCnr);
    	
        ClientResponse iiaResponse = restClient.sendRequest(clientRequestNotifyApproval, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
    
    private boolean validateConditions(List<CooperationCondition> conditions) {
    	
    	Predicate<CooperationCondition> condition = new Predicate<CooperationCondition>()
        {
            @Override
            public boolean test(CooperationCondition c) {
                if (c.getSendingPartner() == null || c.getReceivingPartner() == null) {
                	return true;
                } else if (c.getSendingPartner().getInstitutionId() == null || c.getSendingPartner().getInstitutionId().isEmpty()) {
            		return true;
            	}else if (c.getReceivingPartner().getInstitutionId() == null || c.getReceivingPartner().getInstitutionId().isEmpty()) {
            		return true;
            	}
                return false;
            }

        };
        
        List<CooperationCondition> wrongConditions = conditions.stream().filter(condition).collect(Collectors.toList());
    	 return wrongConditions.isEmpty();
    }

}
