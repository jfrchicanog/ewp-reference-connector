package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    @Path("create")
    @Consumes("application/json")
    public Response create(LearningAgreement learningAgreement) {

        LOG.fine("CREATE: start");

        LOG.fine("CREATE: olearningAgreement: " + learningAgreement.getChangesProposal().getId());

        OlearningAgreement olearningAgreement = converter.convertToOlearningAgreement(learningAgreement, false, null);
        olearningAgreement.setFromPartner(false);
        String id = learningAgreementEJB.insert(olearningAgreement);
        olearningAgreement.setId(id);

        LOG.fine("CREATE: Send notification");

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(olearningAgreement);
        });

        LOG.fine("CREATE: notification sent");

        return Response.ok(id).build();
    }


    @POST
    @Path("change")
    @Consumes("application/json")
    public Response change(LearningAgreement learningAgreement) {
        LOG.fine("CHANGE: start");

        LOG.fine("CHANGE: olearningAgreement: " + learningAgreement.getChangesProposal().getId());

        OlearningAgreement original = learningAgreementEJB.findById(learningAgreement.getOmobilityId());
        if (original == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        OlearningAgreement olearningAgreement = converter.convertToOlearningAgreement(learningAgreement, false, original);
        String id = learningAgreementEJB.update(olearningAgreement);

        if (id == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(olearningAgreement);
        });

        LOG.fine("CHANGE: merge olearningAgreement: " + olearningAgreement.getId());

        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        response.getLa().add(converter.convertToLearningAgreements(olearningAgreement));

        return Response.ok(response).build();
    }

    @POST
    @Path("update/approve")
    @Consumes("application/json")
    public Response updateAccept(@QueryParam("id") String id, OmobilityLasUpdateRequest omobilityLasUpdateRequest) throws JAXBException, IOException, DatatypeConfigurationException {
        LOG.fine("APPROVE: start");
        LOG.fine("APPROVE: ownId: " + id);
        LOG.fine("APPROVE request: " + omobilityLasUpdateRequest.toString());
        if (omobilityLasUpdateRequest.getApproveProposalV1() != null && omobilityLasUpdateRequest.getApproveProposalV1().getSignature() != null) {
            omobilityLasUpdateRequest.getApproveProposalV1().getSignature().setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        }

        learningAgreementEJB.approveChangesProposal(omobilityLasUpdateRequest, id);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(omobilityLasUpdateRequest.getSendingHeiId());
        LOG.fine("APPROVE: map: " + map.toString());
        String url = map.get("update-url");
        LOG.fine("APPROVE: upd url: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);
        clientRequest.setXml(omobilityLasUpdateRequest);

        ClientResponse response = restClient.sendRequest(clientRequest, Empty.class, true, convertObjectToString(omobilityLasUpdateRequest));

        LOG.fine("APPROVE: response: " + response.getRawResponse());

        return Response.ok(response).build();
    }

    @POST
    @Path("update/reject")
    @Consumes("application/json")
    public Response updateReject(@QueryParam("id") String id, OmobilityLasUpdateRequest omobilityLasUpdateRequest) throws JAXBException, IOException {

        LOG.fine("REJCET: start");
        LOG.fine("REJCET: ownId: " + id);
        LOG.fine("REJCET request: " + omobilityLasUpdateRequest.toString());

        learningAgreementEJB.rejectChangesProposal(omobilityLasUpdateRequest, id);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(omobilityLasUpdateRequest.getSendingHeiId());
        LOG.fine("REJCET: map: " + map.toString());
        String url = map.get("update-url");
        LOG.fine("REJCET: upd url: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);
        clientRequest.setXml(omobilityLasUpdateRequest);

        ClientResponse response = restClient.sendRequest(clientRequest, Empty.class, true, convertObjectToString(omobilityLasUpdateRequest));

        LOG.fine("REJCET: response: " + response.getRawResponse());

        return Response.ok(response).build();
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

    private List<LearningAgreement> omobilitiesLas(List<OlearningAgreement> omobilityLasList, List<String> omobilityLasIdList) {
        List<LearningAgreement> omobilitiesLas = new ArrayList<>();
        omobilityLasList.stream().forEachOrdered((m) -> {
            if (omobilityLasIdList.contains(m.getId())) {
                omobilitiesLas.add(converter.convertToLearningAgreements(m));
            }
        });

        return omobilitiesLas;
    }

    private static String convertObjectToString(OmobilityLasUpdateRequest object) throws JAXBException, IOException {
        LOG.fine("XMLCONVERTER: start OmobilityLasUpdateRequest object to String conversion");
        // Create JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(OmobilityLasUpdateRequest.class);

        // Create Marshaller
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the object to XML
        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);

        String xmlString = sw.toString();

        xmlString = xmlString.replaceAll("\\s+", "");

        LOG.fine("XMLCONVERTER: OmobilityLasUpdateRequest object to XML: " + xmlString);

        LOG.fine("XMLCONVERTER: OmobilityLasUpdateRequest object to String conversion finished");

        // Convert XML to byte array
        return xmlString;
    }
}
