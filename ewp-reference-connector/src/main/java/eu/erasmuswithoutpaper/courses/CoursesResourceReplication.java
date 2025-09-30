package eu.erasmuswithoutpaper.courses;


import eu.erasmuswithoutpaper.api.courses.replication.CourseReplicationResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
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
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Path("courses/replication")
public class CoursesResourceReplication {

    private static final Logger LOG = Logger.getLogger(CoursesResourceReplication.class.getCanonicalName());

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

        //TODO: rest to algoria and get the courses

        return Response.ok(response).build();
    }


    /*private void execNotificationToAlgoria(String iiaId, String notifierHeiId) {

        IiaTaskService.globalProperties = properties;
        Callable<String> callableTask = IiaTaskService.createTask(iiaId, IiaTaskEnum.UPDATED, notifierHeiId);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }*/


}
