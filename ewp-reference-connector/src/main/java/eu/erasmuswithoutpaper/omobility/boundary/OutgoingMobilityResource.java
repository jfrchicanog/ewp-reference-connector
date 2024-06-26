package eu.erasmuswithoutpaper.omobility.boundary;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;
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

import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.OmobilitiesIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.endpoints.StudentMobility;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.omobility.control.OutgoingMobilityConverter;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;

@Stateless
@Path("omobilities")
public class OutgoingMobilityResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    OutgoingMobilityConverter mobilityConverter;
    
    @Context
    HttpServletRequest httpRequest;
    
    @Inject
    RegistryClient registryClient;
    
    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityIndexGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList, 
    		@QueryParam("receiving_academic_year_id") String receiving_academic_year_id, @QueryParam("modified_since ") String modified_since) {
    	 
    	if (sendingHeiId == null || sendingHeiId.isEmpty()) {
             throw new EwpWebApplicationException("sending_hei_id is a required parameter", Response.Status.BAD_REQUEST);
         }
    	
    	return mobilityIndex(sendingHeiId, receivingHeiIdList, receiving_academic_year_id, modified_since);
    }
    
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityIndexPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("receiving_hei_id") List<String> receivingHeiIdList, 
    		@FormParam("receiving_academic_year_id") String receiving_academic_year_id, @FormParam("modified_since ") String modified_since) {
        
    	 if (sendingHeiId == null || sendingHeiId.isEmpty()) {
             throw new EwpWebApplicationException("sending_hei_id is a required parameter", Response.Status.BAD_REQUEST);
         }
    	
    	return mobilityIndex(sendingHeiId, receivingHeiIdList, receiving_academic_year_id, modified_since);
    }
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    private javax.ws.rs.core.Response mobilityGet(String sendingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxMobilityIds()) {
            throw new EwpWebApplicationException("Max number of mobility id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        OmobilitiesGetResponse response = new OmobilitiesGetResponse();
        List<Mobility> mobilityListBySendingHei =  em.createNamedQuery(Mobility.findBySendingInstitutionId).setParameter("sendingInstitutionId", sendingHeiId).getResultList();
        List<Mobility> mobilityListByReceivingHei =  em.createNamedQuery(Mobility.findByReceivingInstitutionId).setParameter("sendingInstitutionId", sendingHeiId).getResultList();
        
        List<Mobility> mobilityList = new ArrayList<>();
        mobilityList.addAll(mobilityListBySendingHei);
        mobilityList.addAll(mobilityListByReceivingHei);
        
        if (!mobilityList.isEmpty()) {
        	
        	 Collection<String> heisCoveredByCertificate;
             if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
                 heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
             } else {
                 heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
             }
             
           //checking if caller covers the receiving HEI of this mobility,
         	mobilityList = mobilityList.stream().filter(omobility -> heisCoveredByCertificate.contains(omobility.getReceivingInstitutionId())).collect(Collectors.toList());
             
            response.getSingleMobilityObject().addAll(mobilities(mobilityList, mobilityIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private javax.ws.rs.core.Response mobilityIndex(String sendingHeiId, List<String> receivingHeiIdList, String receiving_academic_year_id, String modified_since) {
        OmobilitiesIndexResponse response = new OmobilitiesIndexResponse();
        
       
        List<Mobility> mobilityList =  em.createNamedQuery(Mobility.findBySendingInstitutionId).setParameter("sendingInstitutionId", sendingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
        	
        	if (receiving_academic_year_id != null && !receiving_academic_year_id.isEmpty()) {
        		mobilityList = mobilityList.stream().filter(omobility -> anyMatchReceivingAcademicYear.test(omobility, receiving_academic_year_id)).collect(Collectors.toList());
        	}
        	
        	if (modified_since != null && !modified_since.isEmpty()) {
                // TODO
        	}
        	
            response.getOmobilityId().addAll(mobilityIds(mobilityList, receivingHeiIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    BiPredicate<Mobility, String> anyMatchReceivingAcademicYear = new BiPredicate<Mobility, String>()
    {
        @Override
        public boolean test(Mobility mobility, String receiving_academic_year_id) {
        	
        	Date start = mobility.getPlannedArrivalDate();
        	Date end = mobility.getPlannedDepartureDate();
        	
        	Calendar calendar = Calendar.getInstance();
        	
        	calendar.setTime(start);
        	int startYear = calendar.get(Calendar.YEAR);
        	
        	calendar.setTime(end);
        	int endYear = calendar.get(Calendar.YEAR);
        	
        	String[] splitStr = receiving_academic_year_id.split("/");
        	int startPeriod = Integer.parseInt(splitStr[0]);
        	int endPeriod = Integer.parseInt(splitStr[1]);
        	
        	return (startPeriod <= startYear && startYear <= endPeriod) && (startPeriod <= endYear && endYear <= endPeriod); 
        }
    };
    
    private List<StudentMobility> mobilities(List<Mobility> mobilityList, List<String> mobilityIdList) {
        List<StudentMobility> mobilities = new ArrayList<>();
        mobilityList.stream().forEachOrdered((m) -> {
            if (mobilityIdList.contains(m.getId())) {
                mobilities.add(mobilityConverter.convertToStudentMobilityForStudies(m));
            }
        });
        
        return mobilities;
    }
    
    private List<String> mobilityIds(List<Mobility> mobilityList, List<String> receivingHeiIdList) {
        List<String> mobilityIds = new ArrayList<>();
        
        if (receivingHeiIdList != null && !receivingHeiIdList.isEmpty()) {
        	 mobilityList.stream().forEachOrdered((m) -> {
                 if (receivingHeiIdList.contains(m.getReceivingInstitutionId())) {
                     mobilityIds.add(m.getId());
                 }
             });
        } else {
        	 mobilityList.stream().forEachOrdered((m) -> {
                mobilityIds.add(m.getId());
             });
        }
        
        return mobilityIds;
    }
}
