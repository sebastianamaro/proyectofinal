package com.example.tuttifrutti.app2.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app2.BroadcastReceivers.GcmBroadcastReceiver;
import com.example.tuttifrutti.app2.Classes.GameAndPlayerNotificationData;
import com.example.tuttifrutti.app2.Classes.GameNotificationData;
import com.example.tuttifrutti.app2.Classes.GenericNotificationData;
import com.example.tuttifrutti.app2.PlayRoundActivity;
import com.example.tuttifrutti.app2.ShowGameDetailsActivity;
import com.example.tuttifrutti.app2.ShowGameResultActivity;
import com.example.tuttifrutti.app2.ShowRoundResultActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.example.tuttifrutti.app2.R;


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
                case Constants.FIRST_ROUND_ENABLED:
                    GameNotificationData firstRoundEnabledData =
                            new Gson().fromJson(dataJson, GameNotificationData.class);
                    i =  new Intent(this, ShowGameDetailsActivity.class);
                    i.putExtra(Constants.GAME_INFO_EXTRA_MESSAGE, new UserGame(firstRoundEnabledData.getGame_id()));
                    this.showNotification(notificationData.getMessage_type(),"La partida ya está disponible para jugar", "Toca para ingresar a jugar", i);
                    break;
                case Constants.GAME_FINISHED:
                    GameNotificationData gameFinishedData =
                            new Gson().fromJson(dataJson, GameNotificationData.class);
                    i =  new Intent(this, ShowGameResultActivity.class);
                    i.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, gameFinishedData.getGame_id());
                    this.showNotification(notificationData.getMessage_type(),"La partida ha finalizado", "Toca para ver los resultados", i);
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

        mBuilder.setLights(0xff00ff00,300,1000);
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        mNotificationManager.notify(id, mBuilder.build());

    }
}
