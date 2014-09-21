package com.example.tuttifrutti.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;

public class ChooseRandomPlayersCountActivity extends ActionBarActivity {

    public Game gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_choose_random_players_count);

        Intent intent = getIntent();

        gameSettings = (Game)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

        NumberPicker np = (NumberPicker)findViewById(R.id.randomPlayersCount);
        np.setMaxValue(4);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
    }

    public void createGame(View view) {
        NumberPicker np = (NumberPicker)findViewById(R.id.randomPlayersCount);

        gameSettings.setRandomPlayersCount(np.getValue());

        if (gameSettings.getCategoriesType().equals("FIXED")) {
            Intent intent = new Intent(getApplicationContext(), ChooseControlledCategoriesActivity.class);
            intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gameSettings);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), ViewCategoriesActivity.class);
            intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gameSettings);
            startActivity(intent);
        }
    }
}
