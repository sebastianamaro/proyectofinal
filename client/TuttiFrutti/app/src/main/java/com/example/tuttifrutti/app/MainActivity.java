package com.example.tuttifrutti.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    public final static String GAME_ID_EXTRA_MESSAGE = "com.example.tuttifrutti.GAMEID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void createGame(View view) {

        Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);

        startActivity(intent);
    }

}
