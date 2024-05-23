package eu.erasmuswithoutpaper.iia.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class MessageNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    public static Response addApprovalNotification(String url, String msg, String token) {

        try {
            HttpPost post = new HttpPost(url);

            post.setEntity(new StringEntity(msg, StandardCharsets.UTF_8));

            //post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            post.setHeader(HttpHeaders.AUTHORIZATION, token);
            //post.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(msg.getBytes(StandardCharsets.UTF_8).length));


            String result = "";
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {

                result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                logger.error("Error sending message! " + e.getMessage());
                //print stack trace with logger
                StackTraceElement[] stack = e.getStackTrace();
                for (StackTraceElement stackTraceElement : stack) {
                    logger.error(stackTraceElement.toString());
                }
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error sending message! " + e.getMessage()).build();
            }

            logger.info("Message sent! " + result);

            Response response = Response.status(Response.Status.OK).entity(result).build();
            return response;
        } catch (Exception e) {
            logger.error("Error sending message! " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error sending message! " + e.getMessage()).build();
        }

    }
}
