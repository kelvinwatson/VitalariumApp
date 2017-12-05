package com.watsonlogic.vitalarium.model.dashboard;

import com.watsonlogic.vitalarium.presenter.task.DashboardCoordinatorActions;

/**
 * Actions for the main dashboard
 */
public interface DashboardDataActions {
    void getProject(String projectId);

    void addObserver(DashboardCoordinatorActions presenter);
}
