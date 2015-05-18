package com.example.ll.fsc.parameterfile;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ll.fsc.R;
import com.example.ll.fsc.database.FsContentProvider;
import com.example.ll.fsc.database.FsParamTbl;
import com.example.ll.fsc.widgets.ExtEditTextPreference;
import com.example.ll.fsc.widgets.ExtSeekBarDialogPreference;
import com.example.ll.fsc.widgets.ExtSeekBarPreference;

import java.util.HashSet;

/**
 * A fragment representing a single Parameter File detail screen.
 * This fragment is either contained in a {@link ParameterFileListActivity}
 * in two-pane mode (on tablets) or a {@link ParameterFileDetailActivity}
 * on handsets.
 */
public class ParameterFileDetailFragment extends PreferenceFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_IS_NEW = "is_new";

    /**
     * The contents this fragment is presenting.
     */
    private long mItemId = -1;
    private boolean mNew = false;

    private String mTblUriBase;
    private int mRowLimit = -1;
    private String mPrefKeyPrefix;
    private String mTblId;

    private final ContentValues mOldContent = new ContentValues();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParameterFileDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /// prepare all data
        mItemId = getArguments().getLong(ARG_ITEM_ID, -1);
        mNew = getArguments().getBoolean(ARG_IS_NEW, false);

        mTblUriBase = FsContentProvider.URI_PATH_FSPARAMS;
        mRowLimit = FsParamTbl.ROW_LIMIT;
        mPrefKeyPrefix = "fsp_";
        mTblId = FsParamTbl.COL_ID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.xml.prefs_fs_parameterfile, container, false);

        addPreferencesFromResource(R.xml.prefs_fs_parameterfile);
        getPreferenceScreen().setEnabled(false);

        // fill data
        fillData();
        fillId();

        return ret;
    }

    @Override
    public void onStop() {
        super.onStop();
        final ContentValues newContent = new ContentValues();
        if (mNew) {
            getFullData(getPreferenceScreen(), newContent);
            getActivity().getContentResolver().insert(Uri.parse(mTblUriBase), newContent);
        }
        else {
            getIncData(getPreferenceScreen(), newContent);
            if (newContent.size() != 0) {
                getActivity().getContentResolver().update(Uri.parse(mTblUriBase + "/" + String.valueOf(mItemId)), newContent, null, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fillId() {
        if (mNew) {
            getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int var1, Bundle var2) {
                    return new CursorLoader(getActivity(),
                            Uri.parse(mTblUriBase),
                            new String[]{"_id"}, null, null, null);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> var1, Cursor var2) {
                    var2.moveToPosition(-1);
                    HashSet<Integer> set = new HashSet<Integer>();
                    while (var2.moveToNext()) {
                        set.add(var2.getInt(0));
                    }
                    {
                        String[] tmp = new String[mRowLimit - set.size()];
                        int cnt = -1;
                        for (int i = 1; i <= mRowLimit; ++i) {
                            if (!set.contains(i)) {
                                tmp[++cnt] = String.valueOf(i);
                            }
                        }
                        final ListPreference mIdPref = (ListPreference)getPreferenceScreen().findPreference(mPrefKeyPrefix + mTblId);
                        mIdPref.setEntries(tmp);
                        mIdPref.setEntryValues(tmp);
                        mIdPref.setValue(tmp[0]);
                    }

                    var2.close();
                }

                @Override
                public void onLoaderReset(Loader<Cursor> var1) {
                    //mAdapter.swapCursor(null);
                }
            });
        }
        else {
            final String vs = String.valueOf(mItemId);
            final ListPreference mIdPref = (ListPreference)getPreferenceScreen().findPreference(mPrefKeyPrefix + mTblId);
            mIdPref.setEntries(new CharSequence[]{ vs });
            mIdPref.setEntryValues(new CharSequence[]{vs});
            mIdPref.setValue(vs);
            mIdPref.setEnabled(false);
        }
    }

    /**
     * fill data using simple cursor adapter
     */
    private void fillData() {
        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int var1, Bundle var2) {
                return new CursorLoader(getActivity(),
                        Uri.parse(mTblUriBase + "/" + String.valueOf(mItemId)),
                        null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> var1, Cursor var2) {
                if (var2 == null || var2.getCount() != 1) {
                    // TODO no any content or multiple entries
                    return;
                }

                var2.moveToFirst();

                /// NOTE don't care id
                for (int i = 1; i < var2.getColumnCount(); ++i) {
                    final String key = var2.getColumnName(i);
                    final Preference pref = getPreferenceScreen().findPreference(mPrefKeyPrefix + key);
                    if (pref instanceof ExtEditTextPreference) {
                        final String value = var2.getString(i);
                        mOldContent.put(key, value);
                        ((ExtEditTextPreference) pref).setText(value);
                    } else if (pref instanceof SwitchPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((SwitchPreference) pref).setChecked(value == 1);
                    } else if (pref instanceof ExtSeekBarPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((ExtSeekBarPreference) pref).setProgress(value);
                    } else if (pref instanceof ExtSeekBarDialogPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((ExtSeekBarDialogPreference) pref).setProgress(value);
                    } else if (pref instanceof ListPreference) {
                        final String value = var2.getString(i);
                        mOldContent.put(key, value);
                        ((ListPreference) pref).setValue(value);
                    } else {
                        Log.e("Prefrence type", " unknown");
                        continue;
                    }
                }

                var2.close();

                getPreferenceScreen().setEnabled(true);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> var1) {
                //mAdapter.swapCursor(null);
            }
        });
    }

    private void getFullData(final Preference pref, final ContentValues cv) {
        if (pref instanceof PreferenceCategory || pref instanceof PreferenceScreen) {
            final PreferenceGroup pg = (PreferenceGroup) pref;
            final int cnt = pg.getPreferenceCount();
            for (int i = 0; i < cnt; ++i) {
                getFullData(pg.getPreference(i), cv);
            }
        }
        else {
            final String key = pref.getKey().substring(mPrefKeyPrefix.length());
            if (pref instanceof ExtEditTextPreference) {
                cv.put(key, ((ExtEditTextPreference) pref).getText());
            } else if (pref instanceof SwitchPreference) {
                cv.put(key, ((SwitchPreference) pref).isChecked() ? 1 : 0);
            } else if (pref instanceof ExtSeekBarPreference) {
                cv.put(key, ((ExtSeekBarPreference) pref).getProgress());
            } else if (pref instanceof ExtSeekBarDialogPreference) {
                cv.put(key, ((ExtSeekBarDialogPreference) pref).getProgress());
            } else if (pref instanceof ListPreference) {
                cv.put(key, ((ListPreference) pref).getValue());
            } else {
                Log.e("Prefrence type", " unknown");
            }
        }

        return;
    }

    private void getIncData(final Preference pref, final ContentValues cv) {
        if (pref instanceof PreferenceCategory || pref instanceof PreferenceScreen) {
            final PreferenceGroup pg = (PreferenceGroup) pref;
            final int cnt = pg.getPreferenceCount();
            for (int i = 0; i < cnt; ++i) {
                getIncData(pg.getPreference(i), cv);
            }
        }
        else {
            final String key = pref.getKey().substring(mPrefKeyPrefix.length());
            if (pref instanceof ExtEditTextPreference) {
                final String v = ((ExtEditTextPreference)pref).getText();
                if (!mOldContent.getAsString(key).equals(v)) {
                    cv.put(key, v);
                }
            }
            else if (pref instanceof SwitchPreference) {
                final int v = ((SwitchPreference) pref).isChecked() ? 1 : 0;
                if (mOldContent.getAsInteger(key) != v) {
                    cv.put(key, v);
                }
            }
            else if (pref instanceof ExtSeekBarPreference) {
                final int v = ((ExtSeekBarPreference) pref).getProgress();
                if (mOldContent.getAsInteger(key) != v) {
                    cv.put(key, v);
                }
            }
            else if (pref instanceof ExtSeekBarDialogPreference) {
                final int v = ((ExtSeekBarDialogPreference) pref).getProgress();
                if (mOldContent.getAsInteger(key) != v) {
                    cv.put(key, v);
                }
            }
            else if (pref instanceof ListPreference) {
                final String v = ((ListPreference)pref).getValue();
                if (!mOldContent.getAsString(key).equals(v)) {
                    cv.put(key, ((ListPreference) pref).getValue());
                }
            }
            else {
                Log.e("Prefrence type", " unknown");
            }
        }

        return;
    }
}
