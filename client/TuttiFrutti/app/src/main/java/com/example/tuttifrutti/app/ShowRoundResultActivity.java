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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.PlayerRoundScoreSummary;
import com.example.TuttiFruttiCore.RoundScoreSummary;
import com.example.TuttiFruttiCore.PlayScoreSummary;
import com.example.TuttiFruttiCore.ScoreInfo;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;

public class ShowRoundResultActivity extends ActionBarActivity {
    int gameId;
    int roundId;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("");
        setContentView(R.layout.activity_show_round_result);
        dialog=new ProgressDialog(this);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE) != null) {
            UserGame userGame = ((UserGame) intent.getSerializableExtra(Constants.GAME_INFO_EXTRA_MESSAGE));
            gameId = userGame.getGameId();
            roundId = -1;
        }
        else
        {
            gameId = intent.getIntExtra(Constants.GAME_ID_EXTRA_MESSAGE, -1);
            roundId = intent.getIntExtra(Constants.ROUND_ID_EXTRA_MESSAGE, -1);
            Button btnJugar = (Button) findViewById(R.id.btnPlayNextRound);
            btnJugar.setVisibility(View.GONE);
            Button btnGameResults= (Button) findViewById(R.id.btnSeeGameResults);
            btnGameResults.setVisibility(View.GONE);
        }

        new GetScoresAsyncTask().execute();
    }

    @Override
    public void onBackPressed() {

        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        if (roundId == -1) {
            Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
            MoveToAnotherActivity(intent);
        }
        super.onBackPressed();
    }


    public void MoveToAnotherActivity(Intent intent){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();

        startActivity(intent);
    }

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, PlayerRoundScoreSummary> {
        TuttiFruttiAPI api;
        boolean connError;

        @Override
        protected PlayerRoundScoreSummary doInBackground(Void... filePlays) {
            String fbId = FacebookHelper.getUserId(getApplicationContext());
            try{
                if (roundId == -1)
                    return api.getRoundScore(gameId, fbId);
                else
                    return api.getRoundScore(gameId, roundId);
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
                return null;
            }
        }

        @Override
        protected void onPostExecute(PlayerRoundScoreSummary result) {
            if (this.connError) {

                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            } else {

                //caso1: jugue y NO se termino la ronda -> sin boton jugar
                //caso2: jugue y se termino la ronda -> con boton PROXIMA ronda
                //caso2: NO jugue la ronda actual ->  boton ESTA ronda
                Button btnJugar = (Button) findViewById(R.id.btnPlayNextRound);
                if (!result.getCanPlayerPlay()) {
                    btnJugar.setEnabled(false);
                    if (!result.getGameStatus().isEmpty())
                    {
                        TextView lblStatus = (TextView) findViewById(R.id.lblStatus);
                        if(result.getGameStatus().equals(Constants.GAME_STATUS_WAITINGFORQUALIF))
                        {
                            lblStatus.setText("Esperando todas las calificaciones");
                        }else if(result.getGameStatus().equals(Constants.GAME_STATUS_SHOWINGRESULTS))
                        {
                            lblStatus.setText("Esperando a que todos vean los resultados");
                        }
                        else if(result.getGameStatus().equals(Constants.GAME_STATUS_PLAYING))
                        {
                            lblStatus.setText("Esperando a que todos jueguen");
                        }
                        else if(result.getGameStatus().equals(Constants.GAME_STATUS_WAITINGFORNEXTROUND))
                        {
                            lblStatus.setText("Esperando a que se inice la próxima ronda. Tocá jugar para empezarla!");
                        }
                    }
                }
                if (result.getRoundNumber() == 1 && !result.getIsComplete()) {
                    Button btnGameResults = (Button) findViewById(R.id.btnSeeGameResults);
                    btnGameResults.setVisibility(View.GONE);
                }

                String[] categories = new String[result.getRoundScoreSummaries().get(0).getPlays().size()];

                int k = 0;

                for (PlayScoreSummary onePlay : result.getRoundScoreSummaries().get(0).getPlays()) {
                    categories[k] = onePlay.getCategory();
                    k++;
                }

                TableLayout table = (TableLayout) findViewById(R.id.resultsTable);
                table.setGravity(Gravity.TOP);

                TableRow contentRow;
                TableRow totalScoreRow = new TableRow(getApplicationContext());
                TableRow playersRow = new TableRow(getApplicationContext());

                AddHeaderTextView(playersRow, "Categorias");
                AddTotalTextView(totalScoreRow, null);

                for (int i = 0; i < categories.length; i++) {
                    contentRow = new TableRow(getApplicationContext());
                    AddHeaderTextView(contentRow, categories[i]);
                    for (int j = 0; j < result.getRoundScoreSummaries().size(); j++) {
                        //si estoy en la primera categoria, aprovecho la recorrida de las lines y lleno los players y el score de la ronda x cada player
                        if (i == 0) {
                            AddHeaderTextView(playersRow, result.getRoundScoreSummaries().get(j).getPlayer().getName());
                            if (result.getIsComplete())
                                AddTotalTextView(totalScoreRow, result.getRoundScoreSummaries().get(j).getScoreInfo());
                        }

                        //en cada interacion, obtengo la play de la line (j), correspondiente a la categoria (i)
                        PlayScoreSummary linePlayForCategory = result.getRoundScoreSummaries().get(j).getPlays().get(i);
                        AddContentTextView(contentRow, linePlayForCategory, result.getIsComplete(), result.getRoundLetter(), result.getRoundScoreSummaries().get(j).getPlayer().getFbId());
                    }

                    //si estoy en la primera categoria, agrego el header con los players antes de las categorias con sus valores
                    if (i == 0)
                        table.addView(playersRow);

                    table.addView(contentRow);
                }

                // la ultima fila que agrego es la de los puntajes
                if (result.getIsComplete())
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

    private void AddTotalTextView(TableRow row, ScoreInfo scoreInfo) {
        TextView text=new TextView(this.getApplicationContext());
        if (scoreInfo != null)
            text.setText(Integer.toString(scoreInfo.getScore()));
        else
            text.setText("TOTAL");
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(50, 50, 50, 50);
        if (scoreInfo != null && scoreInfo.getBest())
            text.setTextColor(Color.parseColor("#10B51B"));
        else
            text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(15);
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
        text.setTextSize(15);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
    }

    private void AddContentTextView(TableRow row, final PlayScoreSummary playScoreSummary, boolean isComplete, String roundLetter ,final String fbId) {
        LinearLayout layout = new LinearLayout(this.getApplicationContext());
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundResource(R.drawable.cell_shape);
        String myFbId = FacebookHelper.getUserId(getApplicationContext());

        TextView text=new TextView(getApplicationContext());
        if (playScoreSummary.getWord().isEmpty()) {
            text.setGravity(Gravity.CENTER);
            text.setText("-");
        }
        else
            text.setText(playScoreSummary.getWord());
        text.setPadding(33, 33, 10, 33);
        text.setTextColor(Color.parseColor(getColorForScore(playScoreSummary.getScoreInfo().getScore())));
        text.setTextSize(20);
        text.setTypeface(null, Typeface.NORMAL);

        TextView score= null;
        ImageView imgReport=null;
        if (isComplete) {
            score = new TextView(this.getApplicationContext());
            if (!playScoreSummary.getWord().isEmpty()) {
                if (playScoreSummary.getScoreInfo().getScore() == -1)
                    score.setText("Tarde");
                else
                    score.setText(Integer.toString(playScoreSummary.getScoreInfo().getScore()));
            }

            score.setPadding(10, 40, 33, 26);
            score.setTextColor(Color.parseColor(getColorForScore(playScoreSummary.getScoreInfo().getScore())));
            score.setTextSize(10);
            score.setTypeface(null, Typeface.NORMAL);
            //layout.addView(score);

            if (myFbId.equals(fbId) && playScoreSummary.getScoreInfo().getScore() == ScoresForPlay.INVALID.getValue()
                    && playScoreSummary.isFixed() && !playScoreSummary.getWord().isEmpty()
                    && Character.toUpperCase(playScoreSummary.getWord().charAt(0)) == Character.toUpperCase(roundLetter.charAt(0)))
            {
                imgReport = new ImageView(this.getApplicationContext());
                imgReport.setImageResource(R.drawable.attention2_small);
                imgReport.setPadding(10, 30, 25, 26);

                imgReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            new ReportWordAsValidAsyncTask(playScoreSummary.getCategory(), playScoreSummary.getWord(), view).execute();
                        } catch (Exception ex) {
                            Log.e("LALA", ex.getMessage());
                        }

                    }
                });
            }
        }

        if (!playScoreSummary.isFixed() && !playScoreSummary.isValidated() && !myFbId.equals(fbId) && !playScoreSummary.getWord().isEmpty()) {
            final ImageView imgOk = new ImageView(this.getApplicationContext());
            imgOk.setImageResource(R.drawable.qualification_ok);
            imgOk.setPadding(10, 40, 25, 26);

            final ImageView imgNo = new ImageView(this.getApplicationContext());
            imgNo.setImageResource(R.drawable.qualification_no);
            imgNo.setPadding(25, 40, 2, 26);

            imgOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new SetQualificationAsyncTask(true, playScoreSummary.getCategory(), fbId, view, imgNo).execute();
                    } catch (Exception ex) {
                        Log.e("LALA", ex.getMessage());
                    }

                }
            });

            imgNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new SetQualificationAsyncTask(false, playScoreSummary.getCategory(), fbId, imgOk, view).execute();
                    }catch (Exception ex)
                    {
                        Log.e("LALA", ex.getMessage());
                    }

                }
            });

            layout.addView(imgNo);
            layout.addView(text);
            if (score != null)
                layout.addView(score);
            layout.addView(imgOk);

        }else
        {
            layout.addView(text);
            if (score != null)
                layout.addView(score);
            if (imgReport != null)
                layout.addView(imgReport);

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
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, gameId);

        MoveToAnotherActivity(intent);
    }

    public void seeGameResults(View v)
    {
        Intent intent = new Intent(getApplicationContext(), ShowGameResultActivity.class);
        intent.putExtra(Constants.GAME_ID_EXTRA_MESSAGE, gameId);

        MoveToAnotherActivity(intent);
    }

    public class SetQualificationAsyncTask extends AsyncTask<Void, Void, Void>
    {
        TuttiFruttiAPI api;
        boolean isValid;
        String category;
        String judgedPlayer;
        View imgYes;
        View imgNo;
        boolean connError;


        public SetQualificationAsyncTask(boolean isValid, String category, String judgedPlayer, View imgYes, View imgNo)
        {
            this.isValid = isValid;
            this.category = category;
            this.judgedPlayer = judgedPlayer;
            this.imgNo = imgNo;
            this.imgYes = imgYes;
        }

        protected void onPreExecute(){
            if(dialog.isShowing())
                dialog.dismiss();

            dialog.setMessage("Enviando calificación...");
            dialog.setCancelable(false);
            dialog.show();
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Void... smth) {
            try
            {
                api.sendQualification(FacebookHelper.getUserId(getApplicationContext()), this.isValid, this.category, this.judgedPlayer, gameId);
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
                this.imgYes.setVisibility(View.GONE);
                this.imgNo.setVisibility(View.GONE);

            }
            dialog.dismiss();
        }
    }

    public class ReportWordAsValidAsyncTask extends AsyncTask<Void, Void, Void>
    {
        TuttiFruttiAPI api;
        String category;
        String word;
        View imgReport;
        boolean connError;


        public ReportWordAsValidAsyncTask(String category, String word, View imgReport)
        {
            this.category = category;
            this.word = word;
            this.imgReport = imgReport;
        }

        protected void onPreExecute(){
            if(dialog.isShowing())
                dialog.dismiss();

            dialog.setMessage("Enviando reporte...");
            dialog.setCancelable(false);
            dialog.show();
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Void... smth) {
            try
            {
                api.reportWordAsValid(this.category, this.word);
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
                this.imgReport.setVisibility(View.GONE);

            }
            dialog.dismiss();
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
