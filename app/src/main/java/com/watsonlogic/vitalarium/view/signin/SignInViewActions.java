package com.watsonlogic.vitalarium.view.signin;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;
import com.watsonlogic.vitalarium.model.user.User;

public interface SignInViewActions {
    void startFirebaseAuthActivity(Intent intent);

    void startDashboardActivity(User user);
}

