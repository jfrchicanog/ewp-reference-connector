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
import eu.erasmuswithoutpaper.security.EwpAuthenticate;

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
    @EwpAuthenticate
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response factsheetGet(@QueryParam("hei_id") List<String> factsheetHeiIdList) {
        return factsheet(factsheetHeiIdList);
    }
    
    @POST
    @EwpAuthenticate
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response factsheetPost(@FormParam("hei_id") List<String> factsheetHeiIdList) {
        return factsheet(factsheetHeiIdList);
    }
    
    private javax.ws.rs.core.Response factsheet(List<String> factsheetHeiIdList) {
        if (factsheetHeiIdList.size() > properties.getMaxFactsheetIds()) {
            throw new EwpWebApplicationException("Max number of FACTSHEET id's has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (factsheetHeiIdList == null || factsheetHeiIdList.isEmpty()) {
        	throw new EwpWebApplicationException("hei_id required.", Response.Status.BAD_REQUEST);
        }
        
        FactsheetResponse response = new FactsheetResponse();
        
        List<MobilityFactsheet> factsheetList = factsheetHeiIdList.stream().map(heiid -> {
        	MobilityFactsheet factSheet = em.createNamedQuery(MobilityFactsheet.findByHeid, MobilityFactsheet.class).setParameter("heiId", heiid).getSingleResult();
        	return factSheet;
        }).collect(Collectors.toList());
        
        if (!factsheetList.isEmpty()) {
            response.getFactsheet().addAll(factsheetConverter.convertToFactsheet(factsheetList));
        }
        
        return javax.ws.rs.core.Response.ok(response).build();
    }         
    
}
