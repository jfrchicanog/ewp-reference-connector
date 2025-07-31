package eu.erasmuswithoutpaper.organization.boundary;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eu.erasmuswithoutpaper.api.institutions.InstitutionsResponse;
import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Stateless
@Path("ounit")
public class GuiOUnitResource {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(GuiOUnitResource.class.getCanonicalName());


    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public Response save(OrganizationUnit ounit, @QueryParam("heiId") String heiId) {

        @SuppressWarnings("unchecked")
        List<Institution> institutions = em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", heiId).getResultList();

        if (institutions == null || institutions.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }

        //First persist the new ounit
        if (ounit.getId() != null && em.find(OrganizationUnit.class, ounit.getId()) != null) {
            em.merge(ounit);
        } else {
            em.persist(ounit);
        }
        em.flush();

        //Second the relation between the ounit and the institution it belongs to
        Institution institution = institutions.get(0);
        if (institution.getOrganizationUnits().contains(ounit)) {
            institution.getOrganizationUnits().remove(ounit);
        }
        institution.getOrganizationUnits().add(ounit);
        em.merge(institution);

        return Response.ok(ounit.getId()).build();
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public Response save(OrganizationUnit ounit) {
        em.merge(ounit);

        return Response.ok(ounit.getId()).build();
    }

    @GET
    @Path("get_all")
    @InternalAuthenticate
    public Response getAll() {
        List<OrganizationUnit> ounitList = em.createNamedQuery(OrganizationUnit.findaAll).getResultList();

        GenericEntity<List<OrganizationUnit>> entity = new GenericEntity<List<OrganizationUnit>>(ounitList) {
        };
        return Response.ok(entity).build();
    }

    @GET
    @Path("get_partner")
    @InternalAuthenticate
    public Response getOunits(@QueryParam("heiId") String heiId, @QueryParam("ounitId") List<String> organizationUnitIdList, @QueryParam("ounitCode") List<String> organizationUnitCodeList) {
        LOG.fine("ounits: Hei searched: " + heiId);

        Map<String, String> heiUrls = registryClient.getEwpOrganizationUnitHeiUrls(heiId);
        if (heiUrls == null || heiUrls.isEmpty()) {
            LOG.fine("ounits: Hei not found: " + heiId);
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        for (Map.Entry<String, String> entry : heiUrls.entrySet()) {
            LOG.fine("ounits: Hei URL: " + entry.getKey() + " -> " + entry.getValue());
        }
        String heiUrl = heiUrls.get("url");
        if (heiUrl == null || heiUrl.isEmpty()) {
            LOG.fine("ounits: Hei URL not found for: " + heiId);
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.fine("ounits: Hei URL found: " + heiUrl);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(heiUrl);
        Map<String, List<String>> paramsMap = new HashMap<>();
        if (organizationUnitIdList != null && !organizationUnitIdList.isEmpty()) {
            paramsMap.put("ounit_id", organizationUnitIdList);
        } else if (organizationUnitCodeList != null && !organizationUnitCodeList.isEmpty()) {
            paramsMap.put("ounit_code", organizationUnitCodeList);
        } else {
            return Response.status(Status.BAD_REQUEST).entity("No organization unit identifiers provided").build();
        }
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);
        LOG.fine("ounits: Params: " + paramsMap);
        ClientResponse clientResponse = restClient.sendRequest(clientRequest, OunitsResponse.class);
        OunitsResponse responseEnity = (OunitsResponse) clientResponse.getResult();
        LOG.fine("ounits: Response: " + clientResponse);

        return Response.ok(clientResponse).build();
    }
}
