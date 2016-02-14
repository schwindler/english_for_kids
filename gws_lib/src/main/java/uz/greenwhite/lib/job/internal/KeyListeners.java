package uz.greenwhite.lib.job.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uz.greenwhite.lib.job.JobListener;

public class KeyListeners {

    private final List<KeyListener> listeners = new ArrayList<KeyListener>();

    @SuppressWarnings("unchecked")
    public <P, R> void add(JobListener<P, R> listener, Object tag) {
        KeyListener v = new KeyListener((JobListener<Object, Object>) listener, tag);
        listeners.add(v);
    }

    public void removeByTag(Object tag) {
        for (Iterator<KeyListener> iterator = listeners.iterator(); iterator.hasNext(); ) {
            KeyListener k = iterator.next();
            if (k.tag == tag) {
                iterator.remove();
            }
        }
    }

    public boolean isEmpty() {
        return listeners.isEmpty();
    }

    public void start(int taskId) {
        for (KeyListener listener : listeners) {
            listener.start(taskId);
        }
    }

    public void progress(int taskId, Object progress) {
        for (KeyListener listener : listeners) {
            listener.progress(taskId, progress);
        }
    }

    public void success(int taskId, Object result) {
        for (KeyListener listener : listeners) {
            listener.success(taskId, result);
        }
    }

    public void failure(int taskId, Throwable ex) {
        for (KeyListener listener : listeners) {
            listener.failure(taskId, ex);
        }
    }
}
