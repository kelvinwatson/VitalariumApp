package com.watsonlogic.vitalarium.model.dashboard;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.presenter.task.DashboardCoordinatorActions;

public class DashboardModel implements DashboardDataActions {
    private DashboardCoordinatorActions presenter;
    private static final String TAG = "DashboardModel";
    public void addObserver(DashboardCoordinatorActions presenter) {
        this.presenter = presenter;
    }
    private DatabaseReference dbRef;

    @Override
    public void getProject(String projectId) {
        //call firebase
        if (dbRef == null){
            dbRef = FirebaseDatabase.getInstance().getReference();
        }
        // TODO: Call Volley and wait for result, then dispatch presenter.onGetProjectComplete(project);


//        DatabaseReference projectRef = dbRef.child("projects").child(projectId);
//        projectRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Project project = dataSnapshot.getValue(Project.class);
//                Log.d(TAG, project.toString());
//                //get tasks
//                //get sprints
            //        presenter.onGetProjectComplete(project); when everything is retrieved

//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Project project = new Project();
    }
}
