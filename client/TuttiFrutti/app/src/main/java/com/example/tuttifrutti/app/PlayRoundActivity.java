package com.example.tuttifrutti.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PlayRoundActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);

        Intent intent = getIntent();

        String gameId = intent.getStringExtra(MainActivity.GAME_ID_EXTRA_MESSAGE);
        //todo: llamar al servidor con el [userId] y gameId y que devuelva las categorias y la letra
        //todo: llenar los tabs con las categorias y mostrar la letra donde corresponda
        //todo: arrancar el timer

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter());

    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 5;
        public SampleFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.create(position + 1);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }

    public static class CategoryFragment extends Fragment {
        public static final String ARG_PAGE = "ARG_PAGE";
        private int mPage;
        public static CategoryFragment create(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            CategoryFragment fragment = new CategoryFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //aca obttengo el control que esta adentro del layout que va a tener cada fragment
            View view = inflater.inflate(R.layout.fragment_page, container, false);
            TextView textView = (TextView) view;
            textView.setText("Fragment #" + mPage);
            return view;
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
