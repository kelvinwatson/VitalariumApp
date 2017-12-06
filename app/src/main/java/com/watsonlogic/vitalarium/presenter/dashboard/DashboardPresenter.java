package com.watsonlogic.vitalarium.presenter.dashboard;

import com.watsonlogic.vitalarium.model.dashboard.DashboardDataActions;
import com.watsonlogic.vitalarium.model.dashboard.DashboardModel;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity;
import com.watsonlogic.vitalarium.view.dashboard.DashboardViewActions;

/**
 * {@link DashboardCoordinatorActions implementation}
 */
public class DashboardPresenter implements DashboardCoordinatorActions {
    private final DashboardActivity view;
    private final DashboardDataActions model;

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
    public void addTask() {

    }

    @Override
    public void updateTask() {

    }

    @Override
    public void moveTask() {

    }

    @Override
    public void onRefreshProject(){

    }
}