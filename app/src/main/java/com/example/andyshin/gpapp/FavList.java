package com.example.andyshin.gpapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by darren on 2017-04-06.
 */

public class FavList extends AppCompatActivity {

    private static final String TAG = "Favorites List Activity";
    DataBaseHelper dbHelper;

    private ListView favListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_list);

        favListView = (ListView) findViewById(R.id.favList);
        dbHelper = new DataBaseHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = dbHelper.getData();

        if (data.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Database empty", Toast.LENGTH_SHORT).show();
        } else {
            while(data.moveToNext()) {
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                favListView.setAdapter(listAdapter);
            }
        }
        //getListItems();
    }

//    private void getListItems() {
//        Log.d(TAG, "getListItems: Displays data within ListView");
//
//        //get data from database helper class
//        Cursor data = dbHelper.getData();
//        ArrayList<String> dataList = new ArrayList<>();
//        //obtain value from column 1 then add it to array list
//        while(data.moveToNext()) {
//            dataList.add(data.getString(1));
//        }
//
//        //create and set list adapter
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
//        favListView.setAdapter(adapter);
//    }
}
