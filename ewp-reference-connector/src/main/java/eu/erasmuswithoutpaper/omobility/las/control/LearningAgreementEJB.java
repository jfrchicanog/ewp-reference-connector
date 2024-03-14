package eu.erasmuswithoutpaper.omobility.las.control;

import eu.erasmuswithoutpaper.omobility.las.boundary.TestEndpointsOLAS;
import eu.erasmuswithoutpaper.omobility.las.entity.ChangesProposal;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LearningAgreementEJB {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(TestEndpointsOLAS.class.getCanonicalName());

    public String insert(OlearningAgreement olearningAgreement) {
        em.persist(olearningAgreement);
        em.flush();
        return olearningAgreement.getId();
    }

    public void update(OlearningAgreement olearningAgreement, OlearningAgreement olearningAgreementDB) {
        ChangesProposal changesProposal = olearningAgreement.getChangesProposal();
        LOG.fine("CHANGE: changesProposal: " + changesProposal);

        em.persist(changesProposal);

        LOG.fine("CHANGE: changesProposal: " + changesProposal.getId());

        olearningAgreementDB.setChangesProposal(changesProposal);
        em.merge(olearningAgreementDB);
        em.flush();
    }

    public List<OlearningAgreement> findAll() {
        return em.createNamedQuery(OlearningAgreement.findAll).getResultList();
    }

    public OlearningAgreement findById(String id) {
        return em.find(OlearningAgreement.class, id);
    }

    public List<OlearningAgreement> findBySendingHeiIdFilterd(String sendingHeiId) {
        return em.createNamedQuery(OlearningAgreement.findBySendingHeiIdFilterd).setParameter("sendingHei", sendingHeiId).getResultList();
    }

    public String getHeiId() {
        List<Institution> internalInstitution = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        return internalInstitution.get(0).getInstitutionId();
    }
}
