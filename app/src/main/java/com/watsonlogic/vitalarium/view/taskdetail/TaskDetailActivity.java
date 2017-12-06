package com.watsonlogic.vitalarium.view.taskdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.task.Task;

import static com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK;

public class TaskDetailActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "TaskDetailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getFragmentManager().beginTransaction().replace(R.id.task_detail_fragment_container,
                TaskDetailFragment.newInstance((Task) getIntent().getParcelableExtra(EXTRA_TASK)),
                TAG_FRAGMENT).commit();
    }
}
