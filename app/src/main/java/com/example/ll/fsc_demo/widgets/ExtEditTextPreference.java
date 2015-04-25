package com.example.ll.fsc_demo.widgets;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ll on 4/25/15.
 */
public class ExtEditTextPreference extends EditTextPreference {
    public ExtEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ExtEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ExtEditTextPreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextPreferenceStyle);
    }

    public ExtEditTextPreference(Context context) {
        this(context, null);
    }

    @Override
    public CharSequence getSummary() {
        final CharSequence format = super.getSummary();
        if (format == null) {
            return super.getText();
        } else {
            return String.format(format.toString(), super.getText());
        }
    }
}
