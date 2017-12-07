package com.watsonlogic.vitalarium.view.taskdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.VitalariumConstants.TaskAction;
import com.watsonlogic.vitalarium.model.task.Task;

import java.util.Arrays;

import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK_ACTION;

public class TaskDetailFragment extends android.app.Fragment {
    private static final String TAG = "TaskDetailFragment";
    private Task task;
    private TaskAction action;
    private TextInputEditText titleField;
    private TextInputEditText descriptionField;
    private Spinner sizeSpinner;
    private Spinner sprintSpinner;
    private Spinner statusSpinner;
    private String[] sizes = new String[]{"Small", "Medium", "Large"};
    private String[] sprints = new String[]{"Backlog", "Current Sprint", "Next Sprint"};
    private String[] statuses = new String[]{"Not Started", "In Progress", "Done"};

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(Task task, String actionString) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_TASK, task);
        args.putString(EXTRA_TASK_ACTION, actionString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            task = args.getParcelable(EXTRA_TASK);
            action = TaskAction.valueOf(args.getString(EXTRA_TASK_ACTION));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        titleField = v.findViewById(R.id.task_title_input);
        descriptionField = v.findViewById(R.id.task_description_input);

        sizeSpinner = v.findViewById(R.id.task_size_spinner);
        SpinnerAdapter sizeSpinnerAdapter = new ArrayAdapter<>(v.getContext(),
                android.R.layout.simple_spinner_dropdown_item, sizes);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        sprintSpinner = v.findViewById(R.id.task_sprint_spinner);
        SpinnerAdapter sprintSpinnerAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, sprints);
        sprintSpinner.setAdapter(sprintSpinnerAdapter);

        statusSpinner = v.findViewById(R.id.task_status_spinner);
        SpinnerAdapter statusSpinnerAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        statusSpinner.setAdapter(statusSpinnerAdapter);

        prepopulateFormForUpdate();

    }

    private void prepopulateFormForUpdate(){
        if (TaskAction.UPDATE != action){
            return;
        }

        if (task.getTitle() != null){
            titleField.setText(task.getTitle());
        }
        if (task.getDescription() != null){
            descriptionField.setText(task.getDescription());
        }

        Log.d(TAG, task.getSize());
        int sizePosition;
        switch(task.getSize()){
            case "M":
                sizePosition = 1;
                break;
            case "L":
                sizePosition = 2;
                break;
            default: //S
                sizePosition = 0;
        }
        sizeSpinner.setSelection(sizePosition);

        int sprintPosition;
        switch(task.getSprint()){
            case "Current Sprint":
                sprintPosition = 1;
                break;
            case "Next Sprint":
                sprintPosition = 2;
                break;
            default: //Backlog
                sprintPosition = 0;
        }
        sprintSpinner.setSelection(sprintPosition);

        int statusPosition;
        switch(task.getStatus()){
            case "In Progress":
                statusPosition = 1;
                break;
            case "Done":
                statusPosition = 2;
                break;
            default: //Not Started
                statusPosition = 0;
        }
        statusSpinner.setSelection(statusPosition);

    }

}
