package com.watsonlogic.vitalarium.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.watsonlogic.vitalarium.databinding.TaskBinding;
import com.watsonlogic.vitalarium.model.task.Task;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Adapter for task object, uses data binding
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final DashboardPresenter presenter;
    private final List<Task> tasks;

    public TaskAdapter(List<Task> tasks, DashboardPresenter presenter) {
        this.tasks = tasks;
        this.presenter = presenter;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TaskBinding binding;
        private WeakReference<DashboardPresenter> presenterRef; //access presenter, but prevent memory leaks

        public TaskViewHolder(TaskBinding binding, DashboardPresenter presenter) {
            super(binding.getRoot());
            this.binding = binding;
            presenterRef = new WeakReference<>(presenter);
        }

        public void bind(Task task) {
            binding.setTask(task);
            binding.setDashboardPresenter(presenterRef.get());
            binding.executePendingBindings();
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(TaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), presenter);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }
}
