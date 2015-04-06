package com.example.ll.fsc_demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ll on 4/7/15.
 */
public class FsDbHelper extends SQLiteOpenHelper {
    public static String TABLE_NAME = "fusion_splice_param";
    public static String COL_ID = "_id";
    public static String COL_NAME = "name";
    public static String COL_MODE = "mode";
    public static String COL_FIBER_TYPE = "fiber_type";

    public FsDbHelper(Context context, int version) {
        super(context, DatabaseInfo.MAIN_DB_FILE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " VARCHAR(20),"
                + COL_MODE + " VARCHAR(20),"
                + COL_FIBER_TYPE + " VARCHAR(20),"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
