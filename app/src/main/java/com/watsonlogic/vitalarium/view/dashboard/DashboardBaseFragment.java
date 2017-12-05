package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.model.task.Task;

import java.util.ArrayList;
import java.util.List;

import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.DashboardPagerAdapter.EXTRA_PROJECT;

/**

 */
public class DashboardBaseFragment extends Fragment {
    private static final String EXTR_CTA = "callToAction";
    private static final String EXTRA_SPRINT_DATE_RANGE = "sprintDateRange";
    private List<Task> tasks = new ArrayList<>();

    private String cta;
    private String sprintDateRange;


    public DashboardBaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param backlogTasks Parameter 1.
     * @return A new instance of fragment DashboardBaseFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static DashboardBaseFragment newInstance(List<Task> backlogTasks) {
//        DashboardBaseFragment fragment = new DashboardBaseFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(EXTRA_PROJECT, project);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cta = getArguments().getString(EXTR_CTA);
            sprintDateRange = getArguments().getString(EXTRA_SPRINT_DATE_RANGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
}
