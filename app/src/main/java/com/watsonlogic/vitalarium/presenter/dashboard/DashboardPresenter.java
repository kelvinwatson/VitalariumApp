package com.watsonlogic.vitalarium.presenter.dashboard;

import android.util.Log;

import com.watsonlogic.vitalarium.model.dashboard.DashboardDataActions;
import com.watsonlogic.vitalarium.model.dashboard.DashboardModel;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity;
import com.watsonlogic.vitalarium.view.dashboard.DashboardViewActions;

/**
 * {@link DashboardCoordinatorActions implementation}
 */
public class DashboardPresenter implements DashboardCoordinatorActions {
    private final DashboardActivity view;
    private final DashboardDataActions model;
    private static final String TAG = "DashboardPresenter";

    public DashboardPresenter(DashboardActivity view) {
        this.view = view;
        this.model = new DashboardModel(view);
        model.addObserver(this);
    }

    @Override
    public void getProject(String projectId) {
        model.getProject(projectId);
    }

    @Override
    public void onGetProjectComplete(Project project) {
        view.onGetProjectComplete(project);
    }


    @Override
    public void onClickAddTask() {

    }

    @Override
    public void onClickTaskMoreOptions(Task task) {
        Log.d(TAG, task.toString());
    }

    @Override
    public void onClickUpdateTask(Task task) {
        Log.d(TAG, task.toString());
    }

    @Override
    public void onClickDeleteTask(Task task) {

    }

    @Override
    public void onMoveTask() {

    }

    @Override
    public void onRefreshProject(){

    }
}