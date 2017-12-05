package com.watsonlogic.vitalarium.model.network;

import android.content.Context;
import android.net.NetworkRequest;
import android.support.annotation.VisibleForTesting;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Request queue for Google Volley requests
 */
public class NetworkRequestSingleton {
    private static NetworkRequestSingleton instance;
    private static Context context;
    private RequestQueue requestQueue;

    private NetworkRequestSingleton(Context context){
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized NetworkRequestSingleton getInstance(Context context){
        if (instance == null){
            instance = new NetworkRequestSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
