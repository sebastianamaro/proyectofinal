package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.UserGame;

import java.util.ArrayList;
import java.util.List;


public class ViewCategoriesActivity extends ActionBarActivity {

    ListView staredCategoriesList;
    ListView fixedCategoriesList;
    ListView allCategoriesList;
    ArrayList<Category> selectedCategories= new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        staredCategoriesList = (ListView) findViewById(R.id.listStarCategory);
        fixedCategoriesList = (ListView) findViewById(R.id.listFixedCategory);
        allCategoriesList = (ListView) findViewById(R.id.listAllCategory);

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


            //popular stared categories
            PopulateCategoriesList(staredCategories, staredCategoriesList );
            //popular fixed categories
            PopulateCategoriesList(fixedCategories, fixedCategoriesList );
            //popular todas
            PopulateCategoriesList(result, allCategoriesList );

        }

    }

    private void PopulateCategoriesList(ArrayList<Category> categories, final ListView list) {
        CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(),categories);


        // Assign adapter to ListView
        list.setAdapter(adapter);

        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//SELECT Category
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // ListView Clicked item value
                Category  itemValue    = (Category) list.getItemAtPosition(position);

                if(selectedCategories.contains(itemValue))
                {
                    Toast.makeText(getApplicationContext(),
                            "La categoria "+ itemValue.getName() + " ya esta seleccionada.", Toast.LENGTH_LONG)
                            .show();          // Show Alert
                }
                else
                    selectedCategories.add(itemValue);
            }

        });
    }

    public class CategoryAdapter extends BaseAdapter{

        Context context;
        private ArrayList<Category> categories;
        public CategoryAdapter(Context context,ArrayList<Category> categories)
        {
            this.categories=categories;
            this.context=context;
        }
        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int i) {
            return categories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        private class ViewHolder {
            ImageView starImageView;
            ImageView reportImageView;
            TextView txtDesc;
        }


        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {


            View.OnClickListener starCategoryListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "STAR Image of listItem ", Toast.LENGTH_SHORT).show();

                }

            };


            View.OnClickListener reportCategoryListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "REPORT Image of listItem ", Toast.LENGTH_SHORT).show();

                }
            };


            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.category_row, null);
                holder = new ViewHolder();
                holder.txtDesc = (TextView) convertView.findViewById(R.id.categoryListText);
                holder.starImageView = (ImageView) convertView.findViewById(R.id.ImageStar);
                holder.reportImageView = (ImageView) convertView.findViewById(R.id.ImageReport);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category rowItem = (Category) getItem(i);

            holder.txtDesc.setText(rowItem.getName());
            if(rowItem.isStared())
                holder.starImageView.setImageResource(R.drawable.icon_star);
            else
                holder.starImageView.setImageResource(R.drawable.icon_star_off);

            if(rowItem.isReported())
                 holder.reportImageView.setImageResource(R.drawable.attention2);
            else
                holder.reportImageView.setImageResource(R.drawable.attention2_off);

            holder.starImageView.setOnClickListener(starCategoryListener);
            holder.reportImageView.setOnClickListener(reportCategoryListener);



            return convertView;


        }
    }
}
