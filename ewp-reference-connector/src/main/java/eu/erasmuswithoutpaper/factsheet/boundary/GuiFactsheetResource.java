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
    		GenericEntity<MobilityFactsheet> entity = new GenericEntity<MobilityFactsheet>(factsheet){};
    		return Response.ok(entity).build();
        } 
        
        return javax.ws.rs.core.Response.ok().build();
    }
}
