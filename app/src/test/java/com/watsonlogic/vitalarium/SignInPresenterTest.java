package com.watsonlogic.vitalarium;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Preconditions;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.watsonlogic.vitalarium.model.signin.SignInModel;
import com.watsonlogic.vitalarium.model.user.User;
import com.watsonlogic.vitalarium.presenter.signin.SignInPresenter;
import com.watsonlogic.vitalarium.view.signin.SignInActivity;
import com.watsonlogic.vitalarium.view.signin.SignInViewActions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

import static org.mockito.Mockito.mock;

public class SignInPresenterTest {
    @Tested
    SignInPresenter signInPresenter; // UUT

    @Injectable
    SignInActivity view;
    @Injectable
    SignInModel model;
    @Mocked
    FirebaseApp firebaseApp;
    @Mocked
    android.util.Log log;
    @Mocked
    FirebaseAuth auth;

    @Before
    public void setup() {
        new MockUp<AuthUI>() {
        };
        new MockUp<FirebaseApp>() {
        };
        new MockUp<FirebaseAuth>() {
            @Mock
            public FirebaseAuth getInstance() {
                return auth;
            }
        };
        new MockUp<Preconditions>() {
            @Mock
            public int checkValidStyle(
                    Context context,
                    int styleId,
                    String errorMessageTemplate,
                    Object... errorMessageArguments) {
                return 0;
            }
        };
        model = new MockUp<SignInModel>() {
            @Mock
            public Intent createFirebaseAuthIntent() {
                return mock(Intent.class);
            }
        }.getMockInstance();

        view = mock(SignInActivity.class);
        signInPresenter = new SignInPresenter(view);
    }


    @Test
    public void testPrepareAndStartFirebaseAuthSignInActivity() throws Exception {
        final Intent intent = new MockUp<Intent>() {
        }.getMockInstance();
        final SignInActivity view = mock(SignInActivity.class);
        final SignInPresenter signInPresenter = new SignInPresenter(view);
        signInPresenter.prepareAndStartFirebaseAuthSignInActivity();
        new Verifications() {{
            model.createFirebaseAuthIntent();
            times = 1;
            view.startFirebaseAuthActivity(intent);
            times = 1;
        }};
    }

    @Test
    public void testInitializeFirebaseAuthStateChangedListener() {
        new MockUp<Intent>() {
        };
        final SignInActivity view = new MockUp<SignInActivity>() {
        }.getMockInstance();
        final SignInModel model = new MockUp<SignInModel>() {
        }.getMockInstance();
        final SignInPresenter signInPresenter = new SignInPresenter(view);
        new Expectations() {{
            model.initializeFirebaseAuthStateChangedListener(signInPresenter);
        }};
        signInPresenter.initializeFirebaseAuthStateChangedListener();
    }

    @Test
    public void testSetOnFirebaseAuthStateChangedListener() {
        new Expectations() {{
            model.addObserver(Matchers.any(SignInPresenter.class));
        }};
        final SignInPresenter signInPresenter = new SignInPresenter(view);
        signInPresenter.setOnFirebaseAuthStateChangedListener();
    }

    @Test
    public void testRemoveOnFirebaseAuthStateChangedListener() {
        final SignInModel model = new MockUp<SignInModel>() {
        }.getMockInstance();
        new Verifications() {{
            model.removeOnFirebaseAuthStateChangedListener();
            times = 1;
        }};
    }

    @Test
    public void testOnUserSignedIn() {
        final SignInActivity view = new MockUp<SignInActivity>() {
        }.getMockInstance();
        final SignInModel model = new MockUp<SignInModel>() {
        }.getMockInstance();
        SignInPresenter signInPresenter = new SignInPresenter(view);
        final User user = mock(User.class);
        signInPresenter.onUserSignedIn(user);
        new Verifications() {{
            view.startDashboardActivity(user);
            times = 1;
        }};
    }

    @Test
    public void testOnUserSignedOut() {
        final SignInActivity view = new MockUp<SignInActivity>() {
        }.getMockInstance();
        final SignInModel model = new MockUp<SignInModel>() {
        }.getMockInstance();
        SignInPresenter signInPresenter = new SignInPresenter(view);
        signInPresenter.onUserSignedOut();
        new Verifications() {{
            model.onUserSignedOut();
            times = 1;
        }};
    }
}