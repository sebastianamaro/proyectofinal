package com.example.tuttifrutti.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.tuttifrutti.app.Classes.GameSettings;
import com.example.tuttifrutti.app.R;

public class ChooseRandomPlayersCountActivity extends ActionBarActivity {

    public GameSettings gameSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_random_players_count);

        Intent intent = getIntent();

        boolean mode = intent.getBooleanExtra(CreateGameActivity.MODE_EXTRA_MESSAGE, false);
        boolean oponents = intent.getBooleanExtra(CreateGameActivity.OPONENTS_EXTRA_MESSAGE, false);
        boolean categories = intent.getBooleanExtra(CreateGameActivity.CATEGORIES_EXTRA_MESSAGE, false);

        gameSettings = new GameSettings(mode, oponents, categories);

        EditText editText = (EditText)findViewById(R.id.randomPlayersCount);
        editText.setText("3");
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
}
