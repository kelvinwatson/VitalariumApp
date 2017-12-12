package com.watsonlogic.vitalarium.model.task;

import com.watsonlogic.vitalarium.presenter.task.TaskCoordinatorActions;

import org.json.JSONException;

public interface TaskDataActions {
    void addTask(Task task);

    void updateTask(Task task, String prevSprintId);

    void addObserver(TaskCoordinatorActions presenter);
}
