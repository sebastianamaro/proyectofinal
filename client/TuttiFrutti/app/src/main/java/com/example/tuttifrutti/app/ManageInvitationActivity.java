package com.example.tuttifrutti.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.InvitationResponse;

import java.util.ArrayList;


public class ManageInvitationActivity extends ActionBarActivity {
    FullGame gameSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_invitation);

        Intent intent = getIntent();
        gameSettings = (FullGame)intent.getSerializableExtra("gameSettings");

        TextView txtUserName=(TextView) this.findViewById(R.id.userNameTextView);
        TextView txtGameMode=(TextView) this.findViewById(R.id.gameModeTextView);
        TextView txtOpponentsMode=(TextView) this.findViewById(R.id.opponentsModeTextView);
        TextView txtCategoriesMode=(TextView) this.findViewById(R.id.categoryModeTextView);

        txtUserName.setText(gameSettings.getOwner().getName());
        txtGameMode.setText(gameSettings.getMode());
        txtOpponentsMode.setText(gameSettings.getOpponentsType());
        txtCategoriesMode.setText(gameSettings.getCategoriesType());



    }

    public void acceptInvitation(View view) {

        InvitationResponse ir= new InvitationResponse("ACCEPTED",gameSettings.getOwner());
        new RespondInvitationAsyncTask().execute(ir);
    }

    public void rejectInvitation(View view) {
        InvitationResponse ir= new InvitationResponse("REJECTED",gameSettings.getOwner());
        new RespondInvitationAsyncTask().execute(ir);
    }

    public class RespondInvitationAsyncTask extends AsyncTask<InvitationResponse, Void, Void>
    {
        TuttiFruttiAPI api;
        @Override
        protected Void doInBackground(InvitationResponse... response) {
            api.respondInvitation(gameSettings.getGameId(), response[0]);
            return null;
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
            startActivity(intent);
        }
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_invitation, menu);
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
