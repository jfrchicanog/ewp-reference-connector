/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.registry.Registry;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Moritz Baader
 */
@Stateless
@Path("testMonitoring")
public class TestMonitoringEndpoint {

    @Inject
    RestClient restClient;
    @Inject
    RegistryClient rc;

    @GET
    @Path("test")
    public Response test() {
        Map<String, String> map = rc.getEwpInstanceHeiUrls("stats.erasmuswithoutpaper.eu");
        return Response.ok(map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"))
        ).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response testMonitoring() throws Exception {

        ClientRequest cr = new ClientRequest();
        cr.setHeiId("uma.es");
        cr.setHttpsec(true);
        cr.setMethod(HttpMethodEnum.POST);
        rc.getEwpInstanceHeiUrls("stats.erasmuswithoutpaper.eu");
        cr.setUrl("https://stats.erasmuswithoutpaper.eu/ewp/monitoring");
        Map<String, List<String>> unknownFields = new HashMap<>();
        unknownFields.put("server_hei_id", Arrays.asList("test.uma.es"));
        unknownFields.put("api_name", Arrays.asList("omobility-las"));
        unknownFields.put("endpoint_name", Arrays.asList("get"));
        unknownFields.put("http_code", Arrays.asList("500"));
        unknownFields.put("server_message", Arrays.asList("Mensaje error Server"));
        ParamsClass pc = new ParamsClass();
        pc.setUnknownFields(unknownFields);
        cr.setParams(pc);
        ClientResponse response = restClient.sendRequest(cr, Empty.class);

        return Response.ok().entity(response.getStatusCode()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testMonitoring(
            @FormParam("server_hei_id") String serverHeiId,
            @FormParam("api_name") String apiName,
            @FormParam("endpoint_name") String endpointName,
            @FormParam("http_code") String httpCode,
            @FormParam("server_message") String serverMessage
    ) {
        MonitoringParams mp = new MonitoringParams();
        mp.setServerHeiId(serverHeiId);
        mp.setApiName(apiName);
        mp.setEndpointName(endpointName);
        mp.setHttpCode(httpCode);
        mp.setServerMessage(serverMessage);

        // Process the data as needed
        return Response.ok(mp).build();
    }
}
