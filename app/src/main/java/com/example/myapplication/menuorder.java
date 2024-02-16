package com.example.myapplication;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.content.UriMatcher;
import androidx.annotation.Nullable;
import java.util.HashMap;
public class menuorder extends ContentProvider {
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "customerDB";
    static final String TABLE_NAME = "customer";

    static final String TABLE_NAME1 = "menu";

    static final int DATABASE_VERSION = 1;
    static final String PROVIDER_NAME = "com.example.myapplication.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/customer";
    static final String URL1 = "content://" + PROVIDER_NAME + "/menu";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final Uri CONTENT_URI1 = Uri.parse(URL1);

    static final String id = "id";
    static final String password = "password";

    static final String item = "item";
    static final String quantity = "quantity";

    static final int uriCode = 1;
    static UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (id TEXT NOT NULL, "
            + " password TEXT NOT NULL)";

    static final String CREATE_DB_TABLE1 = "CREATE TABLE " + TABLE_NAME1
            + " (item TEXT NOT NULL, "
            + " quantity TEXT NOT NULL)";
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
            db.execSQL(CREATE_DB_TABLE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "customer", uriCode);
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "menu", uriCode);
        //uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }
    public menuorder() {
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "com.example.myapplication.provider/customer";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db.insert(TABLE_NAME, null, values);

        return uri;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        return cursor;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        return count;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


