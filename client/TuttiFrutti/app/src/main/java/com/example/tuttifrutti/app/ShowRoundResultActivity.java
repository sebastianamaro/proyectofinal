package com.example.tuttifrutti.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.PlayerRoundScoreSummary;
import com.example.TuttiFruttiCore.RoundScoreSummary;
import com.example.TuttiFruttiCore.PlayScoreSummary;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import java.util.ArrayList;

public class ShowRoundResultActivity extends ActionBarActivity {
    UserGame gameInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FacebookHelper.isSessionOpened())
        {
            Intent i = new Intent(getApplicationContext(), AndroidFacebookConnectActivity.class);
            startActivity(i);
        }

        setTitle("");
        setContentView(R.layout.activity_show_round_result);

        Intent intent = getIntent();
        gameInfo = (UserGame)intent.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE);

        new GetScoresAsyncTask().execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, PlayerRoundScoreSummary> {
        private ProgressDialog Dialog = new ProgressDialog(ShowRoundResultActivity.this);
        TuttiFruttiAPI api;

        @Override
        protected PlayerRoundScoreSummary doInBackground(Void... filePlays) {
            String fbId = FacebookHelper.getUserId();
            return api.getRoundScore(gameInfo.getGameId(), fbId);
        }

        @Override
        protected void onPostExecute(PlayerRoundScoreSummary result) {

            TextView txtTitle = (TextView) findViewById(R.id.roundResultTitle);
            if (result.getIsComplete())
                txtTitle.setText("Resultados de la ronda " + String.valueOf(result.getRoundNumber()));
            else
                txtTitle.setText("Resultados parciales de la ronda " + String.valueOf(result.getRoundNumber()));

            //caso1: jugue y NO se termino la ronda -> sin boton jugar
            //caso2: jugue y se termino la ronda -> con boton PROXIMA ronda
            //caso2: NO jugue la ronda actual ->  boton ESTA ronda
            Button btnJugar = (Button) findViewById(R.id.btnPlayNextRound);
            if (!result.getCanPlayerPlay())
                btnJugar.setEnabled(false);

            String[] categories = new String[result.getRoundScoreSummaries().get(0).getPlays().size()];

            int k = 0;

            for (PlayScoreSummary onePlay:result.getRoundScoreSummaries().get(0).getPlays())
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
            AddTotalTextView(totalScoreRow, -1);

            for (int i=0;i<categories.length;i++)
            {
                contentRow=new TableRow(getApplicationContext());
                AddHeaderTextView(contentRow, categories[i]);
                for (int j=0;j<result.getRoundScoreSummaries().size();j++) {
                    //si estoy en la primera categoria, aprovecho la recorrida de las lines y lleno los players y el score de la ronda x cada player
                    if (i==0) {
                        AddHeaderTextView(playersRow, result.getRoundScoreSummaries().get(j).getPlayer().getName());
                        if(result.getIsComplete())
                            AddTotalTextView(totalScoreRow, result.getRoundScoreSummaries().get(j).getScoreInfo().getScore());
                    }

                    //en cada interacion, obtengo la play de la line (j), correspondiente a la categoria (i)
                    PlayScoreSummary linePlayForCategory = result.getRoundScoreSummaries().get(j).getPlays().get(i);
                    AddContentTextView(contentRow, linePlayForCategory, result.getIsComplete(), result.getRoundScoreSummaries().get(j).getPlayer().getFbId());
                }

                //si estoy en la primera categoria, agrego el header con los players antes de las categorias con sus valores
                if (i==0)
                    table.addView(playersRow);

                table.addView(contentRow);
            }

            // la ultima fila que agrego es la de los puntajes
            if(result.getIsComplete())
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

    private void AddContentTextView(TableRow row, final PlayScoreSummary playScoreSummary, boolean isComplete, final String fbId) {
        LinearLayout layout = new LinearLayout(this.getApplicationContext());
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundResource(R.drawable.cell_shape);

        TextView text=new TextView(getApplicationContext());
        if (playScoreSummary.getWord().isEmpty()) {
            text.setGravity(Gravity.CENTER);
            text.setText("-");
        }
        else
            text.setText(playScoreSummary.getWord());
        text.setPadding(33, 33, 10, 33);
        text.setTextColor(Color.parseColor(getColorForScore(playScoreSummary.getScoreInfo().getScore())));
        text.setTextSize(25);
        text.setTypeface(null, Typeface.NORMAL);
        layout.addView(text);

        TextView score=new TextView(this.getApplicationContext());
        if (isComplete) {

            if (!playScoreSummary.getWord().isEmpty())
                score.setText(Integer.toString(playScoreSummary.getScoreInfo().getScore()));

            score.setPadding(10, 40, 33, 26);
            score.setTextColor(Color.parseColor(getColorForScore(playScoreSummary.getScoreInfo().getScore())));
            score.setTextSize(15);
            score.setTypeface(null, Typeface.NORMAL);
            layout.addView(score);
        }

        String myFbId = FacebookHelper.getUserId();

        if (!playScoreSummary.isFixed() && !playScoreSummary.isValidated() && !myFbId.equals(fbId) && !playScoreSummary.getWord().isEmpty()) {
            //aca primero preguntar si es libre Y si NO la mande ya Y si no es la mia Y si no esta vacia
            ImageView imgOk = new ImageView(this.getApplicationContext());
            imgOk.setImageResource(R.drawable.qualification_ok);
            imgOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new SetQualificationAsyncTask(true, playScoreSummary.getCategory(), fbId).execute();
                    }catch (Exception ex)
                    {
                        Log.e("LALA", ex.getMessage());
                    }

                }
            });

            layout.addView(imgOk);

            ImageView imgNo = new ImageView(this.getApplicationContext());
            imgOk.setImageResource(R.drawable.qualification_no);
            imgOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new SetQualificationAsyncTask(false, playScoreSummary.getCategory(), fbId).execute();
                    }catch (Exception ex)
                    {
                        Log.e("LALA", ex.getMessage());
                    }

                }
            });

            layout.addView(imgNo);
        }


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
        else if (playScore == ScoresForPlay.LATE.getValue())
            color = "#999966"; //gris

        return color;
    }

    public void playNextRound(View v)
    {
        Intent intent = new Intent(getApplicationContext(), PlayRoundActivity.class);
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, gameInfo.getGameId());

        startActivity(intent);
    }

    public class SetQualificationAsyncTask extends AsyncTask<Void, Void, Void>
    {
        TuttiFruttiAPI api;
        boolean isValid;
        String category;
        String judgedPlayer;

        public SetQualificationAsyncTask(boolean isValid, String category, String judgedPlayer)
        {
            this.isValid = isValid;
            this.category = category;
            this.judgedPlayer = judgedPlayer;
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Void... smth) {
            api.sendQualification(FacebookHelper.getUserId(), this.isValid, this.category, this.judgedPlayer, gameInfo.getGameId());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //aca ocultar las imagenes
        }
    }

    public enum ScoresForPlay
    {
        INVALID(0),
        LATE(-1),
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
