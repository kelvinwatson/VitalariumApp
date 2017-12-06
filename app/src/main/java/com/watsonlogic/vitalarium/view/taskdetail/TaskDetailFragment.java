package com.watsonlogic.vitalarium.view.taskdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.task.Task;

import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK;

public class TaskDetailFragment extends android.app.Fragment {
    private Task task;
    private String[] sizes = new String[]{"Small", "Medium", "Large"};
    private String[] sprints = new String[]{"Backlog", "Current Sprint", "Next Sprint"};
    private String[] statuses = new String[]{"Not Started", "In Progress", "Done"};

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = getArguments().getParcelable(EXTRA_TASK);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Spinner sizeSpinner = v.findViewById(R.id.task_size_spinner);
        SpinnerAdapter sizeSpinnerAdapter = new ArrayAdapter<>(v.getContext(),
                android.R.layout.simple_spinner_dropdown_item, sizes);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        Spinner sprintSpinner = v.findViewById(R.id.task_sprint_spinner);
        SpinnerAdapter sprintSpinnerAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, sprints);
        sprintSpinner.setAdapter(sprintSpinnerAdapter);

        Spinner statusSpinner = v.findViewById(R.id.task_status_spinner);
        SpinnerAdapter statusSpinnerAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        statusSpinner.setAdapter(statusSpinnerAdapter);
    }
}
