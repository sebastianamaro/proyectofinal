package com.example.tuttifrutti.app;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.IntentFilter;
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
import java.io.IOException;
import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.FilePlay;
import com.example.TuttiFruttiCore.FinishedRound;
import com.example.TuttiFruttiCore.FullRound;
import com.example.TuttiFruttiCore.Play;
import com.example.TuttiFruttiCore.PlayServicesHelper;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.example.tuttifrutti.app.Classes.StopNotificationData;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



public class PlayRoundActivity extends FragmentActivity implements
        ActionBar.TabListener {

    public final static String STOP_NOTIFICATION_DATA = "com.example.tuttifrutti.STOP_NOTIFICATION_DATA";
    private String fileName;
    private FullRound currentRound;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);

        Intent intent = getIntent();

        registerReceiver(gcmLocalReceiver, new IntentFilter("gcmLocalReceiver"));

        int gameId = intent.getIntExtra(MainActivity.GAME_ID_EXTRA_MESSAGE, -1);
        new APIStartRoundTask().execute(gameId);

    }

    private final BroadcastReceiver gcmLocalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StopNotificationData stopNotificationData = (StopNotificationData) intent.getSerializableExtra(PlayRoundActivity.STOP_NOTIFICATION_DATA);
            if (currentRound == null) {
                TuttiFruttiAPI api = new TuttiFruttiAPI(getString(R.string.server_url));
                int gameId = intent.getIntExtra(MainActivity.GAME_ID_EXTRA_MESSAGE, -1);
                currentRound = api.getCurrentRoundInformation(gameId);
            }
            EndRoundAndSendData(false, stopNotificationData.getPlayer()+" ha dicho basta para mi basta para todos!");

        }
    };

    @Override
    public void onDestroy(){
        unregisterReceiver(gcmLocalReceiver);
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

            textView.addTextChangedListener(new GenericTextWatcher(textView));
            textView.setOnFocusChangeListener(new GenericFocusChangeListener(fileName,categoryIndex, totalCategories, roundId));


            return view;
        }

        public class GenericTextWatcher implements TextWatcher {

            private View view;


            private GenericTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String text = charSequence.toString();
                if (!text.isEmpty() && Character.toLowerCase(text.charAt(0)) != Character.toLowerCase(currentLetter.charAt(0))) {
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
        MenuItem stopButton = menu.findItem(R.id.action_stop);
        stopButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        stopButton.setVisible(true);

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

        if (textView == null)
            showPopUp("el textview es null");
        else if (textView.getText() == null)
            showPopUp("el getText es null");

        String categoryValue = textView.getText().toString();
        new SaveFilePlayFinishRoundTask(validateAllCategoriesPresent,messageToShow).execute(new com.example.tuttifrutti.app.Classes.FilePlay(fileName, position, categoryValue, currentRound.getCategories().length, currentRound.getRoundId()));

    }



    private class APIStartRoundTask extends AsyncTask<Integer, Void, FullRound>{

        TuttiFruttiAPI api;
        @Override
        protected FullRound doInBackground(Integer... integers) {
            api.startRound(integers[0]);
            return api.getCurrentRoundInformation(integers[0]);

        }

        /* */


        @Override
        protected void onPostExecute(FullRound result) {

            currentRound=result;
            fileName = getCacheDir().getAbsolutePath() + "/" + Integer.toString(result.getGameId()) + "_" +  Integer.toString(result.getRoundId())  + ".txt";

            final ActionBar actionBar = getActionBar();

            // Specify that tabs should be displayed in the action bar.
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (String category : result.getCategories()) {
                actionBar.addTab(actionBar.newTab().setText(category).setTabListener(PlayRoundActivity.this));
            }

            View homeIcon = findViewById(android.R.id.home);
            ((View) homeIcon.getParent()).setVisibility(View.GONE);

            //actionBar.setDisplayShowHomeEnabled(false);
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

            new SaveFilePlayStartRoundTask().execute(new com.example.tuttifrutti.app.Classes.FilePlay(fileName, result.getRoundId()));

        }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }
    }

    private class APIFinishRoundTask extends AsyncTask<FinishedRound, Void, Void>{
        TuttiFruttiAPI api;

        @Override
        protected Void doInBackground(FinishedRound... finishedRounds) {
            String regid = new PlayServicesHelper(MainActivity.class.getSimpleName()).getRegistrationId(getApplicationContext());
            String fbId=FacebookHelper.getUserId();
            api.finishRound(finishedRounds[0].getGameId(),finishedRounds[0].getRoundId(),fbId,regid,finishedRounds[0].getStartTime(), finishedRounds[0].getPlays());
            return null;
            }

        protected void onPreExecute(){
            api=new TuttiFruttiAPI(getString(R.string.server_url));
        }

    }

    private class SaveFilePlayStartRoundTask extends SaveFilePlayTask{

        @Override
        protected void onPostExecute(FilePlay result) {

            // 120000 = 2 min
            timer = new CountDownTimer(60000, 1000) {

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

                    //todo: hacer el basta para mi basta para todos con lo que tiene
                }
            }.start();

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

    private class SaveFilePlayFinishRoundTask extends SaveFilePlayTask{

        Boolean validateAllCategoriesPresent;
        String messageToShow;

        public SaveFilePlayFinishRoundTask(Boolean validateAllCategoriesPresent, String messageToShow){
            this.validateAllCategoriesPresent=validateAllCategoriesPresent;
            this.messageToShow=messageToShow;
        }
       protected void onPostExecute(FilePlay currentFilePlay){
           //TODO refact
           boolean complete = true;
           if (validateAllCategoriesPresent) {
               int i = 0;
               while (complete && i < currentRound.getCategories().length) {
                   if (currentFilePlay.CategoriesValues[i] == null || currentFilePlay.CategoriesValues[i] == "")
                       complete = false;

                   i++;
               }
           }

           if (!complete) {
               showPopUp("Debe completar todas las categorias para finalizar la ronda");
           }
           else
           {
               ArrayList<Play> plays= new ArrayList<Play>();

               for (int index = 0; index < currentRound.getCategories().length; index++) {
                   Play play = new Play();
                   play.setCategory(currentRound.getCategories()[index]);
                   play.setWord(currentFilePlay.CategoriesValues[index]);
                   play.setTimeStamp(currentFilePlay.CategoriesTimeStamp[index]);
                   plays.add(play);
               }
               


                new APIFinishRoundTask().execute(new FinishedRound(currentRound.getGameId(),currentRound.getRoundId(), currentFilePlay.StartTime,plays));
               File file = new File(fileName);
               try {
                   file.getCanonicalFile().delete();
               } catch (IOException e) {
                   e.printStackTrace();
               }

               AlertDialog ad = new AlertDialog.Builder(PlayRoundActivity.this).create();
               ad.setCancelable(false); // This blocks the 'BACK' button
               ad.setMessage(messageToShow);
               ad.setButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       timer.cancel();
                       Intent intent;
                       if (currentRound.getGameMode() == "ONLINE") {
                           intent = new Intent(getApplicationContext(), ShowRoundResultActivity.class);
                           intent.putExtra(MainActivity.GAME_ID_EXTRA_MESSAGE, currentRound.getGameId());
                           intent.getIntExtra(MainActivity.ROUND_ID_EXTRA_MESSAGE, currentRound.getRoundId());
                       }
                       else
                           intent = new Intent(getApplicationContext(), MainActivity.class);

                       startActivity(intent);
                   }
               });
               ad.show();

           }
       }

    }


}

