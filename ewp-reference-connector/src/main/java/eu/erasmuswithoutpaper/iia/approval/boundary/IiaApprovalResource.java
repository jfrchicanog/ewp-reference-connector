package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.approval.control.IiaApprovalConverter;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;

@Stateless
@Path("iiasApproval")
public class IiaApprovalResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    IiaApprovalConverter iiaApprovalConverter;
    
    @Context
    HttpServletRequest httpRequest;
          
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiasApprovalGet(@QueryParam("approving_hei_id ") String heiId, @QueryParam("iia_approval_id") List<String> iiaIdList) {
        return iiaApprovalGet(heiId, iiaIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiasApprovalPost(@FormParam("approving_hei_id") String heiId, @FormParam("iia_approval_id") List<String> iiaIdList) {
        return iiaApprovalGet(heiId, iiaIdList);
    }
    
    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response cnrPost(@FormParam("approving_hei_id") String approvingHeiId, @FormParam("iia_approval_id") String iiaApprovalId) {
        if (approvingHeiId == null || approvingHeiId.isEmpty() || iiaApprovalId == null || iiaApprovalId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }
        Notification notification = new Notification();
        notification.setType(NotificationTypes.IIAAPPROVAL);
        notification.setHeiId(approvingHeiId);
        notification.setChangedElementIds(iiaApprovalId);
        notification.setNotificationDate(new Date());
        em.persist(notification);
         
        return javax.ws.rs.core.Response.ok(new ObjectFactory().createIiaApprovalCnrResponse(new Empty())).build();
    }
    
    private javax.ws.rs.core.Response iiaApprovalGet(String heiId, List<String> iiaIdList) {
        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA APPROVAL id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (iiaIdList.isEmpty()) {
        	throw new EwpWebApplicationException("approving_hei_id required.", Response.Status.BAD_REQUEST);
        }
        
        IiasApprovalResponse response = new IiasApprovalResponse();
        
        List<IiaApproval> iiaApprovalList = iiaIdList.stream().map(id -> em.find(IiaApproval.class, id)).filter(iiaApproval -> iiaApproval != null).collect(Collectors.toList());
        if (!iiaApprovalList.isEmpty()) {
            response.getApproval().addAll(iiaApprovalConverter.convertToIiasApproval(heiId, iiaApprovalList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }         
    
}
