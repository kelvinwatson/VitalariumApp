package com.watsonlogic.vitalarium.model.dashboard;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.watsonlogic.vitalarium.VitalariumConstants;
import com.watsonlogic.vitalarium.model.network.NetworkRequestSingleton;
import com.watsonlogic.vitalarium.model.project.Project;
import com.watsonlogic.vitalarium.presenter.dashboard.DashboardCoordinatorActions;
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link DashboardDataActions} implementation
 */
public class DashboardModel implements DashboardDataActions {
    private DashboardCoordinatorActions presenter;
    private DashboardActivity view;
    private static final String TAG = "DashboardModel";
    public void addObserver(DashboardCoordinatorActions presenter) {
        this.presenter = presenter;
    }
    private DatabaseReference dbRef;

    public DashboardModel(DashboardActivity view){
        this.view = view;
    }

    @Override
    public void getProject(String projectId) {
        //call firebase
        if (dbRef == null){
            dbRef = FirebaseDatabase.getInstance().getReference();
        }
        // TODO: Call Volley and wait for result, then dispatch presenter.onGetProjectComplete(project);

        Context context = view.getApplicationContext();
        String firebaseFunctionUrl = VitalariumConstants.BASE_FIREBASE_FUNCTION_URL + "/getProjectFromDb?projectId=" + projectId;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, firebaseFunctionUrl,
                null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    Project project = new Gson().fromJson(String.valueOf(response.getJSONObject("project")), Project.class);
                    Log.d(TAG, project.toString());
                    presenter.onGetProjectComplete(project);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d(TAG, error.toString());
            }
        });

        NetworkRequestSingleton.getInstance(context).addToRequestQueue(jsObjRequest);



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
