package com.example.tuttifrutti.app2;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.FullGame;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app2.Classes.FacebookHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;



public class ViewGameStatusActivity extends ListActivity {

    private PullToRefreshListView mPullToRefreshLayout;
    String fbId;
    ProgressDialog dialog;
    @Override
    public void onResume (){
        super.onResume();
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();

            new FillListViewAsyncTask(true).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog=new ProgressDialog(this);
        setTitle("");
        setContentView(R.layout.activity_view_game_status);

        mPullToRefreshLayout = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancelAll();
                // Do work to refresh the list here.
                new FillListViewAsyncTask(false).execute();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_game_status, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_showRules) {
            Intent i = new Intent(getApplicationContext(), ShowGameRulesActivity.class);
            MoveToAnotherActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MoveToAnotherActivity(startMain);
    }

    public void createGame(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateGameActivity.class);
        MoveToAnotherActivity(intent);
    }


    public void MoveToAnotherActivity(Intent intent){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        startActivity(intent);
    }


    public class FillListViewAsyncTask extends AsyncTask<Void, Void, Void> {
        TuttiFruttiAPI api;
        ArrayList<UserGame> games;
        ArrayList<FullGame> invitations;
        boolean connError;
        boolean showSpinner;

        public FillListViewAsyncTask(boolean showSpiner){
            this.showSpinner=showSpiner;
            if(showSpiner)
            {
                if(dialog.isShowing())
                    dialog.dismiss();

                dialog.setMessage("Obteniendo partidas...");
                dialog.setCancelable(false);
                dialog.show();
            }
        }

        protected void onPreExecute() {


            api = new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Void... filePlays) {
            fbId = FacebookHelper.getUserId(getApplicationContext());
            try {
                games = api.getGames(fbId);
                invitations = api.getPendingInvitations(fbId);
            }catch (ResourceAccessException ex)
            {
                connError = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (this.connError) {
                mPullToRefreshLayout.onRefreshComplete();
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            }
            else {

                ArrayList<UserGame> activeGames = new ArrayList<UserGame>();
                ArrayList<UserGame> finishedGames = new ArrayList<UserGame>();

                for (UserGame ug : games) {
                    if (ug.isFinishedOrRejected())
                        finishedGames.add(ug);
                    else
                        activeGames.add(ug);
                }

                GamesAdapter ga = new GamesAdapter(getApplicationContext(), activeGames, invitations, finishedGames);
                mPullToRefreshLayout.getRefreshableView().setAdapter(ga);
                mPullToRefreshLayout.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        FullGame itemValue = (FullGame) mPullToRefreshLayout.getRefreshableView().getItemAtPosition(position);

                        Intent i;
                        if (itemValue instanceof UserGame) {

                            UserGame ug = (UserGame) itemValue;

                            if (ug.getStatusCode() == Constants.GAME_STATUS_CODE_NOT_STARTED ||   (ug.getStatusCode() == Constants.GAME_STATUS_CODE_NO_PREV_ROUNDS && ug.getIsFirstRound() && !ug.getPlayerHasPlayedCurrentRound())) {
                                i = new Intent(getApplicationContext(), ShowGameDetailsActivity.class);
                            } else if(ug.getStatusCode() == Constants.GAME_STATUS_CODE_FINISHED){
                                i = new Intent(getApplicationContext(), ShowGameResultActivity.class);
                            }else if (!ug.allPlayersRejected())
                                i = new Intent(getApplicationContext(), ShowRoundResultActivity.class);
                             else
                                return;
                        } else {
                            i = new Intent(getApplicationContext(), ShowGameDetailsActivity.class);
                        }

                        i.putExtra(Constants.GAME_INFO_EXTRA_MESSAGE, itemValue);
                        MoveToAnotherActivity(i);
                    }

                });
                // Call onRefreshComplete when the list has been refreshed.
                mPullToRefreshLayout.onRefreshComplete();

            }

            if(dialog.isShowing())
                dialog.dismiss();
        }


        public void MoveToAnotherActivity(Intent intent){
            if(dialog != null && dialog.isShowing())
                dialog.dismiss();

            startActivity(intent);
        }

        public class GamesAdapter extends BaseAdapter {

            private ArrayList<Object> games;
            Context context;
            private int activeGamesSeparatorIndex=0; //ex staredCategoriesSeparatorIndex
            private int invitationsSeparatorIndex; //ex fixedCategoriesSeparatorIndex
            private int finishedGamesSeparatorIndex; //ex allCategoriesSeparatorIndex
            private static final int ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR = 0;
            private static final int ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR = 1;
            private static final int ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR = 2;
            private static final int ITEM_VIEW_TYPE_EMPTY_SEPARATOR = 3;
            private static final int ITEM_VIEW_TYPE_FULL_GAME = 4;
            private static final int ITEM_VIEW_TYPE_USER_GAME = 5;
            private static final int ITEM_VIEW_TYPE_FINISHED_USER_GAME = 6;
            private static final int ITEM_VIEW_TYPE_NO_GAMES = 7;
            private static final int ITEM_VIEW_TYPE_COUNT = 8; // 1,2,3) Headers  4) separador invisible 5) Full Game 6) userGame
            private static final String activeGamesText = "Partidas Activas";
            private static final String invitationsText = "Invitaciones";
            private static final String finishedGamesText = "Partidas Finalizadas";
            boolean showsActiveGames;
            boolean showsInvitations;
            boolean showsFinishedGames;


            public GamesAdapter(Context context, ArrayList<UserGame> activeGames, ArrayList<FullGame> invitations, ArrayList<UserGame> finishedGames) {
                this.context = context;
                this.games = new ArrayList<Object>();
                this.games.addAll(activeGames);
                this.games.addAll(invitations);
                this.games.addAll(finishedGames);


                showsActiveGames = activeGames.size() > 0;
                showsInvitations = invitations.size() > 0;
                showsFinishedGames = finishedGames.size() > 0;


                if (showsActiveGames)
                    this.invitationsSeparatorIndex = activeGames.size() + 2; // Es dos porque tenemos el header y una row transparente para dar el feelling de que son grillas separadas
                else
                    this.invitationsSeparatorIndex = 0; // Solo el header de invitaciones

                if (showsActiveGames)
                    this.finishedGamesSeparatorIndex = activeGames.size() + 2;
                else
                    this.finishedGamesSeparatorIndex = 0;


                if (showsInvitations)
                    this.finishedGamesSeparatorIndex += (invitations.size() + 2);


                if (showsActiveGames)
                    this.games.add(activeGamesSeparatorIndex, activeGamesText);

                if (showsInvitations) {

                    if(showsActiveGames)
                        this.games.add(invitationsSeparatorIndex - 1, null);

                    this.games.add(invitationsSeparatorIndex, invitationsText);
                }

                if (showsFinishedGames) {

                    if(showsActiveGames || showsInvitations)
                        this.games.add(finishedGamesSeparatorIndex - 1, null);

                    this.games.add(finishedGamesSeparatorIndex, finishedGamesText);
                }

                if(!showsActiveGames && !showsInvitations && !this.showsFinishedGames)
                {

                    this.games.add(0,"");
                    this.games.add(0,null);
                    this.games.add(0,null);
                }
            }


            @Override
            public int getItemViewType(int position) {

                if(games.get(position) == null)
                    return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;

                if(!showsActiveGames && !showsInvitations && !this.showsFinishedGames)
                    return ITEM_VIEW_TYPE_NO_GAMES;

                if (showsActiveGames) {
                    if (position == activeGamesSeparatorIndex)
                        return ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR;

                    /*if (showsInvitations) {
                        if (position == invitationsSeparatorIndex - 1)
                            return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;
                    } else if (showsFinishedGames) {
                        if (position == finishedGamesSeparatorIndex - 1)
                            return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;
                    }*/
                }


                if (showsInvitations) {
                    if (position == invitationsSeparatorIndex)
                        return ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR;

                    /*if (showsFinishedGames) {
                        if (position == finishedGamesSeparatorIndex - 1) {
                            return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;
                        }
                    }*/
                }

                if (showsFinishedGames) {
                    if (position == finishedGamesSeparatorIndex)
                        return ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR;
                }


                if (games.get(position) instanceof UserGame) {
                    UserGame ug = (UserGame) games.get(position);
                    if (ug.isFinishedOrRejected())
                        return ITEM_VIEW_TYPE_FINISHED_USER_GAME;
                    else
                        return ITEM_VIEW_TYPE_USER_GAME;
                }

                if (games.get(position) instanceof FullGame)
                    return ITEM_VIEW_TYPE_FULL_GAME;




                return -1;
            }

            @Override
            public int getViewTypeCount() {
                return ITEM_VIEW_TYPE_COUNT;
            }

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
                return getItemViewType(position) == ITEM_VIEW_TYPE_FULL_GAME || getItemViewType(position) == ITEM_VIEW_TYPE_USER_GAME ||getItemViewType(position) == ITEM_VIEW_TYPE_FINISHED_USER_GAME ;
            }

            private class ActiveGamesViewHolder {
                TextView text1;
                TextView text2;
            }

            private class FinishedGamesViewHolder {
                TextView text1;
                TextView text2;
                ImageView imgDelete;
            }

            private class InvitationsViewHolder {
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
                    case ITEM_VIEW_TYPE_ACTIVE_GAMES_SEPARATOR:
                        convertView = SetRowActiveGamesSeparatorViewHolder(convertView);
                        break;
                    case ITEM_VIEW_TYPE_INVITATIONS_SEPARATOR:
                        convertView = SetRowInvitationsSeparatorViewHolder(convertView);
                        break;
                    case ITEM_VIEW_TYPE_FINISHED_GAMES_SEPARATOR:
                        convertView = SetRowFinishedSeparatorViewHolder(convertView);
                        break;
                    case ITEM_VIEW_TYPE_USER_GAME:
                        convertView = SetRowActiveGamesViewHolder(i, convertView);
                        break;
                    case ITEM_VIEW_TYPE_FULL_GAME:
                        convertView = SetRowInvitationsViewHolder(i, convertView);
                        break;
                    case ITEM_VIEW_TYPE_FINISHED_USER_GAME:
                        convertView = SetRowFinishedGamesViewHolder(i, convertView);
                        break;
                    case ITEM_VIEW_TYPE_NO_GAMES:
                        convertView = SetRowNoGamesViewHolder(convertView);
                        break;

                }

                return convertView;


            }

            private View SetRowNoGamesViewHolder(View convertView) {

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_row_no_games, null);
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

            private View SetRowActiveGamesSeparatorViewHolder(View convertView) {

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_separator_active_games, null);
                }
                return convertView;
            }

            private View SetRowInvitationsSeparatorViewHolder(View convertView) {

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_separator_invitations, null);
                }
                return convertView;
            }

            private View SetRowFinishedSeparatorViewHolder(View convertView) {

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_separator_finished_games, null);
                }
                return convertView;
            }

            private View SetRowActiveGamesViewHolder(int position, View convertView) {
                ActiveGamesViewHolder holder = null;

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_row_active_games, null);

                    holder = new ActiveGamesViewHolder();
                    holder.text1 = (TextView) convertView.findViewById(R.id.agtext1);
                    holder.text2 = (TextView) convertView.findViewById(R.id.agtext2);
                    convertView.setTag(holder);
                } else {
                    holder = (ActiveGamesViewHolder) convertView.getTag();
                }

                UserGame rowItem = (UserGame) getItem(position);

                holder.text1.setText(toProperCase(rowItem.getMode()) + " - " + rowItem.getSpanishCategoriesType());

                String namesToShow = "";
                for (Player player : rowItem.getPlayers())
                    namesToShow += player.getName() + " - ";

                namesToShow = "<b>" + namesToShow.substring(0, namesToShow.lastIndexOf(" - ")) + "</b>";

                if (rowItem.getSelectedFriends().size() > 0 && rowItem.getPlayers().size() <= (rowItem.getSelectedFriends().size()+1)) {
                    boolean allPlayersAccepted = true;
                    for (Player selectedFriend : rowItem.getSelectedFriends()) {
                        if (!rowItem.SelectedFriendIsPlayer(selectedFriend.getName())) {
                            namesToShow += " - <font color='grey'>" + selectedFriend.getName() + "</font>";
                            allPlayersAccepted = false;
                        }
                    }
                    if (allPlayersAccepted)
                    {
                        //le saco la negrita si todos aceptaron porque no queda bien
                        namesToShow = namesToShow.replace("<b>", "");
                        namesToShow = namesToShow.replace("</b>", "");
                    }
                }
                else if (rowItem.getRandomPlayersCount() > 0 && rowItem.getPlayers().size() <= rowItem.getRandomPlayersCount())
                {
                    namesToShow += "<font color='grey'> +" + (rowItem.getRandomPlayersCount() - rowItem.getPlayers().size() +1)+ "</font>";
                }

                holder.text2.setText(Html.fromHtml(namesToShow), TextView.BufferType.SPANNABLE);

                return convertView;
            }

            private View SetRowInvitationsViewHolder(int position, View convertView) {
                InvitationsViewHolder holder = null;

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_row_invitation, null);

                    holder = new InvitationsViewHolder();
                    holder.text1 = (TextView) convertView.findViewById(R.id.invtext1);
                    holder.text2 = (TextView) convertView.findViewById(R.id.invtext2);
                    convertView.setTag(holder);
                } else {
                    holder = (InvitationsViewHolder) convertView.getTag();
                }

                FullGame rowItem = (FullGame) getItem(position);

                holder.text1.setText(rowItem.getOwner().getName());
                holder.text2.setText(toProperCase(rowItem.getMode()) + " - " + rowItem.getSpanishCategoriesType());

                return convertView;
            }

            private View SetRowFinishedGamesViewHolder(final int position, View convertView) {
                FinishedGamesViewHolder holder = null;

                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_row_finished_game, null);

                    holder = new FinishedGamesViewHolder();
                    holder.text1 = (TextView) convertView.findViewById(R.id.fgtext1);
                    holder.text2 = (TextView) convertView.findViewById(R.id.fgtext2);
                    holder.imgDelete = (ImageView) convertView.findViewById(R.id.deleteFinishedGame);
                    convertView.setTag(holder);
                } else {
                    holder = (FinishedGamesViewHolder) convertView.getTag();
                }

                UserGame rowItem = (UserGame) getItem(position);

                holder.text1.setText(toProperCase(rowItem.getMode()) + " - " + rowItem.getSpanishCategoriesType());

                if (!rowItem.allPlayersRejected()) {
                    String namesToShow = "";
                    for (Player player : rowItem.getPlayers())
                        namesToShow += player.getName() + " - ";

                    namesToShow = namesToShow.substring(0, namesToShow.lastIndexOf(" - "));
                    holder.text2.setText(namesToShow);
                }else
                    holder.text2.setText("Todos rechazaron");

                View.OnClickListener deleteFinishedGameListener = new View.OnClickListener() {

                    UserGame s = (UserGame) games.get(position);

                    @Override
                    public void onClick(View v) {
                        new DeleteFinishedGameAsyncTask().execute(s);
                    }
                };

                holder.imgDelete.setOnClickListener(deleteFinishedGameListener);

                return convertView;
            }

            private class DeleteFinishedGameAsyncTask extends AsyncTask<UserGame, Void, Void> {
                TuttiFruttiAPI api;
                UserGame ug;
                boolean connError;

                protected void onPreExecute() {
                    api = new TuttiFruttiAPI(getString(R.string.server_url));
                }

                @Override
                protected Void doInBackground(UserGame... userGames) {
                    ug = userGames[0];
                    fbId = FacebookHelper.getUserId(getApplicationContext());
                    try{
                        api.deleteFinishedGame(fbId, ug.getGameId());
                    }catch (ResourceAccessException ex)
                    {
                        this.connError = true;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (this.connError)
                    {
                        Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
                    }else {
                        GamesAdapter.this.games.remove(ug);
                        Object lastElement=GamesAdapter.this.games.get(GamesAdapter.this.games.size()-1);
                        if(lastElement instanceof String) //if it only remains the header -> we remove it
                            GamesAdapter.this.games.remove(lastElement);

                        GamesAdapter.this.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
