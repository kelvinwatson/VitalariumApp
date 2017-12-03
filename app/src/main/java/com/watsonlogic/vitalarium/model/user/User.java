package com.watsonlogic.vitalarium.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private String id;
    private String displayName;
    private String email;
    private String photoUrl;
    private String providerId;
    private List<String> projects;

    public User() {
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
                "email: " + this.email + ", " +
                "photoUrl: " + this.photoUrl + ", " +
                "providerId: " + this.providerId + ", " +
                "projects: " + this.projects.toString() +
                "}";
    }

    protected User(Parcel in) {
        id = in.readString();
        displayName = in.readString();
        email = in.readString();
        photoUrl = in.readString();
        providerId = in.readString();
        if (in.readByte() == 0x01) {
            projects = new ArrayList<String>();
            in.readList(projects, String.class.getClassLoader());
        } else {
            projects = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(displayName);
        dest.writeString(email);
        dest.writeString(photoUrl);
        dest.writeString(providerId);
        if (projects == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(projects);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static class UserBuilder {
        private String id;
        private String displayName;
        private String email;
        private String photoUrl;
        private String providerId;
        private List<String> projects;

        public UserBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public UserBuilder setProviderId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public UserBuilder setProjects(List<String> projects) {
            this.projects = projects;
            return this;
        }

        public User build() {
            return new User(id, displayName, email, photoUrl, providerId, projects);
        }
    }
}
