package tek.gezacsorba.rssreader;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geza on 11/8/17.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    FirebaseAuth auth;
    CallbackManager callbackManager;
    LoginContract.LoginView view;

    public LoginPresenter(FirebaseAuth auth, CallbackManager callbackManager, LoginContract.LoginView view) {
        this.auth = auth;
        this.callbackManager = callbackManager;
        this.view = view;
    }

    private void handleFacebookAccessToken(AppCompatActivity activity, AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        String userId = FirebaseAuth.getInstance().getUid();
                        FirebaseDatabase.getInstance().getReference("users").setValue(new ArrayList<>());
                        if (userId != null) {
                            FirebaseDatabase.getInstance().getReference("users").runTransaction(new Transaction.Handler() {
                                @Override
                                public Transaction.Result doTransaction(MutableData mutableData) {
                                    List<String> users = mutableData.getValue(new GenericTypeIndicator<List<String>>() {
                                    });
                                    if (users == null) users = new ArrayList<>();
                                    users.add(FirebaseAuth.getInstance().getUid());
                                    mutableData.setValue(users);
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                    if (databaseError != null) {
                                        Snackbar.make(activity.getWindow().getDecorView(), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        navigateToFeedActivity(activity);
                                    }
                                }
                            });
                        }
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(activity.getWindow().getDecorView(), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    void navigateToFeedActivity(AppCompatActivity context) {
        view.hideLoading();
        context.startActivity(new Intent(context, FeedActivity.class));
        context.finish();
    }

    @Override
    public void handleAuthResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setupFacebookLogin(LoginButton loginButton) {
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                view.showLoading();
                loginButton.setVisibility(View.GONE);
                handleFacebookAccessToken((AppCompatActivity) loginButton.getContext(),loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                view.showError(error);
            }
        });
    }

    @Override
    public void onCreate(AppCompatActivity context) {
        if (auth.getCurrentUser() != null) {
            navigateToFeedActivity(context);
        }
    }
}
