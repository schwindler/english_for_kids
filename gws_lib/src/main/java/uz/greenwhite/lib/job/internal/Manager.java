package uz.greenwhite.lib.job.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import uz.greenwhite.lib.GWSLIB;
import uz.greenwhite.lib.job.Deferred;
import uz.greenwhite.lib.job.Job;
import uz.greenwhite.lib.job.JobListener;
import uz.greenwhite.lib.job.JobWithProgress;

public class Manager {

    private final AtomicInteger sequence = new AtomicInteger();
    private final TaskContainer taskContainer = new TaskContainer();
    private final DeferredContainer deferredContainer = new DeferredContainer();
    private final KeyContainer keyContainer = new KeyContainer();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Context context;

    public void setContext(Context context) {
        checkMainLooper();
        this.context = context;
    }

    public boolean isRunning(String jobKey) {
        checkMainLooper();
        return taskContainer.findByJobKey(jobKey) != null;
    }

    @SuppressWarnings("unchecked")
    public void execute(Job job, Deferred deferred, Object tag) {
        checkMainLooper();
        int taskId = sequence.incrementAndGet();
        Task task = new Task(taskId, handler, job);
        try {
            taskContainer.add(new TaskWrapper(task));
            deferredContainer.add(new DeferredWrapper(taskId, deferred, tag));
            if (GWSLIB.DEBUG) {
                GWSLIB.log("JOB Manager.execute " + task);
            }
            onStart(task);
            executor.execute(task);
            jobServiceStart(task);
        } catch (Throwable ex) {
            taskContainer.remove(taskId);
            deferredContainer.remove(taskId);
            throw new RuntimeException(ex);
        }

    }

    public void onResult(int taskId, int resultType, Object result) {
        checkMainLooper();
        TaskWrapper taskWrapper = taskContainer.get(taskId);
        Task task = null;
        if (taskWrapper != null) {
            task = taskWrapper.task;
        }
        if (GWSLIB.DEBUG) {
            String[] type = {"", "progress", "success", "failure"};
            GWSLIB.log("JOB Manager.onResult %s taskId=%d %s", type[resultType], taskId, String.valueOf(task));
        }
        if (task != null) {
            switch (resultType) {
                case ResultType.PROGRESS:
                    onProgress(task, result);
                    taskWrapper.lastProgress = result;
                    break;
                case ResultType.SUCCESS:
                    onSuccess(task, result);
                    taskContainer.remove(task.taskId);
                    break;
                case ResultType.FAILURE:
                    onFailure(task, (Throwable) result);
                    taskContainer.remove(task.taskId);
                    break;
            }
        }
    }


    public void stopListening(Object tag) {
        checkMainLooper();
        deferredContainer.removeByTag(tag);
        keyContainer.removeByTag(tag);
    }

    public void listenKey(String jobKey, JobListener jobListener, Object tag) {
        checkMainLooper();
        if (jobKey == null) {
            return;
        }
        keyContainer.add(jobKey, jobListener, tag);
        TaskWrapper taskWrapper = taskContainer.findByJobKey(jobKey);
        if (taskWrapper != null) {
            keyContainer.start(taskWrapper.task);
            if (taskWrapper.lastProgress != null) {
                keyContainer.progress(taskWrapper.task, taskWrapper.lastProgress);
            }
        }
    }

    private void onStart(Task task) {
        keyContainer.start(task);
    }

    private void onProgress(Task task, Object progress) {
        keyContainer.progress(task, progress);
    }

    private void onSuccess(Task task, Object result) {
        jobServiceStop(task);
        deferredContainer.resolve(task.taskId, result);
        keyContainer.success(task, result);
    }

    private void onFailure(Task task, Throwable error) {
        jobServiceStop(task);
        deferredContainer.reject(task.taskId, error);
        keyContainer.failure(task, error);
    }

    private void jobServiceStart(Task task) {
        if (context != null && task.job instanceof JobWithProgress) {
            JobService.startJob(context, task.taskId);
        }
    }

    private void jobServiceStop(Task task) {
        if (context != null && task.job instanceof JobWithProgress) {
            JobService.stopJob(context, task.taskId);
        }
    }

    private void checkMainLooper() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Manager methods must run in main thread.");
        }
    }

    private Manager() {
    }

    private static class LazyHolder {
        private static final Manager INSTANCE = new Manager();
    }

    public static Manager getInstance() {
        return LazyHolder.INSTANCE;
    }
}
