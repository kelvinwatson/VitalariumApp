package com.watsonlogic.vitalarium.presenter.dashboard;

import com.watsonlogic.vitalarium.model.project.Project;

public interface DashboardCoordinatorActions {
    void getProject(String projectId);
    void onGetProjectComplete(Project project);
    void addTask();
    //void deleteTask();
    void updateTask();
    void moveTask();
    void onRefreshProject();
}

