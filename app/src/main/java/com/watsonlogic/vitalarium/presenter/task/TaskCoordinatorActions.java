package com.watsonlogic.vitalarium.presenter.task;

import com.watsonlogic.vitalarium.model.task.Task;

public interface TaskCoordinatorActions {
    void onExitTaskDetail();

    void onAddTask(Task task);

    void onTaskAdded(Task task, boolean isSuccess);

    void onUpdateTask(Task task, String prevSprintId);

    void onTaskUpdated(Task task, boolean isSuccess);
}
