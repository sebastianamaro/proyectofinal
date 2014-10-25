package com.example.tuttifrutti.app;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.TuttiFruttiCore.Constants;
import com.example.TuttiFruttiCore.Game;
import com.example.tuttifrutti.app.Classes.FacebookHelper;
import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;

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

        if (!FacebookHelper.isSessionOpened())
        {
            Intent i = new Intent(getApplicationContext(), AndroidFacebookConnectActivity.class);
            startActivity(i);
        }

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
                            gameSettings.clearSelectedFriends();
                            if (friendPickerFragment != null) {
                                List<GraphUser> selectedFriends =  friendPickerFragment.getSelection();
                                if (selectedFriends.size() <= 4) {
                                    for (GraphUser g : selectedFriends) {
                                        gameSettings.addSelectedFriend(g.getId(), g.getName());
                                    }

                                    Intent intent = new Intent(getApplicationContext(), ViewCategoriesActivity.class);
                                    intent.putExtra(Constants.GAME_SETTINGS_EXTRA_MESSAGE, gameSettings);
                                    startActivity(intent);
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

}
