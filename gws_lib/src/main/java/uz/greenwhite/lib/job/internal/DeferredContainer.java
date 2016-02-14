package uz.greenwhite.lib.job.internal;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DeferredContainer {

    @SuppressLint("UseSparseArrays")
    private final HashMap<Integer, DeferredWrapper> deferreds = new HashMap<Integer, DeferredWrapper>();

    public void add(DeferredWrapper deferredWrapper) {
        deferreds.put(deferredWrapper.taskId, deferredWrapper);
    }

    public void remove(int taskId) {
        deferreds.remove(taskId);
    }

    public void resolve(int taskId, Object result) {
        DeferredWrapper d = deferreds.get(taskId);
        if (d != null) {
            d.deferred.resolve(result);
            deferreds.remove(taskId);
        }
    }

    public void reject(int taskId, Throwable error) {
        DeferredWrapper d = deferreds.get(taskId);
        if (d != null) {
            d.deferred.reject(error);
            deferreds.remove(taskId);
        }
    }

    public void removeByTag(Object tag) {
        Iterator<Map.Entry<Integer, DeferredWrapper>> it = deferreds.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Integer, DeferredWrapper> v = it.next();
            if (v.getValue().tag == tag) {
                it.remove();
            }
        }
    }


}
