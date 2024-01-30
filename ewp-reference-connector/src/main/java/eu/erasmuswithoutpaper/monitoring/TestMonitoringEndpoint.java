/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
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

    @GET
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response testMonitoring() throws Exception {

        // Build the request
        HttpPost request = new HttpPost("https://stats.erasmuswithoutpaper.eu");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("server_hei_id", "test.uma.es"));
        urlParameters.add(new BasicNameValuePair("api_name", "omobility-las"));
        urlParameters.add(new BasicNameValuePair("endpoint_name", "get"));
        urlParameters.add(new BasicNameValuePair("http_code", "500"));
        urlParameters.add(new BasicNameValuePair("server_message", "Mensaje error Server"));

        request.setEntity(new UrlEncodedFormEntity(urlParameters));

        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)){
            
            result = EntityUtils.toString(response.getEntity());
        }

        return Response.ok(result).build();
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
