package com.example.sqliteposts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_V = 1;
    private static final String DB_N = "PostsDB";
    private static final String CR_T = "CREATE TABLE posts (id INTEGER NOT NULL, title TEXT, body TEXT)";

    public DbHelper(Context context) {
        super(context, DB_N, null, DB_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CR_T);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
