package com.example.tuttifrutti.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class PlayRoundActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private String[] categories;
    private String currentLetter;
    private String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);

        Intent intent = getIntent();

        String gameId = intent.getStringExtra(MainActivity.GAME_ID_EXTRA_MESSAGE);
        Integer roundId = 98;

        //todo: llamar al servidor con el [userId] y gameId y que devuelva las categorias y la letra
        categories = new String[6];
        categories[0] = "Animales";
        categories[1] = "Deportes";
        categories[2] = "Paises";
        categories[3] = "Frutas";
        categories[4] = "Marcas de Auto";
        categories[5] = "Colores";

        currentLetter = "F";
        //todo: llenar los tabs con las categorias y mostrar la letra donde corresponda
        //todo: arrancar el timer

        fileName = getCacheDir().getAbsolutePath() + "/hola.txt";

        final ActionBar actionBar = getActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String category : categories) {
            actionBar.addTab(actionBar.newTab().setText(category)
                    .setTabListener(this));
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter());

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //guardar lo que tiene su textbox en la sesion
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //setear en el textbox lo que tengo en sesion
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        public SampleFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }
        @Override
        public int getCount() {
            return categories.length;
        }
        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.create(categories[position], currentLetter, fileName);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return categories[position];
        }
    }

    public static class CategoryFragment extends Fragment {
        public static final String ARG_CATEGORY = "ARG_CATEGORYNAME";
        public static final String ARG_LETTER = "ARG_LETTER";
        public static final String ARG_FILENAME = "ARG_FILENAME";
        private String categoryName;
        private String currentLetter;
        private String fileName;
        public static CategoryFragment create(String categoryName, String currentLetter, String fileName) {
            Bundle args = new Bundle();
            args.putString(ARG_CATEGORY, categoryName);
            args.putString(ARG_LETTER, currentLetter);
            args.putString(ARG_FILENAME,fileName);
            CategoryFragment fragment = new CategoryFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            categoryName = getArguments().getString("ARG_CATEGORYNAME");
            currentLetter = getArguments().getString("ARG_LETTER");
            fileName = getArguments().getString("ARG_FILENAME");
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //aca obtengo el control que esta adentro del layout que va a tener cada fragment
            View view = inflater.inflate(R.layout.fragment_page, container, false);

            TextView letter = (TextView)view.findViewById(R.id.currentLetter);
            letter.setText(currentLetter);

            EditText textView = (EditText)view.findViewById(R.id.categoryValue);

            textView.addTextChangedListener(new GenericTextWatcher(textView,fileName));
            textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                        String newText = v.getText().toString();

                        //todo: guardarlo en el local storage
                        File file= new File(fileName);

                        try {
                            if(!file.exists())
                                file.createNewFile();
                            else {
                                //TODO leer archivo y deserializar con GSON
                            }

                            //ObjetoModelo obj;

                            //Modificar el objeto con la nueva palabra

                            String json=newText;
                            //Serializar con GSON

                            FileOutputStream fos= new FileOutputStream(file,false);
                            PrintWriter pw= new PrintWriter(fos,true);
                            pw.write(json);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return true;
                    }
                    return false;
                }
            });

            return view;
        }

        public class GenericTextWatcher implements TextWatcher{

            private View view;
            private String fileName;

            private GenericTextWatcher(View view, String fileName) {
                this.view = view;
                this.fileName=fileName;
            }

            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString();
                if (!text.isEmpty() && text.charAt(0) != currentLetter.charAt(0))
                {
                    EditText textView = (EditText) view;
                    textView.setError("La palabra debe comenzar con la letra correspondiente a la ronda");
                }
            }

            public void afterTextChanged(Editable editable) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_round, menu);
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

