package com.watsonlogic.vitalarium.view.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.watsonlogic.vitalarium.R;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.presenter.signin.SignInPresenter;
import com.watsonlogic.vitalarium.view.dashboard.DashboardActivity;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements SignInViewActions {
    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "DashboardActivity";
    public static final String EXTRA_VITALARIUM_USER = "VITALARIUM_USER";
    private SignInPresenter presenter;

    private static final List<AuthUI.IdpConfig> authProviders = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(SignInActivity.this);
        presenter.initializeFirebaseAuthStateChangedListener();
        presenter.prepareAndStartFirebaseAuthSignInActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setOnFirebaseAuthStateChangedListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.removeOnFirebaseAuthStateChangedListener();
    }

    @Override
    public void startFirebaseAuthActivity(Intent intent) {
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void startDashboardActivity(User user) {
        Intent loggedInIntent = new Intent(SignInActivity.this, DashboardActivity.class);
        loggedInIntent.putExtra(EXTRA_VITALARIUM_USER, user);
        startActivity(loggedInIntent);
        finish();
    }
}
