package eu.erasmuswithoutpaper.courses;


import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
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
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    //@EwpAuthenticate
    public Response coursesGet(@QueryParam("hei_id") List<String> hei_ids, @QueryParam("los_id") List<String> los_ids,
                               @QueryParam("los_code") List<String> los_codes, @QueryParam("lois_before") List<String> lois_before,
                               @QueryParam("lois_after") List<String> lois_after, @QueryParam("los_at_date") List<String> los_at_date) {
        return courses(hei_ids, los_ids, los_codes, lois_before, lois_after, los_at_date);
    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_XML)
    //@EwpAuthenticate
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

        CoursesResponse response = new CoursesResponse();

        //TODO: implement the actual logic to fetch and filter courses based on the provided parameters.

        return Response.ok(response).build();
    }


    /*private void execNotificationToAlgoria(String iiaId, String notifierHeiId) {

        IiaTaskService.globalProperties = properties;
        Callable<String> callableTask = IiaTaskService.createTask(iiaId, IiaTaskEnum.UPDATED, notifierHeiId);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }*/

    private void execNotificationToAlgoria(String iiaId, String notifierHeiId, IiaTaskEnum iiaTaskService, String description) {

        IiaTaskService.globalProperties = properties;
        Callable<String> callableTask = IiaTaskService.createTask(iiaId, iiaTaskService, notifierHeiId, description);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }

}
