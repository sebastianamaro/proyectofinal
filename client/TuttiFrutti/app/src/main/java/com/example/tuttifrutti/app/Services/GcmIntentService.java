package com.example.tuttifrutti.app.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.TuttiFruttiCore.Constants;
import com.example.tuttifrutti.app.BroadcastReceivers.GcmBroadcastReceiver;
import com.example.tuttifrutti.app.Classes.StopNotificationData;
import com.example.tuttifrutti.app.PlayRoundActivity;
import com.example.tuttifrutti.app.R;
import com.example.tuttifrutti.app.ViewGameStatusActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Iterator;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentActivity = new Intent("gcmLocalReceiver");
        Bundle extras = intent.getExtras();
        if (extras.containsKey("data")){
            String dataJson;
            dataJson = extras.getString("data");
            StopNotificationData stopNotificationData = new Gson().fromJson(dataJson, StopNotificationData.class);
            intentActivity.putExtra(Constants.STOP_NOTIFICATION_DATA, stopNotificationData);
        }
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);


        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                //todo: sacar el gameId de lo q me manda el server
                intentActivity.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, 1);
                sendBroadcast(intentActivity);
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void mostrarNotification(String msg)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("Notificación GCM")
                        .setContentText(msg);

        Intent notIntent =  new Intent(this, ViewGameStatusActivity.class);
        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
