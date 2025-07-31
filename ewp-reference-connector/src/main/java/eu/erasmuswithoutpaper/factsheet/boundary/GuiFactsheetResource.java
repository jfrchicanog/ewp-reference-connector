package eu.erasmuswithoutpaper.factsheet.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@Path("factsheet")
public class GuiFactsheetResource {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(MobilityFactsheet factsheet) {
        em.persist(factsheet);
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(MobilityFactsheet factsheet) {
        String heid = factsheet.getHeiId();
        MobilityFactsheet foundFactsheet = (MobilityFactsheet) em.createNamedQuery(MobilityFactsheet.findByHeid).setParameter("heiId", heid).getSingleResult();

        if (foundFactsheet != null) {
            foundFactsheet.setAccessibility(factsheet.getAccessibility());

            foundFactsheet.setAdditionalInfo(factsheet.getAdditionalInfo());
            foundFactsheet.setAdditionalRequirements(factsheet.getAdditionalRequirements());

            foundFactsheet.setApplicationInfo(factsheet.getApplicationInfo());
            foundFactsheet.setHousingInfo(factsheet.getHousingInfo());
            foundFactsheet.setInsuranceInfo(factsheet.getInsuranceInfo());
            foundFactsheet.setVisaInfo(factsheet.getVisaInfo());

            foundFactsheet.setDecisionWeeksLimit(factsheet.getDecisionWeeksLimit());

            foundFactsheet.setHeiId(factsheet.getHeiId());

            foundFactsheet.setStudentApplicationTerm(factsheet.getStudentApplicationTerm());
            foundFactsheet.setStudentNominationTerm(factsheet.getStudentNominationTerm());

            foundFactsheet.setTorWeeksLimit(factsheet.getTorWeeksLimit());

            em.merge(foundFactsheet);
        } else {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("get_heiid")
    @InternalAuthenticate
    public Response getHei(@QueryParam("hei_id") String heiId) {
        MobilityFactsheet factsheet = (MobilityFactsheet) em.createNamedQuery(MobilityFactsheet.findByHeid).setParameter("heiId", heiId).getSingleResult();

        if (factsheet != null) {
            GenericEntity<MobilityFactsheet> entity = new GenericEntity<MobilityFactsheet>(factsheet) {
            };
            return Response.ok(entity).build();
        }

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("get_partner")
    @InternalAuthenticate
    public Response getPartner(@QueryParam("hei_id") String heiId) {
        if (heiId == null || heiId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        String url = registryClient.getFactsheetHeiUrls(heiId).getOrDefault("url", null);
        if (url == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);
        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("hei_id ", Arrays.asList(heiId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);
        ClientResponse clientResponse = restClient.sendRequest(clientRequest, FactsheetResponse.class);
        FactsheetResponse responseEnity = (FactsheetResponse) clientResponse.getResult();
        if (responseEnity == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(responseEnity).build();
    }
}
