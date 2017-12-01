package com.watsonlogic.vitalarium.presenter.signin;

public interface SignInActions {
    void prepareAndStartFirebaseAuthSignInActivity();

    void initializeFirebaseAuthStateChangedListener();

    void setOnFirebaseAuthStateChangedListener();

    void removeOnFirebaseAuthStateChangedListener();

    void onUserSignedIn();

    void onUserSignedOut();
}
