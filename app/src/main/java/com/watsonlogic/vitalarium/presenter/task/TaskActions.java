package com.watsonlogic.vitalarium.presenter.task;

import com.watsonlogic.vitalarium.model.task.Task;

import java.util.List;

public interface TaskActions {
    List<Task> getTasks();
    void addTask();
    //void deleteTask();
    void updateTask();
    void moveTask();
}
