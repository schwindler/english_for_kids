package uz.greenwhite.lib.job;

import java.util.ArrayList;
import java.util.List;

import uz.greenwhite.lib.job.internal.Manager;

public class JobParallel {

    private final Object TAG;
    private final List<DeferredJob> jobs = new ArrayList<DeferredJob>();

    public JobParallel(Object TAG) {
        this.TAG = TAG;
    }

    @SuppressWarnings("unchecked")
    public <R> DelayedPromise<R> add(Job<R> job) {
        Deferred<R> deferred = new Deferred<R>();
        jobs.add(new DeferredJob((Job<Object>)job, (Deferred<Object>) deferred));
        return new DelayedPromise<R>(deferred);
    }

    public void execute(OnFinish onFinish) {
        JobParallelFinish finish = new JobParallelFinish(onFinish, jobs.size());
        for (DeferredJob job : jobs) {
            Manager.getInstance().execute(job.job, job.deferred, TAG);
            job.deferred.always(finish);
        }
    }

    static class JobParallelFinish implements Promise.OnAlways<Object> {
        public final OnFinish onFinish;
        private int jobCount;

        JobParallelFinish(OnFinish onFinish, int jobCount) {
            this.onFinish = onFinish;
            this.jobCount = jobCount;
        }

        @Override
        public void onAlways(boolean resolved, Object success, Throwable error) {
            jobCount = jobCount - 1;
            if (jobCount == 0) {
                onFinish.onFinish();
            }
        }
    }

    public interface OnFinish {

        void onFinish();

    }

    static class DeferredJob {

        public final Job<Object> job;
        public final Deferred<Object> deferred;

        DeferredJob(Job<Object> job, Deferred<Object> deferred) {
            this.job = job;
            this.deferred = deferred;
        }
    }
}
