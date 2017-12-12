package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.sprint.Sprint;
import com.watsonlogic.vitalarium.view.adapter.TaskAdapter;

import static com.watsonlogic.vitalarium.view.dashboard.BacklogFragment.EXTRA_PROJECT_ID;


public class SprintFragment extends DashboardBaseFragment {
    private static final String EXTRA_SPRINT = "SPRINT";
    private static final String TAG = "SprintFragment";
    private RecyclerView.Adapter sprintTaskAdapter;
    private Sprint sprint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getArguments();
        if (b != null) {
            projectId = b.getString(EXTRA_PROJECT_ID);
            sprint = b.getParcelable(EXTRA_SPRINT);
            if (sprint != null) {
                Log.d(TAG, sprint.toString());
                tasks = sprint.getTasks();
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        sprintTaskAdapter = new TaskAdapter(tasks, presenter);
        recycler.setAdapter(sprintTaskAdapter);
    }

    public static DashboardBaseFragment newInstance(Sprint sprint, String projectId) {
        SprintFragment fragment = new SprintFragment();
        if (sprint != null) {
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_SPRINT, sprint);
            args.putString(EXTRA_PROJECT_ID, projectId);
            fragment.setArguments(args);
        }
        return fragment;
    }
}
