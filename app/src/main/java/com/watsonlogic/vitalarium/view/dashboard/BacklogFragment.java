package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.view.adapter.TaskAdapter;

import java.util.ArrayList;

/**
 * Displays a list of tasks and can serve as the backlog or a sprint
 */
public class BacklogFragment extends DashboardBaseFragment {
    private static final String TAG = "BacklogFragment";
    public static final String EXTRA_PROJECT_ID = "PROJECT_ID";
    public static final String EXTRA_BACKLOG_TASKS = "BACKLOG_TASKS";
    private RecyclerView.Adapter backlogTaskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getArguments();
        if (b != null) {
            tasks = b.getParcelableArrayList(EXTRA_BACKLOG_TASKS);
            projectId = b.getString(EXTRA_PROJECT_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        backlogTaskAdapter = new TaskAdapter(tasks, presenter);
        recycler.setAdapter(backlogTaskAdapter);
    }

    public static BacklogFragment newInstance(ArrayList<Task> backlogTasks, String projectId) {
        BacklogFragment fragment = new BacklogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_PROJECT_ID, projectId);
        args.putParcelableArrayList(EXTRA_BACKLOG_TASKS, backlogTasks);
        fragment.setArguments(args);
        return fragment;
    }
}
