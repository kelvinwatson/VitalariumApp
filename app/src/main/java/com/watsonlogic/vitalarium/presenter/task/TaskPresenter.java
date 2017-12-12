package com.watsonlogic.vitalarium.presenter.task;

import android.util.Log;

import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.model.task.TaskDataActions;
import com.watsonlogic.vitalarium.model.task.TaskModel;
import com.watsonlogic.vitalarium.view.taskdetail.TaskDetailFragment;
import com.watsonlogic.vitalarium.view.taskdetail.TaskViewActions;

public class TaskPresenter implements TaskCoordinatorActions{
    private static final String TAG = "TaskPresenter";
    private TaskDetailFragment view;
    private TaskDataActions model;

    public TaskPresenter(TaskDetailFragment view){
        this.view = view;
        this.model = new TaskModel(view);
        model.addObserver(this);
    }

    @Override
    public void onExitTaskDetail() {
        view.onExitTaskDetail();
    }

    @Override
    public void onAddTask(Task task) {
        model.addTask(task);
    }

    @Override
    public void onTaskAdded(Task task, boolean isSuccess) {
        if (isSuccess){
            Log.d(TAG, "onTaskAdded");
            Log.d(TAG, task.toString());
            view.finishActivityAddSuccess(task);
        } else {
            view.finishActivityAddFailure(task);
        }
    }

    @Override
    public void onUpdateTask(Task task, String prevSprintId) {
        model.updateTask(task, prevSprintId);
    }

    @Override
    public void onTaskUpdated(Task task, boolean isSuccess) {
        if (isSuccess){
            Log.d(TAG, "onTaskUpdated");
            Log.d(TAG, task.toString());
            view.finishActivityUpdateSuccess(task);
        } else {
            view.finishActivityUpdateFailure(task);
        }
    }
}
