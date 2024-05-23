package eu.erasmuswithoutpaper.iia.common;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class MessageNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    public static Response addApprovalNotification(String url, String msg, String token) {

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        /*try {
            clientBuilder.sslContext(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e1) {
            logger.error("Error setting ssl context! " + e1.getMessage());
        }*/

        WebTarget target = clientBuilder.build().target(url);
        //target.property("http.autoredirect", true);

        Invocation.Builder postBuilder = target.request().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
        postBuilder = postBuilder.header("Authorization", token);
        //add content length
        //postBuilder = postBuilder.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(msg.getBytes(StandardCharsets.UTF_8).length));

        /*MultivaluedMap<String, Object> headers = postBuilder.head().getHeaders();
        logger.info("Headers:");
        headers.forEach((key, values) -> {
            logger.info(key + ": " + values);
        });*/


        Response response = postBuilder.post(Entity.json(msg));

        logger.info("Response status: " + response.getStatus());

        //response headers
        MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
        logger.info("Response headers:");
        responseHeaders.forEach((key, values) -> {
            logger.info(key + ": " + values);
        });

        //response body

        logger.info("Response body: " + response.readEntity(String.class));

        return response;
    }
}
