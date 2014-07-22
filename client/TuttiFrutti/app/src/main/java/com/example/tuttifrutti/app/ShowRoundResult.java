package com.example.tuttifrutti.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.LinesCollection;
import com.example.TuttiFruttiAPI;
import com.example.tuttifrutti.app.Classes.FilePlay;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;
import com.example.tuttifrutti.app.Classes.RoundResult;
import com.example.tuttifrutti.app.R;

import java.util.ArrayList;

public class ShowRoundResult extends ActionBarActivity {

    int gameId=1;
    int roundId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_round_result);

       new GetScoresAsyncTask().execute();
    }

    private void AddHeaderTextView(TableRow row, String p) {
        TextView text=new TextView(this.getApplicationContext());
        text.setText(p);
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(50, 50, 50, 50);
        text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(15);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
    }

    private void AddTotalTextView(TableRow row, String p) {
        TextView text=new TextView(this.getApplicationContext());
        text.setText(p);
        text.setBackgroundResource(R.drawable.cell_shape);
        text.setPadding(50, 50, 50, 50);
        text.setTextColor(Color.parseColor("#10B51B"));
        text.setTextSize(15);
        text.setTypeface(null, Typeface.BOLD);

        row.addView(text);
    }

    private void AddContentTextView(TableRow row, String p, String PlayScore) {
        LinearLayout layout = new LinearLayout(this.getApplicationContext());
        layout.setBackgroundResource(R.drawable.cell_shape);

        TextView text=new TextView(getApplicationContext());
        text.setText(p);
        text.setPadding(51, 51, 51, 51);
        if (PlayScore == "0")
            text.setTextColor(Color.parseColor("#ED020E"));
        else if (PlayScore == "20")
            text.setTextColor(Color.parseColor("#10B51B"));
        else
            text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(15);
        text.setTypeface(null, Typeface.NORMAL);
        layout.addView(text);

        TextView score=new TextView(this.getApplicationContext());
        score.setText( PlayScore);
        text.setPadding(50, 50, 3, 50);
        if (PlayScore == "0")
            score.setTextColor(Color.parseColor("#ED020E"));
        else
            score.setTextColor(Color.parseColor("#10B51B"));
        score.setTextSize(10);
        score.setTypeface(null, Typeface.NORMAL);
        layout.addView(score);

        row.addView(layout);
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

    public class GetScoresAsyncTask extends AsyncTask<Void,Void, ArrayList> {

        TuttiFruttiAPI api;
        @Override
        protected ArrayList doInBackground(Void... filePlays) {
            return api.getScores(gameId, roundId);
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            String[] categories= new String[9];
            categories[0] = "Animales";
            categories[1] = "Colores";
            categories[2] = "Marcas de Auto";
            categories[3] = "Frutas";
            categories[4] = "Paises";
            categories[5] = "Nombres de Telo";
            categories[6] = "Lagos de la Patagonia";
            categories[7] = "Constelaciones";
            categories[8] = "Tu vieja";

            TableLayout table = (TableLayout)findViewById(R.id.resultsTable);

            TableRow row=new TableRow(getApplicationContext());
            table.setGravity(Gravity.TOP);

            table.addView(row);

            TableRow totalScoreRow=new TableRow(getApplicationContext());
            TableRow playersRow=new TableRow(getApplicationContext());

            AddHeaderTextView(playersRow, "Categorias");
            AddTotalTextView(totalScoreRow, ""); //la primera es la columna de las categorias

            for (int i=0;i<categories.length;i++)
            {
                row=new TableRow(getApplicationContext());
                AddHeaderTextView(row, categories[i]);
                //todo: recorrer las lines!!!!
                //for (int j=0;j<lines.length;j++) {
                for (int j=0;j<5;j++) {
                    //todo: si i==0 llenar la fila de players:
                    //todo: si i==0 llenar la fila de scores totales, que lo tiene cada line:
                    if (i==0) {
                        AddHeaderTextView(playersRow, "Nitu");
                        //AddTotalTextView(totalScoreRow, line[j].player);
                        AddTotalTextView(totalScoreRow, "180");
                        //AddTotalTextView(totalScoreRow, line[j].totalScore);
                    }

                    if (j % 2 == 0)
                        AddContentTextView(row, "valor", "0");
                    else
                        AddContentTextView(row, "valor", "20");

                    //todo: AddContentTextView(row, line[j].plays[i].word, line[j].plays[i].score);
                    //line[playerLine].plays[category]
                }

                if (i==0)
                    table.addView(playersRow);

                table.addView(row);
            }

            table.addView(totalScoreRow);
        }

        @Override
        protected void onPreExecute() {
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }
}
