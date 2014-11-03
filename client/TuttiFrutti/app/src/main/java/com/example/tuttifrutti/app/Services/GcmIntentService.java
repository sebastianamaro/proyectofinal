package com.example.tuttifrutti.app.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.BroadcastReceivers.GcmBroadcastReceiver;
import com.example.tuttifrutti.app.Classes.GameAndPlayerNotificationData;
import com.example.tuttifrutti.app.Classes.GameNotificationData;
import com.example.tuttifrutti.app.Classes.GenericNotificationData;
import com.example.tuttifrutti.app.PlayRoundActivity;
import com.example.tuttifrutti.app.ShowGameDetailsActivity;
import com.example.tuttifrutti.app.ShowRoundResultActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.example.tuttifrutti.app.R;


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
            String dataJson = extras.getString("data");
            GenericNotificationData notificationData = new Gson().fromJson(dataJson, GenericNotificationData.class);
            Intent i;
            switch (notificationData.getMessage_type()){

                case Constants.INVITATION:
                    GameAndPlayerNotificationData invitationData =
                            new Gson().fromJson(dataJson, GameAndPlayerNotificationData.class);
                    i =  new Intent(this, ShowGameDetailsActivity.class);
                    i.putExtra(Constants.GAME_INFO_EXTRA_MESSAGE, new FullGame(invitationData.getGame_id()));
                    this.showNotification(notificationData.getMessage_type(),invitationData.getPlayer()+" te invitó a una partida", "Toca para ver la invitación", i);
                    break;
                case Constants.ROUND_ENABLED:
                    GameNotificationData roundEnabledData =
                            new Gson().fromJson(dataJson, GameNotificationData.class);
                    i =  new Intent(this, ShowRoundResultActivity.class);
                    i.putExtra(Constants.GAME_INFO_EXTRA_MESSAGE, new UserGame(roundEnabledData.getGame_id()));
                    this.showNotification(notificationData.getMessage_type(),"La siguiente ronda ya está disponible para jugar", "Toca para ingresar a jugar", i);
                    break;
                case Constants.ROUND_STARTED:
                    GameNotificationData roundStartedData =
                            new Gson().fromJson(dataJson, GameNotificationData.class);
                    i =  new Intent(this, PlayRoundActivity.class);
                    i.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, roundStartedData.getGame_id());
                    this.showNotification(notificationData.getMessage_type(),"La ronda ya comenzó!", "Toca para ingresar a jugar", i);
                    break;
                case Constants.ROUND_CLOSED:
                    GameAndPlayerNotificationData roundClosedNotificationData =
                            new Gson().fromJson(dataJson, GameAndPlayerNotificationData.class);
                    intentActivity.putExtra(Constants.ROUND_CLOSED_NOTIFICATION_DATA, roundClosedNotificationData );
                    intentActivity.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, roundClosedNotificationData.getGame_id());
                    sendBroadcast(intentActivity);
                    break;
            };
        }
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void showNotification(int id, String title, String message, Intent i)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contIntent =
                PendingIntent.getActivity(getApplicationContext(),1000,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(contIntent)
                        .setAutoCancel(true);
        mNotificationManager.notify(id, mBuilder.build());

    }
}
