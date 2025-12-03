package eu.erasmuswithoutpaper.courses;


import com.fasterxml.jackson.core.JsonProcessingException;
import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOApiResponse;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskEnum;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskService;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskTypeEnum;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1.CoursesResponse;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
@Path("courses/replication")
public class CoursesResourceReplication {

    private static final Logger LOG = Logger.getLogger(CoursesResourceReplication.class.getCanonicalName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CoursesResourceReplication.class);

    @Inject
    GlobalProperties properties;

    @Context
    HttpServletRequest httpRequest;

    @Inject
    RegistryClient registryClient;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public Response coursesGet(@QueryParam("hei_id") List<String> hei_ids, @QueryParam("modified_since") List<String> modified_since) {
        return courses(hei_ids, modified_since);
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public Response coursesPost(@FormParam("hei_id") List<String> hei_ids, @FormParam("modified_since") List<String> modified_since) {
        return courses(hei_ids, modified_since);
    }

    private Response courses(List<String> hei_ids, List<String> modified_since) {

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (heisCoveredByCertificate.isEmpty()) {
            throw new EwpWebApplicationException("No HEIs covered by this certificate.", Response.Status.FORBIDDEN);
        }

        if (hei_ids == null || hei_ids.isEmpty()) {
            throw new EwpWebApplicationException("hei_id parameter is mandatory.", Response.Status.BAD_REQUEST);
        }
        if (hei_ids.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of hei_id", Response.Status.BAD_REQUEST);
        }

        String hei_id = hei_ids.get(0);

        if (modified_since != null && modified_since.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of modified_since", Response.Status.BAD_REQUEST);
        }

        CourseReplicationResponse response = new CourseReplicationResponse();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("hei_id", hei_id);
        if (modified_since != null && !modified_since.isEmpty()) {
            queryParams.put("modified_since", modified_since.get(0).substring(0, 10));
        }
        try {
            Response resp = AlgoriaTaskService.sendRequest(AlgoriaTaskTypeEnum.COURSES, AlgoriaTaskEnum.GET_LIST, queryParams, new HashMap<>());
            if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
                log.error("Error fetching data from Algoria: " + resp.getStatusInfo().toString());
                throw new EwpWebApplicationException("Error fetching data: " + resp.getStatusInfo().toString(), Response.Status.INTERNAL_SERVER_ERROR);
            }
            AlgoriaLOApiResponse apiResponse = resp.readEntity(AlgoriaLOApiResponse.class);
            response.getLosId().addAll(apiResponse.getElements().stream().map(AlgoriaLOApiResponse.AlgoriaLOElement::getLosId).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new EwpWebApplicationException("Error fetching data: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }


        return Response.ok(response).build();
    }

}
