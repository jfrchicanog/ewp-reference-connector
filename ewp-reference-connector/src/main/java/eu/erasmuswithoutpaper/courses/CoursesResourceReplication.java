package eu.erasmuswithoutpaper.courses;


import com.fasterxml.jackson.core.JsonProcessingException;
import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOApiResponse;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.boundary.CoursesEJB;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskEnum;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskService;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskTypeEnum;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1.CoursesResponse;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
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
import java.time.OffsetDateTime;
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

    @EJB
    CoursesEJB coursesEJB;

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

        String localHeiId = coursesEJB.getHeiId();

        if (!localHeiId.equals(hei_id)) {
            throw new EwpWebApplicationException("Requested hei_id does not match the HEI covered by the certificate.", Response.Status.BAD_REQUEST);
        }

        if (modified_since != null && modified_since.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of modified_since", Response.Status.BAD_REQUEST);
        }

        CourseReplicationResponse response = new CourseReplicationResponse();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("hei_id", "uma.es");
        queryParams.put("mode", "LOS");
        queryParams.put("max_elements", "9000");
        if (modified_since != null && !modified_since.isEmpty()) {
            String dateOnly = null;
            try {
                String input = modified_since.get(0);

                // Parse full xs:dateTime, e.g. 2004-02-12T15:19:21+01:00
                OffsetDateTime odt = OffsetDateTime.parse(input);

                // Extract date in yyyy-MM-dd
                dateOnly = odt.toLocalDate().toString();

            } catch (Exception e) {
                throw new EwpWebApplicationException("Invalid modified_since format.", Response.Status.BAD_REQUEST);
            }

            queryParams.put("lois_after", dateOnly);
        }
        try {
            Response resp = AlgoriaTaskService.sendGetRequest(
                    AlgoriaTaskTypeEnum.COURSES,
                    AlgoriaTaskEnum.GET_LIST,
                    queryParams
            );

            if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
                String errorBody = null;
                try {
                    if (resp.hasEntity()) {
                        errorBody = resp.readEntity(String.class);
                    }
                } catch (Exception e) {
                    errorBody = "Failed to read error body: " + e.getMessage();
                }

                LOG.severe(String.format(
                        "Error fetching data from Algoria. " +
                                "Status: %d (%s), URL params: %s, Body: %s",
                        resp.getStatus(),
                        resp.getStatusInfo().getReasonPhrase(),
                        queryParams,
                        errorBody
                ));

                throw new EwpWebApplicationException(
                        "Error fetching data from Algoria: " + resp.getStatus() + " " + resp.getStatusInfo().getReasonPhrase(),
                        Response.Status.INTERNAL_SERVER_ERROR
                );
            }
            AlgoriaLOApiResponse apiResponse = resp.readEntity(AlgoriaLOApiResponse.class);
            LOG.fine("replication: Fetched " + apiResponse.getElements().size() + " learning outcomes from Algoria.");
            //log first element
            if (!apiResponse.getElements().isEmpty()) {
                LOG.fine("replication: First element: " + apiResponse.getElements().get(0).getLos_id());
            }
            response.getLosId().addAll(apiResponse.getElements().stream().map(AlgoriaLOApiResponse.AlgoriaLOElement::getLos_id).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new EwpWebApplicationException("Error fetching data: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }


        return Response.ok(response).build();
    }

}
