package com.watsonlogic.vitalarium.presenter.signin;

import android.content.Intent;

import com.watsonlogic.vitalarium.model.signin.SignInDataActions;
import com.watsonlogic.vitalarium.model.signin.SignInModel;
import com.watsonlogic.vitalarium.view.signin.SignInViewActions;

public class SignInPresenter implements SignInActions {
    private final SignInViewActions view;
    private final SignInDataActions model;

    public SignInPresenter(SignInViewActions view) {
        this.view = view;
        this.model = new SignInModel();
        model.addObserver(this);
    }

    @Override
    public void prepareAndStartFirebaseAuthSignInActivity() {
        view.startFirebaseAuthActivity(model.createFirebaseAuthIntent());
    }

    @Override
    public void initializeFirebaseAuthStateChangedListener() {
        model.initializeFirebaseAuthStateChangedListener(this);
    }

    @Override
    public void setOnFirebaseAuthStateChangedListener() {
        model.setOnFirebaseAuthStateChangedListener();
    }

    @Override
    public void removeOnFirebaseAuthStateChangedListener() {
        model.removeOnFirebaseAuthStateChangedListener();
    }

    @Override
    public void onUserSignedIn() {
        view.startMainActivity();
    }

    @Override
    public void onUserSignedOut() {
        model.onUserSignedOut();
    }
}
