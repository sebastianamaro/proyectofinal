package com.example.tuttifrutti.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.PlayServicesHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends ActionBarActivity {

    TextView mDisplay;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlayServicesHelper helper = new PlayServicesHelper(MainActivity.class.getSimpleName());
        String regid = "";
        if (helper.checkPlayServices(this))
        {
            regid = helper.getRegistrationId(getApplicationContext());
            if (regid == "")
                helper.registerGCMInBackground(getApplicationContext());

        }
    }

    public void launchRound(View view) {

        Intent intent = new Intent(getApplicationContext(), PlayRoundActivity.class);
        // aca en algun lado deberia saber el ID de la partida
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, 1);

        startActivity(intent);
    }

    public void showRoundResult(View view) {

        Intent intent = new Intent(getApplicationContext(), ShowRoundResultActivity.class);
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, 1);
        intent.getIntExtra(Constants.ROUND_ID_EXTRA_MESSAGE, 1);

        startActivity(intent);
    }

    public void viewCategories(View view) {

        Intent intent = new Intent(getApplicationContext(), ViewCategoriesActivity.class);

        startActivity(intent);
    }

    public void showGameResult(View view) {

        Intent intent = new Intent(getApplicationContext(), ShowGameResultActivity.class);

        startActivity(intent);
    }

    public void createGame(View view) {

        Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);

        startActivity(intent);
    }


    public void viewGames(View view) {

        Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
        startActivity(intent);
    }

}
