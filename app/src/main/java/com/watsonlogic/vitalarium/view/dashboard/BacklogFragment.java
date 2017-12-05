package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of tasks and can serve as the backlog or a sprint
 */
public class BacklogFragment extends DashboardBaseFragment {
    public static final String EXTRA_BACKLOG_TASKS = "BACKLOG_TASKS";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_backlog, container, false);
    }

    public static DashboardBaseFragment newInstance(ArrayList<Task> backlogTasks) {
        BacklogFragment fragment = new BacklogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_BACKLOG_TASKS, backlogTasks);
        fragment.setArguments(args);
        return fragment;
    }
}
