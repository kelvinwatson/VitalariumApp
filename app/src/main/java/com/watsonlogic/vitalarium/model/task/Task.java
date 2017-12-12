package com.watsonlogic.vitalarium.model.task;

import android.os.Parcel;
import android.os.Parcelable;

import com.watsonlogic.vitalarium.model.comment.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Task data model
 */
public class Task implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String size;
    private String sprint;
    private String status;
    private String project;
    private Long dueDate;
    private List<Comment> comments;
    private Long createdOn;
    private String createdBy;
    private Long updatedOn;
    private String updatedBy;
    private String createdByCreatedOnIndex;
    private Boolean success;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Task(String id, String title, String description, String size, String sprint,
                String status, String project, Long dueDate, List<Comment> comments, Long createdOn,
                String createdBy, Long updatedOn, String updatedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.size = size;
        this.sprint = sprint;
        this.status = status;
        this.project = project;
        this.dueDate = dueDate;
        this.comments = comments;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn == null ? createdOn : updatedOn; //initialize with creation date
        this.updatedBy = updatedBy == null ? createdBy : updatedBy; //initialize with creator
        this.createdByCreatedOnIndex = this.createdBy + '_' + this.createdOn;
    }

    @Override
    public String toString() {
        return "Task {" +
                "id: " + this.id + ", " +
                "title: " + this.title + ", " +
                "description: " + this.description + ", " +
                "size: " + this.size + ", " +
                "sprint: " + this.sprint + ", " +
                "status: " + this.status + ", " +
                "project: " + this.project + ", " +
                "dueDate: " + this.dueDate + ", " +
                "comments: " + this.comments + ", " +
                "createdOn: " + this.createdOn + ", " +
                "createdBy: " + this.createdBy + ", " +
                "updatedOn: " + this.updatedOn + ", " +
                "updatedBy: " + this.updatedBy + ", " +
                "createdByCreatedOnIndex: " + this.createdByCreatedOnIndex + ", " +
                "}";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedByCreatedOnIndex() {
        return createdByCreatedOnIndex;
    }

    public void setCreatedByCreatedOnIndex(String createdByCreatedOnIndex) {
        this.createdByCreatedOnIndex = createdByCreatedOnIndex;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    protected Task(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        size = in.readString();
        sprint = in.readString();
        status = in.readString();
        project = in.readString();
        dueDate = in.readByte() == 0x00 ? null : in.readLong();
        if (in.readByte() == 0x01) {
            comments = new ArrayList<Comment>();
            in.readList(comments, Comment.class.getClassLoader());
        } else {
            comments = null;
        }
        createdOn = in.readByte() == 0x00 ? null : in.readLong();
        createdBy = in.readString();
        updatedOn = in.readByte() == 0x00 ? null : in.readLong();
        updatedBy = in.readString();
        createdByCreatedOnIndex = in.readString();
        byte successVal = in.readByte();
        success = successVal == 0x02 ? null : successVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(size);
        dest.writeString(sprint);
        dest.writeString(status);
        dest.writeString(project);
        if (dueDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(dueDate);
        }
        if (comments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(comments);
        }
        if (createdOn == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(createdOn);
        }
        dest.writeString(createdBy);
        if (updatedOn == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(updatedOn);
        }
        dest.writeString(updatedBy);
        dest.writeString(createdByCreatedOnIndex);
        if (success == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (success ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public static class TaskBuilder {
        private String id;
        private String title;
        private String description;
        private String size;
        private String sprint;
        private String status;
        private String project;
        private Long dueDate;
        private List<Comment> comments;
        private Long createdOn;
        private String createdBy;
        private Long updatedOn;
        private String updatedBy;

        public TaskBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public TaskBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public TaskBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder setSize(String size) {
            this.size = size;
            return this;
        }

        public TaskBuilder setSprint(String sprint) {
            this.sprint = sprint;
            return this;
        }

        public TaskBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public TaskBuilder setProject(String project) {
            this.project = project;
            return this;
        }

        public TaskBuilder setDueDate(Long dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public TaskBuilder setComments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public TaskBuilder setCreatedOn(Long createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public TaskBuilder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public TaskBuilder setUpdatedOn(Long updatedOn) {
            this.updatedOn = updatedOn;
            return this;
        }

        public TaskBuilder setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public Task build() {
            return new Task(id, title, description, size, sprint, status, project, dueDate,
                    comments, createdOn, createdBy, updatedOn, updatedBy);
        }
    }
}
