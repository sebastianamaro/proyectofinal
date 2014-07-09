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

import com.example.tuttifrutti.app.BroadcastReceivers.GcmBroadcastReceiver;
import com.example.tuttifrutti.app.MainActivity;
import com.example.tuttifrutti.app.PlayRoundActivity;
import com.example.tuttifrutti.app.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

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
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendBroadcast(new Intent("gcmLocalReceiver"));
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

        Intent notIntent =  new Intent(this, MainActivity.class);
        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
