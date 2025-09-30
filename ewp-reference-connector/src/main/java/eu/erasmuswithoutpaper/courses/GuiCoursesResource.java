package eu.erasmuswithoutpaper.courses;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import https.github_com.erasmus_without_paper.ewp_specs_api_courses.tree.stable_v1.CoursesResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
                                  @QueryParam("loisAfter") List<String> lois_after, @QueryParam("losAtDate") List<String> los_at_date) {
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

        return Response.ok(responseEnity).build();
    }

}
