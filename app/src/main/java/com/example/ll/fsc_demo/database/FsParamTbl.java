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
            + COL_MODE + " TEXT CHECK( " + COL_MODE + " IN ('auto', 'calibrate', 'normal', 'special') ) NOT NULL, "
            + COL_FIBER_TYPE + " TEXT CHECK( " + COL_FIBER_TYPE + " IN ('SM', 'DS', 'NZ', 'MM') ) NOT NULL "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TBL);

        /// just for test TODO: delete it
        String insertHeader = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + "," + COL_NAME + "," + COL_MODE + "," + COL_FIBER_TYPE + ") VALUES(";
        String insertTail = ");";

        database.execSQL(insertHeader + "1, 'test1', 'auto', 'SM'" + insertTail);
        database.execSQL(insertHeader + "2, 'test2', 'auto', 'DS'" + insertTail);
        database.execSQL(insertHeader + "4, 'test4', 'auto', 'NZ'" + insertTail);
        database.execSQL(insertHeader + "10, 'test10', 'auto', 'MM'" + insertTail);
        database.execSQL(insertHeader + "21, 'test21', 'calibrate', 'SM'" + insertTail);
        database.execSQL(insertHeader + "22, 'test22', 'calibrate', 'DS'" + insertTail);
        database.execSQL(insertHeader + "25, 'test25', 'calibrate', 'NZ'" + insertTail);
        database.execSQL(insertHeader + "29, 'test29', 'calibrate', 'MM'" + insertTail);
        database.execSQL(insertHeader + "33, 'test33', 'normal', 'SM'" + insertTail);
        database.execSQL(insertHeader + "36, 'test36', 'normal', 'DS'" + insertTail);
        database.execSQL(insertHeader + "40, 'test40', 'normal', 'NZ'" + insertTail);
        database.execSQL(insertHeader + "42, 'test42', 'normal', 'MM'" + insertTail);
        database.execSQL(insertHeader + "45, 'test45', 'special', 'SM'" + insertTail);
        database.execSQL(insertHeader + "46, 'test46', 'special', 'DS'" + insertTail);
        database.execSQL(insertHeader + "48, 'test48', 'special', 'NZ'" + insertTail);
        database.execSQL(insertHeader + "49, 'test49', 'special', 'MM'" + insertTail);
    }

    public static void onUpgrade(SQLiteDatabase db,
                                 int oldVersion, int newVersion) {
        Log.w(FsParamTbl.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME + ";");
        onCreate(db);
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FsParamTbl.class.getName(), "Downgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME + ";");
        onCreate(db);
    }
}

