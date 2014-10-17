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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Category;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.Player;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.CreateGameAsyncTask;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.example.tuttifrutti.app.Classes.TuttiFruttiAutoCompleteTextView;
import com.tokenautocomplete.TokenCompleteTextView;

import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;


public class ViewCategoriesActivity extends ActionBarActivity implements TokenCompleteTextView.TokenListener{

    ListView categoriesList;
    ArrayList<Category> selectedCategories= new ArrayList<Category>();
    Game gameSettings;
    CategoryAdapter adapter;
    TuttiFruttiAutoCompleteTextView textView;
    Button addCategoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FacebookHelper.isSessionOpened())
        {
            Intent i = new Intent(getApplicationContext(), AndroidFacebookConnectActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_view_categories);
        setTitle("");
        categoriesList = (ListView) findViewById(R.id.categoriesList);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

        addCategoryButton = (Button) findViewById(R.id.addCategory);
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
                if(s.toString().equals(","))
                    s.clear();

            }
        });

        new GetCategoriesAsyncTask().execute();
    }

    public void finish(View view) {
        if(selectedCategories.size()>=4)
        {
            gameSettings.setSelectedCategories(selectedCategories);
            gameSettings.setOwner(new Player(FacebookHelper.getUserId()));
            new CreateGameFreeCategoriesAsyncTask(getString(R.string.server_url), this).execute(gameSettings);
        }
        else
            Toast.makeText(getApplicationContext(), "Debe seleccionar al menos 4 categorias", Toast.LENGTH_LONG).show();

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

    public void addCategory(View view) {
        Category cat= new Category();
        cat.setName(textView.getText().toString().toUpperCase().replace(",", "").trim());
        new CreateCategoryAsyncTask().execute(cat);
    }

    public class CreateCategoryAsyncTask extends AsyncTask<Category, Void,Category>
    {
        TuttiFruttiAPI api;
        boolean connError;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Category doInBackground(Category... categories) {
            try{
                return api.createCategory(categories[0]);
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Category result) {
            if (this.connError)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            }else {
                new GetCategoriesAsyncTask(result).execute();
            }
        }
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
                    Intent intent = new Intent(getApplicationContext(), ViewGameStatusActivity.class);
                    startActivity(intent);
                }
            });
            ad.show();

        }
    }

        public class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<Category>>{

        Category recentlyCreatedCategory;
        TuttiFruttiAPI api;
        boolean connError;

        public GetCategoriesAsyncTask(){
        }

        public GetCategoriesAsyncTask(Category recentlyCreatedCategory){
            this.recentlyCreatedCategory=recentlyCreatedCategory;
        }
        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {

            ArrayList<Category> categories;
            try {
                if (gameSettings.getCategoriesType().equals("FIXED"))
                    categories = api.getFixedCategories();
                else
                    categories = api.getCategoriesForPlayer(FacebookHelper.getUserId());
                return categories;
            }catch (ResourceAccessException ex)
            {
                this.connError = true;
                return null;
            }
        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {
            if (this.connError) {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
            } else {
                ArrayList<String> categoriesNames = new ArrayList<String>();
                ArrayList<Category> categories = new ArrayList<Category>();
                ArrayList<Category> staredCategories = new ArrayList<Category>();
                ArrayList<Category> fixedCategories = new ArrayList<Category>();

                for (Category category : result) {

                    if (category.isStared())
                        staredCategories.add(category);
                    else if (category.isFixed())
                        fixedCategories.add(category);
                    else
                        categories.add(category);

                    categoriesNames.add(category.getName());
                }


                final TuttiFruttiAutoCompleteTextView textView = (TuttiFruttiAutoCompleteTextView) findViewById(R.id.searchView);
                textView.setTokenListener(ViewCategoriesActivity.this);

                ViewCategoriesActivity.this.adapter = new CategoryAdapter(getApplicationContext(), categories, staredCategories, fixedCategories);

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

                if (recentlyCreatedCategory != null)
                    textView.addObject(recentlyCreatedCategory);
            }
        }
    }


    public class CategoryAdapter extends BaseAdapter implements Filterable{

        Context context;
        public ArrayList<Object> originalCategories;
        public ArrayList<Object> filteredCategories;
        private int staredCategoriesSeparatorIndex=0;
        private int fixedCategoriesSeparatorIndex;
        private int freeCategoriesSeparatorIndex;
        private static final int ITEM_VIEW_TYPE_STARED_CATEGORIES_SEPARATOR = 0;
        private static final int ITEM_VIEW_TYPE_FIXED_CATEGORIES_SEPARATOR = 1;
        private static final int ITEM_VIEW_TYPE_ALL_CATEGORIES_SEPARATOR = 2;
        private static final int ITEM_VIEW_TYPE_EMPTY_SEPARATOR = 3;
        private static final int ITEM_VIEW_TYPE_CATEGORY = 4;
        private static final int ITEM_VIEW_TYPE_COUNT = 5; // 1,2,3) Headers  4) separador invisible 5) Full Game 6) userGame
        boolean showsStaredCategories;
        boolean showsFixedCategories;
        boolean showsFreeCategories;
        private static final String staredCategoriesText="Favoritas";
        private static final String fixedCategoriesText="Controladas";
        private static final String freeCategoriesText ="Libres";
        private ItemFilter itemFilter= new ItemFilter();

        public CategoryAdapter(Context context,ArrayList<Category> freeCategories, ArrayList<Category> staredCategories, ArrayList<Category> fixedCategories)
        {
            this.context=context;
            this.originalCategories= new ArrayList<Object>();
            this.originalCategories.addAll(staredCategories);
            this.originalCategories.addAll(fixedCategories);
            this.originalCategories.addAll(freeCategories);


            showsStaredCategories = staredCategories.size() > 0;
            showsFixedCategories = fixedCategories.size() > 0;
            showsFreeCategories = freeCategories.size() > 0;


            if (showsStaredCategories)
                this.fixedCategoriesSeparatorIndex = staredCategories.size() + 2; // Es dos porque tenemos el header y una row transparente para dar el feelling de que son grillas separadas
            else
                this.fixedCategoriesSeparatorIndex = 0;

            if (showsStaredCategories)
                this.freeCategoriesSeparatorIndex = staredCategories.size() + 2;
            else
                this.freeCategoriesSeparatorIndex = 0;


            if (showsFixedCategories)
                this.freeCategoriesSeparatorIndex += (fixedCategories.size() + 2);


            if (showsStaredCategories)
                this.originalCategories.add(staredCategoriesSeparatorIndex,staredCategoriesText);

            if (showsFixedCategories) {

                if(showsStaredCategories)
                    this.originalCategories.add(fixedCategoriesSeparatorIndex - 1, null);

                this.originalCategories.add(fixedCategoriesSeparatorIndex,fixedCategoriesText);
            }

            if (showsFreeCategories) {

                if(showsStaredCategories || showsFixedCategories)
                    this.originalCategories.add(freeCategoriesSeparatorIndex - 1, null);

                this.originalCategories.add(freeCategoriesSeparatorIndex, freeCategoriesText);
            }

            this.filteredCategories=originalCategories;
            if(filteredCategories.size()==0 && !gameSettings.getCategoriesType().equals("FIXED"))
                addCategoryButton.setVisibility(View.VISIBLE);
            else
                addCategoryButton.setVisibility(View.INVISIBLE);
        }


        @Override
        public int getItemViewType(int position) {

            if(filteredCategories.get(position) ==null)
                return ITEM_VIEW_TYPE_EMPTY_SEPARATOR;

            if(filteredCategories.get(position) instanceof String)
            {
                String separator=(String)filteredCategories.get(position);
                if(separator.equals(staredCategoriesText))
                    return ITEM_VIEW_TYPE_STARED_CATEGORIES_SEPARATOR;
                else if (separator.equals(fixedCategoriesText))
                    return ITEM_VIEW_TYPE_FIXED_CATEGORIES_SEPARATOR;
                else if(separator.equals(freeCategoriesText))
                    return ITEM_VIEW_TYPE_ALL_CATEGORIES_SEPARATOR;

            }

            if (filteredCategories.get(position) instanceof Category)
                return ITEM_VIEW_TYPE_CATEGORY;



            return -1;

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
            return getItemViewType(position) == ITEM_VIEW_TYPE_CATEGORY;
        }

        private class CategoryViewHolder {
            ImageView starImageView;
            ImageView reportImageView;
            TextView txtDesc;
        }

        private class SeparatorViewHolder {
            TextView txtSeparator;
        }

        private View SetRowEmptyViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_empty, null);
            }
            return convertView;
        }

        private View SetRowStaredCategoriesSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_categories_stared, null);
            }
            return convertView;
        }

        private View SetRowFixedCategoriesSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_categories_fixed, null);
            }
            return convertView;
        }

        private View SetRowFreeCategoriesSeparatorViewHolder(View convertView) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_separator_categories_free, null);
            }
            return convertView;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {

            final int type = getItemViewType(i);

            switch (type) {
                case ITEM_VIEW_TYPE_EMPTY_SEPARATOR:
                    convertView = SetRowEmptyViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_STARED_CATEGORIES_SEPARATOR:
                    convertView = SetRowStaredCategoriesSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_FIXED_CATEGORIES_SEPARATOR:
                    convertView = SetRowFixedCategoriesSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_ALL_CATEGORIES_SEPARATOR:
                    convertView = SetRowFreeCategoriesSeparatorViewHolder(convertView);
                    break;
                case ITEM_VIEW_TYPE_CATEGORY:
                    convertView = SetRowCategoryViewHolder(i, convertView);
                    break;

            }

            return convertView;


        }

        private View SetRowCategoryViewHolder(final int position, View convertView) {
            View.OnClickListener starCategoryListener = new View.OnClickListener() {
                Object s = filteredCategories.get(position);
                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        new StarCategoryAsyncTask().execute(((Category) s));

                    }
                }
            };

            View.OnClickListener reportCategoryListener = new View.OnClickListener() {

                Object s = filteredCategories.get(position);

                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        if(!((Category) s).isReported())
                            new ReportCategoryAsyncTask().execute(((Category) s));
                    }
                }
            };


            CategoryViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_row_category, null);

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

        public class ReportCategoryAsyncTask extends AsyncTask<Category, Void, Category>
        {
            TuttiFruttiAPI api;
            boolean connError;

            protected void onPreExecute(){
                api=new TuttiFruttiAPI(getString(R.string.server_url));
            }

            @Override
            protected Category doInBackground(Category... categoryId) {
                try{
                    api.reportCategory(categoryId[0].getId());
                }catch (ResourceAccessException ex)
                {
                    this.connError = true;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Category result) {
                if (this.connError)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
                }else {
                    ((Category) result).setReported(true);
                    CategoryAdapter.this.notifyDataSetChanged();
                }
            }
        }

        public class StarCategoryAsyncTask extends AsyncTask<Category, Void, Category>
        {
            TuttiFruttiAPI api;
            boolean connError;

            protected void onPreExecute(){
                api=new TuttiFruttiAPI(getString(R.string.server_url));
            }

            @Override
            protected Category doInBackground(Category... categoryId) {
                try {
                api.starCategory(FacebookHelper.getUserId(),categoryId[0].getId());
                return categoryId[0];
                }catch (ResourceAccessException ex)
                {
                    this.connError = true;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Category result) {
                if (this.connError)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_error_message), Toast.LENGTH_LONG).show();
                }else {
                    ((Category) result).setStared(!((Category) result).isStared());
                    CategoryAdapter.this.notifyDataSetChanged();
                }
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

                int staredCategories=0;
                int fixedCategories=0;
                boolean hasStaredCategories=false;
                boolean hasFixedCategories=false;
                boolean hasFreeCategories=false;


                for (int i = 0; i < count; i++) {

                    Object item=list.get(i);
                    if(item instanceof Category)
                    {
                        if (matches(item, filterString)) {

                            nlist.add(item);

                            if(((Category)item).isStared())
                            {
                                if(!hasStaredCategories)
                                    hasStaredCategories=true;

                                staredCategories++;
                            }
                            else if (((Category)item).isFixed())
                            {
                                if(!hasFixedCategories)
                                    hasFixedCategories=true;

                                fixedCategories++;
                            }
                            else
                            {
                                if(!hasFreeCategories)
                                    hasFreeCategories=true;

                            }
                        }
                    }
                    else
                    {
                       nlist.add(item);
                    }
                }

                if(!hasFreeCategories && showsFreeCategories)
                {
                    nlist.remove(nlist.size()-1);//all
                    nlist.remove(nlist.size()-1);//empty  //because we allways have fixed categories
                }

                if(!hasStaredCategories && showsStaredCategories)
                {
                    if(!(nlist.get(1) instanceof Category))  //VEEEERY UGLY FIX. IF WE DON'T DO THIS, THE USER CAN UNSTAR A CATEGORY,
                    {
                        nlist.remove(0);//stared            // THEN FILTER THAT CATEGORY AND THE SEPARATOR DISSAPEARS (BECAUSE WE DON'T HAVE ANY MORE STARED CATEGORIES)
                        nlist.remove(0);
                    }

                }


                if(!hasFixedCategories && showsFixedCategories)
                {
                    if(hasStaredCategories)
                    {
                        nlist.remove(staredCategories+1);//empty
                        nlist.remove(staredCategories+1);//fixed
                    }
                    else
                    {
                        nlist.remove(0);//empty
                        nlist.remove(0);//fixed
                    }
                }

                if(hasStaredCategories)
                    fixedCategoriesSeparatorIndex=staredCategories+2;
                else
                    fixedCategoriesSeparatorIndex=0;

                if(hasFreeCategories)
                {
                    if(hasStaredCategories)
                        freeCategoriesSeparatorIndex =staredCategories+fixedCategories+4;
                    else if(hasFixedCategories)
                        freeCategoriesSeparatorIndex =fixedCategories+2;
                    else
                    {
                        fixedCategoriesSeparatorIndex=-1;
                        freeCategoriesSeparatorIndex =0;
                    }
                }
                else
                    freeCategoriesSeparatorIndex =-1;

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            private boolean matches(Object item, String categoryString)
            {
                if(item instanceof Category)
                {
                    String filterableString = ((Category)item).getName();
                    return (filterableString.toLowerCase().contains(categoryString)) ;
                }
                return false;


            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCategories = (ArrayList<Object>) results.values;

               if(filteredCategories == null)
                    filteredCategories= new ArrayList<Object>();

                if(filteredCategories.size()==0 && !gameSettings.getCategoriesType().equals("FIXED"))
                    addCategoryButton.setVisibility(View.VISIBLE);
                else
                    addCategoryButton.setVisibility(View.INVISIBLE);

                notifyDataSetChanged();
            }

        }

    }

}
