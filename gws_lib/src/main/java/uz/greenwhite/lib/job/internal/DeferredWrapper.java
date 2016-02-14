package uz.greenwhite.lib.job.internal;

import uz.greenwhite.lib.job.Deferred;

public class DeferredWrapper {

    public final int taskId;
    public final Deferred<Object> deferred;
    public final Object tag;

    public DeferredWrapper(int taskId, Deferred<Object> deferred, Object tag) {
        if (deferred == null || tag == null) {
            throw new NullPointerException();
        }
        this.taskId = taskId;
        this.deferred = deferred;
        this.tag = tag;
    }
}
