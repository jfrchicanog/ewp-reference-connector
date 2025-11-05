package eu.erasmuswithoutpaper.iia.job;

import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.Iia;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class HashRecalcJob {

    @EJB IiasEJB iiasEJB;
    @EJB JobRegistry jobs;

    @Asynchronous
    public void run(String jobId) {
        JobInfo info = jobs.get(jobId);
        if (info == null) return;
        jobs.markRunning(jobId);

        try {
            List<Iia> iias = iiasEJB.findAll(); // small TX; you can fetch IDs only if list is huge
            info.total = iias.size();

            for (Iia i : iias) {
                if (info.status == JobStatus.CANCELED) return; // optional cancel support
                if (i.getOriginal() != null) {
                    // We only recalc hashes for original IIAs
                    jobs.increment(jobId);
                    continue;
                }
                iiasEJB.updateHash(i);
                jobs.increment(jobId);
            }
            jobs.complete(jobId);
        } catch (Throwable t) {
            jobs.fail(jobId, t);
        }
    }
}

