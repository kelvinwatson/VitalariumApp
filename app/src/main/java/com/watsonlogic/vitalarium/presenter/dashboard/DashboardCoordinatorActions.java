package com.watsonlogic.vitalarium.presenter.dashboard;

import android.view.View;

import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;

public interface DashboardCoordinatorActions {
    void getProject(String projectId);

    void onGetProjectComplete(Project project);

    void onClickAddTask();

    void onClickTask(Task task, View recyclerViewItem);

    void onClickTaskMoreOptions(Task task, View button);

    void onClickUpdateTask(Task task);

    void onClickDeleteTask(Task task);

    void onMoveTask();

    void onRefreshProject();
}

