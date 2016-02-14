package uz.greenwhite.lib.job;

import uz.greenwhite.lib.job.internal.Task;

public abstract class JobWithProgress<P, R> implements Job<R> {

    public final String jobKey;
    private Task.ProgressNotifier progressNotifier;

    public JobWithProgress(){
        this.jobKey = null;
    }

    public JobWithProgress(String jobKey) {
        this.jobKey = jobKey;
    }

    public final void setProgressNotifier(Task.ProgressNotifier progressNotifier) {
        this.progressNotifier = progressNotifier;
    }

    protected final void notifyProgress(P progress) {
        if (progressNotifier != null) {
            progressNotifier.notifyProgress(progress);
        }
    }
}
