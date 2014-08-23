package com.example.facebookconnect.facebookconnect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;


public class AndroidFacebookConnectActivity extends Activity {
    private String TAG = "MainActivity";

    private TextView lblEmail;
    private UiLifecycleHelper uiHelper;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (session.isOpened()) {
                Log.i(TAG, "Access Token" + session.getAccessToken());
                Request.newMeRequest(session,
                        new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    Log.i(TAG, "User ID " + user.getId());
                                    Log.i(TAG, "Email " + user.asMap().get("email"));
                                    lblEmail.setText(user.asMap().get("email").toString());
                                }
                            }
                        }
                ).executeAsync();
            }
        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_connect);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {

            //todo: llamar a la main activity
        }
        else {
            lblEmail = (TextView) findViewById(R.id.lblEmail);

            LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
            // set permission list, Don't foeget to add email
            authButton.setReadPermissions(Arrays.asList("public_profile", "email"));
            authButton.setSessionStatusCallback(callback);
            //Session.openActiveSession(this, true, callback);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void getHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.facebookconnect.facebookconnect",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", hash);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
