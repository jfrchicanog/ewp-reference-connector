package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.*;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.boundary.NotifyAux;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.omobility.las.control.LearningAgreementEJB;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents;
import eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution;
import eu.erasmuswithoutpaper.omobility.las.entity.Signature;
import eu.erasmuswithoutpaper.omobility.las.entity.Student;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;

@Path("omobilities/las/test")
public class GuiOutgoingMobilityLearningAgreementsResource {

    @EJB
    LearningAgreementEJB learningAgreementEJB;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(GuiOutgoingMobilityLearningAgreementsResource.class.getCanonicalName());

    @GET
    @Path("")
    public Response hello(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList = learningAgreementEJB.findBySendingHeiIdFilterd(sendingHeiId);

        if (!omobilityLasList.isEmpty()) {

            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        }

        return javax.ws.rs.core.Response.ok(response).build();

    }

    @POST
    @Path("change")
    @Consumes("application/json")
    public Response change(OlearningAgreement olearningAgreement) {
        LOG.fine("CHANGE: start");

        LOG.fine("CHANGE: olearningAgreement: " + olearningAgreement.getChangesProposal().getId());

        String id  = learningAgreementEJB.update(olearningAgreement);

        if(id != null) {
            OlearningAgreement olearningAgreementDB2 = learningAgreementEJB.findById(olearningAgreement.getId());

            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notifyPartner(olearningAgreementDB2);
            });

            LOG.fine("CHANGE: merge olearningAgreement: " + olearningAgreementDB2.getId());


            OmobilityLasGetResponse response = new OmobilityLasGetResponse();
            response.getLa().add(converter.convertToLearningAgreements(olearningAgreementDB2));
            return Response.ok(response).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("create")
    @Consumes("application/json")
    public Response create(OlearningAgreement olearningAgreement) {

        LOG.fine("CREATE: start");

        LOG.fine("CREATE: olearningAgreement: " + olearningAgreement.getChangesProposal().getId());

        String id = learningAgreementEJB.insert(olearningAgreement);

        LOG.fine("NOTIFY: Send notification");

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(olearningAgreement);
        });

        LOG.fine("NOTIFY: notification sent");

        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        response.getLa().add(converter.convertToLearningAgreements(learningAgreementEJB.findById(id)));
        return Response.ok(response).build();
    }

    @POST
    @Path("update/approve")
    @Consumes("application/json")
    public Response update(@QueryParam("heiId") String heiId, @QueryParam("ownId") String id, ApprovedProposal request) {

        LOG.fine("APPROVE: start");
        LOG.fine("APPROVE: heiId: " + heiId);
        LOG.fine("APPROVE: ownId: " + id);
        LOG.fine("APPROVE request: " + request.toString());

        if(id != null && !id.isEmpty()) {
            OmobilityLasUpdateRequest ownUpdate = new OmobilityLasUpdateRequest();
            ApproveProposalV1 ownApprove = new ApproveProposalV1();
            OlearningAgreement olearningAgreement = learningAgreementEJB.findById(id);
            LOG.fine("APPROVE: olearningAgreement: " + (olearningAgreement == null ? "null" : "not null"));
            LOG.fine("APPROVE: changesProposal: " + (olearningAgreement.getChangesProposal() == null ? "null" : "not null"));
            ownApprove.setChangesProposalId(olearningAgreement.getChangesProposal().getId());
            ownApprove.setOmobilityId(id);
            eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature ownSignature = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();
            ownSignature.setSignerApp(request.getSignature().getSignerApp());
            ownSignature.setSignerEmail(request.getSignature().getSignerEmail());
            ownSignature.setSignerName(request.getSignature().getSignerName());
            ownSignature.setSignerPosition(request.getSignature().getSignerPosition());
            ownApprove.setSignature(ownSignature);
            ownUpdate.setApproveProposalV1(ownApprove);
            ownUpdate.setSendingHeiId(heiId);

            learningAgreementEJB.approveChangesProposal(ownUpdate);
        }


        OmobilityLasUpdateRequest requestSend = new OmobilityLasUpdateRequest();
        ApproveProposalV1 approvedProposalV1 = new ApproveProposalV1();
        approvedProposalV1.setChangesProposalId(request.getChangesProposalId());
        approvedProposalV1.setOmobilityId(request.getOmobilityId());
        eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature signature = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();
        signature.setSignerApp(request.getSignature().getSignerApp());
        signature.setSignerEmail(request.getSignature().getSignerEmail());
        signature.setSignerName(request.getSignature().getSignerName());
        signature.setSignerPosition(request.getSignature().getSignerPosition());
        approvedProposalV1.setSignature(signature);
        requestSend.setApproveProposalV1(approvedProposalV1);
        requestSend.setSendingHeiId(heiId);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        String url = map.get("update-url");

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);
        clientRequest.setXml(requestSend);

        ClientResponse response = restClient.sendRequest(clientRequest, Empty.class, true);

        LOG.fine("UPDATE: response: " + response.getRawResponse());

        return Response.ok(response).build();
    }

    @POST
    @Path("update/reject")
    @Consumes("application/json")
    public Response update(@QueryParam("heiId") String heiId, @QueryParam("ownId") String id, CommentProposal request) {

        LOG.fine("UPDATE: start");
        if(id != null && !id.isEmpty()) {
            OmobilityLasUpdateRequest ownUpdate = new OmobilityLasUpdateRequest();
            CommentProposalV1 ownComment = new CommentProposalV1();
            OlearningAgreement olearningAgreement = learningAgreementEJB.findById(id);
            ownComment.setChangesProposalId(olearningAgreement.getChangesProposal().getId());
            ownComment.setOmobilityId(id);
            ownComment.setComment(request.getComment());
            eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature ownSignature = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();
            ownSignature.setSignerApp(request.getSignature().getSignerApp());
            ownSignature.setSignerEmail(request.getSignature().getSignerEmail());
            ownSignature.setSignerName(request.getSignature().getSignerName());
            ownSignature.setSignerPosition(request.getSignature().getSignerPosition());
            ownComment.setSignature(ownSignature);
            ownUpdate.setCommentProposalV1(ownComment);
            ownUpdate.setSendingHeiId(heiId);

            learningAgreementEJB.rejectChangesProposal(ownUpdate);
        }

        OmobilityLasUpdateRequest requestSend = new OmobilityLasUpdateRequest();
        CommentProposalV1 commentProposalV1 = new CommentProposalV1();
        commentProposalV1.setChangesProposalId(request.getChangesProposalId());
        commentProposalV1.setOmobilityId(request.getOmobilityId());
        commentProposalV1.setComment(request.getComment());
        eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature signature = new eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature();
        signature.setSignerApp(request.getSignature().getSignerApp());
        signature.setSignerEmail(request.getSignature().getSignerEmail());
        signature.setSignerName(request.getSignature().getSignerName());
        signature.setSignerPosition(request.getSignature().getSignerPosition());
        commentProposalV1.setSignature(signature);
        requestSend.setCommentProposalV1(commentProposalV1);
        requestSend.setSendingHeiId(heiId);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        String url = map.get("update-url");

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);
        clientRequest.setXml(requestSend);

        ClientResponse response = restClient.sendRequest(clientRequest, Empty.class, true);

        LOG.fine("UPDATE: response: " + response.getRawResponse());

        return Response.ok(response).build();
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

    private List<ClientResponse> notifyPartner(OlearningAgreement olearningAgreement) {
        LOG.fine("NOTIFY: Send notification");

        String localHeiId = learningAgreementEJB.getHeiId();

        List<ClientResponse> partnersResponseList = new ArrayList<>();

        Set<NotifyAux> cnrUrls = new HashSet<>();

        MobilityInstitution partnerSending = olearningAgreement.getSendingHei();
        MobilityInstitution partnerReceiving = olearningAgreement.getReceivingHei();

        LOG.fine("NOTIFY: partnerSending: " + partnerSending.getHeiId());
        LOG.fine("NOTIFY: partnerReceiving: " + partnerReceiving.getHeiId());

        Map<String, String> urls = null;

        if (!localHeiId.equals(partnerSending.getHeiId())) {

            //Get the url for notify the institute not supported by our EWP
            urls = registryClient.getOmobilityLaCnrHeiUrls(partnerSending.getHeiId());

            if (urls != null) {
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    cnrUrls.add(new NotifyAux(partnerSending.getHeiId(), entry.getValue()));
                }
            }

        }
        if (!localHeiId.equals(partnerReceiving.getHeiId())) {

            //Get the url for notify the institute not supported by our EWP
            urls = registryClient.getOmobilityLaCnrHeiUrls(partnerReceiving.getHeiId());

            if (urls != null) {
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    cnrUrls.add(new NotifyAux(partnerReceiving.getHeiId(), entry.getValue()));
                }

            }
        }


        String finalLocalHeiId = localHeiId;
        cnrUrls.forEach(url -> {
            LOG.fine("NOTIFY: url: " + url.getUrl());
            LOG.fine("NOTIFY: heiId: " + url.getHeiId());
            //Notify the other institution about the modification
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setUrl(url.getUrl());//get the first and only one url
            clientRequest.setHeiId(url.getHeiId());
            clientRequest.setMethod(HttpMethodEnum.POST);
            clientRequest.setHttpsec(true);

            Map<String, List<String>> paramsMap = new HashMap<>();
            paramsMap.put("sending_hei_id", Collections.singletonList(finalLocalHeiId));
            paramsMap.put("omobility_id", Collections.singletonList(olearningAgreement.getId()));
            ParamsClass paramsClass = new ParamsClass();
            paramsClass.setUnknownFields(paramsMap);
            clientRequest.setParams(paramsClass);

            ClientResponse iiaResponse = restClient.sendRequest(clientRequest, Empty.class);

            LOG.fine("NOTIFY: response: " + iiaResponse.getRawResponse());

            try {
                if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "ola-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
                } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "ola-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
                }
            } catch (Exception e) {

            }

            partnersResponseList.add(iiaResponse);
        });

        return partnersResponseList;

    }

    private static ListOfComponents getListOfComponents(ChangesProposal changesProposal, ApprovedProposal appCmp) {
        ListOfComponents cmp = new ListOfComponents();

        cmp.setBlendedMobilityComponents(changesProposal.getBlendedMobilityComponents());
        cmp.setComponentsStudied(changesProposal.getComponentsStudied());
        cmp.setComponentsRecognized(changesProposal.getComponentsRecognized());
        cmp.setVirtualComponents(changesProposal.getVirtualComponents());
        cmp.setShortTermDoctoralComponents(changesProposal.getShortTermDoctoralComponents());
        cmp.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
        if (appCmp.getSignature() != null) {
            cmp.setReceivingHeiSignature(appCmp.getSignature());
        }
        cmp.setStudentSignature(changesProposal.getStudentSignature());
        return cmp;
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
        if (lasList == null) {
            return omobilityLasIds;
        }
        lasList.stream().forEachOrdered((m) -> {
            if (receivingHeiIdList.isEmpty() || receivingHeiIdList.contains(m.getReceivingHei())) {
                omobilityLasIds.add(m.getId());
            }
        });

        return omobilityLasIds;
    }

    BiPredicate<OlearningAgreement, String> anyMatchReceivingAcademicYear = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String receiving_academic_year_id) {
            return receiving_academic_year_id.equals(omobility.getReceivingAcademicTermEwpId());
        }
    };

    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedStudent = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String global_id) {
            Student student = omobility.getChangesProposal().getStudent();

            return global_id.equals(student.getGlobalId());
        }
    };

    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedType = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String mobilityType) {

            if (MobilityType.BLENDED.value().equals(mobilityType)) {

                return omobility.getFirstVersion().getBlendedMobilityComponents() != null && !omobility.getFirstVersion().getBlendedMobilityComponents().isEmpty();
            } else if (MobilityType.DOCTORAL.value().equals(mobilityType)) {

                return omobility.getFirstVersion().getShortTermDoctoralComponents() != null && !omobility.getFirstVersion().getShortTermDoctoralComponents().isEmpty();
            } else if (MobilityType.SEMESTRE.value().equals(mobilityType)) {

                if (omobility.getFirstVersion() != null) {

                    return (omobility.getFirstVersion().getComponentsStudied() != null && !omobility.getFirstVersion().getComponentsStudied().isEmpty());
                } else if (omobility.getApprovedChanges() != null) {

                    return (omobility.getApprovedChanges().getComponentsStudied() != null && !omobility.getApprovedChanges().getComponentsStudied().isEmpty());
                }
            }

            return false;
        }
    };
}
