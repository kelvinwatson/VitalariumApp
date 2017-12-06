package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardPresenter;
import com.watsonlogic.vitalarium.view.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of tasks and can serve as the backlog or a sprint
 */
public class BacklogFragment extends DashboardBaseFragment implements DashboardViewActions {
    private DashboardPresenter presenter;
    private static final String TAG = "BacklogFragment";
    public static final String EXTRA_PROJECT_ID = "PROJECT_ID";
    public static final String EXTRA_BACKLOG_TASKS = "BACKLOG_TASKS";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recycler;
    private RecyclerView.Adapter backlogTaskAdapter;
    private List<Task> tasks;
    private String projectId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = new DashboardPresenter((DashboardActivity)getActivity());
        Bundle b = getArguments();
        if (b != null) {
            tasks = b.getParcelableArrayList(EXTRA_BACKLOG_TASKS);
            projectId = b.getString(EXTRA_PROJECT_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_backlog, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        backlogTaskAdapter = new TaskAdapter(tasks, presenter);
        recycler = v.findViewById(R.id.backlog_task_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(backlogTaskAdapter);
        recycler.addItemDecoration(new DividerItemDecoration(v.getContext(), layoutManager.getOrientation()));
        swipeRefresh = v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getProject(projectId);
            }
        });
    }

    public static DashboardBaseFragment newInstance(ArrayList<Task> backlogTasks, String projectId) {
        BacklogFragment fragment = new BacklogFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_PROJECT_ID, projectId);
        args.putParcelableArrayList(EXTRA_BACKLOG_TASKS, backlogTasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onGetProjectComplete(Project project) {

    }
}
