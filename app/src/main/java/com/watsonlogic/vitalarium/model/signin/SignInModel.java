package com.watsonlogic.vitalarium.model.signin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.watsonlogic.vitalarium.BuildConfig;
import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.VitalariumConstants;
import com.watsonlogic.vitalarium.model.network.NetworkRequestSingleton;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.presenter.signin.SignInCoordinatorActions;
import com.watsonlogic.vitalarium.presenter.signin.SignInPresenter;
import com.watsonlogic.vitalarium.view.signin.SignInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * {@link SignInDataActions implementation}
 * Handles data actions for sign in
 */
public class SignInModel implements SignInDataActions {
    private static final String TAG = "SignInModel";
    private static final String GOOGLE_TOS_URL = "https://www.google.com/policies/terms/";
    private static final String FIREBASE_TOS_URL = "https://firebase.google.com/terms/";
    private static final String GOOGLE_PRIVACY_POLICY_URL = "https://www.google.com/policies/privacy/";
    private static final String FIREBASE_PRIVACY_POLICY_URL = "https://firebase.google.com/terms/analytics/#7_privacy";
    private static final List<AuthUI.IdpConfig> authProviders = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build());
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SignInCoordinatorActions presenter;
    private DatabaseReference dbRef;
    private SignInActivity view;

    public SignInModel(SignInActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void addObserver(SignInCoordinatorActions presenter) {
        this.presenter = presenter;
    }

    @Override
    public Intent createFirebaseAuthIntent() {
        return AuthUI.getInstance().createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setLogo(R.drawable.logo)
                .setAvailableProviders(authProviders)
                .setTosUrl(GOOGLE_TOS_URL)
                .setPrivacyPolicyUrl(GOOGLE_PRIVACY_POLICY_URL)
//                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setIsSmartLockEnabled(false)
                .build();
    }

    @Override
    public void initializeFirebaseAuthStateChangedListener(final SignInPresenter presenter) {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // writeUserToDatabaseIfNotExists(user);
                    // TODO Implement
                    onUserSignedIn(user);
                } else {
                    onUserSignedOut();
                }
            }
        };
    }

    private void onUserSignedIn(FirebaseUser user) {
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getToken() instead.
        String uid = user.getUid();

        writeUserToDatabaseIfNotExists(user);
    }

    @Override
    public void initializeFirebaseAuthStateChangedListener() {

    }

    @Override
    public void setOnFirebaseAuthStateChangedListener() {
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        } else {
            initializeFirebaseAuthStateChangedListener();
        }
    }

    @Override
    public void removeOnFirebaseAuthStateChangedListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onUserSignedOut() {
        // TODO Implement
    }

    public void writeUserToDatabaseIfNotExists(@NonNull final FirebaseUser user) {
        if (dbRef == null) {
            dbRef = FirebaseDatabase.getInstance().getReference();
        }

        final DatabaseReference userRef = dbRef.child("users").child(user.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User result = dataSnapshot.getValue(User.class);
                if (result == null) { // New user, add them to the database
                    Log.d(TAG, "User does not exist, add them to the database");
                    initializeFirstTimeUserDatabaseObjects(user);
                } else { //Existing user
                    Log.d(TAG, result.toString());
                    presenter.onUserSignedIn(result);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeFirstTimeUserDatabaseObjects(final FirebaseUser firebaseUser) {
        //Google Volley
        Context context = view.getApplicationContext();
        String firebaseFunctionUrl = VitalariumConstants.BASE_FIREBASE_FUNCTION_URL + "/initializeUserObjectsInDb";

        JSONObject jsonObjLeft = new JSONObject();
        JSONObject jsonObjRight = new JSONObject();
        try {
            jsonObjRight.put("uid", firebaseUser.getUid());
            jsonObjRight.put("displayName", firebaseUser.getDisplayName());
            jsonObjRight.put("email", firebaseUser.getEmail());
            jsonObjRight.put("photoURL", firebaseUser.getPhotoUrl());
            jsonObjLeft.put("user", jsonObjRight.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, jsonObjLeft.toString());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, firebaseFunctionUrl,
                jsonObjLeft, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    User user = new Gson().fromJson(String.valueOf(response.getJSONObject("user")), User.class);
                    Log.d(TAG, user.toString());
                    presenter.onUserSignedIn(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d(TAG, error.toString());
            }
        });

        NetworkRequestSingleton.getInstance(context).addToRequestQueue(jsObjRequest);

        /**
         * Request/response be handled above via Google Volley through Firebase functions as above,
         * rather than calling Firebase from the UI thread as below
         */

//        final DatabaseReference firstSprintRef = dbRef.child("sprints").push();
//        final DatabaseReference secondSprintRef = dbRef.child("sprints").push();
//        final DatabaseReference projectRef = dbRef.child("projects").push();
//
//        long now = System.currentTimeMillis();
//        long fourteenDaysInMilliseconds = 1000 * 60 * 60 * 24 * 14;
//        final Sprint firstSprint = new Sprint.SprintBuilder()
//                .setId(firstSprintRef.getKey())
//                .setStartDate(System.currentTimeMillis())
//                .setEndDate(now + fourteenDaysInMilliseconds)
//                .build();
//        final Sprint secondSprint = new Sprint.SprintBuilder()
//                .setId(secondSprintRef.getKey())
//                .setStartDate(now + fourteenDaysInMilliseconds)
//                .setEndDate(now + fourteenDaysInMilliseconds + fourteenDaysInMilliseconds)
//                .build();
//        final Project project = new Project.ProjectBuilder()
//                .setId(projectRef.getKey())
//                .setSprints(Arrays.asList(firstSprintRef.getKey(), secondSprintRef.getKey()))
//                .setTimezone(TimeZone.getDefault().getID())
//                .build();
//        final User newUser = new User.UserBuilder()
//                .setId(user.getUid())
//                .setDisplayName(user.getDisplayName())
//                .setEmail(user.getEmail())
//                .setPhotoUrl(user.getPhotoUrl().toString())
//                .setProviderId(user.getProviders().get(0))
//                .setProjects(Arrays.asList(projectRef.getKey()))
//                .build();
//
//        dbRef.child("sprints").child(firstSprintRef.getKey()).setValue(firstSprint, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                dbRef.child("sprints").child(secondSprintRef.getKey()).setValue(secondSprint, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        dbRef.child("projects").child(projectRef.getKey()).setValue(project, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                dbRef.child("users").child(newUser.getId()).setValue(newUser, new DatabaseReference.CompletionListener() {
//                                    @Override
//                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                        presenter.onUserSignedIn(newUser);
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }
}


