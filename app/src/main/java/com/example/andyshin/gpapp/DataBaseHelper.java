package com.example.andyshin.gpapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by darren on 2017-04-06.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    //creating database strings for content table
    private static final String DATABASE_NAME = "favlist.db";
    private static final String TABLE_NAME = "video_list";
    private static final String COL1 = "ID";
    private static final String COL2 = "Video";
    private static final String COL3 = "Link";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +
                " VIDEO_TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data was not inserted correctly it returns -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
