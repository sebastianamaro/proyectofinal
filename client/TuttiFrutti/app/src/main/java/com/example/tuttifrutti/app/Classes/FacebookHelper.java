package com.example.tuttifrutti.app.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.TuttiFruttiCore.Constants;
import com.example.tuttifrutti.app.AndroidFacebookConnectActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;

/**
 * Created by Nituguivi on 24/08/2014.
 */
public class FacebookHelper {
    public static boolean isSessionOpened()
    {
        Session session = Session.getActiveSession();
        return session != null && session.isOpened();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREFS_FBID, Context.MODE_PRIVATE);
    }

    public static void storeFbId(Context context, String fbId) {
        final SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREFS_FBID, fbId);
        editor.commit();
    }

    public static String getUserId(Context context){
        final SharedPreferences prefs = getPreferences(context);
        String fbId = prefs.getString(Constants.PREFS_FBID, "");

        if (fbId.isEmpty()) {
            Session session = Session.getActiveSession();

            Request request = Request.newGraphPathRequest(session, "me", null);
            com.facebook.Response response = Request.executeAndWait(request);

            GraphUser user = response.getGraphObjectAs(GraphUser.class);
            String userId =user.getId();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constants.PREFS_FBID, userId);
            editor.commit();
            return userId;
        }else
            return fbId;
    }
}
