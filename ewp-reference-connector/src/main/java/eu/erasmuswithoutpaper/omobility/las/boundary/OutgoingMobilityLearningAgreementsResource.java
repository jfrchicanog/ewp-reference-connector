package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.ApprovedProposal;
import eu.erasmuswithoutpaper.omobility.las.entity.CommentProposal;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.Signature;
import eu.erasmuswithoutpaper.omobility.las.entity.Student;

@Stateless
@Path("omobilitieslas")
public class OutgoingMobilityLearningAgreementsResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;
    
    @Inject
    RestClient restClient;
    
    @Inject
    RegistryClient registryClient;
    
    @Context
    HttpServletRequest httpRequest;
    
    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response indexGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList, @QueryParam("receiving_academic_year_id") String receiving_academic_year_id,
    		@QueryParam("global_id") String globalId, @QueryParam("mobility_type ") String mobilityType, @QueryParam("modified_since  ") String modifiedSince) {
        return omobilityLasIndex(sendingHeiId, receivingHeiIdList, receiving_academic_year_id, globalId, mobilityType, modifiedSince);
    }
    
    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response indexPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("receiving_hei_id") List<String> receivingHeiIdList, @FormParam("receiving_academic_year_id") String receiving_academic_year_id,
    		@FormParam("global_id") String globalId, @FormParam("mobility_type ") String mobilityType, @FormParam("modified_since  ") String modifiedSince) {
        return omobilityLasIndex(sendingHeiId, receivingHeiIdList, receiving_academic_year_id, globalId, mobilityType, modifiedSince);
    }
    
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityLasUpdatePost(OmobilityLasUpdateRequest request) {
        if (request == null) {
            throw new EwpWebApplicationException("No update data was sent", Response.Status.BAD_REQUEST);
        }
        
        if (request.getSendingHeiId() == null || request.getSendingHeiId().isEmpty()) {
        	throw new EwpWebApplicationException("Mising required parameter, sending-hei-id is required", Response.Status.BAD_REQUEST);
        }
        
        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }
        
        if (!heisCoveredByCertificate.contains(request.getSendingHeiId())) {
        	throw new EwpWebApplicationException("The client signature does not cover the receiving HEI of the mobility.", Response.Status.BAD_REQUEST);
        }
        
        if(request.getCommentProposalV1() == null && request.getApproveProposalV1() == null) {
        	throw new EwpWebApplicationException("Mising required parameter, approve-proposal-v1 and comment-proposal-v1 both of them can not be missing", Response.Status.BAD_REQUEST);
        }

        if (request.getApproveProposalV1() != null) {
        	if (request.getApproveProposalV1().getOmobilityId() == null || request.getApproveProposalV1().getOmobilityId().isEmpty()) {
        		throw new EwpWebApplicationException("Mising required parameter, omobility-id is required", Response.Status.BAD_REQUEST);
        	}
        	
        	if (request.getApproveProposalV1().getChangesProposalId() == null) {
        		throw new EwpWebApplicationException("Mising required parameter, changes-proposal-id is required", Response.Status.BAD_REQUEST);
        	}
        	
        	String omobilityId = request.getApproveProposalV1().getOmobilityId();
        	
        	OlearningAgreement olearningAgreement = em.find(OlearningAgreement.class, omobilityId);
        	
        	if (olearningAgreement == null) {
        		throw new EwpWebApplicationException("Learning agreement does not exist", Response.Status.BAD_REQUEST);
        	} else if (olearningAgreement != null) {
        		if (!request.getSendingHeiId().equals(olearningAgreement.getSendingHei().getHeiId())) {
        			throw new EwpWebApplicationException("Sending Hei Id doesn't match Omobility Id's sending HEI", Response.Status.BAD_REQUEST);
        		}
        	}
        }
        
        if (request.getCommentProposalV1() != null) {
        	if (request.getCommentProposalV1().getOmobilityId() == null || request.getCommentProposalV1().getOmobilityId().isEmpty()) {
        		throw new EwpWebApplicationException("Mising required parameter, omobility-id is required", Response.Status.BAD_REQUEST);
        	}
        	
        	if (request.getCommentProposalV1().getChangesProposalId() == null) {
        		throw new EwpWebApplicationException("Mising required parameter, changes-proposal-id is required", Response.Status.BAD_REQUEST);
        	}
        	
        	if (request.getCommentProposalV1().getComment() == null) {
        		throw new EwpWebApplicationException("Mising required parameter, comment is required", Response.Status.BAD_REQUEST);
        	}
        }
        
        eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest mobilityUpdateRequest = new eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest();
        mobilityUpdateRequest.setSendingHeiId(request.getSendingHeiId());
        
        ApprovedProposal appCmp = approveCmpStudiedDraft(request);
        mobilityUpdateRequest.setApproveComponentsStudiedDraft(appCmp);
        
        CommentProposal updateComponentsStudied = updateComponentsStudied(request);
        mobilityUpdateRequest.setUpdateComponentsStudied(updateComponentsStudied);
        
        em.persist(mobilityUpdateRequest);

        OmobilityLasUpdateResponse response = new OmobilityLasUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("Thank you! We will review your suggestion");
        response.getSuccessUserMessage().add(message);
        return javax.ws.rs.core.Response.ok(response).build();
    }

	private CommentProposal updateComponentsStudied(OmobilityLasUpdateRequest request) {
		CommentProposal commentProposal = new CommentProposal();
		
		commentProposal.setChangesProposalId(request.getCommentProposalV1().getChangesProposalId());
		commentProposal.setComment(request.getCommentProposalV1().getComment());
		commentProposal.setOmobilityId(request.getCommentProposalV1().getOmobilityId());
		
		Signature signature = new Signature();
		signature.setSignerApp(request.getCommentProposalV1().getSignature().getSignerApp());
		signature.setSignerEmail(request.getCommentProposalV1().getSignature().getSignerEmail());
		signature.setSignerName(request.getCommentProposalV1().getSignature().getSignerName());
		signature.setSignerPosition(request.getCommentProposalV1().getSignature().getSignerPosition());
		signature.setTimestamp(request.getCommentProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
		
		commentProposal.setSignature(signature);
		
		return commentProposal;
	}

	private ApprovedProposal approveCmpStudiedDraft(OmobilityLasUpdateRequest request) {
		ApprovedProposal appCmp = new ApprovedProposal();
		String changesProposal = request.getApproveProposalV1().getChangesProposalId();
		appCmp.setChangesProposalId(changesProposal);
		
		appCmp.setOmobilityId(request.getApproveProposalV1().getOmobilityId());
		
		Signature signature = new Signature();
		signature.setSignerApp(request.getApproveProposalV1().getSignature().getSignerApp());
		signature.setSignerEmail(request.getApproveProposalV1().getSignature().getSignerEmail());
		signature.setSignerName(request.getApproveProposalV1().getSignature().getSignerName());
		signature.setSignerPosition(request.getApproveProposalV1().getSignature().getSignerPosition());
		signature.setTimestamp(request.getApproveProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
		
		appCmp.setSignature(signature);
		
		return appCmp;
	}


	private javax.ws.rs.core.Response mobilityGet(String sendingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList =  em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHeiId", sendingHeiId).getResultList();
        
        if (!omobilityLasList.isEmpty()) {
        	
        	 Collection<String> heisCoveredByCertificate;
             if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
                 heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
             } else {
                 heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
             }
             
             //checking if caller covers the receiving HEI of this mobility,
             omobilityLasList = omobilityLasList.stream().filter(omobility -> heisCoveredByCertificate.contains(omobility.getReceivingHei().getHeiId())).collect(Collectors.toList());
              
            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private javax.ws.rs.core.Response omobilityLasIndex(String sendingHeiId, List<String> receivingHeiIdList, String receiving_academic_year_id, String globalId, String mobilityType, String modifiedSince) {
    	
    	OmobilityLasIndexResponse response = new OmobilityLasIndexResponse();
    	
        List<OlearningAgreement> mobilityList =  em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHeiId", sendingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
        	
        	if (receiving_academic_year_id != null  && !receiving_academic_year_id.isEmpty()) {
        		mobilityList = mobilityList.stream().filter(omobility -> anyMatchReceivingAcademicYear.test(omobility, receiving_academic_year_id)).collect(Collectors.toList());
    		}
        	
        	if (globalId != null && !globalId.isEmpty()) {
        		mobilityList = mobilityList.stream().filter(omobility -> anyMatchSpecifiedStudent.test(omobility, globalId)).collect(Collectors.toList());
        	}
        	
        	if (mobilityType != null && !mobilityList.isEmpty()) {
        		mobilityList = mobilityList.stream().filter(omobility -> anyMatchSpecifiedType.test(omobility, mobilityType)).collect(Collectors.toList());
        	}
        	
        	if (modifiedSince != null && !modifiedSince.isEmpty()) {
        		
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");//2004-02-12T15:19:21+01:00
                Calendar calendarModifySince = Calendar.getInstance();
                
                try {
        			calendarModifySince.setTime(sdf.parse(modifiedSince));
        		} catch (ParseException e) {
        			throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
        		}
                
                Date modified_since = calendarModifySince.getTime();
                
                List<OlearningAgreement> mobilities = new ArrayList<>();
                mobilityList.stream().forEachOrdered((m) -> {
                	
                	Date studentSignatureDate = m.getFirstVersion().getStudentSignature() != null ? m.getFirstVersion().getStudentSignature().getTimestamp() : null;
                	Date receivingSignatureDate = m.getFirstVersion().getReceivingHeiSignature() != null ? m.getFirstVersion().getReceivingHeiSignature().getTimestamp() : null;
                	Date sendingSignatureDate = m.getFirstVersion().getStudentSignature() != null ? m.getFirstVersion().getStudentSignature().getTimestamp() : null;
                	
                	if (studentSignatureDate != null) {
                		if (studentSignatureDate.after(modified_since)) {
                			mobilities.add(m);
                		}
                	}
                	
                	if (receivingSignatureDate != null) {
                		if (receivingSignatureDate.after(modified_since)) {
                			mobilities.add(m);
                		}
                	}
                	
                	if (sendingSignatureDate != null) {
                		if (sendingSignatureDate.after(modified_since)) {
                			mobilities.add(m);
                    	}
                	}
                });
                
                mobilityList.clear(); 
                mobilityList.addAll(mobilities);
        	}
        	
            response.getOmobilityId().addAll(omobilityLasIds(mobilityList, receivingHeiIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<LearningAgreement> omobilitiesLas(List<OlearningAgreement> omobilityLasList, List<String> omobilityLasIdList) {
        List<LearningAgreement> omobilitiesLas = new ArrayList<>();
        omobilityLasList.stream().forEachOrdered((m) -> {
            if (omobilityLasIdList.contains(m.getId())) {
                omobilitiesLas.add(converter.convertToLearningAgreements(m));
            }
        });
        
        return omobilitiesLas;
    }
    
    private List<String> omobilityLasIds(List<OlearningAgreement> lasList, List<String> receivingHeiIdList) {
        List<String> omobilityLasIds = new ArrayList<>();
        
        lasList.stream().forEachOrdered((m) -> {
            if (receivingHeiIdList.isEmpty() || receivingHeiIdList.contains(m.getReceivingHei())) {
                omobilityLasIds.add(m.getId());
            }
        });
        
        return omobilityLasIds;
    }
    
    BiPredicate<OlearningAgreement, String> anyMatchReceivingAcademicYear = new BiPredicate<OlearningAgreement, String>()
    {
        @Override
        public boolean test(OlearningAgreement omobility, String receiving_academic_year_id) {
        	
        	return receiving_academic_year_id.equals(omobility.getReceivingAcademicTermEwpId());
        }
    };
    
    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedStudent = new BiPredicate<OlearningAgreement, String>()
    {
        @Override
        public boolean test(OlearningAgreement omobility, String global_id ) {
        	Student student = omobility.getChangesProposal().getStudent();
        	        	
        	return global_id.equals(student.getGlobalId()); 
        }
    };
    
    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedType = new BiPredicate<OlearningAgreement, String>()
    {
        @Override
        public boolean test(OlearningAgreement omobility, String mobilityType ) {
        	 
        	if (MobilityType.BLENDED.value().equals(mobilityType)) {
        		
        		return omobility.getFirstVersion().getBlendedMobilityComponents() != null && !omobility.getFirstVersion().getBlendedMobilityComponents().isEmpty();
        	} else if (MobilityType.DOCTORAL.value().equals(mobilityType)) {
        		
        		return omobility.getFirstVersion().getShortTermDoctoralComponents() != null && !omobility.getFirstVersion().getShortTermDoctoralComponents().isEmpty();
        	} else if (MobilityType.SEMESTRE.value().equals(mobilityType)) {
        		
        		if ( omobility.getFirstVersion() != null ) {
        			
					return ( omobility.getFirstVersion().getComponentsStudied() != null && !omobility.getFirstVersion().getComponentsStudied().isEmpty() );
				} else if ( omobility.getApprovedChanges() != null ) {
					
					return ( omobility.getApprovedChanges().getComponentsStudied() != null && !omobility.getApprovedChanges().getComponentsStudied().isEmpty() );
				}
        	}
        	
        	return false;
        }
    };
}
