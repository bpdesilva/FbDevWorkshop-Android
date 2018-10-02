package com.fb.workshop.newsfeed.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stclive";
    private static final String TABLE_NAME = "rssfeeds";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PUBDATE = "pubdate";
    private static final String KEY_LINK = "link";
    private static final String KEY_RSSLINK = "rsslink";
    private static final String KEY_THUMBURL = "thumburl";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_PUBDATE + " TEXT,"
                + KEY_LINK + " TEXT,"
                + KEY_RSSLINK + " TEXT,"
                + KEY_THUMBURL + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    //Adding Record in Database

    public void AddtoFavorite(OfflineItem pj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, pj.getTitle());
        values.put(KEY_DESCRIPTION, pj.getDescription());
        values.put(KEY_PUBDATE, pj.getPubDate());
        values.put(KEY_LINK, pj.getLink());
        values.put(KEY_THUMBURL, pj.getThumbURL());

        Log.d("Added to Database", pj.getTitle());

        //Alternate Key
        values.put(KEY_RSSLINK, pj.getRssLink());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Data
    public ArrayList<OfflineItem> getAllData(String rssLink) {
        ArrayList<OfflineItem> dataList = new ArrayList<OfflineItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE rsslink='" + rssLink + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OfflineItem contact = new OfflineItem();

                contact.setTitle(cursor.getString(1));
                contact.setDescription(cursor.getString(2));
                contact.setPubDate(cursor.getString(3));
                contact.setLink(cursor.getString(4));
                contact.setThumbURL(cursor.getString(6));

                // Adding contact to list
                dataList.add(contact);

            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }

    //delete existing data if new data received

    public void RemoveFav(String url) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT* FROM " + TABLE_NAME + " WHERE rsslink='" + url + "'", null);
        int cnt = cursor.getCount();
        cursor.close();

        if (cnt > 0) {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE rsslink='" + url + "'");
        }

        db.close();
    }

    public enum DatabaseManager {
        INSTANCE;
        DatabaseHandler dbHelper;
        private SQLiteDatabase db;
        private boolean isDbClosed = true;

        public void init(Context context) {
            dbHelper = new DatabaseHandler(context);
            if (isDbClosed) {
                isDbClosed = false;
                this.db = dbHelper.getWritableDatabase();
            }

        }


        public boolean isDatabaseClosed() {
            return isDbClosed;
        }

        public void closeDatabase() {
            if (!isDbClosed && db != null) {
                isDbClosed = true;
                db.close();
                dbHelper.close();
            }
        }
    }
}
