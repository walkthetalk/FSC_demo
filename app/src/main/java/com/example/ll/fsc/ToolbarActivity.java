package com.example.ll.fsc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by ll on 4/6/15.
 */
public class ToolbarActivity extends ActionBarActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fsc_toolbar);
        //Toolbar will now take on default actionbar characteristics
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
