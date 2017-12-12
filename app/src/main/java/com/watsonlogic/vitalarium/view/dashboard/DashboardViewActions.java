package com.watsonlogic.vitalarium.view.dashboard;

import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;

public interface DashboardViewActions {
    void onGetProjectComplete(Project project);

    void onClickAddTask();

    void onClickUpdateTask(Task task);

    void onClickDeleteUpdateTask(Task task);
}
