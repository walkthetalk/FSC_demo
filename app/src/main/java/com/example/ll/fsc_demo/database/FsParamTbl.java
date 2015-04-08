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

        /// just for test TODO: delete it
        String insertHeader = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + "," + COL_NAME + "," + COL_MODE + "," + COL_FIBER_TYPE + ") VALUES(";
        String insertTail = ");";

        database.execSQL(insertHeader + "1, 'test1', 3, 7" + insertTail);
        database.execSQL(insertHeader + "2, 'test2', 3, 7" + insertTail);
        database.execSQL(insertHeader + "4, 'test4', 3, 7" + insertTail);
        database.execSQL(insertHeader + "10, 'test10', 3, 7" + insertTail);
        database.execSQL(insertHeader + "21, 'test21', 3, 7" + insertTail);
        database.execSQL(insertHeader + "22, 'test22', 3, 7" + insertTail);
        database.execSQL(insertHeader + "25, 'test25', 3, 7" + insertTail);
        database.execSQL(insertHeader + "29, 'test29', 3, 7" + insertTail);
        database.execSQL(insertHeader + "33, 'test33', 3, 7" + insertTail);
        database.execSQL(insertHeader + "36, 'test36', 3, 7" + insertTail);
        database.execSQL(insertHeader + "40, 'test40', 3, 7" + insertTail);
    }

    public static void onUpgrade(SQLiteDatabase db,
                                 int oldVersion, int newVersion) {
        Log.w(FsParamTbl.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FsParamTbl.class.getName(), "Downgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }
}

