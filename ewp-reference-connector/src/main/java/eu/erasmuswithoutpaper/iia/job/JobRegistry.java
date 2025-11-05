package eu.erasmuswithoutpaper.iia.job;

// JobRegistry.java
import javax.ejb.Singleton;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JobRegistry {
    private final ConcurrentHashMap<String, JobInfo> jobs = new ConcurrentHashMap<>();

    public String createJob(int total) {
        String id = UUID.randomUUID().toString();
        JobInfo info = new JobInfo();
        info.total = total;
        jobs.put(id, info);
        return id;
    }

    public JobInfo get(String id) { return jobs.get(id); }

    public void markRunning(String id) { JobInfo i = jobs.get(id); if (i != null) i.status = JobStatus.RUNNING; }

    public void increment(String id) { JobInfo i = jobs.get(id); if (i != null) i.processed++; }

    public void complete(String id) { JobInfo i = jobs.get(id); if (i != null) { i.status = JobStatus.COMPLETED; i.finishedAt = System.currentTimeMillis(); } }

    public void fail(String id, Throwable t) {
        JobInfo i = jobs.get(id);
        if (i != null) { i.status = JobStatus.FAILED; i.error = t.getMessage(); i.finishedAt = System.currentTimeMillis(); }
    }

    public void cancel(String id) {
        JobInfo i = jobs.get(id);
        if (i != null) { i.status = JobStatus.CANCELED; i.finishedAt = System.currentTimeMillis(); }
    }
}

