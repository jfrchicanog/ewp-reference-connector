package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.Approval;
import eu.erasmuswithoutpaper.omobility.las.entity.ApproveComponentsStudiedDraft;
import eu.erasmuswithoutpaper.omobility.las.entity.ComponentsStudied;
import eu.erasmuswithoutpaper.omobility.las.entity.Credit;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.SnapshotOfComponentsStudied;
import eu.erasmuswithoutpaper.omobility.las.entity.UpdateComponentsStudied;

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
    public javax.ws.rs.core.Response omobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("/las/et")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }
    
    @POST
    @Path("/las/update")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response omobilityLasUpdatePost(OmobilityLasUpdateRequest request) {
        if (request == null) {
            throw new EwpWebApplicationException("No update data was sent", Response.Status.BAD_REQUEST);
        }

        eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest mobilityUpdateRequest = new eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest();
        mobilityUpdateRequest.setSendingHeiId(request.getSendingHeiId());
        
        ApproveComponentsStudiedDraft appCmp = approveCmpStudiedDraft(request);
        mobilityUpdateRequest.setApproveComponentsStudiedDraft(appCmp);
        
        UpdateComponentsStudied updateComponentsStudied = updateComponentsStudied(request);
        mobilityUpdateRequest.setUpdateComponentsStudied(updateComponentsStudied);
        
        em.persist(mobilityUpdateRequest);

        OmobilityLasUpdateResponse response = new OmobilityLasUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("Thank you! We will review your suggestion");
        response.getSuccessUserMessage().add(message);
        return javax.ws.rs.core.Response.ok(response).build();
    }

	private UpdateComponentsStudied updateComponentsStudied(OmobilityLasUpdateRequest request) {
		UpdateComponentsStudied updateComponentsStudied = new UpdateComponentsStudied();
		
		SnapshotOfComponentsStudied snapshotOfComponentsStudied = new SnapshotOfComponentsStudied();
		snapshotOfComponentsStudied.setApproval(transformToAListApproval(request.getUpdateComponentsStudiedV1().getCurrentLatestDraftSnapshot().getApproval()));
		snapshotOfComponentsStudied.setComponentStudied(transformToAListCmpStudied(request.getUpdateComponentsStudiedV1().getCurrentLatestDraftSnapshot().getComponentStudied()));
		snapshotOfComponentsStudied.setInEffectSince(request.getUpdateComponentsStudiedV1().getCurrentLatestDraftSnapshot().getInEffectSince().toGregorianCalendar().getTime());
		snapshotOfComponentsStudied.setShouldNowBeApprovedBy(transformToAListApprovedBy(request.getUpdateComponentsStudiedV1().getCurrentLatestDraftSnapshot().getShouldNowBeApprovedBy()));
		
		updateComponentsStudied.setCurrentLatestDraftSnapshot(snapshotOfComponentsStudied);
		return updateComponentsStudied;
	}

	private ApproveComponentsStudiedDraft approveCmpStudiedDraft(OmobilityLasUpdateRequest request) {
		ApproveComponentsStudiedDraft appCmp = new ApproveComponentsStudiedDraft();
		eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ApprovingParty aparty = request.getApproveComponentsStudiedDraftV1().getApprovingParty();
		appCmp.setApprovingParty(aparty.value());
		
		SnapshotOfComponentsStudied sanpshotCmpStu = new SnapshotOfComponentsStudied();
		sanpshotCmpStu.setInEffectSince(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getInEffectSince().toGregorianCalendar().getTime());
		sanpshotCmpStu.setApproval(transformToAListApproval(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getApproval()));
		sanpshotCmpStu.setComponentStudied(transformToAListCmpStudied(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getComponentStudied()));
		sanpshotCmpStu.setShouldNowBeApprovedBy(transformToAListApprovedBy(request.getApproveComponentsStudiedDraftV1().getCurrentLatestDraftSnapshot().getShouldNowBeApprovedBy()));
		
		appCmp.setCurrentLatestDraftSnapshot(sanpshotCmpStu);
		return appCmp;
	}

    private List<String> transformToAListApprovedBy(
			List<eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ApprovingParty> shouldNowBeApprovedBy) {
		
    	List<String> parties = shouldNowBeApprovedBy.stream().map(party -> {
			return party.value();
		}).collect(Collectors.toList());
    	
		return parties;
	}

	private List<ComponentsStudied> transformToAListCmpStudied(List<ComponentStudied> componentStudied) {
		List<ComponentsStudied> cmpStudied = componentStudied.stream().map(cmp -> {
			
			ComponentsStudied theCmpStu = new ComponentsStudied();
			theCmpStu.setAcademicTermDisplayName(cmp.getAcademicTermDisplayName());
			theCmpStu.setCredit(transformToAListCredit(cmp.getCredit()));
			theCmpStu.setLoiId(cmp.getLoiId());
			theCmpStu.setLosCode(cmp.getLosCode());
			theCmpStu.setTitle(cmp.getTitle());
			
			return theCmpStu;
		}).collect(Collectors.toList());
		
		return cmpStudied;
	}

	private List<Credit> transformToAListCredit(
			List<eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ComponentStudied.Credit> credit) {
		
		List<Credit> credits = credit.stream().map(c -> {
			Credit theCredit = new Credit();
			
			theCredit.setScheme(c.getScheme());
			theCredit.setValue(c.getValue());
			
			return theCredit;
		}).collect(Collectors.toList());
		
		return credits;
	}

	private List<Approval> transformToAListApproval(
			List<eu.erasmuswithoutpaper.api.omobilities.las.endpoints.SnapshotOfComponentsStudied.Approval> approval) {
		
		List<Approval> approvals = approval.stream().map(app -> {
			Approval theApproval = new Approval();
			
			theApproval.setByParty(app.getByParty().value());
			theApproval.setTimestamp(app.getTimestamp().toGregorianCalendar().getTime());
			
			return theApproval;
		}).collect(Collectors.toList());
		
		return approvals;
	}

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
