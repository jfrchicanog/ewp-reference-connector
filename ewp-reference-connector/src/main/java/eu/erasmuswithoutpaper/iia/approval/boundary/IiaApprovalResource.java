package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.approval.control.IiaApprovalConverter;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.common.IiaTaskService;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;

@Stateless
@Path("iiasApproval")
public class IiaApprovalResource {
	private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
	
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @Inject
    IiaApprovalConverter iiaApprovalConverter;
    
    @Context
    HttpServletRequest httpRequest;
    
    @Inject
    GlobalProperties globalProperties;
          
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiasApprovalGet(@QueryParam("approving_hei_id") String heiId, @QueryParam("owner_hei_id") String owner_hei_id, @QueryParam("iia_id") List<String> iiaIdList, @QueryParam("send_pdf") Boolean pdf) {
        return iiaApprovalGet(heiId, owner_hei_id, iiaIdList, pdf);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiasApprovalPost(@FormParam("approving_hei_id") String heiId, @FormParam("owner_hei_id") String owner_hei_id, @FormParam("iia_id") List<String> iiaIdList, @FormParam("send_pdf") Boolean pdf) {
        return iiaApprovalGet(heiId, owner_hei_id, iiaIdList, pdf);
    }
    
    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response cnrPost(@FormParam("approving_hei_id") String approvingHeiId, @FormParam("owner_hei_id") String owner_hei_id, @FormParam("iia_id") String iiaApprovalId) {
    	 if (approvingHeiId.isEmpty() || approvingHeiId == null) {
        	throw new EwpWebApplicationException("Missing arguments for notification, approving_hei_id required.", Response.Status.BAD_REQUEST);
         }
		 
		 if (owner_hei_id.isEmpty() || owner_hei_id == null) {
        	throw new EwpWebApplicationException("Missing arguments for notification, owner_hei_id required.", Response.Status.BAD_REQUEST);
         }
    	
    	if (iiaApprovalId == null || iiaApprovalId.isEmpty()) {
            throw new EwpWebApplicationException("Missing arguments for notification, iia_id is required.", Response.Status.BAD_REQUEST);
        }
        
        Notification notification = new Notification();
        notification.setType(NotificationTypes.IIAAPPROVAL);
        notification.setHeiId(approvingHeiId);
        notification.setChangedElementIds(iiaApprovalId);
        notification.setNotificationDate(new Date());
        em.persist(notification);
        
        //Register and execute Algoria notification
        execNotificationToAlgoria(iiaApprovalId);
        
        return javax.ws.rs.core.Response.ok(new ObjectFactory().createIiaApprovalCnrResponse(new Empty())).build();
    }
    
    private void execNotificationToAlgoria(String iiaApprovalId) {
    	
		Callable<String> callableTask = IiaTaskService.createTask(iiaApprovalId, IiaTaskService.APPROVED);
		
	   	//Put the task in the queue
	   	IiaTaskService.addTask(callableTask);
	}

	private javax.ws.rs.core.Response iiaApprovalGet(String heiId, String owner_hei_id, List<String> iiaIdList, Boolean pdf) {
		 if (heiId.isEmpty() || heiId == null) {
        	throw new EwpWebApplicationException("approving_hei_id required.", Response.Status.BAD_REQUEST);
         }
		 
		 if (owner_hei_id.isEmpty() || owner_hei_id == null) {
        	throw new EwpWebApplicationException("owner_hei_id required.", Response.Status.BAD_REQUEST);
         }
		
		if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA APPROVAL id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (iiaIdList.isEmpty()) {
        	throw new EwpWebApplicationException("iia_id required.", Response.Status.BAD_REQUEST);
        }
        
        Collection<String> heisCoveredByCertificate;
		 if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
           heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
		 } else {
           heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
		 }
	      
		 if (!heisCoveredByCertificate.contains(heiId)) {
			 throw new EwpWebApplicationException("The client signature does not cover the approving_hei_id.", Response.Status.BAD_REQUEST);
		 }
        
        IiasApprovalResponse response = new IiasApprovalResponse();
        
        //Initialize the result list
        List<IiaApproval> iiaApprovalList = new ArrayList<IiaApproval>();
        
        //Getting all agreements which corresponds to the list of identifiers
        List<Iia> iiaList = iiaIdList.stream().map(id -> em.find(Iia.class, id)).filter(iia -> iia != null).collect(Collectors.toList());
        if(!iiaList.isEmpty()) {
        	
        	//Apply filter by the heiId and the owner of the copy
        	iiaList = iiaList.stream().filter(iia -> equalPartnerHeiId.test(iia, owner_hei_id)).filter(iia -> equalHeiId.test(iia, heiId)).collect(Collectors.toList());
        	
        	if(!iiaList.isEmpty()) {
        		//Extract the iia ids
        		List<String> ids = iiaList.stream().map(iia -> {
        			return iia.getId();
        		}).collect(Collectors.toList());
        		
        		//Get the list of iiaApproval
        		if (!ids.isEmpty()) {
        			iiaApprovalList = ids.stream().map(id -> em.find(IiaApproval.class, id)).filter(iia -> iia != null).collect(Collectors.toList());
        		}
        	}
        }
        
        if (!iiaApprovalList.isEmpty()) {
            response.getApproval().addAll(iiaApprovalConverter.convertToIiasApproval(heiId, iiaApprovalList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    } 
	
	BiPredicate<Iia,String> equalHeiId = new BiPredicate<Iia,String>()
    {
        @Override
        public boolean test(Iia iia, String approving_hei_id) {
        	
        	List<CooperationCondition> cConditions = iia.getCooperationConditions();
        	
        	Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getReceivingPartner().getInstitutionId().equals(approving_hei_id));
        	 
            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };
    
    BiPredicate<Iia,String> equalPartnerHeiId = new BiPredicate<Iia,String>()
	{
		@Override
		public boolean test(Iia iia, String owner_hei_id) {
			List<CooperationCondition> cConditions = iia.getCooperationConditions();
			
			Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getSendingPartner().getInstitutionId().equals(owner_hei_id));
			
			return !stream.collect(Collectors.toList()).isEmpty();
		}
	};
    
}
