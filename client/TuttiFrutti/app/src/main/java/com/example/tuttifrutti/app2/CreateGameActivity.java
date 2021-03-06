package com.example.tuttifrutti.app2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;


public class CreateGameActivity extends ActionBarActivity {

    public static final String MODE_EXTRA_MESSAGE = "MODE_EXTRA_MESSAGE";
    public static final String OPPONENTS_EXTRA_MESSAGE = "OPPONENTS_EXTRA_MESSAGE";
    public static final String CATEGORIES_EXTRA_MESSAGE = "CATEGORIES_EXTRA_MESSAGE";
    private AbstractWheel hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Configuración");
        setContentView(R.layout.activity_create_game);

       hours  = (AbstractWheel) findViewById(R.id.hour_horizontal);



        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(this, 1, 20, "%02d");
        hourAdapter.setItemResource(R.layout.wheel_text_centered);
        hourAdapter.setItemTextResource(R.id.text);
        hours.setViewAdapter(hourAdapter);
        hours.setVisibleItems(3);
        hours.setCurrentItem(10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_game, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_showRulesFromCreateGame) {
            Intent i = new Intent(getApplicationContext(), ShowGameRulesActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void createGame(View view) {

        boolean mode = ((Switch) findViewById(R.id.modeSelector)).isChecked(); //true:online, false:offline
        boolean opponents = ((Switch) findViewById(R.id.opponentsSelector)).isChecked(); //true:aleatorio, false:con amigos
        boolean categories = ((Switch) findViewById(R.id.categoriesSelector)).isChecked(); //true:controladas, false:libres
        int rounds=hours.getCurrentItem()+1;

        Game gs = new Game();
        gs.setSettings(mode, categories, opponents, rounds);

        Intent intent;
        if (opponents)
            intent = new Intent(getApplicationContext(), ChooseRandomPlayersCountActivity.class);
        else
            intent = new Intent(getApplicationContext(), ChooseFriendsActivity.class);

        intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gs);
        startActivity(intent);
    }



}
