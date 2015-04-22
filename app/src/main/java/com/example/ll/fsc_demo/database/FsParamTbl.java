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
    public static final String COL_AUTO_FOCUS = "auto_focus";
    public static final String COL_ECF_REDRESS = "ecf_redress";
    public static final String COL_AUTO_MAG = "auto_mag";
    public static final String COL_TENSION_TEST = "tension_test";

    public static final String COL_KERF_LIMIT = "kerf_limit";
    public static final String COL_LOSS_LIMIT = "loss_limit";
    public static final String COL_CORE_ANGLE_LIMIT = "core_angle_limit";

    public static final String COL_CLEAN_TIME = "clean_time";
    public static final String COL_FUSION_GAP = "fusion_gap";
    public static final String COL_FUSION_POSITION = "fusion_position";
    public static final String COL_PREFUSE_MAG = "prefuse_mag";
    public static final String COL_PREFUSE_TIME = "prefuse_time";
    public static final String COL_FUSION_OVERLAP = "fusion_overlap";
    public static final String COL_ARC1_MAG = "arc1_mag";
    public static final String COL_ARC1_TIME = "arc1_time";
    public static final String COL_ARC2_MAG = "arc2_mag";
    public static final String COL_ARC2_TIME = "arc2_time";
    public static final String COL_ARC2_ON_TIME = "arc2_on_time";
    public static final String COL_ARC2_OFF_TIME = "arc2_off_time";
    public static final String COL_MANUAL_ARC_TIME = "manual_arc_time";

    public static final String COL_TAPER_SPLICE = "taper_splice";
    public static final String COL_TAPER_WAIT = "taper_wait";
    public static final String COL_TAPER_SPEED = "taper_speed";
    public static final String COL_TAPER_LENGTH = "taper_length";

    public static final String COL_LOSS_EST_MODE = "loss_est_mode";
    public static final String COL_LEFT_MFD = "left_mfd";
    public static final String COL_RIGHT_MFD = "right_mfd";
    public static final String COL_MIN_LOSS = "min_loss";
    public static final String COL_SYNTROPY_BEND_COEFFICIENT = "syntropy_bend_coefficient";
    public static final String COL_OPPOSITE_BEND_COEFFICIENT = "opposite_bend_coefficient";
    public static final String COL_MFD_MISMATCH_COEFFICIENT = "mfd_mismatch_coefficient";

    public static final String ABSTRACT[] = new String[] {
            COL_ID, COL_NAME, COL_MODE, COL_FIBER_TYPE,
    };
    public static final String FULL[] = new String[] {
            COL_ID,
            COL_NAME,
            COL_MODE,
            COL_FIBER_TYPE,
            COL_AUTO_FOCUS,
            COL_ECF_REDRESS,
            COL_AUTO_MAG,
            COL_TENSION_TEST,

            COL_KERF_LIMIT,
            COL_LOSS_LIMIT,
            COL_CORE_ANGLE_LIMIT,

            COL_CLEAN_TIME,
            COL_FUSION_GAP,
            COL_FUSION_POSITION,
            COL_PREFUSE_MAG,
            COL_PREFUSE_TIME,
            COL_FUSION_OVERLAP,
            COL_ARC1_MAG,
            COL_ARC1_TIME,
            COL_ARC2_MAG,
            COL_ARC2_TIME,
            COL_ARC2_ON_TIME,
            COL_ARC2_OFF_TIME,
            COL_MANUAL_ARC_TIME,

            COL_TAPER_SPLICE,
            COL_TAPER_WAIT,
            COL_TAPER_SPEED,
            COL_TAPER_LENGTH,

            COL_LOSS_EST_MODE,
            COL_LEFT_MFD,
            COL_RIGHT_MFD,
            COL_MIN_LOSS,
            COL_SYNTROPY_BEND_COEFFICIENT,
            COL_OPPOSITE_BEND_COEFFICIENT,
            COL_MFD_MISMATCH_COEFFICIENT,
    };

    // Database creation SQL statement
    private static final String CREATE_TBL = "create table " + TBL_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_MODE + " TEXT CHECK( " + COL_MODE + " IN ('auto', 'calibrate', 'normal', 'special') ) NOT NULL DEFAULT 'auto', "
            + COL_FIBER_TYPE + " TEXT CHECK( " + COL_FIBER_TYPE + " IN ('SM', 'DS', 'NZ', 'MM') ) NOT NULL DEFAULT 'SM', "
            + COL_AUTO_FOCUS + " BOOLEAN CHECK( " + COL_AUTO_FOCUS + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0, "
            + COL_ECF_REDRESS + " BOOLEAN CHECK( " + COL_ECF_REDRESS + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0, "
            + COL_AUTO_MAG + " BOOLEAN CHECK( " + COL_AUTO_MAG + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0, "
            + COL_TENSION_TEST + " BOOLEAN CHECK( " + COL_TENSION_TEST + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0, "
    COL_KERF_LIMIT,
    COL_LOSS_LIMIT,
    COL_CORE_ANGLE_LIMIT,

    COL_CLEAN_TIME,
    COL_FUSION_GAP,
    COL_FUSION_POSITION,
    COL_PREFUSE_MAG,
    COL_PREFUSE_TIME,
    COL_FUSION_OVERLAP,
    COL_ARC1_MAG,
    COL_ARC1_TIME,
    COL_ARC2_MAG,
    COL_ARC2_TIME,
    COL_ARC2_ON_TIME,
    COL_ARC2_OFF_TIME,
    COL_MANUAL_ARC_TIME,

    COL_TAPER_SPLICE,
    COL_TAPER_WAIT,
    COL_TAPER_SPEED,
    COL_TAPER_LENGTH,

    COL_LOSS_EST_MODE,
    COL_LEFT_MFD,
    COL_RIGHT_MFD,
    COL_MIN_LOSS,
    COL_SYNTROPY_BEND_COEFFICIENT,
    COL_OPPOSITE_BEND_COEFFICIENT,
    COL_MFD_MISMATCH_COEFFICIENT,
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

