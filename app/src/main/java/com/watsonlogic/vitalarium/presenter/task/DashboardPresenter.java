package com.watsonlogic.vitalarium.presenter.task;

import com.watsonlogic.vitalarium.model.dashboard.DashboardDataActions;
import com.watsonlogic.vitalarium.model.dashboard.DashboardModel;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.view.dashboard.DashboardViewActions;

import java.util.List;

public class DashboardPresenter implements DashboardCoordinatorActions {
    private final DashboardViewActions view;
    private final DashboardDataActions model;

    public DashboardPresenter(DashboardViewActions view) {
        this.view = view;
        this.model = new DashboardModel();
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
    public void addTask() {

    }

    @Override
    public void updateTask() {

    }

    @Override
    public void moveTask() {

    }
}