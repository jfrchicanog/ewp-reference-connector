package eu.erasmuswithoutpaper.courses;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.courses.dto.AlgoriaLOApiResponse;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskEnum;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskService;
import eu.erasmuswithoutpaper.iia.common.AlgoriaTaskTypeEnum;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("courses/replication")
public class GuiCoursesReplicationResource {

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final Logger LOG = Logger.getLogger(GuiCoursesReplicationResource.class.getCanonicalName());

    @GET
    @Produces("application/json")
    public Response getIiaCourses(@QueryParam("heiId") String hei_id, @QueryParam("modifiedSince") List<String> modified_since) {
        if (hei_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("hei_id is required").build();
        }

        Map<String, String> heiUrls = registryClient.getCoursesReplicationHeiUrls(hei_id);
        if (heiUrls == null || heiUrls.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No courses replication API found for hei_id: " + hei_id).build();
        }
        if (!heiUrls.containsKey("url")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No courses replication URL found for hei_id: " + hei_id).build();
        }

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(hei_id);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(heiUrls.get("url"));
        Map<String, List<String>> paramsMap = new HashMap<>();
        if (modified_since != null && !modified_since.isEmpty()) {
            paramsMap.put("modified_since", modified_since);
        }
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);
        LOG.fine("courses-replication: Params: " + paramsMap);
        ClientResponse clientResponse = restClient.sendRequest(clientRequest, CourseReplicationResponse.class);
        LOG.fine("courses-replication: Response: " + clientResponse);
        CourseReplicationResponse responseEnity = (CourseReplicationResponse) clientResponse.getResult();
        if (responseEnity == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(responseEnity).build();
    }

    @GET
    @Path("own")
    @Produces("application/json")
    public Response getIiaCoursesOwn(@QueryParam("heiId") String hei_id, @QueryParam("modifiedSince") List<String> modified_since) {
        LOG.fine("own: Params: " + modified_since);
        CourseReplicationResponse response = new CourseReplicationResponse();

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("hei_id", hei_id);
        if (modified_since != null && !modified_since.isEmpty()) {
            queryParams.put("modified_since", modified_since.get(0).substring(0, 10));
        }
        try {
            Response resp = AlgoriaTaskService.sendGetRequest(AlgoriaTaskTypeEnum.COURSES, AlgoriaTaskEnum.GET_LIST, queryParams);
            if (resp.getStatus() != Response.Status.OK.getStatusCode()) {
                LOG.fine("Error fetching data from Algoria: " + resp.getStatusInfo().toString());
                throw new EwpWebApplicationException("Error fetching data: " + resp.getStatusInfo().toString(), Response.Status.INTERNAL_SERVER_ERROR);
            }
            AlgoriaLOApiResponse apiResponse = resp.readEntity(AlgoriaLOApiResponse.class);
            response.getLosId().addAll(apiResponse.getElements().stream().map(AlgoriaLOApiResponse.AlgoriaLOElement::getLosId).collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            throw new EwpWebApplicationException("Error fetching data: " + e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }


        return Response.ok(response).build();
    }

}
