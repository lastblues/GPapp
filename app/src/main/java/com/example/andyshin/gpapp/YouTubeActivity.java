package com.example.andyshin.gpapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

/**
 * Created by Andy Shin on 4/6/2017.
 */

public class YouTubeActivity extends AppCompatActivity{

    private static final String[] YOUTUBE_PLAYLISTS = {
            //SMT retrospective
            "PLUBonmwJMSaRLLcJSbnu22sKwV-agPc01",
            //Fire emblem retrospective
            "PLUBonmwJMSaTzqs1rQrmTuPNE8eQWDS8w",
            //Suikoden retrospective
            "PLUBonmwJMSaTWe6lhhsyvB_ud_nfJHwmN",
            //Star ocean retrospective
            "PLUBonmwJMSaQhniJJK10dwLeECbm-sMiV",
            //Tales retrospective
            "PLUBonmwJMSaQ4fKHVCgo9xVNtp4A56y-T",
            //Fire emblem playthrough
            "PLUBonmwJMSaRvAZlIqlMJGc3FZU3KmNhm"
    };

    //ARRAY FOR JUST THE RETROSPECTIVES
    private static final String[] RETRO_PLAYLISTS = {
            //SMT retrospective
            "PLUBonmwJMSaRLLcJSbnu22sKwV-agPc01",
            //Fire emblem retrospective
            "PLUBonmwJMSaTzqs1rQrmTuPNE8eQWDS8w",
            //Suikoden retrospective
            "PLUBonmwJMSaTWe6lhhsyvB_ud_nfJHwmN",
            //Star ocean retrospective
            "PLUBonmwJMSaQhniJJK10dwLeECbm-sMiV",
            //Tales retrospective
            "PLUBonmwJMSaQ4fKHVCgo9xVNtp4A56y-T",

    };

    //ARRAY FOR JUST THE RETROSPECTIVES
    private static final String[] PLAY_PLAYLISTS = {
            //SACRED STONES PLAYLIST
            //https://www.youtube.com/playlist?list=PLUBonmwJMSaTEOTrwn13EbZvRUzUF4VLf
            "PLUBonmwJMSaTEOTrwn13EbZvRUzUF4VLf",
            //FIRE EMBLEM 7 PLAYLIST
            //https://www.youtube.com/playlist?list=PLUBonmwJMSaRvAZlIqlMJGc3FZU3KmNhm
            "PLUBonmwJMSaRvAZlIqlMJGc3FZU3KmNhm"
    };

    //ARRAY FOR JUST THE REVIEWS
    private static final String[] REVIEW_PLAYLISTS = {
            //RPG SITE REVIEW
            //https://www.youtube.com/playlist?list=PLUBonmwJMSaTjbHB49QdAnfDO2nOKPXbx
            "PLUBonmwJMSaTjbHB49QdAnfDO2nOKPXbx",
    };

    //AN ARRAY TO HOLD ALL THE PLAYLISTS
    private static final String[] PLAYLISTS [] = {
            YOUTUBE_PLAYLISTS,
            RETRO_PLAYLISTS,
            PLAY_PLAYLISTS,
            REVIEW_PLAYLISTS
    };

    //https://www.youtube.com/playlist?list=PLUBonmwJMSaTjbHB49QdAnfDO2nOKPXbx

    //DRAWER CODE
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private int plPos;
    //END OF DRAWER CODE

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //INTENT CODE
        Intent intent = getIntent();
        String aPos = intent.getStringExtra ("pos");

        switch(aPos){
            case "1":
                plPos = 1;
                break;
            case "2":
                plPos = 2;
                break;
            case "3":
                plPos = 3;
                break;
            default:
                break;
        }

        Toast.makeText(YouTubeActivity.this, ""+plPos, Toast.LENGTH_SHORT).show();

        //DRAWER CODE
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //actionBar.show();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //END OF DRAWER CODE

        if (Config.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                    .setTitle("Missing API Key")
                    .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, PLAYLISTS [plPos]))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.you_tube, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_recyclerview) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, PLAYLISTS [plPos]))
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //SIDE DRAWER CODE
    private void addDrawerItems(){
        String[] itemArray = {"", "Retrospective", "Let's Play", "Reviews", "Favorites", "", ""};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity2.this, "Tab clicked", Toast.LENGTH_SHORT).show();



                //RETROSPECTIVE
                if(position == 1){
                    String posVal = "1";
                    Toast.makeText(YouTubeActivity.this, "" + posVal, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(YouTubeActivity.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                //LET'S PLAY
                if(position == 2){
                    String posVal = "2";
                    Toast.makeText(YouTubeActivity.this, "Let's Play", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(YouTubeActivity.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                //Reviews
                if(position == 3){
                    String posVal = "3";
                    Toast.makeText(YouTubeActivity.this, "Reviews", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(YouTubeActivity.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                //FAVORITES
                if(position == 4){
                    Toast.makeText(YouTubeActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                    Intent favIntent = new Intent(YouTubeActivity.this, FavList.class);
                    favIntent.putExtra("position", 4);
                    startActivity(favIntent);
                }
            }
        });
    }//end of addDrawerItems()

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
    }//end of setupDrawer()

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }//end of onPostcreate()

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }//end of onconfigurationChanged()
}
