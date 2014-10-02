package com.example.tuttifrutti.app;

import android.app.AlertDialog;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.TuttiFruttiAPI;
import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.TuttiFruttiCore.UserGame;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.example.tuttifrutti.app.Classes.InternalFileHelper;
import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ChooseFriendsActivity extends FragmentActivity {

    public static final Uri FRIEND_PICKER = Uri.parse("picker://friend");
    private FriendPickerFragment friendPickerFragment;
    public Game gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_choose_friends);

        //getFriends();

        Intent intent = getIntent();
        gameSettings = (Game)intent.getSerializableExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE);

        Bundle args = getIntent().getExtras();

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            // First time through, we create our fragment programmatically.
            friendPickerFragment = new FriendPickerFragment(args);
            friendPickerFragment.setUserId(null);
            fm.beginTransaction()
                    .add(R.id.friend_picker_fragment, friendPickerFragment)
                    .commit();
        } else {
            // Subsequent times, our fragment is recreated by the framework and already has saved and
            // restored its state, so we don't need to specify args again. (In fact, this might be
            // incorrect if the fragment was modified programmatically since it was created.)
            friendPickerFragment = (FriendPickerFragment) fm.findFragmentById(R.id.friend_picker_fragment);
        }
            // Set the listener to handle errors
            friendPickerFragment.setOnErrorListener(new PickerFragment.OnErrorListener() {
                @Override
                public void onError(PickerFragment<?> fragment,
                                    FacebookException error) {
                    ChooseFriendsActivity.this.onError(error);
                }
            });
            // Set the listener to handle button clicks
            friendPickerFragment.setOnDoneButtonClickedListener(
                    new PickerFragment.OnDoneButtonClickedListener() {
                        @Override
                        public void onDoneButtonClicked(PickerFragment<?> fragment) {
                            if (friendPickerFragment != null) {
                                List<GraphUser> selectedFriends =  friendPickerFragment.getSelection();
                                if (selectedFriends.size() <= 4) {

                                    new SaveFriendsPictureAsyncTask().execute(selectedFriends);

                                    for (GraphUser g : selectedFriends) {
                                        gameSettings.addSelectedFriend(g.getId(), g.getName());
                                    }

                                    if (gameSettings.getCategoriesType().equals("FIXED")) {
                                        Intent intent = new Intent(getApplicationContext(), ChooseControlledCategoriesActivity.class);
                                        intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gameSettings);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), ViewCategoriesActivity.class);
                                        intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gameSettings);
                                        startActivity(intent);
                                    }
                                }else
                                    Toast.makeText(getApplicationContext(),
                                            "Elegí hasta 4 amigos!",
                                            Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }

    private void onError(Exception error) {
        onError(error.getLocalizedMessage(), false);
    }

    private void onError(String error, final boolean finishActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_dialog_title).
                setMessage(error).
                setPositiveButton(R.string.error_dialog_button_text,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
        builder.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

            try {
                friendPickerFragment.loadData(true);
            } catch (Exception ex) {
                onError(ex);
            }

    }

    private class SaveFriendsPictureAsyncTask extends AsyncTask<List<GraphUser>, Void, Void> {
        InternalFileHelper helper;
        List<GraphUser> friendsList;

        protected void onPreExecute() {
            helper = new InternalFileHelper();
        }

        @Override
        protected Void doInBackground(List<GraphUser>... friends) {
            friendsList = friends[0];

            for (GraphUser f : friendsList)
            {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                // path to /data/data/yourapp/app_data/imageDir
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                // Create imageDir
                File mypath=new File(directory,f.getId()+".png");

                helper.saveToInternalSorage(mypath, getFbProfilePicture(f.getId()));
            }

            return null;
        }
    }

    private Bitmap getFbProfilePicture(String fbId)
    {
        try {
            InputStream inputStream;
            HttpGet httpRequest = new HttpGet(URI.create("http://graph.facebook.com/" + fbId + "/picture?type=normal"));
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
            Bitmap bmp = BitmapFactory.decodeStream(bufHttpEntity.getContent());
            httpRequest.abort();

            return bmp;
        }catch (Exception ex)
        {
            return null;
        }
    }

}
