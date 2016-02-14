package uz.greenwhite.lib.job.internal;

import uz.greenwhite.lib.job.JobListener;

public class KeyListener {

    public final JobListener<Object, Object> listener;
    public final Object tag;

    private long runningTaskId;

    public KeyListener(JobListener<Object, Object> listener, Object tag) {
        if (listener == null || tag == null) {
            throw new NullPointerException();
        }
        this.listener = listener;
        this.tag = tag;
        this.runningTaskId = 0;
    }

    void start(int taskId) {
        if (runningTaskId != taskId) {
            runningTaskId = taskId;
            listener.onStart();
        }
    }

    void stop(int taskId) {
        if (runningTaskId == taskId) {
            runningTaskId = 0;
            listener.onStop();
        }
    }

    void progress(int taskId, Object progress) {
        start(taskId);
        listener.onProgress(progress);
    }

    void success(int taskId, Object result) {
        start(taskId);
        listener.onSuccess(result);
        stop(taskId);
    }

    void failure(int taskId, Throwable ex) {
        start(taskId);
        listener.onFailure(ex);
        stop(taskId);
    }

}
