package com.example.andyshin.gpapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.R.attr.value;
import static android.app.SearchManager.ACTION_KEY;

/**
 * Created by Andy Shin on 3/15/2017.
 */

public class EmbedActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener{

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    CheckBox checkBox;
    Button favorite;
    DataBaseHelper dbHelper;
    //private YouTube


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayback);

        Bundle bundle = getIntent().getExtras();
        int value = bundle.getInt(ACTION_KEY);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);

        favorite = (Button) findViewById(R.id.favButton);
        favorite.setOnClickListener(this);

        dbHelper = new DataBaseHelper(this);

        dbHelper = new DataBaseHelper(this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        //player.cueVideo("fhWaJi1Hsfo");
        //player.cuePlaylist("PLUBonmwJMSaRLLcJSbnu22sKwV-agPc01?");   //play https://www.youtube.com/playlist?list=PLUBonmwJMSaRLLcJSbnu22sKwV-agPc01?
        if (!wasRestored) {
            //player.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            Intent intent = getIntent();
            String ytLink = intent.getStringExtra("link");
            player.cueVideo("" + ytLink);   //play https://www.youtube.com/playlist?list=PLUBonmwJMSaRLLcJSbnu22sKwV-agPc01?
            Toast.makeText(this, "WATCHING: " + ytLink, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.checkBox:

                if (checkBox.isChecked()) {
                    Intent intent = getIntent();
                    String ytLink = intent.getStringExtra("link");
                    String newEntry = ytLink;

                    AddData(newEntry);
                    Toast.makeText(getApplicationContext(),"Added to Favorites", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.favButton:
                Toast.makeText(getApplicationContext(), "Going to Favorites", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmbedActivity.this, FavList.class);
                startActivity(intent);
            break;
            }
        }

    public void AddData(String newEntry) {
        boolean insertData = dbHelper.addData(newEntry);

        if (insertData == true) {
            Toast.makeText(getApplicationContext(), "Data Successfully Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

}