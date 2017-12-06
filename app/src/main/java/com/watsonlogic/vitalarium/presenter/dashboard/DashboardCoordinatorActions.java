package com.watsonlogic.vitalarium.presenter.dashboard;

import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;

public interface DashboardCoordinatorActions {
    void getProject(String projectId);

    void onGetProjectComplete(Project project);

    void onClickAddTask();

    void onClickTaskMoreOptions(Task task);

    void onClickUpdateTask(Task task);

    void onClickDeleteTask(Task task);

    void onMoveTask();

    void onRefreshProject();
}

