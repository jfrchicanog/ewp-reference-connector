package eu.erasmuswithoutpaper.iia.boundary;

import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CoursesEJB {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public String getHeiId() {
        List<Institution> internalInstitution = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        return internalInstitution.get(0).getInstitutionId();
    }
}
