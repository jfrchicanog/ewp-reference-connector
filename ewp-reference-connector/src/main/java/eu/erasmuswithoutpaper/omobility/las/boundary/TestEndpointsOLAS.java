package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.omobilities.las.OmobilityLas;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("omobilities/las/test")
public class TestEndpointsOLAS {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @GET
    @Path("")
    public Response hello() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OlearningAgreement> cq = cb.createQuery(OlearningAgreement.class);
        Root<OlearningAgreement> rootEntry = cq.from(OlearningAgreement.class);
        CriteriaQuery<OlearningAgreement> all = cq.select(rootEntry);
        TypedQuery<OlearningAgreement> allQuery = em.createQuery(all);
        return Response.ok(allQuery.getResultList()).build();

    }


    @POST
    @Path("create")
    @Consumes("application/json")
    public Response create(OlearningAgreement olearningAgreement) {

        em.persist(olearningAgreement);



        return Response.ok(em.find(OlearningAgreement.class, olearningAgreement.getId())).build();
    }
}
