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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
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
    @InternalAuthenticate
    public void save(OrganizationUnit ounit) {
        if (ounit.getId() == null || ounit.getId().isEmpty()) {
            em.persist(ounit);
        } else {
            em.merge(ounit);
        }
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
