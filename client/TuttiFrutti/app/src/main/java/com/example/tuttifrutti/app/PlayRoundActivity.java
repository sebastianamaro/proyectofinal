package com.example.tuttifrutti.app;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.example.FullRound;
import com.example.Play;
import com.example.TuttiFruttiAPI;
import com.example.tuttifrutti.app.Classes.RoundResult;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;
import com.example.tuttifrutti.app.Classes.RoundResult;




public class PlayRoundActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private String fileName;
    private FullRound currentRound;
    private TuttiFruttiAPI api;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);

        Intent intent = getIntent();

        int gameId = intent.getIntExtra(MainActivity.GAME_ID_EXTRA_MESSAGE, -1);
        api=new TuttiFruttiAPI(getString(R.string.server_url));
        api.startRound(gameId);
        currentRound= api.getCurrentRoundInformation(gameId);
       
        int userId=9;

        fileName = getCacheDir().getAbsolutePath() + "/" + Integer.toString(gameId) + "_" +  Integer.toString(currentRound.getRoundId()) + "_" + Integer.toString(userId) + ".txt";

        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String category : currentRound.getCategories()) {
            actionBar.addTab(actionBar.newTab().setText(category)
                    .setTabListener(this));
        }

        actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter());

        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    int prevSelectedTab = 0;
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.

                        if (prevSelectedTab != position)
                            prevSelectedTab = position;

                        getActionBar().setSelectedNavigationItem(position);
                    }

                }
        );

        new InternalFileHelper().startRound(fileName, currentRound.getRoundId());
        // 120000 = 2 min
        timer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                //ver donde mostrarlo

                getActionBar().setTitle(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));
            }

            public void onFinish() {
                getActionBar().setTitle(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(0),
                        TimeUnit.MILLISECONDS.toSeconds(0))
                );

                EndRoundAndSendData(false, "Se te terminó el tiempo!");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                //todo: hacer el basta para mi basta para todos con lo que tiene
            }
        }.start();

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        public SampleFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }
        @Override
        public int getCount() {
            return currentRound.getCategories().length;
        }
        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.create(position, currentRound.getLetter(), fileName, currentRound.getCategories().length, currentRound.getRoundId());
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return currentRound.getCategories()[position];
        }
    }

    public static class CategoryFragment extends Fragment {
        public static final String ARG_CATEGORY = "ARG_CATEGORYINDEX";
        public static final String ARG_LETTER = "ARG_LETTER";
        public static final String ARG_FILENAME = "ARG_FILENAME";
        public static final String ARG_TOTALCATEGORIES = "ARG_TOTALCATEGORIES";
        public static final String ARG_ROUNDID = "ARG_ROUNDID";
        private int categoryIndex;
        private String currentLetter;
        private String fileName;
        private int totalCategories;
        private int roundId;

        public static CategoryFragment create(int categoryIndex, String currentLetter, String fileName, int totalCategories, int roundId) {
            Bundle args = new Bundle();
            args.putInt(ARG_CATEGORY, categoryIndex);
            args.putString(ARG_LETTER, currentLetter);
            args.putString(ARG_FILENAME, fileName);
            args.putInt(ARG_TOTALCATEGORIES, totalCategories);
            args.putInt(ARG_ROUNDID, roundId);
            CategoryFragment fragment = new CategoryFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            categoryIndex = getArguments().getInt(ARG_CATEGORY);
            currentLetter = getArguments().getString(ARG_LETTER);
            fileName = getArguments().getString(ARG_FILENAME);
            totalCategories = getArguments().getInt(ARG_TOTALCATEGORIES);
            roundId = getArguments().getInt(ARG_ROUNDID);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //aca obtengo el control que esta adentro del layout que va a tener cada fragment
            View view = inflater.inflate(R.layout.fragment_page, container, false);

            TextView letter = (TextView) view.findViewById(R.id.currentLetter);
            letter.setText(currentLetter);

            EditText textView = (EditText) view.findViewById(R.id.categoryValue);
            textView.setTag(categoryIndex);

            textView.addTextChangedListener(new GenericTextWatcher(textView, fileName));
            textView.setOnFocusChangeListener(new GenericFocusChangeListener(categoryIndex, totalCategories, roundId));

            return view;
        }

        public class GenericTextWatcher implements TextWatcher {

            private View view;
            private String fileName;

            private GenericTextWatcher(View view, String fileName) {
                this.view = view;
                this.fileName = fileName;
            }

            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString();
                if (!text.isEmpty() && text.charAt(0) != currentLetter.charAt(0)) {
                    EditText textView = (EditText) view;
                    textView.setError("La palabra debe comenzar con la letra correspondiente a la ronda");
                }
            }

            public void afterTextChanged(Editable editable) {

            }
        }


        public class GenericFocusChangeListener implements View.OnFocusChangeListener {

            private final int Category;
            private final int TotalCategories;
            private final int RoundId;
            private final InternalFileHelper Helper;

            public GenericFocusChangeListener(int category, int totalCategories, int roundId) {
                Category = category;
                TotalCategories = totalCategories;
                RoundId = roundId;
                Helper = new InternalFileHelper();
            }

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredString = ((EditText) v).getText().toString();
                    if (enteredString.isEmpty())
                        return;

                    RoundResult currentRoundResult = Helper.saveCategoryValue(fileName, this.Category, enteredString, totalCategories, roundId);
                }

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

        if (id == R.id.action_stop)
        {
            EndRoundAndSendData(true, "Tus datos fueron enviados exitosamente");
        }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void EndRoundAndSendData(Boolean validateAllCategoriesPresent, String messageToShow) {
        int position = getActionBar().getSelectedTab().getPosition();
        EditText textView = (EditText)findViewById(R.id.pager).findViewWithTag(position);

        String categoryValue = textView.getText().toString();
        RoundResult currentRoundResult = new InternalFileHelper().saveCategoryValue(fileName, position, categoryValue, currentRound.getCategories().length, currentRound.getRoundId());

        boolean complete = true;
        if (validateAllCategoriesPresent) {
            int i = 0;
            while (complete && i < currentRound.getCategories().length) {
                if (currentRoundResult.CategoriesValues[i] == null || currentRoundResult.CategoriesValues[i] == "")
                    complete = false;

                i++;
            }
        }

        if (!complete) {
            showPopUp("Debe completar todas las categorias para finalizar la ronda");
        }
        else
        {
            List<Play> plays= new ArrayList<Play>();

            for(int index=0;index<currentRound.getCategories().length;index++){
                Play play= new Play();
                play.setCategory(currentRound.getCategories()[index]);
                play.setWord(currentRoundResult.CategoriesValues[index]);
                play.setTimeStamp(currentRoundResult.CategoriesTimeStamp[index]);
                plays.add(play);
            }

            Play[] playArray=new Play[plays.size()];
            plays.toArray(playArray);

            api.finishRound(currentRound.getGameId(),currentRound.getRoundId(),"USERID",currentRoundResult.StartTime,playArray);

            File file = new File(fileName);
            try {
                file.getCanonicalFile().delete();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setCancelable(false); // This blocks the 'BACK' button
            ad.setMessage(messageToShow);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timer.cancel();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            ad.show();

        }
    }

    public void showPopUp(String message)
    {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false); // This blocks the 'BACK' button
        ad.setMessage(message);
        ad.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }

}



