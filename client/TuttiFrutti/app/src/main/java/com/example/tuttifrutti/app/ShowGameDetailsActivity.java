package com.example.tuttifrutti.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.InvitationResponse;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.ResourceAccessException;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class ShowGameDetailsActivity extends ActionBarActivity {
    private ArrayList<Bitmap> profilePics;
    FullGame game;
    ListView detailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("");
        setContentView(R.layout.activity_show_game_details);

        Intent i = getIntent();
        game = (FullGame)i.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE);

        detailsList = (ListView) findViewById(R.id.detailsList);
        if(isPlayableGame(game))
        {
            //no pregunto si ya jugo, porque si ya jugo no va a estar en esta pantalla
            if (((UserGame)game).getStatusCode() == Constants.GAME_STATUS_CODE_NOT_STARTED) {
                Button b = (Button)findViewById(R.id.btnPlay);
                b.setEnabled(false);
            }
        }
        else
        {
            RelativeLayout playRL = (RelativeLayout)findViewById(R.id.btnPlayLayout);
            playRL.setVisibility(View.GONE);

            RelativeLayout invitationRL=(RelativeLayout)findViewById(R.id.btnInvitationLayout);
            invitationRL.setVisibility(View.VISIBLE);
        }


        new GetGameAsyncTask().execute(game);
    }

    public static boolean isPlayableGame(FullGame game){
       return game instanceof UserGame;
    }

    public void play(View view)
    {
        Intent intent = new Intent(getApplicationContext(), PlayRoundActivity.class);
        // aca en algun lado deberia saber el ID de la partida
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, game.getGameId());

        startActivity(intent);
    }

    public class GetGameAsyncTask extends AsyncTask<FullGame,Void, Game>
    {
        TuttiFruttiAPI api;
        FullGame gameInfo;
        boolean connError;
        private ProgressDialog Dialog = new ProgressDialog(ShowGameDetailsActivity.this);

        @Override
        protected Game doInBackground(FullGame... userGame) {
            gameInfo = userGame[0];

            try {
                return api.getGame(gameInfo.getGameId());
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
                return null;
            }
        }

        protected void onPreExecute(){

            api=new TuttiFruttiAPI(getString(R.string.server_url));
            Dialog.setMessage("Obteniendo detalles...");
            Dialog.show();
        }

        @Override
        protected void onPostExecute(Game result) {
            if (this.connError) {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            } else {

                ArrayList<SummarizedPlayer> players = new ArrayList<SummarizedPlayer>();
                String myId = FacebookHelper.getUserId();
                for (Player p : result.getPlayers()) {
                    String playerFbId = p.getFbId();
                    if (!playerFbId.equals(myId))
                        players.add(new SummarizedPlayer(p, true));
                }

                if (result.getOpponentsType().equals("FRIENDS"))
                    for (Player p : result.getSelectedFriends()) {
                        if (!result.getPlayers().contains(p))
                            players.add(new SummarizedPlayer(p, false));
                    }

                GameDetailsAdapter gda = new GameDetailsAdapter(getApplicationContext(), players, result);
                detailsList.setAdapter(gda);
                Dialog.dismiss();
            }
        }
    }



    private class GameDetailsAdapter extends BaseAdapter{

        private ArrayList<Object> details;
        Context context;
        private int detailsSeparatorIndex=0;
        private int playersSeparatorIndex;
        private int categoriesSeparatorIndex;
        private static final int ITEM_VIEW_TYPE_INFORMATION_SEPARATOR = 0;
        private static final int ITEM_VIEW_TYPE_INFORMATION = 1;
        private static final int ITEM_VIEW_TYPE_PLAYERS_SEPARATOR = 2;
        private static final int ITEM_VIEW_TYPE_CATEGORIES_SEPARATOR = 3;
        private static final int ITEM_VIEW_TYPE_EMPTY_SEPARATOR = 4;
        private static final int ITEM_VIEW_TYPE_PLAYER = 5;
        private static final int ITEM_VIEW_TYPE_CATEGORY = 6;
        private static final int ITEM_VIEW_TYPE_COUNT = 7;
        private static final String detailsText = "Detalles";
        private static final String playersText = "Jugadores";
        private static final String categoriesText = "Categorias";
        boolean showsPlayers;
        boolean showsRandomPlayersCount;
        Game game;


        public GameDetailsAdapter(Context context, ArrayList<SummarizedPlayer> players, Game game)
        {
            this.game=game;
            this.context=context;
            this.details = new ArrayList<Object>();

            /*
            * OWNER
            * MODE
            * OPPONENTS
            * CATEGORIES TYPE
            * RANDOM PLAYERS COUNT?
            *
            * */

            this.details.add(detailsText);
            this.details.add("Creada por:|"+game.getOwner().getName());
            this.details.add("Modo:         |"  +game.getMode().substring(0, 1).toUpperCase() + game.getMode().substring(1).toLowerCase());
            this.details.add("Oponentes:|" +game.getSpanishOpponentsType());
            this.details.add("Categorías:|"+game.getSpanishCategoriesType());

            if (!game.getOpponentsType().equals("FRIENDS"))
            {
                int randomPlayersLeftToAccept = game.getRandomPlayersCount() - (game.getPlayers().size() - 1);
                if (randomPlayersLeftToAccept > 0) {
                    showsRandomPlayersCount=true;
                    if(randomPlayersLeftToAccept>1)
                        this.details.add("Faltan aceptar "+ randomPlayersLeftToAccept+ " jugadores");
                    else
                        this.details.add("Falta aceptar "+ randomPlayersLeftToAccept+ " jugador");
                }
            }


            this.details.addAll(players);
            this.details.addAll(game.getSelectedCategories());


            showsPlayers = players.size() > 0;


            if (showsPlayers)
            {
                playersSeparatorIndex=6;
                if(showsRandomPlayersCount)
                    playersSeparatorIndex++;

                this.details.add(playersSeparatorIndex-1, null);

                this.details.add(playersSeparatorIndex, playersText);
                this.categoriesSeparatorIndex = playersSeparatorIndex + players.size() + 2; // Es dos porque tenemos el header y una row transparente para dar el feelling de que son grillas separadas


            }
            else {
                this.categoriesSeparatorIndex = 6; // Solo el header de invitaciones
                if(showsRandomPlayersCount)
                    categoriesSeparatorIndex++;


                this.playersSeparatorIndex=-1;

            }

            this.details.add(categoriesSeparatorIndex-1, null);

            this.details.add(categoriesSeparatorIndex, categoriesText);

        }

        @Override
        public int getItemViewType(int position) {

            if(getItem(position) == null)
                return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;

            if(getItem(position) instanceof SummarizedPlayer)
                return ITEM_VIEW_TYPE_PLAYER;

            if(getItem(position) instanceof Category)
                return ITEM_VIEW_TYPE_CATEGORY;


            if(showsPlayers && position==playersSeparatorIndex)
                return ITEM_VIEW_TYPE_PLAYERS_SEPARATOR;

            if(position==categoriesSeparatorIndex)
                return ITEM_VIEW_TYPE_CATEGORIES_SEPARATOR;

            if(position ==0) //details
                return ITEM_VIEW_TYPE_INFORMATION_SEPARATOR;

            if(position <=7)
                return ITEM_VIEW_TYPE_INFORMATION;
            return -1;
        }


        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        @Override
        public int getCount() {
            return details.size();
        }

        @Override
        public Object getItem(int i) {
            return details.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }


        private class PlayerViewHolder {
            ImageView friendPicture;
            TextView name;
            TextView status;
        }

        private class CategoryViewHolder {
            TextView text1;
        }

        private class DetailViewHolder {
            TextView text1;
            TextView text2;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            final int type = getItemViewType(i);

            switch (type) {
                case ITEM_VIEW_TYPE_EMPTY_SEPARATOR:
                    convertView = SetRowEmptyViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_PLAYERS_SEPARATOR:
                    convertView = SetRowPlayerSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_CATEGORIES_SEPARATOR:
                    convertView = SetRowCategorySeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_INFORMATION_SEPARATOR:
                    convertView = SetRowDetailsSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_PLAYER:
                    convertView = SetRowPlayerViewHolder(i, convertView);
                    break;
                case ITEM_VIEW_TYPE_CATEGORY:
                    convertView = SetRowCategoryViewHolder(i, convertView);
                    break;
                case ITEM_VIEW_TYPE_INFORMATION:
                    convertView = SetRowDetailsViewHolder(i, convertView);
                    break;
            }

            return convertView;
        }

        private View SetRowEmptyViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_empty, null);
            }
            return convertView;
        }

        private View SetRowPlayerSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_player, null);
            }
            return convertView;
        }

        private View SetRowCategorySeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_category, null);
            }
            return convertView;
        }

        private View SetRowDetailsSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_details, null);
            }
            return convertView;
        }

        private View SetRowDetailsViewHolder(int position, View convertView){
            DetailViewHolder holder=null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.simple_list_item_details, null);

                holder = new DetailViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.detailText1);
                holder.text2 = (TextView) convertView.findViewById(R.id.detailText2);
                convertView.setTag(holder);
            }
            else {
                holder = (DetailViewHolder) convertView.getTag();
            }

            String detail = (String)details.get(position);
            if(detail.contains("|")){
                String description=detail.substring(0,detail.indexOf("|"));
                String detailValue=detail.substring(detail.indexOf("|")+1,detail.length());
                holder.text1.setText(description);
                holder.text2.setText(detailValue);
            }else
            {
                holder.text1.setText(detail);
            }



            return convertView;

        }

        private View SetRowCategoryViewHolder(int position, View convertView){
            CategoryViewHolder holder=null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.simple_list_item_categories, null);

                holder = new CategoryViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.categoryText);
                convertView.setTag(holder);
            }
            else {
                holder = (CategoryViewHolder) convertView.getTag();
            }

            Category category = (Category)details.get(position);

            holder.text1.setText(category.getName());
            return convertView;

        }

        private View SetRowPlayerViewHolder(int position, View convertView)
        {
            PlayerViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_row_player, null);

                holder = new PlayerViewHolder();
                holder.friendPicture = (ImageView) convertView.findViewById(R.id.profilePicture);
                holder.name = (TextView) convertView.findViewById(R.id.playerName);
                holder.status = (TextView) convertView.findViewById(R.id.playerStatus);
                convertView.setTag(holder);
            }
            else {
                holder = (PlayerViewHolder) convertView.getTag();
            }

            SummarizedPlayer player = (SummarizedPlayer)details.get(position);

            holder.name.setText(player.name);
            holder.friendPicture.setImageBitmap(player.image);

            if (!player.isConfirmedPlayer) {
                holder.friendPicture.setEnabled(false);
                holder.name.setEnabled(false);
                holder.status.setText("Todavía no aceptó");
                holder.status.setEnabled(false);
            }
            else {
                holder.name.setEnabled(true);
                holder.status.setEnabled(true);
                holder.friendPicture.setEnabled(true);
                holder.status.setText("Ya es tu contrincante!");
            }

            return convertView;

        }

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
                HttpGet httpRequest = new HttpGet(URI.create("http://graph.facebook.com/" + this.fbId.toString() + "/picture?type=square&width=120&height=120") );
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
                sameSame = this.fbId.equals(((SummarizedPlayer) object).fbId);
            }

            return sameSame;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(this.fbId);
        }
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
        boolean connError;
        @Override
        protected Void doInBackground(String... response) {
            String fbId= FacebookHelper.getUserId();

            Player me = new Player();
            me.setFbId(fbId);

            InvitationResponse ir= new InvitationResponse(response[0],me);

            try {
                api.respondInvitation(game.getGameId(), ir);
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
            }
            return null;
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.connError)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
                startActivity(intent);
            }
        }
    }
}

