package uz.greenwhite.lib.job.internal;

import android.os.Handler;

import uz.greenwhite.lib.job.Job;
import uz.greenwhite.lib.job.JobWithProgress;

public class Task implements Runnable {

    public final int taskId;
    public final String jobKey;
    public final Handler handler;
    public final Job<Object> job;

    public Task(int taskId, Handler handler, Job<Object> job) {
        this.taskId = taskId;
        this.jobKey = getJobKey(job);
        this.handler = handler;
        this.job = job;
    }

    @Override
    public String toString() {
        return "Task(" + taskId + ", " + String.valueOf(jobKey) + ", " + job.getClass().getName() + ")";
    }

    @Override
    public void run() {
        try {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            setProgressNotifier();
            Object result = job.execute();
            deliverResult(ResultType.SUCCESS, result);
        } catch (Throwable ex) {
            deliverResult(ResultType.FAILURE, ex);
        } finally {
            clearProgressNotifier();
        }
    }

    private void deliverResult(final int resultType, final Object result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Manager.getInstance().onResult(taskId, resultType, result);
            }
        });
    }

    private void setProgressNotifier() {
        if (job instanceof JobWithProgress) {
            ((JobWithProgress) job).setProgressNotifier(new ProgressNotifier());
        }
    }

    private void clearProgressNotifier() {
        if (job instanceof JobWithProgress) {
            ((JobWithProgress) job).setProgressNotifier(null);
        }
    }

    public class ProgressNotifier {

        public void notifyProgress(Object progress) {
            deliverResult(ResultType.PROGRESS, progress);
        }
    }

    private static String getJobKey(Job job) {
        if (job instanceof JobWithProgress) {
            JobWithProgress r = (JobWithProgress) job;
            if (r.jobKey != null) {
                return r.jobKey;
            }
        }
        return null;
    }

}
