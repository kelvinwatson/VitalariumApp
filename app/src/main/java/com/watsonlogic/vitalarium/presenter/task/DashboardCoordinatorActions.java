package com.watsonlogic.vitalarium.presenter.task;

import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;

import java.util.List;

public interface DashboardCoordinatorActions {
    void getProject(String projectId);
    void onGetProjectComplete(Project project);
    void addTask();
    //void deleteTask();
    void updateTask();
    void moveTask();
}
