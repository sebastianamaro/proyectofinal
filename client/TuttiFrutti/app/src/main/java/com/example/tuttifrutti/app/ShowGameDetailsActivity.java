package com.example.tuttifrutti.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ShowGameDetailsActivity extends ActionBarActivity {
    private ArrayList<Bitmap> profilePics;
    UserGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_details);

        Intent i = getIntent();
        game = (UserGame)i.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE);

        if (game.getRoundId() == -2) {
            Button b = (Button)findViewById(R.id.btnPlay);
            b.setEnabled(false);
        }

        new GetGameAsyncTask().execute(game);
    }

    public class GetGameAsyncTask extends AsyncTask<UserGame,Void, Game>
    {
        TuttiFruttiAPI api;
        UserGame gameInfo;

        @Override
        protected Game doInBackground(UserGame... userGame) {
            gameInfo = userGame[0];
            return api.getGame(gameInfo.getGameId());
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(Game result) {
            //todo: ver los aleatorios, los muestro? o solo que aceptaron X/Total

            TextView txtGameMode=(TextView) findViewById(R.id.gameModeTextView);
            TextView txtOpponentsMode=(TextView) findViewById(R.id.opponentsModeTextView);
            TextView txtCategoriesMode=(TextView) findViewById(R.id.categoryModeTextView);

            txtGameMode.setText(result.getMode().substring(0, 1).toUpperCase() + result.getMode().substring(1).toLowerCase());
            txtOpponentsMode.setText(result.getSpanishOpponentsType());
            txtCategoriesMode.setText(result.getSpanishCategoriesType());

            TextView txt = (TextView) findViewById(R.id.categoryRandomPlayersTextView);
            TextView lbl = (TextView) findViewById(R.id.lblRandomPlayers);
            lbl.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);

            ArrayList<SummarizedPlayer> players = new ArrayList<SummarizedPlayer>();
            String myId = FacebookHelper.getUserId();
            for (Player p : result.getPlayers()) {
                String playerFbId = p.getFbId();
                if (!playerFbId.equals(myId))
                    players.add(new SummarizedPlayer(p, true));
            }
            if (result.getOpponentsType().equals("FRIENDS"))
                for (Player p : result.getSelectedFriends()) {
                    if(!result.getPlayers().contains(p))
                        players.add(new SummarizedPlayer(p, false));
                }
            else
            {
                //random players count es sin contarme a mi, entonces a get players le tengo que restar 1
                int randomPlayersLeftToAccept = result.getRandomPlayersCount() - (result.getPlayers().size()-1);

                if (randomPlayersLeftToAccept > 0) {
                    lbl.setVisibility(View.VISIBLE);
                    txt.setVisibility(View.VISIBLE);
                    txt.setText(Integer.toString(randomPlayersLeftToAccept));
                }
            }

            if (players.size() > 0) {
                PlayersAdapter playerAdapter = new PlayersAdapter(getApplicationContext(),
                        R.layout.playerlistitem, players);
                ListView list = (ListView) findViewById(R.id.playersList);
                list.setAdapter(playerAdapter);
            }else
            {
                TextView playersTitle = (TextView) findViewById(R.id.playersTitle);
                playersTitle.setVisibility(View.GONE);
            }


            ListView listView = (ListView) findViewById(R.id.categoriesList);
            listView.setTextFilterEnabled(true);

            ArrayList<String> categoryNames= new ArrayList<String>();
            for(Category cat : result.getSelectedCategories())
                categoryNames.add(cat.getName());

            ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.simple_list_item_custom,categoryNames);

            listView.setAdapter(categoriesAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_game_details, menu);
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

    private class PlayersAdapter extends ArrayAdapter<SummarizedPlayer> {

        private ArrayList<SummarizedPlayer> playersList;

        public PlayersAdapter(Context context, int resourceId,
                           ArrayList<SummarizedPlayer> playersList) {

            super(context, resourceId, playersList);

            this.playersList = new ArrayList<SummarizedPlayer>();
            this.playersList.addAll(playersList);
        }

        private class ViewHolder {
            ImageView friendPicture;
            TextView name;
            TextView status;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.playerlistitem, null);

                holder = new ViewHolder();
                holder.friendPicture = (ImageView) convertView.findViewById(R.id.profilePicture);
                holder.name = (TextView) convertView.findViewById(R.id.playerName);
                holder.status = (TextView) convertView.findViewById(R.id.playerStatus);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            SummarizedPlayer player = playersList.get(position);

            holder.name.setText(player.name);
            holder.friendPicture.setImageBitmap(player.image);

            if (!player.isConfirmedPlayer) {
                holder.friendPicture.setEnabled(false);
                holder.name.setEnabled(false);
                holder.status.setText("Todavía no aceptó");
                holder.status.setEnabled(false);
            }
            else
                holder.status.setText("Ya es tu contrincante!");

            return convertView;
        }

    }

    private void play(View view)
    {
        Intent intent = new Intent(getApplicationContext(), PlayRoundActivity.class);
        // aca en algun lado deberia saber el ID de la partida
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, game.getGameId());

        startActivity(intent);

    }

    private class SummarizedPlayer
    {
        public String fbId;
        public String name;
        public boolean isConfirmedPlayer;
        public Bitmap image;

        public SummarizedPlayer(Player fullPlayer, boolean isConfirmed)
        {
            this.isConfirmedPlayer = isConfirmed;
            this.fbId = fullPlayer.getFbId();
            this.name = fullPlayer.getName();

            try {
                InputStream inputStream;
                HttpGet httpRequest = new HttpGet(URI.create("http://graph.facebook.com/" + this.fbId.toString() + "/picture?type=normal") );
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
                Bitmap bmp = BitmapFactory.decodeStream(bufHttpEntity.getContent());
                httpRequest.abort();

                this.image = bmp;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public boolean equals(Object object)
        {
            boolean sameSame = false;

            if (object != null && object instanceof SummarizedPlayer)
            {
                sameSame = this.fbId == ((SummarizedPlayer) object).fbId;
            }

            return sameSame;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(this.fbId);
        }
    }
}

