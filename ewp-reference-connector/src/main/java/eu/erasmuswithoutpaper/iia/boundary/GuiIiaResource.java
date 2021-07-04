package eu.erasmuswithoutpaper.iia.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumberVariants;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;

@Stateless
@Path("iia")
public class GuiIiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;

    @GET
    @Path("get_all")
    public Response getAll() {
        List<Iia> iiaList = em.createNamedQuery(Iia.findAll).getResultList();
        GenericEntity<List<Iia>> entity = new GenericEntity<List<Iia>>(iiaList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("mobility_types")
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = em.createNamedQuery(MobilityType.findAll).getResultList();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {};
        
        return Response.ok(entity).build();
    }
    
    @GET
    @Path("mobility_unit_variants")
    public Response getMobilityNumberVariants() {
        String[] statuses = MobilityNumberVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("duration_unit_variants")
    public Response getDurationUnitVariants() {
        String[] statuses = DurationUnitVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {};
        
        return Response.ok(entity).build();
    }
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(Iia iia) {
        em.persist(iia);
    }

    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias-index")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasIndex(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
    
    @POST
    @Path("iias")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iias(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.class);
        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }
    
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response update(@FormParam("iia") Iia iia) {
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
		
		em.persist(iia);

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
    			
    	//Verify if tha agreement is approved by the oter institution. 
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
