package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuttifrutti.app.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseControlledCategoriesActivity extends Activity {
    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_controlled_categories);

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Category category = (Category) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + category,
                        Toast.LENGTH_LONG).show();
            }
        });

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
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
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

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Category> categoryList = dataAdapter.categoryList;
                for(int i=0;i<categoryList.size();i++){
                    Category category = categoryList.get(i);
                    if(category.isSelected()){
                        responseText.append("\n" + category.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

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
}
