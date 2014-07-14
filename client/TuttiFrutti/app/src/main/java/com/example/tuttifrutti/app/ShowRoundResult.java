package com.example.tuttifrutti.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
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

        ScrollView sv = new ScrollView(this);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        TableLayout table = (TableLayout)findViewById(R.id.resultsTable);

        TableRow row=new TableRow(this.getApplicationContext());

        table.setGravity(Gravity.TOP);

        for (String p : players)
        {
            TextView text=new TextView(this.getApplicationContext());

            text.setText(p);
            row.addView(text);
        }

        table.addView(row);
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
