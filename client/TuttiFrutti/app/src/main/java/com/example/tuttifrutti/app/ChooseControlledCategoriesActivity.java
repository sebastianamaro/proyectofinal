package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.tuttifrutti.app.Classes.CreateGameAsyncTask;
import com.example.tuttifrutti.app.Classes.FacebookHelper;

import java.util.ArrayList;

public class ChooseControlledCategoriesActivity extends Activity {
    MyCustomAdapter dataAdapter = null;
    Game gameSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FacebookHelper.isSessionOpened())
        {
            Intent i = new Intent(getApplicationContext(), AndroidFacebookConnectActivity.class);
            startActivity(i);
        }

        setTitle("");
        setContentView(R.layout.activity_choose_controlled_categories);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

        //Generate list View from ArrayList
        displayListView();
    }

    private void displayListView() {
       new GetCategoriesAsyncTask().execute();
    }


    public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>> {

        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {
            return api.getFixedCategories();
        }

        TuttiFruttiAPI api;

        protected void onPreExecute() {
            api = new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {
            //create an ArrayAdaptar from the String Array
            dataAdapter = new MyCustomAdapter(getApplicationContext(), R.layout.list_row_controlled_category, result);
            ListView listView = (ListView) findViewById(R.id.categoryList);
            listView.setAdapter(dataAdapter);
        }
    }

    private class MyCustomAdapter extends ArrayAdapter<Category> {

        private ArrayList<Category> categoryList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Category> categoryList) {
            super(context, textViewResourceId, categoryList);
            this.categoryList = new ArrayList<Category>();
            this.categoryList.addAll(categoryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_row_controlled_category, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textViewTitle);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Category category = (Category) cb.getTag();
                        category.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category category = categoryList.get(position);
            holder.name.setText(category.getName());
            holder.name.setChecked(category.isSelected());
            holder.name.setTag(category);

            return convertView;
        }
    }

    public void addCategoriesToGame(View v)
    {
        ArrayList<Category> categoryList = dataAdapter.categoryList;
        ArrayList<Category> selectedCategories  = new ArrayList<Category>();

        for (Category c : categoryList)
            if(c.isSelected())
                selectedCategories.add(c);


        if (selectedCategories.size() >= 4) {
            gameSettings.setSelectedCategories(selectedCategories);
            gameSettings.setOwner(new Player(FacebookHelper.getUserId()));
            CreateGameControlledCategoriesAsyncTask task = new CreateGameControlledCategoriesAsyncTask(getString(R.string.server_url),this);
            task.execute(gameSettings);

        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar al menos 4 categorias", Toast.LENGTH_LONG).show();
        }
    }


    public class CreateGameControlledCategoriesAsyncTask extends CreateGameAsyncTask{

        public CreateGameControlledCategoriesAsyncTask(String serverUrl, Activity redirectionActivity)
        {
            super(serverUrl,redirectionActivity);
        }


        @Override
        protected void onPostExecute(Void result) {

            ad.setCancelable(false); // This blocks the 'BACK' button
            ad.setMessage("Se ha creado la partida!");
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            ad.show();
        }
    }
}
