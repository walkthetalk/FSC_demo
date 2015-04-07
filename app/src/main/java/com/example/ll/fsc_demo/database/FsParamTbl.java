package com.example.ll.fsc_demo.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ll on 4/7/15.
 */
public class FsParamTbl {
    public static final String TBL_NAME = "fusion_splice_param";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_MODE = "mode";
    public static final String COL_FIBER_TYPE = "fiber_type";
    public static final String ABSTRACT[] = new String[] {
            COL_ID, COL_NAME, COL_MODE, COL_FIBER_TYPE,
    };
    public static final String FULL[] = new String[] {
            COL_ID, COL_NAME, COL_MODE, COL_FIBER_TYPE,
    };

    // Database creation SQL statement
    private static final String CREATE_TBL = "create table " + TBL_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_MODE + " INTEGER, "
            + COL_FIBER_TYPE + " INTEGER"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TBL);

        String tmp1 = "INSERT INTO " + TBL_NAME + "("
                    + COL_ID + "," + COL_NAME + "," + COL_MODE + "," + COL_FIBER_TYPE + ") VALUES("
                    + "1, 'test1', 3, 7" + ");";
        String tmp2 = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + "," + COL_NAME + "," + COL_MODE + "," + COL_FIBER_TYPE + ") VALUES("
                + "3, 'test3', 2, 5" + ");";
        String tmp3 = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + "," + COL_NAME + "," + COL_MODE + "," + COL_FIBER_TYPE + ") VALUES("
                + "7, 'test7', 5, 6" + ");";
        database.execSQL(tmp1);
        database.execSQL(tmp2);
        database.execSQL(tmp3);
    }

    public static void onUpgrade(SQLiteDatabase database,
                                 int oldVersion, int newVersion) {
        Log.w(FsParamTbl.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(database);
    }
}

