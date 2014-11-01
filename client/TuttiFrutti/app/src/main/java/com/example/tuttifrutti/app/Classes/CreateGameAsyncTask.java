package com.example.tuttifrutti.app.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.tuttifrutti.app.R;

import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.web.client.ResourceAccessException;

import java.util.concurrent.TimeoutException;

/**
 * Created by Sebastian on 13/09/2014.
*/

//todo: meter esto en una clase
public abstract class CreateGameAsyncTask extends AsyncTask<Game,Void, Void> {

    protected AlertDialog ad;
    TuttiFruttiAPI api;
    boolean hasError;
    Activity currentActivity;

    public CreateGameAsyncTask(String serverUrl, Activity currentActivity)
    {
        api=new TuttiFruttiAPI(serverUrl);
        ad=new AlertDialog.Builder(currentActivity).create();
    }

    @Override
    protected Void doInBackground(Game... settings) {

        try {
            Game gs = settings[0];
            api.createGame(gs);
        }catch (ResourceAccessException ex)
        {
            Toast.makeText(currentActivity, "En este momento no tenes conexión a Internet. Intentalo mas tarde!", 20);
        }
        return null;
    }

}