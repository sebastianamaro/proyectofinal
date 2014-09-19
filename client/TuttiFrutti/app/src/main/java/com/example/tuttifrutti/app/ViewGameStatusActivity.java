package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    String fbId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_status);

        listViewGames = (ListView) findViewById(R.id.listGames);
        new FillListViewAsyncTask().execute();
    }

    @Override
    public void onBackPressed() {
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

    public void createGame(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);
        startActivity(intent);
    }

    public class FillListViewAsyncTask extends AsyncTask<Void, Void, Void> {
        TuttiFruttiAPI api;
        ArrayList<UserGame> games;
        ArrayList<FullGame> invitations;

        protected void onPreExecute() {
            api = new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Void... filePlays) {
            fbId = FacebookHelper.getUserId();
            games = api.getGames(fbId);
            invitations = api.getPendingInvitations(fbId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            ArrayList<UserGame> activeGames = new ArrayList<UserGame>();
            ArrayList<UserGame> finishedGames = new ArrayList<UserGame>();

            for (UserGame ug : games)
                if (ug.getStatus().equals("CLOSED"))
                    finishedGames.add(ug);
                else
                    activeGames.add(ug);

            GamesAdapter ga = new GamesAdapter(getApplicationContext(), activeGames, invitations, finishedGames);
            listViewGames.setAdapter(ga);

            listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Object itemValue = (Object) listViewGames.getItemAtPosition(position);

                    if (itemValue instanceof FullGame) {
                        Intent intent = new Intent(getApplicationContext(), ManageInvitationActivity.class);
                        intent.putExtra("gameSettings", (FullGame) itemValue);
                        startActivity(intent);
                    } else if (itemValue instanceof UserGame) {
                        // TODO depende del flujo!
                    }
                }
            });
        }
    }

    public class GamesAdapter extends BaseAdapter {

        Context context;
        private ArrayList<Object> games;
        private int activeGamesSeparatorIndex=0; //ex staredCategoriesSeparatorIndex
        private int invitationsSeparatorIndex; //ex fixedCategoriesSeparatorIndex
        private int finishedGamesSeparatorIndex; //ex allCategoriesSeparatorIndex
        private static final int ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR = 0;
        private static final int ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR = 1;
        private static final int ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR = 2;
        private static final int ITEM_VIEW_TYPE_EMPTY_SEPARATOR = 3;
        private static final int ITEM_VIEW_TYPE_FULL_GAME_SEPARATOR = 4;
        private static final int ITEM_VIEW_TYPE_USER_GAME_SEPARATOR = 5;
        private static final int ITEM_VIEW_TYPE_FINISHED_USER_GAME_SEPARATOR = 6;
        private static final int ITEM_VIEW_TYPE_COUNT = 7; // 1,2,3) Headers  4) separador invisible 5) Full Game 6) userGame
        private static final String activeGamesText="Partidas Activas";
        private static final String invitationsText="Invitaciones";
        private static final String finishedGamesText="Todas";

        public GamesAdapter(Context context,ArrayList<UserGame> activeGames, ArrayList<FullGame> invitations, ArrayList<UserGame> finishedGames)
        {
            this.context=context;
            this.games= new ArrayList<Object>();
            this.games.addAll(activeGames);
            this.games.addAll(invitations);
            this.games.addAll(finishedGames);

            this.invitationsSeparatorIndex=activeGames.size() + 2; // Es dos porque tenemos el header y una row transparente para dar el feelling de que son grillas separadas
            this.finishedGamesSeparatorIndex=activeGames.size() +invitations.size()  + 4;

            this.games.add(activeGamesSeparatorIndex,activeGamesText);
            this.games.add(invitationsSeparatorIndex,invitationsText);
            this.games.add(finishedGamesSeparatorIndex,finishedGamesText);
        }


        @Override
        public int getItemViewType(int position) {

            if(position == activeGamesSeparatorIndex)
                return ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR;


            if(position == invitationsSeparatorIndex-1)
                return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;

            if(position == invitationsSeparatorIndex)
                return ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR;

            if(position == finishedGamesSeparatorIndex-1)
                return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;

            if(position == finishedGamesSeparatorIndex)
                return ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR;

            if(games.get(position) instanceof FullGame)
                return ITEM_VIEW_TYPE_FULL_GAME_SEPARATOR;

            if(games.get(position) instanceof UserGame)
            {
                UserGame ug= (UserGame) games.get(position);
                if(ug.getStatus().equals("CLOSED"))
                    return ITEM_VIEW_TYPE_FINISHED_USER_GAME_SEPARATOR;
                else
                    return ITEM_VIEW_TYPE_USER_GAME_SEPARATOR;
            }

            return -1;
        }

        @Override
        public int getViewTypeCount() { return ITEM_VIEW_TYPE_COUNT; }

        @Override
        public int getCount() {
            return games.size();
        }

        @Override
        public Object getItem(int i) {
            return games.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean isEnabled(int position) {
            // A separator cannot be clicked !
            return getItemViewType(position) == ITEM_VIEW_TYPE_FULL_GAME_SEPARATOR || getItemViewType(position) == ITEM_VIEW_TYPE_USER_GAME_SEPARATOR;
        }

        private class ActiveGamesViewHolder {
            TextView text1;
            TextView text2;
        }

        private class FinishedGamesViewHolder {
            TextView text1;
            ImageView imgDelete;
        }

        private class InvitationsViewHolder {
            TextView text1;
            TextView text2;
        }


        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            final int type = getItemViewType(i);

            switch (type)
            {
                case ITEM_VIEW_TYPE_EMPTY_SEPARATOR:
                    convertView=SetRowEmptyViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR:
                    convertView=SetRowActiveGamesSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR:
                    convertView=SetRowInvitationsSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR:
                    convertView=SetRowFinishedSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_USER_GAME_SEPARATOR:
                    convertView=SetRowActiveGamesViewHolder(i, convertView);
                    break;
                case ITEM_VIEW_TYPE_FULL_GAME_SEPARATOR:
                    convertView=SetRowInvitationsViewHolder(i,convertView);
                    break;
                case ITEM_VIEW_TYPE_FINISHED_USER_GAME_SEPARATOR:
                    convertView=SetRowFinishedGamesViewHolder(i,convertView);
                    break;

            }

            return convertView;


        }

        private View SetRowEmptyViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.empty_list_separator, null);
            }
            return convertView;
        }

        private View SetRowActiveGamesSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.active_games_list_separator, null);
            }
            return convertView;
        }

        private View SetRowInvitationsSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.invitations_list_separator, null);
            }
            return convertView;
        }

        private View SetRowFinishedSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.finished_games_separator, null);
            }
            return convertView;
        }

        private View SetRowActiveGamesViewHolder(int position, View convertView) {
            ActiveGamesViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.active_game_row, null);

                holder = new ActiveGamesViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.agtext1);
                holder.text2 = (TextView) convertView.findViewById(R.id.agtext2);
                convertView.setTag(holder);
            }
            else {
                holder = (ActiveGamesViewHolder) convertView.getTag();
            }

            UserGame rowItem = (UserGame) getItem(position);

            holder.text1.setText(rowItem.getGameId());
            holder.text2.setText(rowItem.getStatus());

            return convertView;
        }

        private View SetRowInvitationsViewHolder(int position, View convertView) {
            InvitationsViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.invitation_row, null);

                holder = new InvitationsViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.invtext1);
                holder.text2 = (TextView) convertView.findViewById(R.id.invtext2);
                convertView.setTag(holder);
            }
            else {
                holder = (InvitationsViewHolder) convertView.getTag();
            }

            FullGame rowItem = (FullGame) getItem(position);

            holder.text1.setText(rowItem.getOwner().getName());
            holder.text2.setText(rowItem.getMode());

            return convertView;
        }

        private View SetRowFinishedGamesViewHolder(int position, View convertView) {
            FinishedGamesViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.finished_game_row, null);

                holder = new FinishedGamesViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.fgtext1);
                holder.imgDelete = (ImageView) convertView.findViewById(R.id.deleteFinishedGame);
                convertView.setTag(holder);
            }
            else {
                holder = (FinishedGamesViewHolder) convertView.getTag();
            }

            FullGame rowItem = (FullGame) getItem(position);

            holder.text1.setText(rowItem.getOwner().getName());

            return convertView;
        }


    }

}
