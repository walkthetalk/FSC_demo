package com.example.ll.fsc.parameterfile;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.ListFragment;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import com.example.ll.fsc.R;
import com.example.ll.fsc.database.FsContentProvider;
import com.example.ll.fsc.database.FsParamTbl;

/**
 * A list fragment representing a list of Parameter Files. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ParameterFileDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ParameterFileListFragment extends ListFragment {

    private ParameterFileListAdapter mAdapter;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(long id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParameterFileListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fillData(FsContentProvider.URI_PATH_FSPARAMS,
                FsParamTbl.ABSTRACT,
                R.layout.fragment_fs_parameterfile_list, new int[] {
                        R.id.fsp__id,
                        R.id.fsp_name,
                        R.id.fsp_mode,
                        R.id.fsp_fiber_type,
                },
                R.id.checkableChild,
                R.id.fs_parameterfile_icon);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.setListShownNoAnimation(false);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(new ListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                Log.i(checked ? "SELECTED " : "DESELECTED ", String.valueOf(position));
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        //deleteSelectedItems();
                        Log.i("LISTVIEW", " WANT DELETE");
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.parameter_file_list, menu);
                mAdapter.setActionMode(true);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
                mAdapter.setActionMode(false);
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * fill data using simple cursor adapter
     */
    private void fillData(final String uri, final String[] from, int layout, int[] to, int checker, int icon) {
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
    }
}
