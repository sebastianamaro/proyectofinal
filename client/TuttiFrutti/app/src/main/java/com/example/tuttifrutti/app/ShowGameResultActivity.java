package com.example.tuttifrutti.app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.TuttiFruttiCore.GameScoreSummary;

import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.GameRoundScoreSummary;
import com.example.TuttiFruttiCore.PlayerResult;
import com.example.TuttiFruttiCore.ScoreInfo;
import com.example.TuttiFruttiAPI;

import java.util.ArrayList;

public class ShowGameResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_result);

        new GetScoresAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_game_result, menu);
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

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, GameScoreSummary> {
        private ProgressDialog Dialog = new ProgressDialog(ShowGameResultActivity.this);
        TuttiFruttiAPI api;

        @Override
        protected GameScoreSummary doInBackground(Void... filePlays) {
            //return api.getRoundScore(gameId, roundId);
            return api.getGameScores(1);
        }

        @Override
        protected void onPostExecute(GameScoreSummary result) {
            TableLayout table = (TableLayout)findViewById(R.id.resultsTable);
            table.setGravity(Gravity.TOP);

            GameRoundScoreSummary roundRes;

            TableRow contentRow;
            TableRow totalScoreRow=new TableRow(getApplicationContext());
            TableRow playersRow=new TableRow(getApplicationContext());

            AddHeaderTextView(playersRow, "Rondas");

            for (String p : result.getPlayers())
            {
                AddHeaderTextView(playersRow, p);
            }

            table.addView(playersRow);

            for (int i=0;i<result.getRoundsResult().size();i++)
            {
                contentRow=new TableRow(getApplicationContext());
                roundRes = result.getRoundsResult().get(i);
                AddHeaderTextView(contentRow, "Ronda " + Integer.toString(i+1));

                for (int j=0;j<roundRes.getScores().size();j++) {
                    ScoreInfo score = roundRes.getScores().get(j);
                    AddContentTextView(contentRow, score);
                }

                table.addView(contentRow);
            }

            AddTotalTextView(totalScoreRow, null); //la primera es la columna de las rondas
            ArrayList<ScoreInfo> playersRes = result.getPlayerResult();
            for (int k=0;k<playersRes.size();k++) {
                AddTotalTextView(totalScoreRow, playersRes.get(k));
            }

            // la ultima fila que agrego es la de los puntajes
            table.addView(totalScoreRow);

            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            Dialog.setMessage("Calculando resultados...");
            Dialog.show();
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

    private void AddHeaderTextView(TableRow row, String p) {
        TextView text=new TextView(this.getApplicationContext());
        text.setText(p);
        text.setGravity(Gravity.CENTER);
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(35, 35, 35, 35);
        text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(20);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
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
