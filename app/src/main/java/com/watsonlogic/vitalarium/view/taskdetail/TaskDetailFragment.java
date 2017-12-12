package com.watsonlogic.vitalarium.view.taskdetail;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.VitalariumConstants.TaskAction;
import com.watsonlogic.vitalarium.model.sprint.Sprint;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.model.user.User;

import java.util.ArrayList;

import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_PROJECT_ID;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_SPRINTS;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK_ACTION;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.RC_ADD;
import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.RC_UPDATE;
import static com.watsonlogic.vitalarium.view.signin.SignInActivity.EXTRA_VITALARIUM_USER;

public class TaskDetailFragment extends android.app.Fragment implements TaskViewActions {
    private static final String TAG = "TaskDetailFragment";
    private String projectId;
    private User user;
    private Task task;
    private TaskAction action;
    private TextInputEditText titleField;
    private TextInputEditText descriptionField;
    private Spinner sizeSpinner;
    private Spinner sprintSpinner;
    private Spinner statusSpinner;
    private String[] sizes = new String[]{"Small", "Medium", "Large"};
    private String[] sprints = new String[]{"Backlog", "Current sprint", "Next sprint"};
    private String[] statuses = new String[]{"Not Started", "In Progress", "Done"};

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newAddTaskInstance(User user, String projectId, String actionString) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VITALARIUM_USER, user);
        args.putString(EXTRA_TASK_ACTION, actionString);

        args.putString(EXTRA_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskDetailFragment newUpdateInstance(User user, Task task, String actionString, ArrayList<Sprint> sprintList) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_VITALARIUM_USER, user);
        args.putString(EXTRA_TASK_ACTION, actionString);

        args.putParcelable(EXTRA_TASK, task);
        args.putParcelableArrayList(EXTRA_SPRINTS, sprintList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            user = args.getParcelable(EXTRA_VITALARIUM_USER);
            action = TaskAction.valueOf(args.getString(EXTRA_TASK_ACTION));

            //add
            projectId = args.getString(EXTRA_PROJECT_ID);
            //update
            task = args.getParcelable(EXTRA_TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        titleField = root.findViewById(R.id.task_title_input);
        titleField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(((TextInputEditText) v).getText())) {
                    ((TextInputLayout) root.findViewById(R.id.task_title_textinputlayout))
                            .setError(getString(R.string.error_title_field_empty));
                }
            }
        });
        descriptionField = root.findViewById(R.id.task_description_input);

        sizeSpinner = root.findViewById(R.id.task_size_spinner);
        SpinnerAdapter sizeSpinnerAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, sizes);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        sprintSpinner = root.findViewById(R.id.task_sprint_spinner);
        SpinnerAdapter sprintSpinnerAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, sprints);
        sprintSpinner.setAdapter(sprintSpinnerAdapter);

        statusSpinner = root.findViewById(R.id.task_status_spinner);
        SpinnerAdapter statusSpinnerAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_dropdown_item, statuses);
        statusSpinner.setAdapter(statusSpinnerAdapter);

        prepopulateFormForUpdate();
    }

    public void prepopulateFormForUpdate() {
        if (task == null || TaskAction.UPDATE != action) {
            return;
        }

        if (task.getTitle() != null) {
            titleField.setText(task.getTitle());
        }
        if (task.getDescription() != null) {
            descriptionField.setText(task.getDescription());
        }

        Log.d(TAG, task.getSize());
        int sizePosition;
        switch (task.getSize()) {
            case "Medium":
                sizePosition = 1;
                break;
            case "Large":
                sizePosition = 2;
                break;
            default: //S
                sizePosition = 0;
        }
        sizeSpinner.setSelection(sizePosition);

        int sprintPosition;
        switch (task.getSprint()) {
            case "Current sprint":
                sprintPosition = 1;
                break;
            case "Next sprint":
                sprintPosition = 2;
                break;
            default: //Backlog
                sprintPosition = 0;
        }
        sprintSpinner.setSelection(sprintPosition);

        int statusPosition;
        switch (task.getStatus()) {
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

    public void onExitTaskDetail() {
        if (TaskAction.UPDATE == action) {
            if (hasChanges()) {
                Log.d(TAG, "Changes were made");
                // update the local task object
                // then call the database
                String prevSprintId = task.getSprint();
                Task newTask = onUpdateTaskInternal();
                ((TaskDetailActivity) getActivity()).onUpdateTask(newTask, prevSprintId);
            } else {
                Log.d(TAG, "No changes were made");
                getActivity().finish(); //finish without result since no changes were made
            }
        } else {
            //construct task object
            if (areRequiredFieldsComplete()) {
                Task newTask = constructNewTask();
                ((TaskDetailActivity) getActivity()).onAddTask(newTask);
            } else {
                //task incomplete, show dialog asking if OK to exit (won't be saved) --> cancel/ok
            }
        }
    }

    private boolean areRequiredFieldsComplete() {
        return !TextUtils.isEmpty(titleField.getText());
    }

    private Task constructNewTask() {
        return new Task.TaskBuilder().setTitle(titleField.getText().toString())
                .setDescription(descriptionField.getText().toString())
                .setSize(sizeSpinner.getSelectedItem().toString())
                .setSprint(sprintSpinner.getSelectedItem().toString())
                .setStatus(statusSpinner.getSelectedItem().toString())
                .setProject(projectId)
                .setCreatedOn(System.currentTimeMillis())
                .setCreatedBy(user.getId())
                .build();
    }

    private boolean hasChanges() {
        //TODO null checks???

        if (!titleField.getText().toString().equals(task.getTitle())) {
            Log.d(TAG, "title has changed");
            Log.d(TAG, titleField.getText().toString());
            Log.d(TAG, task.getTitle());
            return true;
        }
        if (!descriptionField.getText().toString().equals(task.getDescription())) {
            Log.d(TAG, "desc has changed");
            return true;
        }
        if (!sizeSpinner.getSelectedItem().toString().equals(task.getSize())) {
            Log.d(TAG, "size has changed");
            return true;
        }
        if (!sprintSpinner.getSelectedItem().toString().equals(task.getSprint())) {
            Log.d(TAG, "sprint has changed");
            return true;
        }
        if (!statusSpinner.getSelectedItem().toString().equals(task.getStatus())) {
            Log.d(TAG, "status has changed");
            return true;
        }

        Log.d(TAG, "no changes");
        return false;
    }

    /**
     * Private method to set task object internally before saving to database
     */
    @Override
    public Task onUpdateTaskInternal() {
        return new Task.TaskBuilder().setId(task.getId())
                .setTitle(titleField.getText().toString())
                .setDescription(descriptionField.getText().toString())
                .setSize(sizeSpinner.getSelectedItem().toString())
                .setSprint(sprintSpinner.getSelectedItem().toString())
                .setStatus(statusSpinner.getSelectedItem().toString())
                .setProject(projectId)
                .setCreatedOn(task.getCreatedOn())
                .setCreatedBy(task.getCreatedBy())
                .setUpdatedOn(System.currentTimeMillis())
                .setUpdatedBy(user.getId()) //TODO: maybe different if allowing other users
                .build();
    }

    @Override
    public void finishActivityUpdateSuccess(Task task) {
        Log.d(TAG, "finishActivityUpdateSuccess");
        Activity activity = getActivity();
        if (activity != null) {
            ((TaskDetailActivity) getActivity()).onFinishActivity(RC_UPDATE, 200, task);
        }
    }

    @Override
    public void finishActivityUpdateFailure(Task task) {
        Log.d(TAG, "finishActivityUpdateFailure");
        Activity activity = getActivity();
        if (activity != null) {
            //FIXME: different error codes?
            ((TaskDetailActivity) getActivity()).onFinishActivity(RC_UPDATE, 500, task);
        }
    }

    @Override
    public void finishActivityAddSuccess(Task task) {
        Log.d(TAG, "finishActivityAddSuccess");
        Activity activity = getActivity();
        if (activity != null) {
            ((TaskDetailActivity) getActivity()).onFinishActivity(RC_ADD, 200, task);
        }
    }

    @Override
    public void finishActivityAddFailure(Task task) {
        Log.d(TAG, "finishActivityAddFailure");
        Activity activity = getActivity();
        if (activity != null) {
            //FIXME: different error codes?
            ((TaskDetailActivity) getActivity()).onFinishActivity(RC_ADD, 500, task);
        }
    }
}
