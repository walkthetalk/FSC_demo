package com.example.ll.fsc.database;

/**
 * Created by ll on 4/7/15.
 */
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class FsContentProvider extends ContentProvider {

    private FsDbHelper mDB;

    private static final String AUTHORITY = "com.example.ll.fsc.database";
    private static final String URI_BASEPATH = "content://" + AUTHORITY + "/";

    private static final int URI_ID_FSPARAMS = 10;
    private static final int URI_ID_FSPARAM = 20;
    private static final String URI_BASEPATH_FSPARAMS = "fsparam";

    private static final int URI_ID_HEATPARAMS = 30;
    private static final int URI_ID_HEATPARAM = 40;
    private static final String URI_BASEPATH_HEATPARAMS = "heat";

    public static final String URI_PATH_FSPARAMS = URI_BASEPATH + URI_BASEPATH_FSPARAMS;
    public static final String URI_PATH_HEATPARAMS = URI_BASEPATH + URI_BASEPATH_HEATPARAMS;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, URI_BASEPATH_FSPARAMS, URI_ID_FSPARAMS);
        sURIMatcher.addURI(AUTHORITY, URI_BASEPATH_FSPARAMS + "/#", URI_ID_FSPARAM);
        sURIMatcher.addURI(AUTHORITY, URI_BASEPATH_HEATPARAMS, URI_ID_HEATPARAMS);
        sURIMatcher.addURI(AUTHORITY, URI_BASEPATH_HEATPARAMS + "/#", URI_ID_HEATPARAM);
    }

    @Override
    public boolean onCreate() {
        mDB = new FsDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (sURIMatcher.match(uri)) {
            case URI_ID_FSPARAMS:
                checkColumns(projection, FsParamTbl.FULL);
                queryBuilder.setTables(FsParamTbl.TBL_NAME);
                break;
            case URI_ID_FSPARAM:
                checkColumns(projection, FsParamTbl.FULL);
                queryBuilder.setTables(FsParamTbl.TBL_NAME);
                // adding the ID to the original query
                queryBuilder.appendWhere(FsParamTbl.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            case URI_ID_HEATPARAMS:
                checkColumns(projection, HeatParamTbl.FULL);
                queryBuilder.setTables(HeatParamTbl.TBL_NAME);
                break;
            case URI_ID_HEATPARAM:
                checkColumns(projection, HeatParamTbl.FULL);
                queryBuilder.setTables(HeatParamTbl.TBL_NAME);
                // adding the ID to the original query
                queryBuilder.appendWhere(FsParamTbl.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI FOR QUERY: " + uri);
        }

        SQLiteDatabase db = mDB.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String ret = null;
        switch (sURIMatcher.match(uri)) {
            case URI_ID_FSPARAMS:
                ret = ContentResolver.CURSOR_DIR_BASE_TYPE + URI_BASEPATH_FSPARAMS;
                break;
            case URI_ID_FSPARAM:
                ret = ContentResolver.CURSOR_ITEM_BASE_TYPE + URI_BASEPATH_FSPARAMS;
                break;
            case URI_ID_HEATPARAMS:
                ret = ContentResolver.CURSOR_DIR_BASE_TYPE + URI_BASEPATH_HEATPARAMS;
                break;
            case URI_ID_HEATPARAM:
                ret = ContentResolver.CURSOR_ITEM_BASE_TYPE + URI_BASEPATH_HEATPARAMS;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI FOR GETTYPE: " + uri);
        }

        return ret;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        String tbl = null;
        switch (sURIMatcher.match(uri)) {
            case URI_ID_FSPARAMS:
                tbl = FsParamTbl.TBL_NAME;
                break;
            case URI_ID_HEATPARAMS:
                tbl = HeatParamTbl.TBL_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI FOR INSERT: " + uri);
        }
        final long id = sqlDB.insert(FsParamTbl.TBL_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri.toString() + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsDeleted = 0;
        String id = null;
        switch (uriType) {
            case URI_ID_FSPARAMS:
                rowsDeleted = sqlDB.delete(FsParamTbl.TBL_NAME, selection,
                        selectionArgs);
                break;
            case URI_ID_FSPARAM:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FsParamTbl.TBL_NAME,
                            FsParamTbl.COL_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(FsParamTbl.TBL_NAME,
                            FsParamTbl.COL_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case URI_ID_HEATPARAMS:
                rowsDeleted = sqlDB.delete(HeatParamTbl.TBL_NAME, selection,
                        selectionArgs);
                break;
            case URI_ID_HEATPARAM:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(HeatParamTbl.TBL_NAME,
                            FsParamTbl.COL_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(HeatParamTbl.TBL_NAME,
                            FsParamTbl.COL_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI FOR DELETE: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsUpdated = 0;
        String id = null;
        switch (uriType) {
            case URI_ID_FSPARAMS:
                rowsUpdated = sqlDB.update(FsParamTbl.TBL_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case URI_ID_FSPARAM:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(FsParamTbl.TBL_NAME,
                            values,
                            FsParamTbl.COL_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(FsParamTbl.TBL_NAME,
                            values,
                            FsParamTbl.COL_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case URI_ID_HEATPARAMS:
                rowsUpdated = sqlDB.update(HeatParamTbl.TBL_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case URI_ID_HEATPARAM:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(HeatParamTbl.TBL_NAME,
                            values,
                            FsParamTbl.COL_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(HeatParamTbl.TBL_NAME,
                            values,
                            FsParamTbl.COL_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI FOR UPDATE: " + uri);
        }

        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    private void checkColumns(String[] projection, String[] available) {
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
