package com.example.tuttifrutti.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.UserGame;

import java.util.ArrayList;


public class ViewCategoriesActivity extends ActionBarActivity {

    ListView staredCategories;
    ListView fixedCategories;
    ListView allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        staredCategories = (ListView) findViewById(R.id.listStarCategory);
        fixedCategories = (ListView) findViewById(R.id.listFixedCategory);
        allCategories = (ListView) findViewById(R.id.listAllCategory);

        new GetCategoriesAsyncTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_categories, menu);
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

    public void reportCategory(View view) {
    }

    public void starCategory(View view) {
    }

    public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>>{

        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {
            return api.getCategories();
        }

        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {

            ArrayList<Category> staredCategories= new ArrayList<Category>();
            ArrayList<Category> fixedCategories= new ArrayList<Category>();
            ArrayList<String> categoriesNames= new ArrayList<String>();

            for(Category category : result){
                if(category.isStared())
                    staredCategories.add(category);
                else if(category.isFixed())
                    fixedCategories.add(category);

                categoriesNames.add(category.getName());

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewCategoriesActivity.this, android.R.layout.simple_dropdown_item_1line, categoriesNames);
            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.categoryText);
            textView.setAdapter(adapter);




        }

    }
}
