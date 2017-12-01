package com.watsonlogic.vitalarium.view.signin;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

public interface SignInViewActions {
    void startFirebaseAuthActivity(Intent intent);

    void startMainActivity();
}

