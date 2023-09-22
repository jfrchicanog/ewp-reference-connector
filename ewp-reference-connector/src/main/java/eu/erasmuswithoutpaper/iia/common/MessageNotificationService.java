package eu.erasmuswithoutpaper.iia.common;

import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.imobility.control.IncomingMobilityConverter;

public class MessageNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);

    public static Response addApprovalNotification(String url, String msg, String token) {

        ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        try {
            clientBuilder.sslContext(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e1) {
            logger.error("Error setting ssl context! " + e1.getMessage());
        }

        WebTarget target = clientBuilder.build().target(url);
        target.property("http.autoredirect", true);

        Invocation.Builder postBuilder = target.request().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
        postBuilder = postBuilder.header("Authorization", token);
        
        logger.info("\n\n------------------------------------");
        logger.info("url: " + url);
        logger.info("msg: " + msg);
        logger.info("toke: " + token);
        logger.info("\n\n------------------------------------\n\n");
        
        Response response = postBuilder.post(Entity.json(msg));

        return response;
    }
}
