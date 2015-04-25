package com.example.ll.fsc_demo.parameterfile;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ll.fsc_demo.R;
import com.example.ll.fsc_demo.database.FsContentProvider;
import com.example.ll.fsc_demo.database.FsParamTbl;
import com.example.ll.fsc_demo.dummy.DummyContent;
import com.example.ll.fsc_demo.widgets.SeekBarDialogPreference;
import com.example.ll.fsc_demo.widgets.SeekBarPreference;

import java.util.HashMap;
import java.util.Hashtable;

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

    private static HashMap<String, ParamInitor> mInitor;
    private static ParamInitor mSwitchInitor = new ParamInitor() {
        @Override
        public void init(Cursor cursor, PreferenceScreen screen, final String key) {
            final int i = cursor.getColumnIndex(key);
            final Preference pref = screen.findPreference("fsp_" + key);
            final int newVal = cursor.getInt(i);
            ((SwitchPreference) pref).setChecked(newVal != 0);;
        }
    };
    private static ParamInitor mListInitor = new ParamInitor() {
        @Override
        public void init(Cursor cursor, PreferenceScreen screen, final String key) {
            final int i = cursor.getColumnIndex(key);
            final Preference pref = screen.findPreference("fsp_" + key);
            final String newVal = cursor.getString(i);
            ((ListPreference) pref).setValue(newVal);
        }
    };
    private static ParamInitor mTextInitor = new ParamInitor() {
        @Override
        public void init(Cursor cursor, PreferenceScreen screen, final String key) {
            final int i = cursor.getColumnIndex(key);
            final Preference pref = screen.findPreference("fsp_" + key);
            final String newVal = cursor.getString(i);
            Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
                private String mFormat = pref.getSummary().toString();
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue)
                {
                    preference.setSummary(String.format(mFormat, (String) newValue));
                    return true;
                }
            };
            if (pref instanceof EditTextPreference) {
                ((EditTextPreference) pref).setText(newVal);
                listener.onPreferenceChange(pref, newVal);
                pref.setOnPreferenceChangeListener(listener);
            }
        }
    };
    private static ParamInitor mSeekbarInitor = new ParamInitor() {
        @Override
        public void init(Cursor cursor, PreferenceScreen screen, String key) {
            final int i = cursor.getColumnIndex(key);
            final Preference pref = screen.findPreference("fsp_" + key);
            final int newVal = cursor.getInt(i);
            ((SeekBarPreference) pref).setProgress(newVal);
        }
    };
    private static ParamInitor mSeedbarDialogInitor = new ParamInitor() {
        @Override
        public void init(Cursor cursor, PreferenceScreen screen, String key) {
            final int i = cursor.getColumnIndex(key);
            final Preference pref = screen.findPreference("fsp_" + key);
            final int newVal = cursor.getInt(i);
            ((SeekBarDialogPreference) pref).setProgress(newVal);
        }
    };

    static {
        mInitor = new HashMap<>();
        mInitor.put(FsParamTbl.COL_NAME, mTextInitor);
        mInitor.put(FsParamTbl.COL_MODE, mListInitor);
        mInitor.put(FsParamTbl.COL_FIBER_TYPE, mListInitor);
        mInitor.put(FsParamTbl.COL_ALIGN_MODE, mListInitor);
        mInitor.put(FsParamTbl.COL_AUTO_FOCUS, mSwitchInitor);
        mInitor.put(FsParamTbl.COL_ECF_REDRESS, mSwitchInitor);
        mInitor.put(FsParamTbl.COL_AUTO_MAG, mSwitchInitor);
        mInitor.put(FsParamTbl.COL_TENSION_TEST, mSwitchInitor);
        mInitor.put(FsParamTbl.COL_KERF_LIMIT, mSeekbarInitor);
        mInitor.put(FsParamTbl.COL_LOSS_LIMIT, mSeedbarDialogInitor);
    }

    /**
     * The dummy content this fragment is presenting.
     */
    private Uri mUri;
    private DummyContent.DummyItem mItem;
    private HashMap<String, String> mOldContent = new HashMap<>();
    private HashMap<String, String> mNewContent = new HashMap<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParameterFileDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mUri = Uri.parse(FsContentProvider.URI_PATH_FSPARAMS + "/" + String.valueOf(getArguments().getLong(ARG_ITEM_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.xml.prefs_fs_parameterfile, container, false);

        addPreferencesFromResource(R.xml.prefs_fs_parameterfile);
        // disable persistent
        int cnt = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < cnt; ++i) {
            getPreferenceScreen().getPreference(i).setPersistent(false);
        }

        getPreferenceScreen().setEnabled(false);

        if (mUri != null) {
            fillData(mUri, FsParamTbl.FULL);
        }

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            // TODO ((TextView) rootView.findViewById(R.id.parameterfile_detail)).setText(mItem.content);
        }

        return ret;
    }

    /**
     * fill data using simple cursor adapter
     */
    private void fillData(final Uri uri, final String[] from) {
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int var1, Bundle var2) {
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        uri,
                        from, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> var1, Cursor var2) {
                if (var2 == null || var2.getCount() != 1) {
                    // TODO no any content
                    return;
                }

                var2.moveToFirst();

                for (HashMap.Entry<String, ParamInitor> entry : mInitor.entrySet()) {
                    final String key = entry.getKey();
                    final ParamInitor value = entry.getValue();
                    value.init(var2, getPreferenceScreen(), key);
                }

                var2.close();

                getPreferenceScreen().setEnabled(true);
                //mAdapter.swapCursor(var2);

                //setListShown(true);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> var1) {
                //mAdapter.swapCursor(null);
            }
        });
    }

    private interface ParamInitor {
        void init(Cursor cursor, PreferenceScreen screen, final String key);
    }
}
