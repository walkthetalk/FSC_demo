package com.example.ll.fsc_demo.parameterfile;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import com.example.ll.fsc_demo.R;
import com.example.ll.fsc_demo.database.FsContentProvider;
import com.example.ll.fsc_demo.database.FsParamTbl;
import com.example.ll.fsc_demo.dummy.DummyContent;

/**
 * A list fragment representing a list of Parameter Files. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ParameterFileDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ParameterFileListFragment extends ListFragment
    implements LoaderManager.LoaderCallbacks<Cursor>, ListView.MultiChoiceModeListener {

    private SimpleCursorAdapter mAdapter;

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
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
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

        fillData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(this);
        /*
        getListView().setOnItemClickListener(new OnItemClickListener() {
                        

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChoiceModeMultipleModalActivity.this, "选择了一个item", 300).show();
            }
        });
        */
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
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
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
    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] {
                FsParamTbl.COL_ID,
                FsParamTbl.COL_NAME,
                FsParamTbl.COL_MODE,
                FsParamTbl.COL_FIBER_TYPE,
        };
        // Fields on the UI to which we map
        int[] to = new int[] {
                R.id.fs_parameterfile_number,
                R.id.fs_parameterfile_name,
                R.id.fs_parameterfile_mode,
                R.id.fs_parameterfile_fiber_type,
        };

        getLoaderManager().initLoader(0, null, this);
        mAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.fragment_fs_parameterfile_list,
                null, from, to, 0);

        setListAdapter(mAdapter);
    }

    /**
     * implementation of {@link LoaderManager.LoaderCallbacks<Cursor>}
     */
    @Override
    public Loader<Cursor> onCreateLoader(int var1, Bundle var2)
    {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                FsContentProvider.CONTENT_URI,
                FsParamTbl.ABSTRACT, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> var1, Cursor var2)
    {
        mAdapter.swapCursor(var2);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> var1)
    {
        mAdapter.swapCursor(null);
    }

    /**
     * implementation of {@link android.widget.AbsListView.MultiChoiceModeListener}
     */
    private View mMultiSelectActionBarView;
    private TextView mSelectedCount;
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // actionmode的菜单处理
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.multi_select_menu, menu);
        if (mMultiSelectActionBarView == null) {
            mMultiSelectActionBarView = LayoutInflater.from(ChoiceModeMultipleModalActivity.this)
                    .inflate(R.layout.list_multi_select_actionbar, null);
            mSelectedCount =
                    (TextView) mMultiSelectActionBarView.findViewById(R.id.selected_conv_count);
        }
        mode.setCustomView(mMultiSelectActionBarView);

        ((TextView) mMultiSelectActionBarView.findViewById(R.id.title)).setText(R.string.select_item);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        if (mMultiSelectActionBarView == null) {

            ViewGroup v = (ViewGroup) LayoutInflater.from(ChoiceModeMultipleModalActivity.this)
                    .inflate(R.layout.list_multi_select_actionbar, null);
            mode.setCustomView(v);

            mSelectedCount = (TextView) v.findViewById(R.id.selected_conv_count);
        }
        //更新菜单的状态
        MenuItem mItem = menu.findItem(R.id.action_slelect);
        if (getListView().getCheckedItemCount() == mAdapter.getCount()) {
            mItem.setTitle(R.string.action_deselect_all);
        } else {
            mItem.setTitle(R.string.action_select_all);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_slelect:
                if (getListView().getCheckedItemCount() == mAdapter.getCount()) {
                    unSelectedAll();
                } else {
                    selectedAll();
                }
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        getListView().clearChoices();
    }


    @Override
    public void onItemCheckedStateChanged(ActionMode mode,
                                          int position, long id, boolean checked) {
        updateSeletedCount();
        mode.invalidate();
        mAdapter.notifyDataSetChanged();
    }


    private void updateSeletedCount() {
        mSelectedCount.setText(Integer.toString(getListView().getCheckedItemCount()));
    }

    private void selectedAll(){
        for(int i= 0; i< mAdapter.getCount(); i++){
            getListView().setItemChecked(i, true);
        }
        this.updateSeletedCount();
    }

    private void unSelectedAll(){
        getListView().clearChoices();
        getListView().setItemChecked(0, false);
        this.updateSeletedCount();
    }
}
