package com.example.ll.fsc.parameterfile;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ll.fsc.R;


/**
 * An activity representing a list of Parameter Files. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ParameterFileDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ParameterFileListFragment} and the item details
 * (if present) is a {@link ParameterFileDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ParameterFileListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ParameterFileListActivity extends ActionBarActivity
        implements ParameterFileListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameterfile_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fsc_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.parameterfile_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ParameterFileListFragment) getFragmentManager()
                    .findFragmentById(R.id.parameterfile_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish();
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link ParameterFileListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(long id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(ParameterFileDetailFragment.ARG_ITEM_ID, id);
            ParameterFileDetailFragment fragment = new ParameterFileDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.parameterfile_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ParameterFileDetailActivity.class);
            detailIntent.putExtra(ParameterFileDetailFragment.ARG_ITEM_ID, id);
            if (true) { // TODO
                detailIntent.putExtra(ParameterFileDetailFragment.ARG_IS_NEW, true);
            }
            startActivity(detailIntent);
        }
    }
}
