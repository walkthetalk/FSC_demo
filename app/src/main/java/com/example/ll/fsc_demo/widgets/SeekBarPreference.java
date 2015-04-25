package com.example.ll.fsc_demo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ll.fsc_demo.R;

public class SeekBarPreference extends Preference implements SeekBar.OnSeekBarChangeListener {
    private static class SavedState extends BaseSavedState {
        int min;
        int max;
        int step;
        int ratio;
        int progress;

        public SavedState(Parcel source) {
            super(source);
            progress = source.readInt();
            min = source.readInt();
            max = source.readInt();
            step = source.readInt();
            ratio = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(progress);
            dest.writeInt(min);
            dest.writeInt(max);
            dest.writeInt(step);
            dest.writeInt(ratio);
        }
    }

    private int mMin = 0;
    private int mMax = 100;
    private int mProgress = 50;
    private int mStep = 1;
    private int mRatio = 1;

    private boolean mTrackingTouch;
    private TextView mSummaryView;

    public SeekBarPreference(Context context) {
        this(context, null);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SeekBarPreference, defStyle, 0);
        setMin(a.getInt(R.styleable.SeekBarPreference_min, mMin));
        setMax(a.getInt(R.styleable.SeekBarPreference_max, mMax));
        setStep(a.getInt(R.styleable.SeekBarPreference_step, mStep));
        setRatio(a.getInt(R.styleable.SeekBarPreference_ratio, mRatio));
        a.recycle();
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(toAbsProgress(mMax));
        seekBar.setProgress(toAbsProgress(mProgress));
        seekBar.setEnabled(isEnabled());

        mSummaryView = (TextView) view.findViewById(android.R.id.summary);
    }

    @Override
    protected Integer onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        if (fromUser) {
            syncProgress(seekBar);
        }
    }

    @Override
    public CharSequence getSummary() {
        CharSequence tmp = super.getSummary();
        return String.format(tmp.toString(), ((float)mProgress / mRatio));
    }

    @Override
    public void setSummary(CharSequence summary) {
        super.setSummary(summary);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mProgress = myState.progress;
        mMin = myState.min;
        mMax = myState.max;
        mStep = myState.step;
        mRatio = myState.ratio;
        notifyChanged();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            return superState;
        }
        final SavedState myState = new SavedState(superState);
        myState.progress = mProgress;
        myState.max = mMax;
        myState.min = mMin;
        myState.step = mStep;
        myState.ratio = mRatio;
        return myState;
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setProgress(restoreValue ? getPersistedInt(mProgress)
                : (Integer) defaultValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mTrackingTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTrackingTouch = false;
        if (toRealProgress(seekBar.getProgress()) != mProgress) {
            syncProgress(seekBar);
        }
    }

    public void setMax(int max) {
        if (max != mMax) {
            mMax = max;
            notifyChanged();
        }
    }

    public void setMin(int min) {
        if (min != mMin) {
            mMin = min;
            notifyChanged();
        }
    }

    public void setStep(int step) {
        if (step != mStep) {
            mStep = step;
            notifyChanged();
        }
    }

    public void setRatio(int ratio) {
        if (ratio != mRatio) {
            mRatio = ratio;
            notifyChanged();
        }
    }

    public int getRatio() {
        return mRatio;
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    private void setProgress(int progress, boolean notifyChanged) {
        if (progress > mMax) {
            progress = mMax;
        }
        if (progress < mMin) {
            progress = mMin;
        }
        if (progress != mProgress) {
            mProgress = progress;
            persistInt(progress);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    private void syncProgress(SeekBar seekBar) {
        final int progress = seekBar.getProgress();
        final int realProgress = toRealProgress(progress);
        if (realProgress != mProgress) {
            if (callChangeListener(realProgress)) {
                setProgress(realProgress, false);
                if (mSummaryView != null) {
                    mSummaryView.setText(getSummary());
                }
            } else {
                seekBar.setProgress(toAbsProgress(mProgress));
            }
        }
    }

    private int toRealProgress(int abs) {
        return abs * mStep + mMin;
    }

    private int toAbsProgress(int real) {
        return (real - mMin) / mStep;
    }
}
