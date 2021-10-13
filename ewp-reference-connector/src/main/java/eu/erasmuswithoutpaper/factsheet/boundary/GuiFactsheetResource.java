package eu.erasmuswithoutpaper.factsheet.boundary;

import javax.ejb.Stateless;
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

import eu.erasmuswithoutpaper.factsheet.entity.MobilityFactsheet;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Stateless
@Path("factsheet")
public class GuiFactsheetResource {
	
	@PersistenceContext(unitName = "connector")
    EntityManager em;
	
	@POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(MobilityFactsheet factsheet) {
        em.persist(factsheet);
    }
	
	@GET
    @Path("get_heiid")
    @InternalAuthenticate
    public Response getHei(@QueryParam("hei_id") String heiId) {
		MobilityFactsheet factsheet = (MobilityFactsheet) em.createNamedQuery(MobilityFactsheet.findByHeid).setParameter("heiId", heiId).getSingleResult();
        
        if (factsheet != null) {
    		GenericEntity<MobilityFactsheet> entity = new GenericEntity<MobilityFactsheet>(factsheet){};
    		return Response.ok(entity).build();
        } 
        
        return javax.ws.rs.core.Response.ok().build();
    }
}
