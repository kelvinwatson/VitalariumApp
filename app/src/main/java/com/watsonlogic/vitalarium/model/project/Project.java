package com.watsonlogic.vitalarium.model.project;

import com.watsonlogic.vitalarium.model.sprint.Sprint;
import com.watsonlogic.vitalarium.model.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Project data model
 */
public class Project {
    private String id;
    private List<Sprint> sprints;
    private ArrayList<Task> backlog;
    private String timezone;
    private Boolean success;

    public Project(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public ArrayList<Task> getBacklog() {
        return backlog;
    }

    public void setBacklog(ArrayList<Task> backlog) {
        this.backlog = backlog;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Project(String id, List<Sprint> sprints, ArrayList<Task> backlog, String timezone){
        this.id = id;
        this.sprints = sprints;
        this.backlog = backlog;
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "Project {" +
                "id: " + this.id + ", " +
                "sprints: " + this.sprints.toString() + ", " +
                "backlog: " + this.backlog + ", " +
                "timezone: " + this.timezone + ", " +
                "}";
    }

    public static class ProjectBuilder{
        private String id;
        private List<Sprint> sprints;
        private ArrayList<Task> backlog;
        private String timezone;

        public ProjectBuilder setId(String id){
            this.id = id;
            return this;
        }

        public ProjectBuilder setSprints(List<Sprint> sprints){
            this.sprints = sprints;
            return this;
        }

        public ProjectBuilder setBacklog(ArrayList<Task> backlog){
            this.backlog = backlog;
            return this;
        }

        public ProjectBuilder setTimezone(String timezone){
            this.timezone = timezone;
            return this;
        }

        public Project build(){
            return new Project(id, sprints, backlog, timezone);
        }
    }
}
