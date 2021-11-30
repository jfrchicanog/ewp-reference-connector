package eu.erasmuswithoutpaper.iia.boundary;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.common.IiaTaskService;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;

@Stateless
@Path("iias")
public class IiaResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    IiaConverter iiaConverter;
    
    @Context
    HttpServletRequest httpRequest;
    
    @Inject
    GlobalProperties globalProperties;
    
    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexGet(@QueryParam("hei_id")  List<String> heiId, @QueryParam("partner_hei_id") String partner_hei_id, @QueryParam("receiving_academic_year_id") List<String> receiving_academic_year_id, @QueryParam("modified_since") List<String> modified_since) {
        return iiaIndex(heiId, partner_hei_id, receiving_academic_year_id, modified_since);
    }
    
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexPost(@FormParam("hei_id") List<String> heiId,@FormParam("partner_hei_id") String partner_hei_id, @FormParam("receiving_academic_year_id") List<String> receiving_academic_year_id,  @FormParam("modified_since") List<String> modified_since) {
        return iiaIndex(heiId, partner_hei_id, receiving_academic_year_id, modified_since);
    }
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getGet(@QueryParam("hei_id") String heiId, @QueryParam("iia_id") List<String> iiaIdList, @QueryParam("iia_code") List<String> iiaCodeList) {
        if ( (iiaCodeList != null && !iiaCodeList.isEmpty()) && (iiaIdList != null && !iiaIdList.isEmpty()) ) {
        	throw new EwpWebApplicationException("Providing both iia_code and iia_id is not correct", Response.Status.BAD_REQUEST);
        }
        
        if ( (iiaCodeList == null || iiaCodeList.isEmpty()) && (iiaIdList == null || iiaIdList.isEmpty()) ) {
        	throw new EwpWebApplicationException("Missing argumanets, iia_code or iia_id is required", Response.Status.BAD_REQUEST);
        }
        
        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA ids has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (iiaCodeList.size() > properties.getMaxIiaCodes()) {
            throw new EwpWebApplicationException("Max number of IIA codes has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        List<String> iiaIdentifiers = new ArrayList<>();
        
        //Flag to define the search criteria. It is two possible way for identifying an IIA, by identifiers OR local IIA codes.
        boolean byLocalCodes = false;
        
        if (iiaIdList != null && !iiaIdList.isEmpty()) {
        	iiaIdentifiers.addAll(iiaIdList);
        } else {
        	iiaIdentifiers.addAll(iiaCodeList);
        	byLocalCodes = true;
        }
		return iiaGet(heiId, iiaIdentifiers, byLocalCodes);
        	
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getPost(@FormParam("hei_id") String heiId, @FormParam("iia_id") List<String> iiaIdList, @FormParam("iia_code") List<String> iiaCodeList) {
    	 if ( (iiaCodeList != null && !iiaCodeList.isEmpty()) && (iiaIdList != null && !iiaIdList.isEmpty()) ) {
         	throw new EwpWebApplicationException("Providing both iia_code and iia_id is not correct", Response.Status.BAD_REQUEST);
         }
         
         if ( (iiaCodeList == null || iiaCodeList.isEmpty()) && (iiaIdList == null || iiaIdList.isEmpty()) ) {
         	throw new EwpWebApplicationException("Missing argumanets, iia_code or iia_id is required", Response.Status.BAD_REQUEST);
         }
         
         List<String> iiaIdentifiers = new ArrayList<>();
         
         //Flag to define the search criteria. It is two possible way for identifying an IIA, by identifiers OR local IIA codes.
         boolean byLocalCodes = false;
         
         if (iiaIdList != null && !iiaIdList.isEmpty()) {
         	iiaIdentifiers = iiaIdList;
         } else {
         	iiaIdentifiers = iiaCodeList;
         	byLocalCodes = true;
         }
 		return iiaGet(heiId, iiaIdentifiers, byLocalCodes);
    }

    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response cnrPost(@FormParam("notifier_hei_id") String notifierHeiId, @FormParam("iia_id") String iiaId) {
        if (notifierHeiId == null || notifierHeiId.isEmpty() || iiaId == null || iiaId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }
        
        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }
        
        if (heisCoveredByCertificate.contains(notifierHeiId)) {
        	 Notification notification = new Notification();
             notification.setType(NotificationTypes.IIA);
             notification.setHeiId(notifierHeiId);
             notification.setChangedElementIds(iiaId);
             notification.setNotificationDate(new Date());
             em.persist(notification);
             
             //Register and execute Algoria notification
             execNotificationToAlgoria(iiaId);
             
        } else {
        	throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        }
        
        return javax.ws.rs.core.Response.ok(new ObjectFactory().createIiaCnrResponse(new Empty())).build(); 
    }
    
    private void execNotificationToAlgoria(String iiaId) {
    	
		Callable<String> callableTask = IiaTaskService.createTask(iiaId, IiaTaskService.MODIFIED);
		
	   	//Put the task in the queue
	   	IiaTaskService.addTask(callableTask);
	}
    
    private javax.ws.rs.core.Response iiaGet(String heiId, List<String> iiaIdList, boolean byLocalCodes) {
    	IiasGetResponse response = new IiasGetResponse();
        
        // TODO: Should IIA hold hei/institution id (if not hei_id will not be used, only use iia id)
        Predicate<Iia> condition = new Predicate<Iia>()
        {
        	boolean match = false;
        	
            @Override
            public boolean test(Iia iia) {
            	List<CooperationCondition> cConditions = iia.getCooperationConditions();
            	
            	cConditions.forEach(c -> {
            		if (heiId.equals(c.getSendingPartner().getInstitutionId()) || heiId.equals(c.getReceivingPartner().getInstitutionId())) {
            			match = true;
                    } 
            	});
                
                return match;
            }

        };
        
        List<Iia> iiaList = null;
        if (byLocalCodes) {
        	iiaList = iiaIdList.stream().map(iiaCode -> em.createNamedQuery(Iia.findByIiaCode,Iia.class).setParameter("iiaCode", iiaCode).getSingleResult()).filter(iia -> iia != null).filter(condition).collect(Collectors.toList());
        } else {
        	iiaList = iiaIdList.stream().map(id -> em.find(Iia.class, id)).filter(iia -> iia != null).filter(condition).collect(Collectors.toList());
        }
        
        if (!iiaList.isEmpty()) {
            response.getIia().addAll(iiaConverter.convertToIias(heiId, iiaList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private boolean isInstitutionInEwp(String institutionId) {
        List<Institution> institutionList =  em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", institutionId).getResultList();
        return !institutionList.isEmpty();
    }

    private javax.ws.rs.core.Response iiaIndex( List<String> heiIds, String partner_hei_id, List<String> receiving_academic_year_id, List<String> modified_since) {
       
    	System.out.println("Modified since tiene cantidad de elementos " + modified_since.size());
    	if(modified_since != null && modified_since.size() > 1) {
    		System.out.println("entro");
    		throw new EwpWebApplicationException("Not allow more than one value of modified_since", Response.Status.BAD_REQUEST);
    	}
    	
        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        boolean validationError = false;
        Iterator<String> iteratorHeids = heiIds.iterator();
        while(iteratorHeids.hasNext() && !validationError) {
        	String heiId = iteratorHeids.next();
        	
        	if (!isInstitutionInEwp(heiId)) {
        		validationError = true;
        		
                throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
            }
        	
        	if (partner_hei_id != null) {
            	if (partner_hei_id.equals(heiId)) {
            		validationError = true;
            		
            		throw new EwpWebApplicationException("Not a valid partner_hei_id, it must be different from hei_id.", Response.Status.BAD_REQUEST);
            	}
            }
        	
        	// if (!heisCoveredByCertificate.contains(heiId)) {
        	// 	validationError = true;
        		
            // 	throw new EwpWebApplicationException("The client signature does not cover the hei_id .", Response.Status.BAD_REQUEST);
            // }
        }
       
        //receiving_academic_year_id
        if (receiving_academic_year_id != null) {
        	boolean match =  true;
        	Iterator<String> iterator = receiving_academic_year_id.iterator();
        	System.out.println("coleccion de annos " + receiving_academic_year_id);
        	while (iterator.hasNext() && match) {
				String yearId = (String) iterator.next();
				System.out.println("formato del anno " + yearId);
				
				if (!yearId.matches("\\d{4}\\/\\d{4}")) {
            		match = false;
            	}
			}
        	
        	if (!match) {
        		throw new EwpWebApplicationException("receiving_academic_year_id is not in the correct format", Response.Status.BAD_REQUEST);
        	}
        }

		IiasIndexResponse response = new IiasIndexResponse();
        List<Iia> filteredIiaList = em.createNamedQuery(Iia.findAll).getResultList();
        		
        if (!filteredIiaList.isEmpty()) {
        	
        	List<Iia> tempIiaList = new ArrayList<>();
        	for (String heiId : heiIds) {
        		tempIiaList.addAll(filteredIiaList.stream().filter(iia -> equalHeiId.test(iia, heiId)).collect(Collectors.toList()));
			}
        	
        	filteredIiaList = new ArrayList<Iia>(tempIiaList);
        	
        	if (partner_hei_id != null) {
    			filteredIiaList = filteredIiaList.stream().filter(iia -> equalPartnerHeiId.test(iia, partner_hei_id)).collect(Collectors.toList());
    		}
    		
        	List<Iia> filteredIiaByReceivingAcademic = new ArrayList<>();
    		if (receiving_academic_year_id != null) {
    			
    			for(String year_id : receiving_academic_year_id) {
    				List<Iia> filterefList = filteredIiaList.stream().filter(iia -> anyMatchReceivingAcademicYear.test(iia, year_id)).collect(Collectors.toList());
    				
    				filteredIiaByReceivingAcademic.addAll(filterefList);
    			}
    			
    			filteredIiaList = new ArrayList<Iia>(filteredIiaByReceivingAcademic);
    		}
    		
    		if (modified_since != null) {
    			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");//2004-02-12T15:19:21+01:00
	   			Calendar calendarModifySince = Calendar.getInstance();
		        if (modified_since != null) { 
		        	
		        	List<Iia> tempFilteredModifiedSince = new ArrayList<>();
		        	Iterator<String> modifiedSinceIter = modified_since.iterator();
		        	boolean match = true;
		        	while (modifiedSinceIter.hasNext() && match) {
						String modifiedValue = modifiedSinceIter.next();
						
						try {
				    			calendarModifySince.setTime(sdf.parse(modifiedValue));
				    			
				    			tempFilteredModifiedSince.addAll(filteredIiaList.stream().filter(iia -> compareModifiedSince.test(iia, calendarModifySince)).collect(Collectors.toList()));
				    		} catch (ParseException e) {
				    			throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
				    		}
					}
		        	filteredIiaList = new ArrayList<>(tempFilteredModifiedSince);
		        }
    		}
        }
        
        if (!filteredIiaList.isEmpty()) {
    		response.getIiaId().addAll(iiaIds(filteredIiaList, heisCoveredByCertificate));
    	}
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    BiPredicate<Iia,String> equalHeiId = new BiPredicate<Iia,String>()
    {
        @Override
        public boolean test(Iia iia, String heiId) {
        	
        	List<CooperationCondition> cConditions = iia.getCooperationConditions();
        	
        	Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getSendingPartner().getInstitutionId().equals(heiId));
        	 
            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };
    
    BiPredicate<Iia,String> equalPartnerHeiId = new BiPredicate<Iia,String>()
	{
		@Override
		public boolean test(Iia iia, String partner_hei_id) {
			List<CooperationCondition> cConditions = iia.getCooperationConditions();
			
			Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getReceivingPartner().getInstitutionId().equals(partner_hei_id));
			
			return !stream.collect(Collectors.toList()).isEmpty();
		}
	};
    
	BiPredicate<Iia, String> anyMatchReceivingAcademicYear = new BiPredicate<Iia, String>()
    {
        @Override
        public boolean test(Iia iia, String receiving_academic_year_id) {
        	
        	List<CooperationCondition> cConditions = iia.getCooperationConditions();
        	
    		Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getReceivingAcademicYearId().stream().anyMatch(yearId -> yearId.equals(receiving_academic_year_id)));
        	 
            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };
    
    BiPredicate<Iia, Calendar> compareModifiedSince = new BiPredicate<Iia, Calendar>()
    {
        @Override
        public boolean test(Iia iia, Calendar calendarModifySince) {
        	
        	Calendar calendarModify = Calendar.getInstance();
			calendarModify.setTime(iia.getModifyDate());
        	
            return calendarModify.after(calendarModifySince);
        }

    };
    
    private List<String> iiaIds(List<Iia> iiaList, Collection<String> heisCoveredByCertificate) {
        return iiaList.stream().filter((iia) -> {
            return iia.getCooperationConditions().stream().anyMatch(
                    cond -> heisCoveredByCertificate.contains(cond.getReceivingPartner().getInstitutionId()) || 
                            heisCoveredByCertificate.contains(cond.getSendingPartner().getInstitutionId()));
        }).map(iia -> iia.getId()).collect(Collectors.toList());
    }
}
