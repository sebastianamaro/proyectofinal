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

import com.example.TuttiFruttiCore.GameResult;
import com.example.Line;
import com.example.Play;
import com.example.Player;
import com.example.TuttiFruttiCore.RoundResult;
import com.example.TuttiFruttiCore.RoundScore;
import com.example.TuttiFruttiAPI;
import com.example.tuttifrutti.app.Classes.RoundResult;

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

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, GameResult> {
        private ProgressDialog Dialog = new ProgressDialog(ShowGameResultActivity.this);
        TuttiFruttiAPI api;

        @Override
        protected GameResult doInBackground(Void... filePlays) {
            //return api.getRoundScore(gameId, roundId);
            return api.getGameScores(1);
        }

        @Override
        protected void onPostExecute(GameResult result) {
            TableLayout table = (TableLayout)findViewById(R.id.resultsTable);
            table.setGravity(Gravity.TOP);

            RoundResult roundRes;

            TableRow contentRow;
            TableRow totalScoreRow=new TableRow(getApplicationContext());
            TableRow playersRow=new TableRow(getApplicationContext());

            AddHeaderTextView(playersRow, "Rondas");

            for (Player p : result.getPlayers())
            {
                AddHeaderTextView(playersRow, p.getName());
            }

            table.addView(playersRow);

            //-1 porque la ultima son los scores totales
            for (int i=0;i<result.getRoundsResult().size()-1;i++)
            {
                contentRow=new TableRow(getApplicationContext());
                roundRes = result.getRoundsResult().get(i);
                AddHeaderTextView(contentRow, "Ronda " + Integer.toString(i+1));

                for (int j=0;j<roundRes.getScores().size();j++) {
                    RoundScore score = roundRes.getScores().get(j);
                    AddContentTextView(contentRow, score);
                }

                table.addView(contentRow);
            }

            AddTotalTextView(totalScoreRow, null); //la primera es la columna de las rondas
            roundRes = result.getRoundsResult().get(result.getRoundsResult().size()-1);
            for (int k=0;k<roundRes.getScores().size();k++) {
                AddTotalTextView(totalScoreRow, roundRes.getScores().get(k));
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

    private void AddTotalTextView(TableRow row, RoundScore p) {
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

    private void AddContentTextView(TableRow row, RoundScore p) {
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
