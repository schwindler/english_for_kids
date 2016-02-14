package uz.greenwhite.lib.job.internal;

public class TaskWrapper {

    public final Task task;
    public Object lastProgress;

    public TaskWrapper(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        this.task = task;
    }
}
