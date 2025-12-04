package eu.erasmuswithoutpaper.courses;


import com.fasterxml.jackson.core.JsonProcessingException;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOIApiResponse;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOPKApiResponse;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.common.*;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1.CoursesResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.logging.Logger;

@Stateless
@Path("courses")
public class CoursesResource {

    private static final Logger LOG = Logger.getLogger(CoursesResource.class.getCanonicalName());

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
    public Response coursesGet(@QueryParam("hei_id") List<String> hei_ids, @QueryParam("los_id") List<String> los_ids,
                               @QueryParam("los_code") List<String> los_codes, @QueryParam("lois_before") List<String> lois_before,
                               @QueryParam("lois_after") List<String> lois_after, @QueryParam("los_at_date") List<String> los_at_date) {
        return courses(hei_ids, los_ids, los_codes, lois_before, lois_after, los_at_date);
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public Response coursesPost(@FormParam("hei_id") List<String> hei_ids, @FormParam("los_id") List<String> los_ids,
                                @FormParam("los_code") List<String> los_codes, @FormParam("lois_before") List<String> lois_before,
                                @FormParam("lois_after") List<String> lois_after, @FormParam("los_at_date") List<String> los_at_date) {
        return courses(hei_ids, los_ids, los_codes, lois_before, lois_after, los_at_date);
    }

    private Response courses(List<String> hei_ids, List<String> los_ids, List<String> los_codes,
                             List<String> lois_before, List<String> lois_after, List<String> los_at_date) {

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

        if ((los_ids == null || los_ids.isEmpty()) && (los_codes == null || los_codes.isEmpty())) {
            throw new EwpWebApplicationException("At least one of los_id or los_code parameters is required.", Response.Status.BAD_REQUEST);
        }
        if (los_ids != null && los_ids.size() > properties.getMaxLosIds()) {
            throw new EwpWebApplicationException("Too many los_id values. Max is " + properties.getMaxLosIds(), Response.Status.BAD_REQUEST);
        }
        if (los_codes != null && los_codes.size() > properties.getMaxLosCodes()) {
            throw new EwpWebApplicationException("Too many los_code values. Max is " + properties.getMaxLosCodes(), Response.Status.BAD_REQUEST);
        }

        if (lois_before != null && lois_before.size() > 1) {
            throw new EwpWebApplicationException("Too many lois_before values. Max is 1", Response.Status.BAD_REQUEST);
        }
        if (lois_after != null && lois_after.size() > 1) {
            throw new EwpWebApplicationException("Too many lois_after values. Max is 1", Response.Status.BAD_REQUEST);
        }
        if (los_at_date != null && los_at_date.size() > 1) {
            throw new EwpWebApplicationException("Too many los_at_date values. Max is 1", Response.Status.BAD_REQUEST);
        }

        if (los_ids != null && !los_ids.isEmpty() && los_codes != null && !los_codes.isEmpty()) {
            throw new EwpWebApplicationException("Cannot provide both lois_before and lois_after parameters.", Response.Status.BAD_REQUEST);
        }

        List<String> codes = new ArrayList<>();
        if (los_ids != null && !los_ids.isEmpty()) {
            codes = los_ids;
        } else if (los_codes != null && !los_codes.isEmpty()) {
            codes = los_codes;
        }

        LOG.fine("own: Params: " + hei_id + ", code: " + codes);
        CoursesResponse response = new CoursesResponse();

        Map<String, String> queryParams = new HashMap<>();
        try {
            for (String code : codes) {

                Response resp = AlgoriaTaskService.sendGetRequest(
                        AlgoriaTaskTypeEnum.COURSES,
                        AlgoriaTaskEnum.GET_DETAILS,
                        queryParams,
                        code
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
                AlgoriaLOPKApiResponse apiResponse = resp.readEntity(AlgoriaLOPKApiResponse.class);
                LOG.fine("own: Fetched " + (apiResponse.getElement() != null ? "1" : "0") + " learning outcomes from Algoria.");
                //log first element
                if (apiResponse.getElement() != null) {
                    LOG.fine("own: First element: " + apiResponse.getElement().getLos_id());
                }
                CoursesResponse.LearningOpportunitySpecification los = CourseConverter.convert(apiResponse);

                AlgoriaLOIApiResponse algoriaLOIApiResponse = getLoi(apiResponse.getElement().getLos_id(), lois_before, lois_after, los_at_date);

                los.setSpecifies(CourseConverter.convert(algoriaLOIApiResponse));
                response.getLearningOpportunitySpecification().add(los);
            }
        } catch (JsonProcessingException e) {
            throw new EwpWebApplicationException("Error fetching data: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.ok(response).build();
    }

    private AlgoriaLOIApiResponse getLoi(String los_id, List<String> lois_before, List<String> lois_after, List<String> los_at_date) throws JsonProcessingException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("hei_id", "uma.es");
        queryParams.put("mode", "LOI");
        queryParams.put("max_elements", "9000");
        queryParams.put("los_id", los_id);
        if (lois_before != null && !lois_before.isEmpty()) {
            queryParams.put("lois_before", lois_before.get(0));
        }
        if (lois_after != null && !lois_after.isEmpty()) {
            queryParams.put("lois_after", lois_after.get(0));
        }
        if (los_at_date != null && !los_at_date.isEmpty()) {
            queryParams.put("los_at_date", los_at_date.get(0));
        }

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
        AlgoriaLOIApiResponse apiResponse = resp.readEntity(AlgoriaLOIApiResponse.class);
        LOG.fine("own: Fetched " + apiResponse.getElements().size() + " learning outcomes from Algoria.");
        //log first element
        if (!apiResponse.getElements().isEmpty()) {
            LOG.fine("own: First element: " + apiResponse.getElements().get(0).getLoi_id());
        }
        return apiResponse;
    }

}
