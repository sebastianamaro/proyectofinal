package com.example.tuttifrutti.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.tuttifrutti.app.Classes.CreateGameAsyncTask;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.example.tuttifrutti.app.Classes.TuttiFruttiAutoCompleteTextView;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewCategoriesActivity extends ActionBarActivity implements TokenCompleteTextView.TokenListener{

    ListView categoriesList;
    ArrayList<Category> selectedCategories= new ArrayList<Category>();
    Game gameSettings;
    CategoryAdapter adapter;
    TuttiFruttiAutoCompleteTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        setTitle("");
        categoriesList = (ListView) findViewById(R.id.categoriesList);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

        textView = (TuttiFruttiAutoCompleteTextView) findViewById(R.id.searchView);
        textView.allowDuplicates(false);

        textView.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                if (source instanceof SpannableStringBuilder) {
                    return source;
                } else {
                    StringBuilder filteredStringBuilder = new StringBuilder();
                    for (int i = start; i < end; i++) {
                        char currentChar = source.charAt(i);
                        if (Character.isLetter(currentChar)) {
                            filteredStringBuilder.append(currentChar);
                        }
                    }
                    return filteredStringBuilder.toString();
                }
            }
        }});
        textView.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null && adapter != null){
                    System.out.println("Text ["+s+"]");

                    String currentWord=s.toString();

                    currentWord=currentWord.replace(",, ","").trim();

                    if(currentWord.length()>0 && !currentWord.equals(","))
                        adapter.getFilter().filter(currentWord);
                    else
                    {
                        adapter.filteredCategories=adapter.originalCategories;
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
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
        if(this.selectedCategories.remove(token))
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

    public class CategoryAdapter extends BaseAdapter implements Filterable{

        Context context;
        public ArrayList<Object> originalCategories;
        public ArrayList<Object> filteredCategories;
        private int staredCategoriesSeparatorIndex=0;
        private int fixedCategoriesSeparatorIndex;
        private int allCategoriesSeparatorIndex;
        private static final int ITEM_VIEW_TYPE_CATEGORY = 0;
        private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
        private static final int ITEM_VIEW_TYPE_COUNT = 2;
        private static final String staredCategoriesText="Favoritas";
        private static final String fixedCategoriesText="Controladas";
        private static final String allCategoriesText="Todas";
        private ItemFilter itemFilter= new ItemFilter();

        public CategoryAdapter(Context context,ArrayList<Category> allCategories, ArrayList<Category> staredCategories, ArrayList<Category> fixedCategories)
        {
            this.context=context;
            this.originalCategories= new ArrayList<Object>();
            this.originalCategories.addAll(staredCategories);
            this.originalCategories.addAll(fixedCategories);
            this.originalCategories.addAll(allCategories);

            this.fixedCategoriesSeparatorIndex=staredCategories.size() + 1;
            this.allCategoriesSeparatorIndex=staredCategories.size() +fixedCategories.size()  + 2;

            this.originalCategories.add(staredCategoriesSeparatorIndex,staredCategoriesText);
            this.originalCategories.add(fixedCategoriesSeparatorIndex,fixedCategoriesText);
            this.originalCategories.add(allCategoriesSeparatorIndex,allCategoriesText);
            this.filteredCategories=originalCategories;
        }


        @Override
        public int getItemViewType(int position) {
            return (filteredCategories.get(position) instanceof String) ? ITEM_VIEW_TYPE_SEPARATOR:ITEM_VIEW_TYPE_CATEGORY;
        }

        @Override
        public int getViewTypeCount() { return ITEM_VIEW_TYPE_COUNT; } //Tenemos dos tipos de views, las categories y los separators

        @Override
        public int getCount() {
            return filteredCategories.size();
        }

        @Override
        public Object getItem(int i) {
            return filteredCategories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean isEnabled(int position) {
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
                Object s = filteredCategories.get(i);
                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        new StarCategoryAsyncTask().execute(((Category) s));

                    }
                }
            };

            View.OnClickListener reportCategoryListener = new View.OnClickListener() {

                Object s = filteredCategories.get(i);

                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        if(!((Category) s).isReported())
                            new ReportCategoryAsyncTask().execute(((Category) s));
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

            if(rowItem.isFixed())
                holder.reportImageView.setVisibility(View.INVISIBLE);
            else
                holder.reportImageView.setVisibility(View.VISIBLE);

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

        @Override
        public Filter getFilter() {
            return itemFilter;
        }



        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final ArrayList<Object> list = originalCategories;

                int count = list.size();
                final ArrayList<Object> nlist = new ArrayList<Object>(count);

                String filterableString ;

                for (int i = 0; i < count; i++) {

                    if((list.get(i) instanceof Category))
                    {
                        filterableString = ((Category)list.get(i)).getName();
                        if (filterableString.toLowerCase().contains(filterString)) {
                            nlist.add(list.get(i) );
                        }
                    }
                    else
                        nlist.add(list.get(i) );

                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCategories = (ArrayList<Object>) results.values;
                notifyDataSetChanged();
            }

        }

    }


    public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>>{

        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {

            ArrayList<Category> staredCategories= api.getCategoriesForPlayer(FacebookHelper.getUserId());
            return staredCategories;
        }

        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {

            ArrayList<String> categoriesNames= new ArrayList<String>();
            ArrayList<Category> categories=new ArrayList<Category>();
            ArrayList<Category> staredCategories=new ArrayList<Category>();
            ArrayList<Category> fixedCategories=new ArrayList<Category>();

            for(Category category : result){

                if(category.isStared())
                    staredCategories.add(category);
                else if(category.isFixed())
                    fixedCategories.add(category);
                else
                    categories.add(category);

                    categoriesNames.add(category.getName());
            }

            final TuttiFruttiAutoCompleteTextView textView = (TuttiFruttiAutoCompleteTextView) findViewById(R.id.searchView);
            textView.setTokenListener(ViewCategoriesActivity.this);

            ViewCategoriesActivity.this.adapter= new CategoryAdapter(getApplicationContext(), categories, staredCategories, fixedCategories);

            categoriesList.setAdapter(ViewCategoriesActivity.this.adapter);

            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
