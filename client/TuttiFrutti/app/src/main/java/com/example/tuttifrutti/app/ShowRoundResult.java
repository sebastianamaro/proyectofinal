package com.example.tuttifrutti.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import com.example.tuttifrutti.app.R;

public class ShowRoundResult extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_round_result);

        //todo: recibir esto de la API
        String[] players= new String[5];
        players[0] = "Magui";
        players[1] = "Seba";
        players[2] = "Martu";
        players[3] = "Kevin";
        players[4] = "Nitu";

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

        TableRow row=new TableRow(this.getApplicationContext());
        table.setGravity(Gravity.TOP);

        AddHeaderTextView(row, "Categorias");
        //todo: esto deberia estar adentro de la recorrida de las lines de la primera categoria
        for (String p : players)
        {
            AddHeaderTextView(row, p);
        }

        table.addView(row);

        TableRow totalScoreRow=new TableRow(this.getApplicationContext());
        TableRow playersRow=new TableRow(this.getApplicationContext());

        for (int i=0;i<categories.length;i++)
        {
            row=new TableRow(this.getApplicationContext());
                AddHeaderTextView(row, categories[i]);
                //todo: recorrer las lines!!!!
                //for (int j=0;j<lines.length;j++) {
                for (int j=0;j<players.length;j++) {

                    //todo: si i==0 llenar la fila de players:
                    if (i==0)
                        AddHeaderTextView(playersRow, "Nitu");
                    //AddTotalTextView(totalScoreRow, line[j].player);

                    //todo: si i==0 llenar la fila de scores:
                    if (i==0)
                        AddTotalTextView(totalScoreRow, "180");
                        //AddTotalTextView(totalScoreRow, line[j].totalScore);

                    AddContentTextView(row, "valor");

                    //todo: AddContentTextView(row, line[j].plays[i]);
                    //line[playerLine].plays[category]
                }

            table.addView(row);
        }

        table.addView(totalScoreRow);

        //todo: agregar la row de scores


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

    private void AddContentTextView(TableRow row, String p) {
        LinearLayout layout = new LinearLayout(this.getApplicationContext());
        layout.setBackgroundResource(R.drawable.cell_shape);

        TextView text=new TextView(this.getApplicationContext());
        text.setText(p);
        text.setPadding(51, 51, 51, 51);
        text.setTextColor(Color.parseColor("#C96609"));
        text.setTextSize(15);
        text.setTypeface(null, Typeface.NORMAL);
        layout.addView(text);

        TextView score=new TextView(this.getApplicationContext());
        score.setText("20");
        text.setPadding(50, 50, 3, 50);
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
}
