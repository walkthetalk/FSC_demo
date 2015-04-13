package com.example.ll.fsc_demo.widgets;

import android.content.Context;
import android.content.DialogInterface;

import android.content.res.TypedArray;

import android.preference.DialogPreference;

import android.util.AttributeSet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ll.fsc_demo.R;

/**
 * The SeekBarDialogPreference class is a DialogPreference based and provides a
 * seekbar preference.
 * @author Casper Wakkers
 */
public class SeekBarDialogPreference extends
        DialogPreference implements SeekBar.OnSeekBarChangeListener {
    // Layout widgets.
    private SeekBar seekBar = null;
    private TextView valueText = null;

    // Custom xml attributes.
    private float maximumValue = 0;
    private float minimumValue = 0;
    private float stepSize = 0;
    private String units = null;

    private float value = 0;

    /**
     * The SeekBarDialogPreference constructor.
     * @param context of this preference.
     * @param attrs custom xml attributes.
     */
    public SeekBarDialogPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeekBarDialogPreference);
        maximumValue = a.getFloat(R.styleable.SeekBarDialogPreference_max, 0);
        minimumValue = a.getFloat(R.styleable.SeekBarDialogPreference_min, 0);
        stepSize = a.getFloat(R.styleable.SeekBarDialogPreference_step, 1);
        units = a.getString(R.styleable.SeekBarDialogPreference_unit);
        a.recycle();
    }

    public SeekBarDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public SeekBarDialogPreference(Context context) {
        this(context, null);
    }

    /**
     * {@inheritDoc}
     */
    protected View onCreateDialogView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View view = layoutInflater.inflate(
                R.layout.seekbardialogpreference, null);

        seekBar = (SeekBar)view.findViewById(R.id.seekbar);
        valueText = (TextView)view.findViewById(R.id.valueText);

        Log.i("HEHE ", String.valueOf(value) + "  set to " + String.valueOf((int) ((value - minimumValue) / stepSize)) + "  max is " + String.valueOf((int) ((maximumValue - minimumValue) / stepSize)));
/*
        value = getPersistedFloat(minimumValue);

        if (value < minimumValue) {
            value = minimumValue;
        }
        else if (value > maximumValue) {
            value = maximumValue;
        }
*/
        seekBar.setIndeterminate(false);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setKeyProgressIncrement(1);
        seekBar.setMax((int) ((maximumValue - minimumValue) / stepSize));
        Log.i("HEHE ", seekBar.isIndeterminate() ? "ERROR" : "OK");
        seekBar.setProgress((int) ((value - minimumValue) / stepSize));

        Log.i("HEHE get progress ", String.valueOf(seekBar.getProgress()) + " get max is " + String.valueOf(seekBar.getMax()));

        return view;
    }
    /**
     * {@inheritDoc}
     */
    public void onProgressChanged(SeekBar seek, int newValue,
                                  boolean fromTouch) {
        // Round the value to the closest integer value.
        value = newValue * stepSize + minimumValue;

        // Set the valueText text.
        valueText.setText(String.valueOf(value) +
                (units == null ? "" : units));

        callChangeListener(value);
    }
    /**
     * {@inheritDoc}
     */
    public void onStartTrackingTouch(SeekBar seek) {
    }
    /**
     * {@inheritDoc}
     */
    public void onStopTrackingTouch(SeekBar seek) {
    }
    /**
     * {@inheritDoc}
     */
    public void onClick(DialogInterface dialog, int which) {
        // if the positive button is clicked, we persist the value.
        if (which == DialogInterface.BUTTON_POSITIVE) {
                Log.i("HEHE CLICK ", String.valueOf(value));
                persistFloat(value);
        }

        super.onClick(dialog, which);
    }
}
