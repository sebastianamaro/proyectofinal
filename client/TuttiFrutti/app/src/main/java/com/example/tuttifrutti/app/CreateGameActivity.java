package com.example.tuttifrutti.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.PlayServicesHelper;
import com.example.tuttifrutti.app.Classes.FacebookHelper;


public class CreateGameActivity extends ActionBarActivity {

    public static final String MODE_EXTRA_MESSAGE = "MODE_EXTRA_MESSAGE";
    public static final String OPONENTS_EXTRA_MESSAGE = "OPONENTS_EXTRA_MESSAGE";
    public static final String CATEGORIES_EXTRA_MESSAGE = "CATEGORIES_EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_game, menu);
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

    public void createGame(View view) {

        boolean mode = ((Switch) findViewById(R.id.modeSelector)).isChecked(); //true:online, false:offline
        boolean oponents = ((Switch) findViewById(R.id.oponentsSelector)).isChecked(); //true:aleatorio, false:con amigos
        boolean categories = ((Switch) findViewById(R.id.categoriesSelector)).isChecked(); //true:controladas, false:libres

        Game gs = new Game();
        gs.setSettings(mode, categories, oponents);

        Intent intent;
        if (oponents)
            intent = new Intent(getApplicationContext(), ChooseRandomPlayersCountActivity.class);
        else
            intent = new Intent(getApplicationContext(), ChooseFriendsActivity.class);

        intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gs);
        startActivity(intent);
    }



}
