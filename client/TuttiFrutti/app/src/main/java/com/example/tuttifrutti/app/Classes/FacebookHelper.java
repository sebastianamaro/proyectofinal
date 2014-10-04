package com.example.tuttifrutti.app.Classes;

import android.content.Context;
import android.content.Intent;

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

    public static String getUserId(){
        Session session = Session.getActiveSession();

        Request request = Request.newGraphPathRequest(session, "me", null);
        com.facebook.Response response = Request.executeAndWait(request);

        GraphUser user = response.getGraphObjectAs(GraphUser.class);
        return user.getId();
    }
}
