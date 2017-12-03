package com.watsonlogic.vitalarium.model.signin;

import android.content.Intent;

import com.watsonlogic.vitalarium.presenter.signin.SignInCoordinatorActions;
import com.watsonlogic.vitalarium.presenter.signin.SignInPresenter;

public interface SignInDataActions {
    void addObserver(SignInCoordinatorActions presenter);
    void initializeFirebaseAuthStateChangedListener();
    void setOnFirebaseAuthStateChangedListener();
    Intent createFirebaseAuthIntent();
    void removeOnFirebaseAuthStateChangedListener();
    void onUserSignedOut();

    void initializeFirebaseAuthStateChangedListener(SignInPresenter presenter);
}
