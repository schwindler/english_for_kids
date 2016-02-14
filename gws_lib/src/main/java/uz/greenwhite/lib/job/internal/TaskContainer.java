package uz.greenwhite.lib.job.internal;

import android.annotation.SuppressLint;

import java.util.HashMap;

public class TaskContainer {

    @SuppressLint("UseSparseArrays")
    private final HashMap<Integer, TaskWrapper> taskWrappers = new HashMap<Integer, TaskWrapper>();

    public TaskWrapper get(int taskId) {
        return taskWrappers.get(taskId);
    }

    public void add(TaskWrapper taskWrapper) {
        if (findByJobKey(taskWrapper.task.jobKey) != null) {
            throw new IllegalArgumentException("Job key is dubplicated key=" + taskWrapper.task.jobKey);
        }
        taskWrappers.put(taskWrapper.task.taskId, taskWrapper);
    }

    public void remove(int taskId) {
        taskWrappers.remove(taskId);
    }

    public TaskWrapper findByJobKey(String jobKey) {
        if (jobKey != null) {
            for (TaskWrapper taskWrapper : taskWrappers.values()) {
                if (jobKey.equals(taskWrapper.task.jobKey)) {
                    return taskWrapper;
                }
            }
        }
        return null;
    }

}
