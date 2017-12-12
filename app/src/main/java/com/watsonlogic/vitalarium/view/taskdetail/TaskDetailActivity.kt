package com.watsonlogic.vitalarium.view.taskdetail

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.watsonlogic.vitalarium.R
import com.watsonlogic.vitalarium.VitalariumConstants.TaskAction
import com.watsonlogic.vitalarium.model.sprint.Sprint
import com.watsonlogic.vitalarium.model.task.Task
import com.watsonlogic.vitalarium.model.user.User
import com.watsonlogic.vitalarium.presenter.task.TaskPresenter
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.*
import com.watsonlogic.vitalarium.view.signin.SignInActivity.EXTRA_VITALARIUM_USER


class TaskDetailActivity : AppCompatActivity() {
    companion object {
        private val TAG = "TaskDetailFragment";
        private val TAG_FRAGMENT = "TaskDetailFragment"
        private var lastClickTime:Long = 0;
    }

    private lateinit var presenter: TaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //show back button

        var taskDetailFragment: TaskDetailFragment
        when (intent.getStringExtra(EXTRA_TASK_ACTION)){
            TaskAction.ADD.name -> {
                taskDetailFragment = TaskDetailFragment.newAddTaskInstance(
                        intent.getParcelableExtra<User>(EXTRA_VITALARIUM_USER),
                        intent.getStringExtra(EXTRA_PROJECT_ID) as String,
                        intent.getStringExtra(EXTRA_TASK_ACTION) as String)
            }
            else -> { // TaskAction.UPDATE.name
                taskDetailFragment = TaskDetailFragment.newUpdateInstance(
                        intent.getParcelableExtra<User>(EXTRA_VITALARIUM_USER),
                        intent.getParcelableExtra<Parcelable>(EXTRA_TASK) as Task,
                        intent.getStringExtra(EXTRA_TASK_ACTION) as String,
                        intent.getParcelableArrayListExtra<Parcelable>(EXTRA_SPRINTS) as ArrayList<Sprint>)
            }
        }

        fragmentManager.beginTransaction().replace(R.id.task_detail_fragment_container,
                taskDetailFragment,
                TAG_FRAGMENT).addToBackStack(TAG_FRAGMENT).commit()
        presenter = TaskPresenter(taskDetailFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            item.setEnabled(false); //prevent double pressing
            return super.onOptionsItemSelected(item)
        }
        lastClickTime = SystemClock.elapsedRealtime();

        when (item.getItemId()) {
            android.R.id.home -> {
                // app icon in action bar clicked; goto parent activity.
                presenter.onExitTaskDetail();
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun onUpdateTask(task: Task, prevSprintId: String){
        presenter.onUpdateTask(task, prevSprintId);
    }

    fun onAddTask(task: Task){
        presenter.onAddTask(task);
    }

    fun onFinishActivity(requestCode: Int, status: Int, task: Task){
        Log.d(TAG, "onFinishActivity")
        var intent = Intent()
        intent.putExtra(EXTRA_TASK, task)
        this.setResult(status, intent)
        this.finish()
    }
}
