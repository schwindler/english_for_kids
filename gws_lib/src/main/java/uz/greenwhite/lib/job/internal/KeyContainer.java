package uz.greenwhite.lib.job.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uz.greenwhite.lib.job.JobListener;

public class KeyContainer {

    private final HashMap<String, KeyListeners> listeners = new HashMap<String, KeyListeners>();

    public <P, R> void add(String key, JobListener<P, R> listener, Object tag) {
        KeyListeners keyListeners = listeners.get(key);
        if (keyListeners == null) {
            keyListeners = new KeyListeners();
            listeners.put(key, keyListeners);
        }
        keyListeners.add(listener, tag);
    }

    public void removeByTag(Object tag) {
        Iterator<Map.Entry<String, KeyListeners>> it = listeners.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, KeyListeners> entry = it.next();
            entry.getValue().removeByTag(tag);
            if (entry.getValue().isEmpty()) {
                it.remove();
            }
        }
    }

    public void start(Task task) {
        KeyListeners keyListeners = listeners.get(task.jobKey);
        if (keyListeners != null) {
            keyListeners.start(task.taskId);
        }
    }

    public void progress(Task task, Object progress) {
        KeyListeners keyListeners = listeners.get(task.jobKey);
        if (keyListeners != null) {
            keyListeners.progress(task.taskId, progress);
        }
    }

    public void success(Task task, Object result) {
        KeyListeners keyListeners = listeners.get(task.jobKey);
        if (keyListeners != null) {
            keyListeners.success(task.taskId, result);
        }
    }

    public void failure(Task task, Throwable ex) {
        KeyListeners keyListeners = listeners.get(task.jobKey);
        if (keyListeners != null) {
            keyListeners.failure(task.taskId, ex);
        }
    }

}
