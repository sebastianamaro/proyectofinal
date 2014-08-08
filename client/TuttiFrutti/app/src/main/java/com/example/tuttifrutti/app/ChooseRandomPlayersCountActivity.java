package com.example.tuttifrutti.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Game;
import com.example.tuttifrutti.app.Classes.PlayServicesHelper;

public class ChooseRandomPlayersCountActivity extends ActionBarActivity {

    public Game gameSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_random_players_count);

        Intent intent = getIntent();

        boolean mode = intent.getBooleanExtra(CreateGameActivity.MODE_EXTRA_MESSAGE, false);
        boolean oponents = intent.getBooleanExtra(CreateGameActivity.OPONENTS_EXTRA_MESSAGE, false);
        boolean categories = intent.getBooleanExtra(CreateGameActivity.CATEGORIES_EXTRA_MESSAGE, false);

        String modeString;
        String oponentsString;
        String categoriesString;

        if(mode)
            modeString="ONLINE";
        else
            modeString="OFFLINE";

        if(oponents)
            oponentsString="RANDOM";
        else
            oponentsString="FRIENDS";

        if(categories)
            categoriesString="FIXED";
        else
            categoriesString="FREE";

        gameSettings = new Game(modeString, oponentsString, categoriesString,1);

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
        CreateGameTask task = new CreateGameTask();
        task.execute(gameSettings);
    }

    //todo: meter esto en una clase
    private class CreateGameTask extends AsyncTask<Game,Void, Void> {

        AlertDialog ad;
        TuttiFruttiAPI api;

        @Override
        protected Void doInBackground(Game... settings) {

            Game gs=settings[0];
            TuttiFruttiAPI api= new TuttiFruttiAPI(getString(R.string.server_url));

            PlayServicesHelper helper = new PlayServicesHelper();
            String regid = "";
            if (helper.checkPlayServices(ChooseRandomPlayersCountActivity.this))
            {
                regid = helper.getRegistrationId(getApplicationContext());
                if (regid == "")
                    helper.registerGCMInBackground(getApplicationContext());
            }


            api.createGame(gs.getMode(),gs.getOpponentsType(),gs.getCategoriesType(),gs.getRandomPlayersCount(),regid);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ad.setCancelable(false); // This blocks the 'BACK' button
            ad.setMessage("Se ha creado la partida!");
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            ad.show();

        }

        @Override
        protected void onPreExecute(){
            ad=new AlertDialog.Builder(ChooseRandomPlayersCountActivity.this).create();

            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }

}
