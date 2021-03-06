package com.watsonlogic.vitalarium.presenter.dashboard;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.dashboard.DashboardDataActions;
import com.watsonlogic.vitalarium.model.dashboard.DashboardModel;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity;
import com.watsonlogic.vitalarium.view.dashboard.DashboardViewActions;
import com.watsonlogic.vitalarium.view.taskdetail.TaskDetailFragment;

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
        Log.d(TAG, "add task");
        view.onClickAddTask();
    }

    @Override
    public void onClickTask(final Task task, View recyclerViewItem) {
        DashboardPresenter.this.onClickUpdateTask(task);
    }

    @Override
    public void onClickTaskMoreOptions(final Task task, View moreButton) {
        Log.d(TAG, task.toString());
        PopupMenu popup = new PopupMenu(view, moreButton);
        popup.getMenuInflater().inflate(R.menu.task, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_update:
                        DashboardPresenter.this.onClickUpdateTask(task);
                        break;
                    case R.id.action_delete:
                        DashboardPresenter.this.onClickDeleteTask(task);
                        break;
                }
                Toast.makeText(
                        view,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });
        popup.show(); //showing popup menu
    }

    @Override
    public void onClickUpdateTask(Task task) {
        view.onClickUpdateTask(task);
    }

    @Override
    public void onClickDeleteTask(Task task) {
        Log.d(TAG, task.toString());
        view.onClickDeleteUpdateTask(task);
    }

    @Override
    public void onMoveTask() {

    }

    @Override
    public void onRefreshProject() {

    }
}