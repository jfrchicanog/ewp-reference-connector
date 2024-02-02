/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.monitoring;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RestClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 *
 * @author Moritz Baader
 */
@Stateless
public class SendMonitoringService {

    @Inject
    RestClient restClient;

    public Response sendMonitoring(String serverHeid, String apiName, String endpointName, String httpCode, String serverMessage, String clientMessage) throws Exception {

        ClientRequest cr = new ClientRequest();
        cr.setHeiId("uma.es");
        cr.setHttpsec(true);
        cr.setMethod(HttpMethodEnum.POST);
        cr.setUrl("https://dev-stats.erasmuswithoutpaper.eu/ewp/monitoring/");
        
        Map<String, List<String>> unknownFields = new HashMap<>();
        unknownFields.put("server_hei_id", Arrays.asList(serverHeid));
        unknownFields.put("api_name", Arrays.asList(apiName));
        
        if (endpointName != null && !endpointName.isEmpty()) {
            unknownFields.put("endpoint_name", Arrays.asList(endpointName));
        }
        if (httpCode != null && !httpCode.isEmpty()) {
            unknownFields.put("http_code", Arrays.asList(httpCode));
        }
        if (serverMessage != null && !serverMessage.isEmpty()) {
            unknownFields.put("server_message", Arrays.asList(serverMessage));
        }
        if (clientMessage != null && !clientMessage.isEmpty()) {
            unknownFields.put("client_message", Arrays.asList(clientMessage));
        }
        
        ParamsClass pc = new ParamsClass();
        pc.setUnknownFields(unknownFields);
        cr.setParams(pc);
        ClientResponse response = restClient.sendRequest(cr, Empty.class);

        return Response.ok(response).build();
    }

}
