package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.PlayServicesHelper;
import com.example.TuttiFruttiCore.Player;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;


public class AndroidFacebookConnectActivity extends Activity {
    private TextView lblEmail;
    private UiLifecycleHelper uiHelper;
    String regid = "";

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (session.isOpened()) {
                Request.newMeRequest(session,
                        new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                if (user != null) {
                                    PlayServicesHelper helper = new PlayServicesHelper(AndroidFacebookConnectActivity.class.getSimpleName());
                                    regid = helper.getRegistrationId(getApplicationContext());

                                    if (regid != null && !regid.isEmpty()) {
                                        Player newPlayer = new Player();
                                        newPlayer.setEmail(user.asMap().get("email").toString());
                                        newPlayer.setName(user.getName());
                                        newPlayer.setFbId(user.getId());
                                        newPlayer.setRegistrationId(regid);

                                        FacebookHelper.storeFbId(getApplicationContext(), user.getId());

                                        TuttiFruttiAPI api = new TuttiFruttiAPI(getString(R.string.server_url));
                                        api.AddPlayer(newPlayer);

                                        Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
                                        startActivity(intent);
                                    }else
                                        Toast.makeText(getApplicationContext(),
                                                "EL REGISTRATION ID ES NULL",
                                                Toast.LENGTH_LONG).show();
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

        setTitle("");
        setContentView(R.layout.activity_facebook_connect);

        PlayServicesHelper helper = new PlayServicesHelper(AndroidFacebookConnectActivity.class.getSimpleName());
        if (helper.checkPlayServices(AndroidFacebookConnectActivity.this))
        {
            regid = helper.getRegistrationId(getApplicationContext());
            if (regid.isEmpty())
                helper.registerGCMInBackground(getApplicationContext());
        }

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            Log.e("sessionToken", session.getAccessToken());
            Log.e("sessionTokenDueDate", session.getExpirationDate().toLocaleString());
            Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
            startActivity(intent);
        }
        else {
            LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
            // set permission list, Don't foeget to add email
            authButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends", ""));
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
                    "com.example.tuttifrutti.app",
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
