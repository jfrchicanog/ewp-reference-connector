package eu.erasmuswithoutpaper.factsheet.boundary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.factsheet.FactsheetResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.factsheet.control.FactsheetConverter;
import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;

@Stateless
@Path("factsheet")
public class FactsheetResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    GlobalProperties properties;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    FactsheetConverter factsheetConverter;
    
    @Context
    HttpServletRequest httpRequest;
          
    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response factsheetGet(@QueryParam("hei_id") List<String> factsheetIdList) {
        return factsheet(factsheetIdList);
    }
    
    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response factsheetPost(@FormParam("hei_id") List<String> factsheetIdList) {
        return factsheet(factsheetIdList);
    }
    
    private javax.ws.rs.core.Response factsheet(List<String> factsheetIdList) {
        if (factsheetIdList.size() > properties.getMaxFactsheetIds()) {
            throw new EwpWebApplicationException("Max number of FACTSHEET id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (factsheetIdList.isEmpty()) {
        	throw new EwpWebApplicationException("factsheet_id required.", Response.Status.BAD_REQUEST);
        }
        
        FactsheetResponse response = new FactsheetResponse();
        
        List<MobilityFactsheet> factsheetList = factsheetIdList.stream().map(id -> em.find(MobilityFactsheet.class, id)).filter(factsheet -> factsheet != null).collect(Collectors.toList());
        if (!factsheetList.isEmpty()) {
            response.getFactsheet().addAll(factsheetConverter.convertToFactsheet(factsheetList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }         
    
}
