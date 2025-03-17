package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.omobility.las.control.LearningAgreementEJB;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.*;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OmobilitiesLasAuxThread {

    @EJB
    LearningAgreementEJB learningAgreementEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(OmobilitiesLasAuxThread.class.getCanonicalName());

    public void createLas(String heiId, String omobilityId) throws Exception {
        LOG.fine("OmobilitiesLasAuxThread: Start auxiliary thread to create LAS for mobility " + omobilityId);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        LOG.fine("OmobilitiesLasAuxThread: map: " + (map == null ? "null" : map.toString()));
        if (map == null || map.isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: No LAS URLs found for HEI " + heiId);
            return;
        }

        String url = map.get("get-url");

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setHeiId(heiId);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);

        LOG.fine("OmobilitiesLasAuxThread: url: " + url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("sending_hei_id", Collections.singletonList(heiId));
        paramsMap.put("omobility_id", Collections.singletonList(omobilityId));
        ParamsClass paramsClass = new ParamsClass();
        paramsClass.setUnknownFields(paramsMap);
        clientRequest.setParams(paramsClass);

        LOG.fine("OmobilitiesLasAuxThread: params: " + paramsMap.toString());

        ClientResponse omobilityLasGetResponse = restClient.sendRequest(clientRequest, OmobilityLasGetResponse.class);

        LOG.fine("NOTIFY: response: " + omobilityLasGetResponse.getRawResponse());

        if (omobilityLasGetResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            if (omobilityLasGetResponse.getStatusCode() <= 599 && omobilityLasGetResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "omobility-las", "get", Integer.toString(omobilityLasGetResponse.getStatusCode()), omobilityLasGetResponse.getErrorMessage(), null);
            } else if (omobilityLasGetResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "omobility-las", "get", Integer.toString(omobilityLasGetResponse.getStatusCode()), omobilityLasGetResponse.getErrorMessage(), "Error");
            }
            return;
        }

        OmobilityLasGetResponse response = (OmobilityLasGetResponse) omobilityLasGetResponse.getResult();

        LOG.fine("OmobilitiesLasAuxThread: response: " + response.toString());

        if (response == null) {
            LOG.fine("OmobilitiesLasAuxThread: response is null");
            return;
        }

        if (response.getLa() == null) {
            LOG.fine("OmobilitiesLasAuxThread: response.getLa() is null");
            return;
        }

        if (response.getLa().isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: response.getLa() is empty");
            return;
        }

        LearningAgreement learningAgreement = response.getLa().get(0);

        LOG.fine("OmobilitiesLasAuxThread: learningAgreement: " + learningAgreement.toString());

        List<OlearningAgreement> omobilityLasList = learningAgreementEJB.findBySendingHeiId(heiId);

        omobilityLasList = omobilityLasList.stream().filter(ola -> ola.getOmobilityId().equals(learningAgreement.getOmobilityId())).collect(Collectors.toList());
        if (omobilityLasList.isEmpty()) {
            createOlearningAgreement(learningAgreement);
        } else {
            updateOlearningAgreement(learningAgreement, omobilityLasList.get(0));
        }


        LOG.fine("OmobilitiesLasAuxThread: End auxiliary thread to create LAS for mobility " + omobilityId);

    }

    private void createOlearningAgreement(LearningAgreement la) {
        LOG.fine("OmobilitiesLasAuxThread: createOlearningAgreement");

        OlearningAgreement ola = converter.convertToOlearningAgreement(la, true, null);
        if (ola.getChangesProposal() != null && ola.getChangesProposal().getComponentsRecognized() != null && !ola.getChangesProposal().getComponentsRecognized().isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: before insert " + ola.getChangesProposal().getComponentsRecognized().get(0).getTitle());
        }
        ola.setModificateSince(new java.util.Date());
        learningAgreementEJB.insert(ola);

        LOG.fine("OmobilitiesLasAuxThread: OlearningAgreement created");
    }

    private void updateOlearningAgreement(LearningAgreement la, OlearningAgreement ola) {
        LOG.fine("OmobilitiesLasAuxThread: updateOlearningAgreement");

        OlearningAgreement updatedOla = converter.convertToOlearningAgreement(la, true, ola);
        updatedOla.setModificateSince(new java.util.Date());
        learningAgreementEJB.merge(updatedOla);

        LOG.fine("OmobilitiesLasAuxThread: OlearningAgreement updated");
    }

}
