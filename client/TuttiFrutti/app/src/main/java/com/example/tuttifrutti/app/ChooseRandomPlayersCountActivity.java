package com.example.tuttifrutti.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import com.example.TuttiFruttiCore.Game;

public class ChooseRandomPlayersCountActivity extends ActionBarActivity {

    public Game gameSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_random_players_count);

        Intent intent = getIntent();

        gameSettings = (Game)intent.getSerializableExtra("gameSettings");

        NumberPicker np = (NumberPicker)findViewById(R.id.randomPlayersCount);
        np.setMaxValue(4);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_random_players_count, menu);

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
        NumberPicker np = (NumberPicker)findViewById(R.id.randomPlayersCount);

        gameSettings.setRandomPlayersCount(np.getValue());

        if (gameSettings.getCategoriesType().equals("FIXED")) {
            Intent intent = new Intent(getApplicationContext(), ChooseControlledCategoriesActivity.class);
            intent.putExtra("gameSettings", gameSettings);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), ViewCategoriesActivity.class);
            intent.putExtra("gameSettings", gameSettings);
            startActivity(intent);
        }
    }
}
