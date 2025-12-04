package eu.erasmuswithoutpaper.courses;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOApiResponse;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOIApiResponse;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOPKApiResponse;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskEnum;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskService;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskTypeEnum;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1.CoursesResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("courses")
public class GuiCoursesResource {

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final java.util.logging.Logger LOG = Logger.getLogger(GuiCoursesResource.class.getCanonicalName());

    @GET
    @Produces("application/json")
    public Response getIiaCourses(@QueryParam("heiId") String hei_id, @QueryParam("losId") List<String> los_ids,
                                  @QueryParam("losCode") List<String> los_codes, @QueryParam("loisBefore") List<String> lois_before,
                                  @QueryParam("loisAfter") List<String> lois_after, @QueryParam("losAtDate") List<String> los_at_date, @QueryParam("format") String format) {
        if (hei_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("hei_id is required").build();
        }
        if ((los_ids == null || los_ids.isEmpty()) && (los_codes == null || los_codes.isEmpty())){
            throw new EwpWebApplicationException("At least one of los_id or los_code parameters is required.", Response.Status.BAD_REQUEST);
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

        Map<String, String> heiUrls = registryClient.getCoursesHeiUrls(hei_id);
        if (heiUrls == null || heiUrls.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No courses API found for hei_id: " + hei_id).build();
        }
        if (!heiUrls.containsKey("url")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No courses API found for hei_id: " + hei_id).build();
        }

        if (los_ids != null && !los_ids.isEmpty() && los_codes != null && !los_codes.isEmpty()) {
            throw new EwpWebApplicationException("Cannot provide both lois_before and lois_after parameters.", Response.Status.BAD_REQUEST);
        }

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(hei_id);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(heiUrls.get("url"));
        Map<String, List<String>> paramsMap = new HashMap<>();
        if (los_ids != null && !los_ids.isEmpty()) {
            paramsMap.put("los_id", los_ids);
        }
        if (los_codes != null && !los_codes.isEmpty()) {
            paramsMap.put("los_code", los_codes);
        }
        if (lois_before != null && !lois_before.isEmpty()) {
            paramsMap.put("lois_before", lois_before);
        }
        if (lois_after != null && !lois_after.isEmpty()) {
            paramsMap.put("lois_after", lois_after);
        }
        if (los_at_date != null && !los_at_date.isEmpty()) {
            paramsMap.put("los_at_date", los_at_date);
        }
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);
        LOG.fine("courses: Params: " + paramsMap);
        ClientResponse clientResponse = restClient.sendRequest(clientRequest, CoursesResponse.class);
        LOG.fine("courses: Response: " + clientResponse);
        CoursesResponse responseEnity = (CoursesResponse) clientResponse.getResult();
        if (responseEnity == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        if ("xml".equalsIgnoreCase(format)) {
            return Response.ok(responseEnity, MediaType.APPLICATION_XML).build();
        }

        return Response.ok(responseEnity).build();
    }

    @GET
    @Path("own")
    @Produces(MediaType.APPLICATION_XML)
    public Response getIiaCoursesOwn(@QueryParam("heiId") String hei_id, @QueryParam("losId") List<String> los_ids,
                                  @QueryParam("losCode") List<String> los_codes, @QueryParam("loisBefore") List<String> lois_before,
                                  @QueryParam("loisAfter") List<String> lois_after, @QueryParam("losAtDate") List<String> los_at_date) {

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
