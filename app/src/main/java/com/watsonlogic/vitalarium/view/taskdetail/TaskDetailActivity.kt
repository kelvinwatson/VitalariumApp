package com.watsonlogic.vitalarium.view.taskdetail

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toolbar

import com.watsonlogic.vitalarium.R
import com.watsonlogic.vitalarium.model.task.Task

import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity.EXTRA_TASK


class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //show back button
        fragmentManager.beginTransaction().replace(R.id.task_detail_fragment_container,
                TaskDetailFragment.newInstance(intent.getParcelableExtra<Parcelable>(EXTRA_TASK) as Task),
                TAG_FRAGMENT).addToBackStack(TAG_FRAGMENT).commit()
    }

    companion object {
        private val TAG_FRAGMENT = "TaskDetailFragment"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                // app icon in action bar clicked; goto parent activity.
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
