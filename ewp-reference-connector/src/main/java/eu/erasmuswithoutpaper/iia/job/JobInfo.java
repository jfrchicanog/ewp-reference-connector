package eu.erasmuswithoutpaper.iia.job;

public class JobInfo {
    public volatile JobStatus status = JobStatus.QUEUED;
    public volatile int total = 0;
    public volatile int processed = 0;
    public volatile String error = null;
    public final long startedAt = System.currentTimeMillis();
    public volatile Long finishedAt = null;
}