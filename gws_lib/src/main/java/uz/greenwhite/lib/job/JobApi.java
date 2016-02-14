package uz.greenwhite.lib.job;


import android.content.Context;

import uz.greenwhite.lib.job.internal.Manager;

public class JobApi {

    private static final JobHelper JOB_HELPER = new JobHelper();

    public static void runJobWithProgressInService(Context context) {
        Manager.getInstance().setContext(context);
    }

    public static boolean isRunning(String jobKey) {
        return Manager.getInstance().isRunning(jobKey);
    }

    public static <R> Promise<R> execute(Job<R> job) {
        return JOB_HELPER.execute(job);
    }

    public static void stopListening() {
        JOB_HELPER.stopListening();
    }

    public static JobParallel parallel() {
        return JOB_HELPER.parallel();
    }

    public static <P, R> void listenKey(String key, JobListener<P, R> jobListener) {
        JOB_HELPER.listenKey(key, jobListener);
    }
}
