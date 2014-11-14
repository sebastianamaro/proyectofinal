package com.example.tuttifrutti.app2.Classes;

import android.content.Context;
import android.content.Intent;

import com.example.tuttifrutti.app2.AndroidFacebookConnectActivity;

/**
 * Created by Nituguivi on 26/09/2014.
 */
public class UncaughtExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
    Context currentContext;

    public UncaughtExceptionHandler(Context c)
    {
        this.currentContext = c;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (throwable instanceof NoLoggedUserException)
        {
            Intent i = new Intent(currentContext, AndroidFacebookConnectActivity.class);
            this.currentContext.startActivity(i);
        }
    }
}
