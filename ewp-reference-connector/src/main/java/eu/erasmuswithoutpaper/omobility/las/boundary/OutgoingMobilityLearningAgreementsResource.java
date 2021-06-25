package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.ApproveComponentsStudiedDraft;
import eu.erasmuswithoutpaper.omobility.las.entity.ApprovingParty;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.SnapshotOfComponentsStudied;

@Stateless
@Path("omobilities")
public class OutgoingMobilityLearningAgreementsResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;
    
    @Inject
    RestClient restClient;
    
    @GET
    @Path("/las/index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response indexGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList) {
        return omobilityLasIndex(sendingHeiId, receivingHeiIdList);
    }
    
    @POST
    @Path("/las/index")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response indexPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("receiving_hei_id") List<String> receivingHeiIdList) {
        return omobilityLasIndex(sendingHeiId, receivingHeiIdList);
    }
    
    @GET
    @Path("/las/get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("/las/et")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response mobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
   /* @POST
    @Path("/las/update")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityLasUpdatePost(OmobilityLasUpdateRequest request) {
        if (request == null) {
            throw new EwpWebApplicationException("No update data was sent", Response.Status.BAD_REQUEST);
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            //MobilityUpdateRequestType type = MobilityUpdateRequestType.APPROVE_COMPONENTS_STUDIED_DRAFT_V1;
            String updateInformation = null;
            if (request.getApproveComponentsStudiedDraftV1() != null) {
                //type = MobilityUpdateRequestType.APPROVE_COMPONENTS_STUDIED_DRAFT_V1;
                updateInformation = mapper.writeValueAsString(request.getApproveComponentsStudiedDraftV1());
            } else if (request.getUpdateComponentsStudiedV1()!= null) {
                //type = MobilityUpdateRequestType.UPDATE_COMPONENTS_STUDIED_V1;
                updateInformation = mapper.writeValueAsString(request.getUpdateComponentsStudiedV1());
            } 
            
            eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest mobilityUpdateRequest = new eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest();
            //mobilityUpdateRequest.setType(type);
            mobilityUpdateRequest.setSendingHeiId(request.getSendingHeiId());
            
            ApproveComponentsStudiedDraft appCmp = new ApproveComponentsStudiedDraft();
            eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ApprovingParty aparty = request.getApproveComponentsStudiedDraftV1().getApprovingParty());
            appCmp.setApprovingParty(aparty.value());
            
            SnapshotOfComponentsStudied sanpshotCmpStu = new SnapshotOfComponentsStudied();
            sanpshotCmpStu.setInEffectSince(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getInEffectSince().);
            sanpshotCmpStu.setApproval(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getApproval());
            sanpshotCmpStu.setComponentStudied(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getComponentStudied());
            sanpshotCmpStu.setShouldNowBeApprovedBy(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getShouldNowBeApprovedBy());
            
            appCmp.setCurrentLatestDraftSnapshot(currentLatestDraftSnapshot);
            
            mobilityUpdateRequest.setApproveComponentsStudiedDraft(approveComponentsStudiedDraft);
            //mobilityUpdateRequest.setUpdateRequestDate(new Date());
            //mobilityUpdateRequest.setUpdateInformation(updateInformation);
            em.persist(mobilityUpdateRequest);
        } catch (JsonProcessingException ex) {
            throw new EwpWebApplicationException("Unexpected error", Response.Status.INTERNAL_SERVER_ERROR);
        }

        OmobilitiesUpdateResponse response = new OmobilitiesUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("Thank you! We will review your suggestion");
        response.getSuccessUserMessage().add(message);
        return javax.ws.rs.core.Response.ok(response).build();
    }*/
    

    private javax.ws.rs.core.Response mobilityGet(String sendingHeiId, List<String> mobilityIdList) {
        if (mobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList =  em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHeiId", sendingHeiId).getResultList();
        if (!omobilityLasList.isEmpty()) {
            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private javax.ws.rs.core.Response omobilityLasIndex(String sendingHeiId, List<String> receivingHeiIdList) {
        OmobilityLasIndexResponse response = new OmobilityLasIndexResponse();
        List<OlearningAgreement> mobilityList =  em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHeiId", sendingHeiId).getResultList();
        if (!mobilityList.isEmpty()) {
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
}
