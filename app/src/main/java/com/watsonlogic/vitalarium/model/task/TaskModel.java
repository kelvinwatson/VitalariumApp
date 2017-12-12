package com.watsonlogic.vitalarium.model.task;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.watsonlogic.vitalarium.VitalariumConstants;
import com.watsonlogic.vitalarium.model.network.NetworkRequestSingleton;
import com.watsonlogic.vitalarium.presenter.task.TaskCoordinatorActions;
import com.watsonlogic.vitalarium.view.taskdetail.TaskDetailFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskModel implements TaskDataActions {
    private static final String TAG = "TaskModel";
    private TaskDetailFragment view;
    private TaskCoordinatorActions presenter;

    public TaskModel(TaskDetailFragment view) {
        this.view = view;
    }

    public void addObserver(TaskCoordinatorActions presenter) {
        this.presenter = presenter;
    }

    /**
     * Creates a JSONObject of structure above from the Task POJO and calls firebase database to
     * create the Task via Volley
     * <p>
     * "task":"{
     * "createdBy": "abcde",
     * "createdByCreatedOnIndex": "abcde_12345",
     * "createdOn":12345,
     * "description": "",
     * "dueDate":0,
     * "id":"-L-mY5GlQGoKuOQkbyiZ",
     * "project": "-L-ZSr4QBmjA3YP1LN2V",
     * "size": "M\",
     * "sprint": "Backlog",
     * "status": "In Progress",
     * "title": "Task Title",
     * "updatedBy":"abcde",
     * "updatedOn":12345}"
     * }
     *
     * @param task
     */

    @Override
    public void addTask(Task task) {
        Context context = view.getActivity();
        JSONObject jsonObjLeft = new JSONObject();
        try {
            JSONObject jsonObjRight = new JSONObject(new GsonBuilder().create().toJson(task));
            jsonObjLeft.put("task", jsonObjRight);
            Log.d(TAG, jsonObjLeft.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String firebaseFunctionUrl = VitalariumConstants.BASE_FIREBASE_FUNCTION_URL + "/createTask";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, firebaseFunctionUrl,
                jsonObjLeft, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    Log.d(TAG, "onResponse!!!");
                    Task task = new Gson().fromJson(String.valueOf(response.getJSONObject("task")), Task.class);
                    Log.d(TAG, task.toString());
                    presenter.onTaskAdded(task, true);
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse Err!!!");
                    //TODO: Handle errors
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse!!!");
                Log.d(TAG, error.toString());
            }
        });

        NetworkRequestSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    /**
     * Creates a JSONObject of structure above from the Task POJO and calls firebase database to
     * update the Task via Volley
     *
     * @param task to be updated in the database
     */
    @Override
    public void updateTask(Task task, String prevSprintId) {
        Context context = view.getActivity();

        JSONObject jsonObjLeft = new JSONObject();
        try {
            JSONObject jsonObjRight = new JSONObject(new GsonBuilder().create().toJson(task));
            jsonObjLeft.put("prevSprintId", prevSprintId);
            jsonObjLeft.put("task", jsonObjRight);
            Log.d(TAG, jsonObjLeft.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String firebaseFunctionUrl = VitalariumConstants.BASE_FIREBASE_FUNCTION_URL + "/updateTask";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, firebaseFunctionUrl,
                jsonObjLeft, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    Log.d(TAG, "onResponse!!!");
                    Task task = new Gson().fromJson(String.valueOf(response.getJSONObject("task")), Task.class);
                    Log.d(TAG, task.toString());
                    presenter.onTaskUpdated(task, true);
                } catch (JSONException e) {
                    //TODO: Handle errors
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });

        NetworkRequestSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
