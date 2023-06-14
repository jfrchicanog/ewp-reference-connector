package eu.erasmuswithoutpaper.organization.boundary;

import java.util.List;

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

        GenericEntity<List<OrganizationUnit>> entity = new GenericEntity<List<OrganizationUnit>>(ounitList) {};
        return Response.ok(entity).build();
    }
}
