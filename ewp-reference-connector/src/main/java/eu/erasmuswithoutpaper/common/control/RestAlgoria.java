package eu.erasmuswithoutpaper.common.control;

import eu.erasmuswithoutpaper.iia.common.MessageNotificationService;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class RestAlgoria {

    public static GlobalProperties globalProperties = new GlobalProperties();

    private static final Logger logger = LoggerFactory.getLogger(RestAlgoria.class);

    public void getAcademicYear(String date) throws URISyntaxException, MalformedURLException {
        URIBuilder url = new URIBuilder(globalProperties.getAlgoriaAcademicCourseURL());
        url.addParameter("date", date);

        String token = globalProperties.getAlgoriaAuthotizationToken();


        Response result = MessageNotificationService.addApprovalNotification(url.build().toURL().toString(), "", token);
        logger.info("Status code: " + result.getStatusInfo().getStatusCode());

        logger.info(result.readEntity(String.class));
    }
}
