package com.example.ll.fsc_demo.parameterfile;

import android.app.LoaderManager;
import android.content.ContentValues;
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
import com.example.ll.fsc_demo.widgets.ExtEditTextPreference;
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

    /**
     * The dummy content this fragment is presenting.
     */
    private Uri mUri;
    private DummyContent.DummyItem mItem;
    private ContentValues mOldContent = new ContentValues();
    private ContentValues mNewContent = new ContentValues();

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

    @Override
    public void onStop() {
        super.onStop();
        if (mNewContent.size() != 0) {
            getActivity().getContentResolver().update(mUri, mNewContent, null, null);
            Log.d("DETAIL ", "onStop------------------------");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DETAIL ", "onDestroy------------------------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("DETAIL ", "onDetach------------------------");
    }

    private Preference.OnPreferenceChangeListener mTextListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final String key = preference.getKey().substring("fsp_".length());
            final String value = (String)newValue;
            final String oldValue = mOldContent.getAsString(key);
            if (oldValue.equals(value)) {
                mNewContent.remove(key);
            }
            else {
                mNewContent.put(key, value);
            }
            return true;
        }
    };

    private Preference.OnPreferenceChangeListener mSwitchListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final String key = preference.getKey().substring("fsp_".length());
            final int value = ((Boolean)newValue) ? 1 : 0;
            final int oldValue = mOldContent.getAsInteger(key);
            if (oldValue == value) {
                mNewContent.remove(key);
            }
            else {
                mNewContent.put(key, value);
            }
            return true;
        }
    };

    private Preference.OnPreferenceChangeListener mIntListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            final String key = preference.getKey().substring("fsp_".length());
            final int value = (int)newValue;
            final int oldValue = mOldContent.getAsInteger(key);
            if (oldValue == value) {
                mNewContent.remove(key);
            }
            else {
                mNewContent.put(key, value);
            }
            return true;
        }
    };

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
                    // TODO no any content or multiple entries
                    return;
                }

                var2.moveToFirst();

                /// NOTE don't care id
                for (int i = 1; i < var2.getColumnCount(); ++i) {
                    final String key = var2.getColumnName(i);
                    final Preference pref = getPreferenceScreen().findPreference("fsp_" + key);
                    if (pref instanceof ExtEditTextPreference) {
                        final String value = var2.getString(i);
                        mOldContent.put(key, value);
                        ((ExtEditTextPreference)pref).setText(var2.getString(i));
                        pref.setOnPreferenceChangeListener(mTextListener);
                    }
                    else if (pref instanceof SwitchPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((SwitchPreference)pref).setChecked(var2.getInt(i) == 1);
                        pref.setOnPreferenceChangeListener(mSwitchListener);
                    }
                    else if (pref instanceof SeekBarPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((SeekBarPreference)pref).setProgress(var2.getInt(i));
                        pref.setOnPreferenceChangeListener(mIntListener);
                    }
                    else if (pref instanceof SeekBarDialogPreference) {
                        final int value = var2.getInt(i);
                        mOldContent.put(key, value);
                        ((SeekBarDialogPreference)pref).setProgress(var2.getInt(i));
                        pref.setOnPreferenceChangeListener(mIntListener);
                    }
                    else if (pref instanceof ListPreference) {
                        final String value = var2.getString(i);
                        mOldContent.put(key, value);
                        ((ListPreference)pref).setValue(var2.getString(i));
                        pref.setOnPreferenceChangeListener(mTextListener);
                    }
                    else {
                        Log.e("Prefrence type", " unknown");
                        continue;
                    }
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
}
