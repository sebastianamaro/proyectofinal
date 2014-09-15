package com.example.tuttifrutti.app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.UserGame;
import com.example.TuttiFruttiCore.PlayServicesHelper;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import java.util.ArrayList;


public class ViewGameStatusActivity extends ActionBarActivity {

    ListView listViewGames ;
    ListView listViewInvitations ;
    PlayServicesHelper helper;
    String fbId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_status);

        //helper= new PlayServicesHelper(MainActivity.class.getSimpleName());
        //registrationId=helper.getRegistrationId(getApplicationContext());

        // Get ListView object from xml
        listViewGames = (ListView) findViewById(R.id.listGames);
        listViewInvitations = (ListView) findViewById(R.id.listInvitations);


        new ViewGameStatusAsyncTaks().execute();
        new ViewPendingInvitationsAsyncTaks().execute();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
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

        @Override
        protected ArrayList<UserGame> doInBackground(Void... filePlays) {
            fbId = FacebookHelper.getUserId();
            return api.getGames(fbId);
        }

        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<UserGame> result) {



           UserGameAdapter adapter = new UserGameAdapter(getApplicationContext(), android.R.layout.simple_list_item_2,  android.R.id.text1, result);


            // Assign adapter to ListView
            listViewGames.setAdapter(adapter);

            // ListView Item Click Listener
            listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    UserGame  itemValue    = (UserGame) listViewGames.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :"+itemPosition+"  ListItem : " +itemValue.getStatus() , Toast.LENGTH_LONG)
                            .show();

                }

            });
        }
    }

    public class ViewPendingInvitationsAsyncTaks extends AsyncTask<Void,Void, ArrayList<FullGame>>
    {
        TuttiFruttiAPI api;

        @Override
        protected ArrayList<FullGame> doInBackground(Void... filePlays) {
            fbId = FacebookHelper.getUserId();
            return api.getPendingInvitations(fbId);
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<FullGame> result) {


           GameAdapter adapter = new GameAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, result);


            // Assign adapter to ListView
            listViewInvitations.setAdapter(adapter);

            // ListView Item Click Listener
            listViewInvitations.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    int itemPosition     = position;

                    // ListView Clicked item value
                    FullGame  itemValue    = (FullGame) listViewInvitations.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                            .show();


                    Intent intent = new Intent(getApplicationContext(), ManageInvitationActivity.class);
                    intent.putExtra("gameSettings", itemValue);
                    startActivity(intent);

                    // ListView Clicked item index

                }

            });
        }
    }

    private class GameAdapter extends ArrayAdapter<FullGame> {

        private ArrayList<FullGame> gameList;

        public GameAdapter(Context context, int resourceId, int textViewResourceId,
                               ArrayList<FullGame> categoryList) {
            super(context, resourceId,textViewResourceId, categoryList);
            this.gameList = new ArrayList<FullGame>();
            this.gameList.addAll(categoryList);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = super.getView(position, convertView, parent);

            }
            Game game = gameList.get(position);
            ((TextView)convertView.findViewById(android.R.id.text1)).setText(game.getOwner().getName());
            ((TextView)convertView.findViewById(android.R.id.text2)).setText(game.getMode());
            ((TextView)convertView.findViewById(android.R.id.text1)).setTextColor(getResources().getColor(R.color.black));
            ((TextView)convertView.findViewById(android.R.id.text2)).setTextColor(getResources().getColor(R.color.black));
            return convertView;

        }

    }

    private class UserGameAdapter extends ArrayAdapter<UserGame> {

        private ArrayList<UserGame> userGameList;

        public UserGameAdapter(Context context,int resourceId, int textViewResourceId,
                           ArrayList<UserGame> categoryList) {
            super(context, resourceId,textViewResourceId, categoryList);
            this.userGameList = new ArrayList<UserGame>();
            this.userGameList.addAll(categoryList);
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = super.getView(position, convertView, parent);

            }

            UserGame game = userGameList.get(position);
            ((TextView)convertView.findViewById(android.R.id.text1)).setText(String.valueOf(game.getGameId()));
            ((TextView)convertView.findViewById(android.R.id.text2)).setText(game.getStatus());

            ((TextView)convertView.findViewById(android.R.id.text1)).setTextColor(getResources().getColor(R.color.black));
            ((TextView)convertView.findViewById(android.R.id.text2)).setTextColor(getResources().getColor(R.color.black));
            return convertView;

        }

    }
}
