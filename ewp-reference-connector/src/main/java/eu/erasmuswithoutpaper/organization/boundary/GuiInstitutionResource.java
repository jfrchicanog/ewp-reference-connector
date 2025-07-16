package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.boundary.AuxIiaThread;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("institution")
public class GuiInstitutionResource {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;
    
    @Inject
    SendMonitoringService sendMonitoringService;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(GuiInstitutionResource.class.getCanonicalName());

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public void save(Institution institution) {
        if (institution.getId() == null || institution.getId().isEmpty()) {
            em.persist(institution);
        } else {
            em.merge(institution);
        }
    }

    @GET
    @Path("get_all")
    @InternalAuthenticate
    public Response getAll() {
        List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();

        GenericEntity<List<Institution>> entity = new GenericEntity<List<Institution>>(institutionList) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response institutionHeis() {
        List<HeiEntry> institutionsHeis = registryClient.getEwpInstanceHeisWithUrls();

        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(institutionsHeis) {
        };
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public javax.ws.rs.core.Response institutions(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse.class);
        try {
            if (response.getStatusCode() <= 599 && response.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(request.getHeiId(), "institutions", null, Integer.toString(response.getStatusCode()), response.getErrorMessage(), null);
            }else if(response.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(request.getHeiId(), "institutions", null, Integer.toString(response.getStatusCode()), response.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }
        return javax.ws.rs.core.Response.ok(response).build();
    }

    @GET
    @Path("hei-data")
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public javax.ws.rs.core.Response heiData(@QueryParam("heiId") String heiId) {
        LOG.fine("hei-data: Hei searched: " + heiId);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(registryClient.getEwpInstanceHeiUrls(heiId).get("url"));
        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("hei_id ", Collections.singletonList(heiId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);
        LOG.fine("hei-data: Params: " + paramsMap);
        ClientResponse clientResponse = restClient.sendRequest(clientRequest, InstitutionsResponse.class);
        InstitutionsResponse responseEnity = (InstitutionsResponse) clientResponse.getResult();
        LOG.fine("hei-data: Response: " + clientResponse);

        if (responseEnity == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).entity(clientResponse).build();
        }

        return Response.ok(responseEnity).build();

    }

    @GET
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public javax.ws.rs.core.Response organizationUnitsHeis() {
        List<HeiEntry> organizationUnitHeis = registryClient.getEwpOrganizationUnitHeisWithUrls();

        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(organizationUnitHeis) {
        };
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("ounits-heis")
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public javax.ws.rs.core.Response organizationUnits(ClientRequest request) {
        ClientResponse response = restClient.sendRequest(request, eu.erasmuswithoutpaper.api.ounits.OunitsResponse.class);
        try {
            if (response.getStatusCode() <= 599 && response.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(request.getHeiId(), "organizational-units", null, Integer.toString(response.getStatusCode()), response.getErrorMessage(), null);
            }else if(response.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(request.getHeiId(), "organizational-units", null, Integer.toString(response.getStatusCode()), response.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }
        return javax.ws.rs.core.Response.ok(response).build();
    }
}
