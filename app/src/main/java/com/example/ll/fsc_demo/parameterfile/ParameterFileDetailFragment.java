package com.example.ll.fsc_demo.parameterfile;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ll.fsc_demo.R;
import com.example.ll.fsc_demo.database.FsContentProvider;
import com.example.ll.fsc_demo.database.FsParamTbl;
import com.example.ll.fsc_demo.dummy.DummyContent;

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
    private DummyContent.DummyItem mItem;

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
            /*
            fillData(FsContentProvider.URI_PATH_FSPARAMS + "/" + String.valueOf(getArguments().getLong(ARG_ITEM_ID)),
                    FsParamTbl.ABSTRACT,
                    R.layout.fragment_fs_parameterfile_list, new int[] {
                            R.id.fs_parameterfile_number,
                            R.id.fs_parameterfile_name,
                            R.id.fs_parameterfile_mode,
                            R.id.fs_parameterfile_fiber_type,
                    },
                    R.id.checkableChild,
                    R.id.fs_parameterfile_icon);
                    */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = super.onCreateView(inflater, container, savedInstanceState);
        //View rootView = inflater.inflate(R.xml.prefs_fs_parameterfile, container, false);

        addPreferencesFromResource(R.xml.prefs_fs_parameterfile);
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            // TODO ((TextView) rootView.findViewById(R.id.parameterfile_detail)).setText(mItem.content);
        }

        return ret;
    }

    /**
     * fill data using simple cursor adapter
     */
    private void fillData(final String uri, final String[] from, int layout, int[] to, int checker, int icon) {
        /*
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int var1, Bundle var2) {
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        Uri.parse(uri),
                        from, null, null, null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> var1, Cursor var2) {
                mAdapter.swapCursor(var2);

                setListShown(true);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> var1) {
                mAdapter.swapCursor(null);
            }
        });
        mAdapter = new ParameterFileListAdapter(getActivity(),
                layout,
                null, from, to, 0,
                checker, icon, R.drawable.ic_activated, R.drawable.ic_blank,
                10);

        setListAdapter(mAdapter);
        */
    }
}
