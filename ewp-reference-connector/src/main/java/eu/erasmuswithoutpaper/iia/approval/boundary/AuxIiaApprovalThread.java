package eu.erasmuswithoutpaper.iia.approval.boundary;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.boundary.AuxIiaThread;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.Iia;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuxIiaApprovalThread {

    @EJB
    IiasEJB iiasEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(AuxIiaApprovalThread.class.getCanonicalName());


    public void getApprovedIias(String heiId, String iiaId) {

        LOG.fine("AuxIiaApprovalThread: start getApprovedIias");

        String localHeiId = iiasEJB.getHeiId();

        LOG.fine("AuxIiaApprovalThread: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaApprovalHeiUrls(heiId);
        if (map == null) {
            return;
        }

        LOG.fine("AuxIiaApprovalThread: MAP ENCONTRADO");

        String url = map.get("url");
        if (url == null) {
            return;
        }

        LOG.fine("AuxIiaApprovalThread: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("AuxIiaApprovalThread: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasApprovalResponse.class);

        LOG.fine("AuxIiaApprovalThread: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            //TODO: Handle error, notify monitoring
            return;
        }

        LOG.fine("AuxIiaApprovalThread: Respuesta raw: " + clientResponse.getRawResponse());

        IiasApprovalResponse responseEnity = (IiasApprovalResponse) clientResponse.getResult();

        if (responseEnity == null) {
            return;
        }

        if (responseEnity.getApproval() == null || responseEnity.getApproval().isEmpty()) {
            return;
        }

        IiasApprovalResponse.Approval approval = responseEnity.getApproval().get(0);

        Iia iia = iiasEJB.findById(approval.getIiaId());
        if (!iia.getConditionsHash().equals(approval.getIiaHash())) {
            return;
        }

        IiaApproval iiaApproval = new IiaApproval();
        iiaApproval.setHeiId(heiId);
        iiaApproval.setIia(iia);
        iiaApproval.setConditionsHash(approval.getIiaHash());

        iiasEJB.insertIiaApproval(iiaApproval);
    }
}
