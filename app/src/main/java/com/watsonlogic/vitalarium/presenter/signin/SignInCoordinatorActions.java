package com.watsonlogic.vitalarium.presenter.signin;

import com.watsonlogic.vitalarium.model.user.User;

public interface SignInCoordinatorActions {
    void prepareAndStartFirebaseAuthSignInActivity();

    void initializeFirebaseAuthStateChangedListener();

    void setOnFirebaseAuthStateChangedListener();

    void removeOnFirebaseAuthStateChangedListener();

    void onUserSignedIn(User user);

    void onUserSignedOut();
}
