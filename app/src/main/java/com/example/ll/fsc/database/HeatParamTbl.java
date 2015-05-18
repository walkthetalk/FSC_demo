package com.example.ll.fsc.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ll on 4/10/15.
 */
public class HeatParamTbl {
    public static final String TBL_NAME = "heat_param";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_MATERIAL = "mode";
    public static final String COL_LENGTH = "fiber_type";
    public static final String ABSTRACT[] = new String[] {
            COL_ID, COL_NAME, COL_MATERIAL, COL_LENGTH,
    };
    public static final String FULL[] = new String[] {
            COL_ID, COL_NAME, COL_MATERIAL, COL_LENGTH,
    };

    // Database creation SQL statement
    private static final String CREATE_TBL = "create table " + TBL_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_MATERIAL + " INTEGER, "
            + COL_LENGTH + " INTEGER"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TBL);

        /// just for test TODO: delete it
        String insertHeader = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + "," + COL_NAME + "," + COL_MATERIAL + "," + COL_LENGTH + ") VALUES(";
        String insertTail = ");";

        database.execSQL(insertHeader + "1, 'Standard 60mm', 3, 7" + insertTail);
        database.execSQL(insertHeader + "2, 'Standard 40mm', 3, 7" + insertTail);
        database.execSQL(insertHeader + "4, 'Micro250 40mm', 3, 7" + insertTail);
        database.execSQL(insertHeader + "10, 'Micro250 60mm', 3, 7" + insertTail);
        database.execSQL(insertHeader + "21, 'Micro400 40mm', 3, 7" + insertTail);
        database.execSQL(insertHeader + "22, 'Micro400 20mm', 3, 7" + insertTail);
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
