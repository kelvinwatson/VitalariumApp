package com.watsonlogic.vitalarium.model.user;

import java.util.List;

public class User {
    private String id;
    private String displayName;
    private String email;
    private String photoUrl;
    private String providerId;
    private List<String> projects;

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String displayName, String email, String photoUrl, String providerId, List<String> projects) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.providerId = providerId;
        this.projects = projects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "User {" +
                "id: " + this.id + ", " +
                "displayName: " + this.displayName + ", " +
                "email: " + this.email + "," +
                "photoUrl: " + this.photoUrl + ", " +
                "providerId: " + this.providerId + ", " +
                "projects: " + this.projects.toString() +
                "}";
    }

    public static class UserBuilder {
        private String id;
        private String displayName;
        private String email;
        private String photoUrl;
        private String providerId;
        private List<String> projects;

        public UserBuilder setId(String id){
            this.id = id;
            return this;
        }

        public UserBuilder setDisplayName(String displayName){
            this.displayName = displayName;
            return this;
        }

        public UserBuilder setEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder setPhotoUrl(String photoUrl){
            this.photoUrl = photoUrl;
            return this;
        }

        public UserBuilder setProviderId(String providerId){
            this.providerId = providerId;
            return this;
        }

        public UserBuilder setProjects(List<String> projects){
            this.projects = projects;
            return this;
        }

        public User build(){
            return new User(id, displayName, email, photoUrl, providerId, projects);
        }
    }
}
