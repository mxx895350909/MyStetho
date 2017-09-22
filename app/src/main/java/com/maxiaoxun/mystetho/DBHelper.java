package com.maxiaoxun.mystetho;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maxiaoxun on 17/9/22 14:30.
 * email maxiaoxun@meituan.com
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myStetho.db";
    public static final String TABLE_NAME = "Members";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (Name text, Age integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE ID EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
