package com.example.tuttifrutti.app;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.PlayServicesHelper;

import java.util.ArrayList;

public class ChooseControlledCategoriesActivity extends Activity {
    MyCustomAdapter dataAdapter = null;
    Game gameSettings;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_controlled_categories);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra("gameSettings");

        //Generate list View from ArrayList
        displayListView();
    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Category> categoryList = new ArrayList<Category>();
        Category category = new Category("Animales",false);
        categoryList.add(category);
        category = new Category("Colores",false);
        categoryList.add(category);
        category = new Category("Paises",false);
        categoryList.add(category);
        category = new Category("Lagos",false);
        categoryList.add(category);
        category = new Category("Constelaciones",false);
        categoryList.add(category);
        category = new Category("Marcas de Auto",false);
        categoryList.add(category);
        category = new Category("Capitales del Mundo",false);
        categoryList.add(category);


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.listview_row_category, categoryList);
        ListView listView = (ListView) findViewById(R.id.categoryList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<Category> {

        private ArrayList<Category> categoryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Category> categoryList) {
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

            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.listview_row_category, null);

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
        ArrayList<String> selectedCategories = new ArrayList<String>();
        ArrayList<Category> categoryList = dataAdapter.categoryList;
        for(int i=0;i<categoryList.size();i++){
            Category category = categoryList.get(i);
            if(category.isSelected()){
                selectedCategories.add(category.getName());
            }
        }

        if (selectedCategories.size() >= 4) {
            gameSettings.setSelectedCategories(selectedCategories);

            CreateGameTask task = new CreateGameTask();
            task.execute(gameSettings);
        }else
            Toast.makeText(getApplicationContext(),
                    "Debe seleccionar al menos 4 categorias",
                    Toast.LENGTH_LONG).show();
    }

    public class Category {

        String name = null;
        boolean selected = false;

        public Category(String name, boolean selected) {
            super();
            this.name = name;
            this.selected = selected;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

    }

    //todo: meter esto en una clase
    private class CreateGameTask extends AsyncTask<Game,Void, Void> {

        AlertDialog ad;
        TuttiFruttiAPI api;

        @Override
        protected Void doInBackground(Game... settings) {

            Game gs=settings[0];
            TuttiFruttiAPI api= new TuttiFruttiAPI(getString(R.string.server_url));

            PlayServicesHelper helper = new PlayServicesHelper(MainActivity.class.getSimpleName());
            String regid = "";
            if (helper.checkPlayServices(ChooseControlledCategoriesActivity.this))
            {
                regid = helper.getRegistrationId(getApplicationContext());
                if (regid == "")
                    helper.registerGCMInBackground(getApplicationContext());
            }

            gs.setOwner(new Player(regid));
            api.createGame(gs);
            return null;
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

        @Override
        protected void onPreExecute(){
            ad=new AlertDialog.Builder(ChooseControlledCategoriesActivity.this).create();

            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }
}
