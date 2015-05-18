package com.example.ll.fsc.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ll on 4/7/15.
 */
public class FsParamTbl {
    public static final String TBL_NAME = "fusion_splice_param";
    public static final int ROW_LIMIT = 80;

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_MODE = "mode";
    public static final String COL_FIBER_TYPE = "fiber_type";
    public static final String COL_AUTO_FOCUS = "auto_focus";
    public static final String COL_ECF_REDRESS = "ecf_redress";
    public static final String COL_ALIGN_MODE = "align_mode";
    public static final String COL_AUTO_MAG = "auto_mag";
    public static final String COL_TENSION_TEST = "tension_test";

    public static final String COL_KERF_LIMIT = "kerf_limit";
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

    public static final String COL_EST_LOSS = "est_loss";
    public static final String COL_LOSS_LIMIT = "loss_limit";
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
            COL_ALIGN_MODE,
            COL_AUTO_MAG,
            COL_TENSION_TEST,

            COL_KERF_LIMIT,
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

            COL_EST_LOSS,
            COL_LOSS_LIMIT,
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
            + "  " +  COL_ID + " INTEGER CHECK( " + COL_ID + " < " + ROW_LIMIT + ") PRIMARY KEY AUTOINCREMENT"
            + ", " +  COL_NAME + " TEXT NOT NULL"
            + ", " +  COL_MODE + " TEXT CHECK( " + COL_MODE + " IN ('auto', 'calibrate', 'normal', 'special') ) NOT NULL DEFAULT 'auto'"
            + ", " +  COL_FIBER_TYPE + " TEXT CHECK( " + COL_FIBER_TYPE + " IN ('SM', 'DS', 'NZ', 'MM') ) NOT NULL DEFAULT 'SM'"
            + ", " +  COL_AUTO_FOCUS + " BOOLEAN CHECK( " + COL_AUTO_FOCUS + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0"
            + ", " +  COL_ECF_REDRESS + " BOOLEAN CHECK( " + COL_ECF_REDRESS + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0"
            + ", " +  COL_ALIGN_MODE + " TEXT CHECK( " + COL_ALIGN_MODE + " IN ('fine', 'clad', 'core', 'manual') ) NOT NULL DEFAULT 'fine'"
            + ", " +  COL_AUTO_MAG + " BOOLEAN CHECK( " + COL_AUTO_MAG + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0"
            + ", " +  COL_TENSION_TEST + " BOOLEAN CHECK( " + COL_TENSION_TEST + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0"

            + ", " +  COL_KERF_LIMIT + " INTEGER CHECK( " + COL_KERF_LIMIT + " <= 1000 ) NOT NULL DEFAULT 200"     // unit: 0.01
            + ", " +  COL_CORE_ANGLE_LIMIT + " INTEGER CHECK( " + COL_CORE_ANGLE_LIMIT + " < 100 ) NOT NULL DEFAULT 10"   // unit: 0.01

            + ", " +  COL_CLEAN_TIME + " INTEGER CHECK( " + COL_CLEAN_TIME + " <= 1000 ) NOT NULL DEFAULT 200"     // unit: ms
            + ", " +  COL_FUSION_GAP + " INTEGER CHECK( " + COL_FUSION_GAP + " <= 20 ) NOT NULL DEFAULT 8" // unit: um
            + ", " +  COL_FUSION_POSITION + " INTEGER CHECK( -20 < " + COL_FUSION_POSITION + " AND " + COL_FUSION_POSITION + " <= 20 ) NOT NULL DEFAULT 0" // unit: um
            + ", " +  COL_PREFUSE_MAG + " INTEGER CHECK( " + COL_PREFUSE_MAG + " <= 500 ) NOT NULL DEFAULT 100"     // unit: 0.01V
            + ", " +  COL_PREFUSE_TIME + " INTEGER CHECK( " + COL_PREFUSE_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_FUSION_OVERLAP + " INTEGER CHECK( " + COL_FUSION_OVERLAP + " <= 20 ) NOT NULL DEFAULT 10"     // unit: um

            + ", " +  COL_ARC1_MAG + " INTEGER CHECK( " + COL_ARC1_MAG + " <= 500 ) NOT NULL DEFAULT 100"     // unit: 0.01V
            + ", " +  COL_ARC1_TIME + " INTEGER CHECK( " + COL_ARC1_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_ARC2_MAG + " INTEGER CHECK( " + COL_ARC2_MAG + " <= 500 ) NOT NULL DEFAULT 100"     // unit: 0.01V
            + ", " +  COL_ARC2_TIME + " INTEGER CHECK( " + COL_ARC2_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_ARC2_ON_TIME + " INTEGER CHECK( " + COL_ARC2_ON_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_ARC2_OFF_TIME + " INTEGER CHECK( " + COL_ARC2_OFF_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_MANUAL_ARC_TIME + " INTEGER CHECK( " + COL_MANUAL_ARC_TIME + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms

            + ", " +  COL_TAPER_SPLICE + " BOOLEAN CHECK( " + COL_TAPER_SPLICE + " IN ( 0, 1 ) ) NOT NULL DEFAULT 0"
            + ", " +  COL_TAPER_WAIT + " INTEGER CHECK( " + COL_TAPER_WAIT + " <= 2000 ) NOT NULL DEFAULT 500"     // unit: ms
            + ", " +  COL_TAPER_SPEED + " INTEGER CHECK( " + COL_TAPER_SPEED + " <= 100 ) NOT NULL DEFAULT 20"     // unit: 0.01
            + ", " +  COL_TAPER_LENGTH + " INTEGER CHECK( " + COL_TAPER_LENGTH + " <= 100 ) NOT NULL DEFAULT 50"     // unit: um

            + ", " +  COL_EST_LOSS + " BOOLEAN CHECK( " + COL_EST_LOSS + " IN ( 0, 1 ) ) NOT NULL DEFAULT 1"
            + ", " +  COL_LOSS_LIMIT + " INTEGER CHECK( " + COL_LOSS_LIMIT + " <= 100 ) NOT NULL DEFAULT 3"        // unit: 0.01db
            + ", " +  COL_LOSS_EST_MODE + " TEXT CHECK( " + COL_LOSS_EST_MODE + " IN ('fine', 'core', 'cladding') ) NOT NULL DEFAULT 'fine'"
            + ", " +  COL_LEFT_MFD + " INTEGER CHECK( " + COL_LEFT_MFD + " <= 1250 ) NOT NULL DEFAULT 93"     // unit: 0.1um
            + ", " +  COL_RIGHT_MFD + " INTEGER CHECK( " + COL_RIGHT_MFD + " <= 1250 ) NOT NULL DEFAULT 93"     // unit: 0.1um
            + ", " +  COL_MIN_LOSS + " INTEGER CHECK( " + COL_MIN_LOSS + " <= 100 ) NOT NULL DEFAULT 1"        // unit: 0.01db
            + ", " +  COL_SYNTROPY_BEND_COEFFICIENT + " INTEGER CHECK( " + COL_SYNTROPY_BEND_COEFFICIENT + " <= 100 ) NOT NULL DEFAULT 1"        // unit: 0.01
            + ", " +  COL_OPPOSITE_BEND_COEFFICIENT + " INTEGER CHECK( " + COL_OPPOSITE_BEND_COEFFICIENT + " <= 100 ) NOT NULL DEFAULT 1"        // unit: 0.01
            + ", " +  COL_MFD_MISMATCH_COEFFICIENT + " INTEGER CHECK( " + COL_MFD_MISMATCH_COEFFICIENT + " <= 100 ) NOT NULL DEFAULT 1"        // unit: 0.01

            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TBL);

        /// just for test TODO: delete it
        String insertHeader = "INSERT INTO " + TBL_NAME + "("
                + COL_ID + ","
                + COL_NAME + ","
                + COL_MODE + ","
                + COL_FIBER_TYPE + ","
                + COL_AUTO_FOCUS + ","
                + COL_ECF_REDRESS + ","
                + COL_ALIGN_MODE + ","
                + COL_AUTO_MAG + ","
                + COL_TENSION_TEST + ","

                + COL_KERF_LIMIT + ","
                + COL_CORE_ANGLE_LIMIT + ","

                + COL_CLEAN_TIME + ","
                + COL_FUSION_GAP + ","
                + COL_FUSION_POSITION + ","
                + COL_PREFUSE_MAG + ","
                + COL_PREFUSE_TIME + ","
                + COL_FUSION_OVERLAP + ","
                + COL_ARC1_MAG + ","
                + COL_ARC1_TIME + ","
                + COL_ARC2_MAG + ","
                + COL_ARC2_TIME + ","
                + COL_ARC2_ON_TIME + ","
                + COL_ARC2_OFF_TIME + ","
                + COL_MANUAL_ARC_TIME + ","

                + COL_TAPER_SPLICE + ","
                + COL_TAPER_WAIT + ","
                + COL_TAPER_SPEED + ","
                + COL_TAPER_LENGTH + ","

                + COL_EST_LOSS + ","
                + COL_LOSS_LIMIT + ","
                + COL_LOSS_EST_MODE + ","
                + COL_LEFT_MFD + ","
                + COL_RIGHT_MFD + ","
                + COL_MIN_LOSS + ","
                + COL_SYNTROPY_BEND_COEFFICIENT + ","
                + COL_OPPOSITE_BEND_COEFFICIENT + ","
                + COL_MFD_MISMATCH_COEFFICIENT + ") VALUES(";
        String insertTail = ");";

        database.execSQL(insertHeader + "1,  'test1',  'auto',      'SM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "2,  'test2',  'auto',      'DS', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "4,  'test4',  'auto',      'NZ', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "10, 'test10', 'auto',      'MM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "21, 'test21', 'calibrate', 'SM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "22, 'test22', 'calibrate', 'DS', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "25, 'test25', 'calibrate', 'NZ', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "29, 'test29', 'calibrate', 'MM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "33, 'test33', 'normal',    'SM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "36, 'test36', 'normal',    'DS', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "40, 'test40', 'normal',    'NZ', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "42, 'test42', 'normal',    'MM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "45, 'test45', 'special',   'SM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "46, 'test46', 'special',   'DS', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "48, 'test48', 'special',   'NZ', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
        database.execSQL(insertHeader + "49, 'test49', 'special',   'MM', 1, 1, 'fine', 1, 0, 200, 10, 200, 8, 0, 100, 500, 10, 100, 500, 100, 500, 500, 500, 500, 0, 500, 20, 50, " + "1, 3, 'fine', 93, 93, 1, 1, 1, 1 " + insertTail);
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

