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

import java.util.ArrayList;
import java.util.List;


public class ViewCategoriesActivity extends ActionBarActivity {

    ListView categoriesList;
    ArrayList<Category> selectedCategories= new ArrayList<Category>();
    Game gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);

        categoriesList = (ListView) findViewById(R.id.categoriesList);

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra("gameSettings");

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

    public void finish(View view) {
        gameSettings.setSelectedCategories(selectedCategories);
        new CreateGameFreeCategoriesAsyncTask(getString(R.string.server_url), this).execute(gameSettings);
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



    public static Object convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(viewBmp);

    }

    public TextView createContactTextView(String text){
        //creating textview dynamically
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(12);
        tv.setBackgroundResource(R.drawable.oval);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cross_bar, 0);
        return tv;
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
                        new StarCategoryAsyncTask().execute(((Category) s).getId());
                    }
                }
            };


            View.OnClickListener reportCategoryListener = new View.OnClickListener() {

                Object s = categories.get(i);

                @Override
                public void onClick(View v) {
                    if(s instanceof Category)
                    {
                        new ReportCategoryAsyncTask().execute(((Category) s).getId());
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
                 holder.reportImageView.setImageResource(R.drawable.attention2);
            else
                holder.reportImageView.setImageResource(R.drawable.attention2_off);

            holder.starImageView.setOnClickListener(starCategoryListener);
            holder.reportImageView.setOnClickListener(reportCategoryListener);
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

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewCategoriesActivity.this, android.R.layout.simple_dropdown_item_1line, categoriesNames);
            MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.categoryText);
            textView.setAdapter(adapter);


            final CategoryAdapter categoryAdapter = new CategoryAdapter(getApplicationContext(), result, staredCategories, fixedCategories);


            // Assign adapter to ListView
            categoriesList.setAdapter(categoryAdapter);

            // ListView Item Click Listener
            categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //SELECT Category
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item value
                    Category itemValue = (Category) categoriesList.getItemAtPosition(position);

                    if (selectedCategories.contains(itemValue)) {
                        Toast.makeText(getApplicationContext(),
                                "La categoria " + itemValue.getName() + " ya esta seleccionada.", Toast.LENGTH_LONG)
                                .show();          // Show Alert
                    } else {
                        selectedCategories.add(itemValue);

                        final SpannableStringBuilder sb = new SpannableStringBuilder();
                        TextView tv = createContactTextView(itemValue.getName());
                        BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
                        bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());

                        sb.append(itemValue.getName()+ ",");
                        sb.setSpan(new ImageSpan(bd), sb.length()-(itemValue.getName().length()+1), sb.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        MultiAutoCompleteTextView textView = (MultiAutoCompleteTextView) findViewById(R.id.categoryText);
                        textView.setTokenizer(new CustomCommaTokenizer());
                        textView.setThreshold(0);
                        textView.setText(sb);
                    }
                }

            });
        }

        public class CustomCommaTokenizer extends MultiAutoCompleteTextView.CommaTokenizer {
            @Override
            public CharSequence terminateToken(CharSequence text) {
                CharSequence charSequence = super.terminateToken(text);
                return charSequence.subSequence(0, charSequence.length()-1);
            }
        }

    }


    public class ReportCategoryAsyncTask extends AsyncTask<Integer, Void, Void>
    {
        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Integer... categoryId) {
            api.reportCategory(categoryId[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            new GetCategoriesAsyncTask().execute();
        }
    }



    public class StarCategoryAsyncTask extends AsyncTask<Integer, Void, Void>
    {
        TuttiFruttiAPI api;

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

        @Override
        protected Void doInBackground(Integer... categoryId) {
            api.starCategory(FacebookHelper.getUserId(),categoryId[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            new GetCategoriesAsyncTask().execute();
        }
    }
}
