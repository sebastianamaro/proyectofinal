package com.example.tuttifrutti.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.PlayServicesHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ViewGameStatusActivity extends ActionBarActivity {

    ListView listView ;
    PlayServicesHelper helper;
    String registrationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_status);

        helper= new PlayServicesHelper();
        registrationId=helper.getRegistrationId(getApplicationContext());
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);



        new ViewGameStatusAsyncTaks().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_game_status, menu);
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

    public class ViewGameStatusAsyncTaks extends AsyncTask<Void,Void, ArrayList<UserGame>>
    {
        TuttiFruttiAPI api;

        @Override
        protected ArrayList<UserGame> doInBackground(Void... filePlays) {
            return api.getGames(registrationId);
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<UserGame> result) {

            String[] values= new String[result.size()];
            int i=0;
            for(UserGame userGame : result) {
               values[i]=userGame.getGameId()+ " - " + userGame.getStatus();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);


            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                            .show();

                }

            });
        }
    }
}
