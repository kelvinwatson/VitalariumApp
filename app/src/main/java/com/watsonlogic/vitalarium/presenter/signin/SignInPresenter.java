package com.watsonlogic.vitalarium.presenter.signin;

import com.watsonlogic.vitalarium.model.signin.SignInDataActions;
import com.watsonlogic.vitalarium.model.signin.SignInModel;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.view.signin.SignInActivity;

/**
 * {@link SignInCoordinatorActions implementation}
 */
public class SignInPresenter implements SignInCoordinatorActions {
    private final SignInActivity view;
    private final SignInDataActions model;

    public SignInPresenter(SignInActivity view) {
        this.view = view;
        this.model = new SignInModel(view);
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
    public void onUserSignedIn(User user) {
        view.startDashboardActivity(user);
    }

    @Override
    public void onUserSignedOut() {
        model.onUserSignedOut();
    }
}
