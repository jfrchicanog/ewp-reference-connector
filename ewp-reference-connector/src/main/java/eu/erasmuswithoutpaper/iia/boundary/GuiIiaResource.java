package eu.erasmuswithoutpaper.iia.boundary;

import java.util.ArrayList;
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

import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
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
    public javax.ws.rs.core.Response update(Iia iia) {
    	Iia foundIia = em.find(Iia.class, iia.getIiaCode());
    	
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
		return javax.ws.rs.core.Response.ok().build();
	} 
    
    @POST
    @Path("iias-approve")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApprove(@FormParam("hei_id") String heiId, @FormParam("iia_code") String iiaCode) {
    	if (heiId == null || heiId.isEmpty() || iiaCode == null || iiaCode.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }
    	
    	//seek the iia by code
    	List<Iia> foundIia = em.createNamedQuery(Iia.findByIiaCode).setParameter("iiaCode", iiaCode).getResultList();
    	
    	if (foundIia.isEmpty()) {
    		return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
    	}
    	
    	//get the first one found
    	Iia theIia = foundIia.get(0);
    	theIia.setInEfect(true);//it was approved
    	em.persist(theIia);
    	
    	//Get the url for notify the institute
    	Map<String, String> urls = registryClient.getIiaApprovalCnrHeiUrls(heiId);
    	List<String> urlValues = new ArrayList<String>(urls.values());
    	
    	ClientRequest clientRequest = new ClientRequest();
    	clientRequest.setUrl(urls.get(urlValues.get(0)));//get the first and only one url
    	clientRequest.setHeiId(heiId);
    	clientRequest.setMethod(HttpMethodEnum.POST);
    	clientRequest.setHttpsec(true);
    	
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
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
