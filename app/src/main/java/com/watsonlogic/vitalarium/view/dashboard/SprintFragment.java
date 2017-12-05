package com.watsonlogic.vitalarium.view.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.sprint.Sprint;


public class SprintFragment extends DashboardBaseFragment {
    private static final String EXTRA_SPRINT = "SPRINT";
    private static final String TAG = "SprintFragment";
    private Sprint sprint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b = getArguments();
        if (b != null) {
            sprint = b.getParcelable(EXTRA_SPRINT);
            if (sprint != null){
                Log.d(TAG, sprint.toString());
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sprint, container, false);
    }

    public static DashboardBaseFragment newInstance(Sprint sprint) {
        SprintFragment fragment = new SprintFragment();
        if (sprint != null){
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_SPRINT, sprint);
            fragment.setArguments(args);
        }
        return fragment;
    }
}
