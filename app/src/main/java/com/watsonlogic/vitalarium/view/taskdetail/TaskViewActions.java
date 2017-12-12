package com.watsonlogic.vitalarium.view.taskdetail;

import com.watsonlogic.vitalarium.model.task.Task;

public interface TaskViewActions {
    void prepopulateFormForUpdate();

    void onExitTaskDetail();

    Task onUpdateTaskInternal();

    void finishActivityAddSuccess(Task task);

    void finishActivityAddFailure(Task task);

    void finishActivityUpdateSuccess(Task task);

    void finishActivityUpdateFailure(Task task);
}
