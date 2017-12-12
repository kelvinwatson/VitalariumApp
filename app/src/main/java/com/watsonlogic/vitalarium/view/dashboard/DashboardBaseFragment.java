package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardPresenter;

import java.util.ArrayList;
import java.util.List;

/**

 */
public abstract class DashboardBaseFragment extends Fragment {
    private static final String TAG = "DashboardBaseFragment";
    private static final String EXTR_CTA = "callToAction";
    private static final String EXTRA_SPRINT_DATE_RANGE = "sprintDateRange";
    protected DashboardPresenter presenter;
    protected RecyclerView recycler;
    protected List<Task> tasks = new ArrayList<>();
    protected String projectId;

    public DashboardBaseFragment() {
        // Required empty public constructor
    }

    private int getFragmentLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        presenter = new DashboardPresenter((DashboardActivity)getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        SwipeRefreshLayout swipeRefresh = v.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getProject(projectId);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recycler = v.findViewById(R.id.task_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(v.getContext(), layoutManager.getOrientation()));

        super.onViewCreated(v, savedInstanceState);
    }
}
