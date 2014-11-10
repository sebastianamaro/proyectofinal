package com.example.tuttifrutti.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.GameScoreSummary;

import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.GameRoundScoreSummary;
import com.example.TuttiFruttiCore.PlayerResult;
import com.example.TuttiFruttiCore.ScoreInfo;
import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.UserGame;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;

public class ShowGameResultActivity extends ActionBarActivity {
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_result);
        setTitle("");
        dialog=new ProgressDialog(this);
        Intent intent = getIntent();
        //UserGame game = (UserGame)intent.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE);
        int gameId = intent.getIntExtra(Constants.GAME_ID_EXTRA_MESSAGE, -1);
        new GetScoresAsyncTask(gameId).execute();
    }

    @Override
    public void onBackPressed() {
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        super.onBackPressed();
    }

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, GameScoreSummary> {

        TuttiFruttiAPI api;
        int gameId;
        boolean connError;

        public GetScoresAsyncTask(int gameId)
        {
            this.gameId = gameId;
        }

        @Override
        protected GameScoreSummary doInBackground(Void... filePlays) {
            try {
                return api.getGameScores(this.gameId);
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
                return null;
            }
        }

        @Override
        protected void onPostExecute(GameScoreSummary result) {
            if (this.connError) {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            } else {
                TableLayout table = (TableLayout) findViewById(R.id.resultsTable);
                table.setGravity(Gravity.TOP);

                GameRoundScoreSummary roundRes;

                TableRow contentRow;
                TableRow totalScoreRow = new TableRow(getApplicationContext());
                TableRow playersRow = new TableRow(getApplicationContext());

                AddHeaderTextView(playersRow, "Rondas");

                for (String p : result.getPlayers()) {
                    AddHeaderTextView(playersRow, p);
                }

                table.addView(playersRow);
                //las rondas vienen al reves, primero la ultima y ultima la primera
                for (int i = result.getRoundsResult().size() - 1; i >= 0; i--) {
                    contentRow = new TableRow(getApplicationContext());
                    roundRes = result.getRoundsResult().get(i);
                    AddRoundHeaderTextView(contentRow, roundRes, gameId);

                    for (int j = 0; j < roundRes.getScores().size(); j++) {
                        ScoreInfo score = roundRes.getScores().get(j);
                        AddContentTextView(contentRow, score);
                    }

                    table.addView(contentRow);
                }

                AddTotalTextView(totalScoreRow, null); //la primera es la columna de las rondas
                ArrayList<ScoreInfo> playersRes = result.getPlayerResult();
                for (int k = 0; k < playersRes.size(); k++) {
                    AddTotalTextView(totalScoreRow, playersRes.get(k));
                }

                // la ultima fila que agrego es la de los puntajes
                table.addView(totalScoreRow);

            }
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            if(dialog.isShowing())
                dialog.dismiss();

            dialog.setMessage("Calculando resultados...");
            dialog.setCancelable(false);
            dialog.show();
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }

    private void AddTotalTextView(TableRow row, ScoreInfo p) {
        TextView text=new TextView(this.getApplicationContext());
        if (p==null)
            text.setText("TOTAL");
        else
            text.setText(Integer.toString(p.getScore()));
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(40, 40, 40, 40);
        if (p!=null && p.getBest())
            text.setTextColor(Color.parseColor("#10B51B"));
        else
            text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(20);
        text.setTypeface(null, Typeface.BOLD);
        text.setGravity(Gravity.CENTER);

        row.addView(text);
    }

    private void AddHeaderTextView (TableRow row, String haderText)
    {
        TextView text = new TextView(this.getApplicationContext());
        text.setText(haderText);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(35, 35, 35, 35);
        text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(20);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
    }

    private void AddRoundHeaderTextView(TableRow row, GameRoundScoreSummary roundScoreSummary, int gameId) {
        final int fGameId = gameId;
        final int froundId = roundScoreSummary.getRoundId();

        Button b = new Button(this.getApplicationContext());
        b.setId(froundId);
        b.setText("Ronda " + roundScoreSummary.getRoundLetter());
        b.setBackgroundResource(R.drawable.smallbutton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShowRoundResultActivity.class);
                i.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, fGameId);
                i.putExtra(Constants.ROUND_ID_EXTRA_MESSAGE, froundId);

                 MoveToAnotherActivity(i);
            }
        });

        RelativeLayout rl = new RelativeLayout(this.getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(270,110);
        rl.setBackgroundResource(R.drawable.cell_shape);
        rl.setPadding(20, 20, 20, 20);

        rl.addView(b,lp);

        row.addView(rl);
        //row.addView(b);
    }


    public void MoveToAnotherActivity(Intent intent){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        startActivity(intent);
    }

    private void AddContentTextView(TableRow row, ScoreInfo p) {
        TextView text=new TextView(getApplicationContext());

        if (p==null)
            text.setText("-");
        else
            text.setText(Integer.toString(p.getScore()));

        text.setPadding(35, 35, 35, 35);
        if (p!=null && p.getBest())
            text.setTextColor(Color.parseColor("#10B51B"));
        else
            text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(20);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setTypeface(null, Typeface.NORMAL);

        row.addView(text);
    }
}
