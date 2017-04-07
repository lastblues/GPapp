package com.example.andyshin.gpapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    //android.app.ActionBar actionBar = getActionBar();

    List<DataItem> dataItemList = SampleDataProvider.dataItemList;

    //Drawer code referenced from:
    //http://blog.teamtreehouse.com/add-navigation-drawer-android

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //RecyclerView code
        //YouTubeAdapter youTubeAdapter = new YouTubeAdapter();
        DataItemAdapter adapter = new DataItemAdapter(this, dataItemList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);

        //drawer menu code
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //actionBar.show();
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }//end of onCreate()

    //SIDE DRAWER CODE
    private void addDrawerItems(){
        String[] itemArray = {"Profile", "Retrospective", "Let's Play", "Reviews", "Favorites", "Settings", "Exit"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity2.this, "Tab clicked", Toast.LENGTH_SHORT).show();


                //PROFILE
                if(position == 0){
                    Toast.makeText(MainActivity2.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent profileIntent = new Intent(MainActivity2.this, ProfileActivity.class);
                    startActivity(profileIntent);
                }

                //RETROSPECTIVE
                if(position == 1){
                    String posVal = "1";
                    Toast.makeText(MainActivity2.this, "" + posVal, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                //LET'S PLAY
                if(position == 2){
                    String posVal = "2";
                    Toast.makeText(MainActivity2.this, "Let's Play", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                if(position == 3){
                    String posVal = "3";
                    Toast.makeText(MainActivity2.this, "Reviews", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, YouTubeActivity.class);
                    intent.putExtra("pos", posVal);
                    startActivity(intent);
                }

                //FAVORITES
                if(position == 4){
                    Toast.makeText(MainActivity2.this, "Favorites", Toast.LENGTH_SHORT).show();
                    Intent favIntent = new Intent(MainActivity2.this, YouTubeActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//end of onOptionsItemsSelected
}


