package com.watsonlogic.vitalarium.model.sprint;

import android.os.Parcel;
import android.os.Parcelable;

import com.watsonlogic.vitalarium.model.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Sprint data model
 */
public class Sprint implements Parcelable {
    private String id;
    private String startDate;
    private String endDate;
    private List<Task> tasks;

    public Sprint() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Sprint(String id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Sprint {" +
                "id: " + this.id + ", " +
                "startDate: " + this.startDate + ", " +
                "endDate: " + this.endDate + ", " +
                "tasks: " + this.tasks + ", " +
                "}";
    }

    protected Sprint(Parcel in) {
        id = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        if (in.readByte() == 0x01) {
            tasks = new ArrayList<Task>();
            in.readList(tasks, Task.class.getClassLoader());
        } else {
            tasks = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(startDate);
        dest.writeString(endDate);
        if (tasks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tasks);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sprint> CREATOR = new Parcelable.Creator<Sprint>() {
        @Override
        public Sprint createFromParcel(Parcel in) {
            return new Sprint(in);
        }

        @Override
        public Sprint[] newArray(int size) {
            return new Sprint[size];
        }
    };

    public static class SprintBuilder {
        private String id;
        private String startDate;
        private String endDate;

        public SprintBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public SprintBuilder setStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public SprintBuilder setEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Sprint build() {
            return new Sprint(id, startDate, endDate);
        }
    }
}
