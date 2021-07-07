
package eu.erasmuswithoutpaper.organization.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Stateless
@Path("person")
public class GuiPersonResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @InternalAuthenticate
    public void add(Person person) {
        em.persist(person);
    }

    @GET
    @Path("get_all")
    @InternalAuthenticate
    public Response getAll() {
        List<Person> personList = em.createNamedQuery(Person.findAll).getResultList();
        GenericEntity<List<Person>> entity = new GenericEntity<List<Person>>(personList) {};
        
        return Response.ok(entity).build();
    }

    @GET
    @Path("get_gender_names")
    @InternalAuthenticate
    public Response getGenderNamnes() {
        String[] genderNames = Gender.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(genderNames) {};
        
        return Response.ok(entity).build();
    }
}
