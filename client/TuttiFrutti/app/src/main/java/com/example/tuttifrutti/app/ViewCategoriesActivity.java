package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.CreateGameAsyncTask;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.example.tuttifrutti.app.Classes.TuttiFruttiAutoCompleteTextView;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.List;


public class ViewCategoriesActivity extends ActionBarActivity implements TokenCompleteTextView.TokenListener{

    ListView categoriesList;
    ArrayList<Category> selectedCategories= new ArrayList<Category>();
    Game gameSettings;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        setTitle("");
        categoriesList = (ListView) findViewById(R.id.categoriesList);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra("gameSettings");

        new GetCategoriesAsyncTask().execute();
    }

    public void finish(View view) {
        gameSettings.setSelectedCategories(selectedCategories);
        new CreateGameFreeCategoriesAsyncTask(getString(R.string.server_url), this).execute(gameSettings);
    }

    @Override
    public void onTokenAdded(Object token) {
        if(!selectedCategories.contains(token))
        {
            this.selectedCategories.add((Category)token);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onTokenRemoved(Object token) {
        if(this.selectedCategories.remove((Category)token))
            adapter.notifyDataSetChanged();
    }

    public class CreateGameFreeCategoriesAsyncTask extends CreateGameAsyncTask {

        public CreateGameFreeCategoriesAsyncTask(String serverUrl, Activity redirectionActivity)
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

    public class CategoryAdapter extends BaseAdapter{

        Context context;
        private ArrayList<Object> categories;
        private int staredCategoriesSeparatorIndex=0;
        private int fixedCategoriesSeparatorIndex;
        private int allCategoriesSeparatorIndex;
        private static final int ITEM_VIEW_TYPE_CATEGORY = 0;
        private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
        private static final int ITEM_VIEW_TYPE_COUNT = 2;
        private static final String staredCategoriesText="Favoritas";
        private static final String fixedCategoriesText="Controladas";
        private static final String allCategoriesText="Todas";
        public int selectedPosition;

        public CategoryAdapter(Context context,ArrayList<Category> allCategories, ArrayList<Category> staredCategories, ArrayList<Category> fixedCategories)
        {
            this.context=context;
            this.categories= new ArrayList<Object>();
            this.categories.addAll(staredCategories);
            this.categories.addAll(fixedCategories);
            this.categories.addAll(allCategories);

            this.fixedCategoriesSeparatorIndex=staredCategories.size() + 1;
            this.allCategoriesSeparatorIndex=staredCategories.size() +fixedCategories.size()  + 2;

            this.categories.add(staredCategoriesSeparatorIndex,staredCategoriesText);
            this.categories.add(fixedCategoriesSeparatorIndex,fixedCategoriesText);
            this.categories.add(allCategoriesSeparatorIndex,allCategoriesText);
        }


        @Override
        public int getItemViewType(int position) {
            return (categories.get(position) instanceof String) ? ITEM_VIEW_TYPE_SEPARATOR:ITEM_VIEW_TYPE_CATEGORY;
        }

        @Override
        public int getViewTypeCount() { return ITEM_VIEW_TYPE_COUNT; } //Tenemos dos tipos de views, las categories y los separators

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

        @Override
        public boolean isEnabled(int position) {
            // A separator cannot be clicked !
            return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
        }

        private class CategoryViewHolder {
            ImageView starImageView;
            ImageView reportImageView;
            TextView txtDesc;
        }

        private class SeparatorViewHolder {
            TextView txtSeparator;
        }


        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {


            View.OnClickListener starCategoryListener = new View.OnClickListener() {
                Object s = categories.get(i);
                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        new StarCategoryAsyncTask().execute(((Category) s));

                    }
                }
            };

            View.OnClickListener reportCategoryListener = new View.OnClickListener() {

                Object s = categories.get(i);

                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        if(!((Category) s).isReported())
                        {
                            new ReportCategoryAsyncTask().execute(((Category) s));

                        }

                    }

                }
            };

            final int type = getItemViewType(i);

            if(type == ITEM_VIEW_TYPE_CATEGORY)
                convertView = SetRowCategoryViewHolder(i, convertView, starCategoryListener, reportCategoryListener);
            else
                convertView = SetRowSeparatorViewHolder(i, convertView);



            return convertView;


        }

        private View SetRowCategoryViewHolder(int position, View convertView, View.OnClickListener starCategoryListener, View.OnClickListener reportCategoryListener) {
            CategoryViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.category_row, null);

                holder = new CategoryViewHolder();
                holder.txtDesc = (TextView) convertView.findViewById(R.id.categoryListText);
                holder.starImageView = (ImageView) convertView.findViewById(R.id.ImageStar);
                holder.reportImageView = (ImageView) convertView.findViewById(R.id.ImageReport);
                convertView.setTag(holder);
            }
            else {
                holder = (CategoryViewHolder) convertView.getTag();
            }

            Category rowItem = (Category) getItem(position);

            holder.txtDesc.setText(rowItem.getName());

            if(rowItem.isStared())
                holder.starImageView.setImageResource(R.drawable.icon_star);
            else
                holder.starImageView.setImageResource(R.drawable.icon_star_off);

            if(rowItem.isReported())
            {
                holder.reportImageView.setImageResource(R.drawable.attention2);
                holder.reportImageView.setEnabled(false);
            }
            else
            {
                holder.reportImageView.setImageResource(R.drawable.attention2_off);
                holder.reportImageView.setEnabled(true);
            }

            holder.starImageView.setOnClickListener(starCategoryListener);
            holder.reportImageView.setOnClickListener(reportCategoryListener);

           if(selectedCategories.contains(rowItem))
           {
               convertView.setBackgroundColor(getResources().getColor(R.color.blue));
               holder.txtDesc.setTextColor(getResources().getColor(R.color.white));
           }
            else
           {
               convertView.setBackgroundColor(getResources().getColor(R.color.white));
               holder.txtDesc.setTextColor(getResources().getColor(R.color.skyblue));
           }

            return convertView;
        }

        private View SetRowSeparatorViewHolder(int position, View convertView) {
            SeparatorViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.category_row_separator, null);

                holder = new SeparatorViewHolder();
                holder.txtSeparator = (TextView) convertView.findViewById(R.id.separatorText);
                convertView.setTag(holder);
            }
            else {
                holder = (SeparatorViewHolder) convertView.getTag();
            }

            String rowItem = (String) getItem(position);

            holder.txtSeparator.setText(rowItem);
            return convertView;
        }


        public class ReportCategoryAsyncTask extends AsyncTask<Category, Void, Category>
        {
            TuttiFruttiAPI api;

            protected void onPreExecute(){
                api=new TuttiFruttiAPI(getString(R.string.server_url));
            }

            @Override
            protected Category doInBackground(Category... categoryId) {
                api.reportCategory(categoryId[0].getId());
                return null;
            }

            @Override
            protected void onPostExecute(Category result) {

                ((Category) result).setReported(true);
                CategoryAdapter.this.notifyDataSetChanged();
            }
        }



        public class StarCategoryAsyncTask extends AsyncTask<Category, Void, Category>
        {
            TuttiFruttiAPI api;


            protected void onPreExecute(){
                api=new TuttiFruttiAPI(getString(R.string.server_url));
            }

            @Override
            protected Category doInBackground(Category... categoryId) {
                api.starCategory(FacebookHelper.getUserId(),categoryId[0].getId());
                return categoryId[0];
            }

            @Override
            protected void onPostExecute(Category result) {

                ((Category) result).setStared(!((Category) result).isStared());
                CategoryAdapter.this.notifyDataSetChanged();
            }
        }
    }


    public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>>{

        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {

            ArrayList<Category> staredCategories= api.getStaredCategories(FacebookHelper.getUserId());
            return staredCategories;
        }

        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {

            ArrayList<String> categoriesNames= new ArrayList<String>();
            ArrayList<Category> staredCategories=new ArrayList<Category>();
            ArrayList<Category> fixedCategories=new ArrayList<Category>();
            for(Category category : result){
                if(category.isStared())
                    staredCategories.add(category);
                else if(category.isFixed())
                    fixedCategories.add(category);

                categoriesNames.add(category.getName());

            }

            ArrayAdapter<Category> adapter = new FilteredArrayAdapter<Category>(ViewCategoriesActivity.this, R.layout.category_autocomplete, result) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            if (convertView == null) {

                                LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                                convertView = (View)l.inflate(R.layout.category_autocomplete, parent, false);
                            }

                            Category p = getItem(position);
                            ((TextView)convertView.findViewById(R.id.name)).setText(p.getName());

                            return convertView;
                        }

                        @Override
                        protected boolean keepObject(Category obj, String mask) {
                            mask = mask.toLowerCase();
                            return obj.getName().toLowerCase().startsWith(mask);
                        }
                    };

            final TuttiFruttiAutoCompleteTextView textView = (TuttiFruttiAutoCompleteTextView) findViewById(R.id.searchView);
            textView.setAdapter(adapter);
            textView.setTokenListener(ViewCategoriesActivity.this);


             ViewCategoriesActivity.this.adapter= new CategoryAdapter(getApplicationContext(), result, staredCategories, fixedCategories);


            // Assign adapter to ListView
            categoriesList.setAdapter(ViewCategoriesActivity.this.adapter);

            // ListView Item Click Listener
            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //SELECT Category
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {


                    // ListView Clicked item value
                    Category itemValue = (Category) categoriesList.getItemAtPosition(position);


                    if (selectedCategories.contains(itemValue)) {
                        selectedCategories.remove(itemValue);
                        textView.removeObject(itemValue);
                    } else {
                        selectedCategories.add(itemValue);
                        textView.addObject(itemValue);
                    }

                    ViewCategoriesActivity.this.adapter.notifyDataSetChanged();
                }

            });
        }
    }


}
