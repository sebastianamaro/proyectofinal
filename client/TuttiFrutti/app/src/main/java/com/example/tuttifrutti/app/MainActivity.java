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

import com.example.TuttiFruttiCore.PlayServicesHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends ActionBarActivity {
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public final static String GAME_ID_EXTRA_MESSAGE = "com.example.tuttifrutti.GAMEID";
    public final static String ROUND_ID_EXTRA_MESSAGE = "com.example.tuttifrutti.ROUNDID";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    String SENDER_ID = "630267112121";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCMDemo";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchRound(View view) {

        Intent intent = new Intent(getApplicationContext(), PlayRoundActivity.class);
        // aca en algun lado deberia saber el ID de la partida
        intent.putExtra(GAME_ID_EXTRA_MESSAGE, 1);

        startActivity(intent);
    }

    public void showRoundResult(View view) {

        Intent intent = new Intent(getApplicationContext(), ShowRoundResultActivity.class);
        intent.putExtra(MainActivity.GAME_ID_EXTRA_MESSAGE, 1);
        intent.getIntExtra(MainActivity.ROUND_ID_EXTRA_MESSAGE, 1);

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
