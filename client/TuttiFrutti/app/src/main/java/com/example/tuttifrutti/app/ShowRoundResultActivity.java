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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.RoundScoreSummary;
import com.example.TuttiFruttiCore.PlayScoreSummary;

import java.util.ArrayList;

public class ShowRoundResultActivity extends ActionBarActivity {

    int gameId=1;
    int roundId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_round_result);

       new GetScoresAsyncTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_round_result, menu);
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

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, ArrayList<RoundScoreSummary>> {
        private ProgressDialog Dialog = new ProgressDialog(ShowRoundResultActivity.this);
        TuttiFruttiAPI api;

        @Override
        protected ArrayList<RoundScoreSummary> doInBackground(Void... filePlays) {
            return api.getRoundScore(gameId, roundId);
        }

        @Override
        protected void onPostExecute(ArrayList<RoundScoreSummary> result) {

            String[] categories = new String[result.get(0).getPlays().size()];

            int k = 0;

            for (PlayScoreSummary onePlay:result.get(0).getPlays())
            {
                categories[k] = onePlay.getCategory();
                k++;
            }

            TableLayout table = (TableLayout)findViewById(R.id.resultsTable);
            table.setGravity(Gravity.TOP);

            TableRow contentRow;
            TableRow totalScoreRow=new TableRow(getApplicationContext());
            TableRow playersRow=new TableRow(getApplicationContext());

            AddHeaderTextView(playersRow, "Categorias");
            AddTotalTextView(totalScoreRow, -1); //la primera es la columna de las categorias

            for (int i=0;i<categories.length;i++)
            {
                contentRow=new TableRow(getApplicationContext());
                AddHeaderTextView(contentRow, categories[i]);
                for (int j=0;j<result.size();j++) {
                    //si estoy en la primera categoria, aprovecho la recorrida de las lines y lleno los players y el score de la ronda x cada player
                    if (i==0) {
                        AddHeaderTextView(playersRow, result.get(j).getPlayer().getName());
                        AddTotalTextView(totalScoreRow, result.get(j).getScoreInfo().getScore());
                    }

                    //en cada interacion, obtengo la play de la line (j), correspondiente a la categoria (i)
                    PlayScoreSummary linePlayForCategory = result.get(j).getPlays().get(i);
                    AddContentTextView(contentRow, linePlayForCategory.getWord(), linePlayForCategory.getScoreInfo().getScore());
                }

                //si estoy en la primera categoria, agrego el header con los players antes de las categorias con sus valores
                if (i==0)
                    table.addView(playersRow);

                table.addView(contentRow);
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

    private void AddTotalTextView(TableRow row, int p) {
        TextView text=new TextView(this.getApplicationContext());
        if (p != -1)
            text.setText(Integer.toString(p));
        else
            text.setText("TOTAL");
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(50, 50, 50, 50);
        text.setTextColor(Color.parseColor("#10B51B"));
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
        text.setPadding(40, 40, 40, 40);
        text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(20);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
    }

    private void AddContentTextView(TableRow row, String p, int PlayScore) {
        LinearLayout layout = new LinearLayout(this.getApplicationContext());
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundResource(R.drawable.cell_shape);

        TextView text=new TextView(getApplicationContext());
        if (p.isEmpty()) {
            text.setGravity(Gravity.CENTER);
            text.setText("-");
        }
        else
            text.setText(p);
        text.setPadding(33, 33, 10, 33);
        text.setTextColor(Color.parseColor(getColorForScore(PlayScore)));
        text.setTextSize(25);
        text.setTypeface(null, Typeface.NORMAL);
        layout.addView(text);

        TextView score=new TextView(this.getApplicationContext());
        if (!p.isEmpty())
            score.setText(Integer.toString(PlayScore));
        score.setPadding(10, 40, 33, 26);
        score.setTextColor(Color.parseColor(getColorForScore(PlayScore)));
        score.setTextSize(15);
        score.setTypeface(null, Typeface.NORMAL);
        layout.addView(score);

        row.addView(layout);
    }

    private String getColorForScore(int playScore) {
        String color = null;
        if (playScore == ScoresForPlay.INVALID.getValue())
            color = "#ED020E"; //rojo
        else if (playScore == ScoresForPlay.ONLY.getValue())
            color ="#10B51B";//verde
        else if (playScore == ScoresForPlay.UNIQUE.getValue())
            color = "#6B078F"; //violeta
        else if (playScore == ScoresForPlay.REPEATED.getValue())
            color = "#0000FF"; //azul

        return color;
    }

    public enum ScoresForPlay
    {
        INVALID(0),
        REPEATED(5),
        UNIQUE(10),
        ONLY(20);

        private final int value;

        private ScoresForPlay(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
