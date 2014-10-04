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
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.InvitationResponse;
import com.example.TuttiFruttiCore.PlayServicesHelper;
import com.example.TuttiFruttiCore.Player;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import java.util.ArrayList;


public class ManageInvitationActivity extends ActionBarActivity {
    FullGame gameSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FacebookHelper.isSessionOpened())
        {
            Intent i = new Intent(getApplicationContext(), AndroidFacebookConnectActivity.class);
            startActivity(i);
        }

        setTitle("");
        setContentView(R.layout.activity_manage_invitation);

        Intent intent = getIntent();
        gameSettings = (FullGame)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

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
        SendInvitationResponse("ACCEPTED");
    }

    public void rejectInvitation(View view) {
        SendInvitationResponse("REJECTED");
    }

    public void SendInvitationResponse(String response) {

        new RespondInvitationAsyncTask().execute(response);
    }

    public class RespondInvitationAsyncTask extends AsyncTask<String, Void, Void>
    {
        TuttiFruttiAPI api;
        @Override
        protected Void doInBackground(String... response) {
            String fbId= FacebookHelper.getUserId();

            Player me = new Player();
            me.setFbId(fbId);

            InvitationResponse ir= new InvitationResponse(response[0],me);

            api.respondInvitation(gameSettings.getGameId(), ir);
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
}
