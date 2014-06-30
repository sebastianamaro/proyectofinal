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
import android.widget.Switch;

import com.example.TuttiFruttiAPI;
import com.example.tuttifrutti.app.Classes.PlayServicesHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.example.tuttifrutti.app.Classes.GameSettings;


public class CreateGameActivity extends ActionBarActivity {

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

        boolean mode = ((Switch)findViewById(R.id.modeSelector)).isChecked(); //true:online, false:offline
        boolean oponents = ((Switch)findViewById(R.id.oponentsSelector)).isChecked(); //true:aleatorio, false:con amigos
        boolean categories = ((Switch)findViewById(R.id.categoriesSelector)).isChecked(); //true:controladas, false:libres
        GameSettings gs= new GameSettings(mode,oponents,categories);
        CreateGameTask task= new CreateGameTask();
        task.execute(gs);


    }

    private class CreateGameTask extends AsyncTask<GameSettings,Void, Void>{

        AlertDialog ad;
        TuttiFruttiAPI api;

        @Override
        protected Void doInBackground(GameSettings... settings) {

            GameSettings gs=settings[0];
            TuttiFruttiAPI api= new TuttiFruttiAPI(getString(R.string.server_url));

            PlayServicesHelper helper = new PlayServicesHelper();
            String regid = "";
            if (helper.checkPlayServices(CreateGameActivity.this))
            {
                regid = helper.getRegistrationId(getApplicationContext());
                if (regid == "")
                    helper.registerGCMInBackground(getApplicationContext());
            }


            api.createGame(gs.getMode(),gs.getOpponents(),gs.getCategories(),regid);
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
            ad=new AlertDialog.Builder(CreateGameActivity.this).create();

            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }
}
