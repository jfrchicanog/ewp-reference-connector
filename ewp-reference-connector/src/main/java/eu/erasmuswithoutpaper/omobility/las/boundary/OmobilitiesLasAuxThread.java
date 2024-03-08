package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class OmobilitiesLasAuxThread {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(OmobilitiesLasAuxThread.class.getCanonicalName());

    public void createLas(String heiId, String mobilityId) {
        LOG.fine("OmobilitiesLasAuxThread: Start auxiliary thread to create LAS for mobility " + mobilityId);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        LOG.fine("OmobilitiesLasAuxThread: map: " + (map == null ? "null" : map.toString()));
        if (map == null || map.isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: No LAS URLs found for HEI " + heiId);
            return;
        }

        String url = map.get("get");

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setHeiId(heiId);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("sending_hei_id", Collections.singletonList(heiId));
        paramsMap.put("omobility_id", Collections.singletonList(mobilityId));
        ParamsClass paramsClass = new ParamsClass();
        paramsClass.setUnknownFields(paramsMap);
        clientRequest.setParams(paramsClass);

        ClientResponse omobilityLasGetResponse = restClient.sendRequest(clientRequest, OmobilityLasGetResponse.class);

        LOG.fine("NOTIFY: response: " + omobilityLasGetResponse.getRawResponse());
    }
}
