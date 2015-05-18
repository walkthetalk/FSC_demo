package com.example.ll.fsc.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ll.fsc.R;

public class ExtSeekBarPreference extends Preference implements SeekBar.OnSeekBarChangeListener {
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

    private CharSequence mSummaryP;
    private CharSequence mSummaryZ;
    private CharSequence mSummaryN;

    private boolean mTrackingTouch;
    private TextView mSummaryView;

    public ExtSeekBarPreference(Context context) {
        this(context, null);
    }

    public ExtSeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public ExtSeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ExtSeekBarPreference, defStyle, 0);
        mSummaryP = a.getString(R.styleable.ExtSeekBarPreference_summaryp);
        mSummaryZ = a.getString(R.styleable.ExtSeekBarPreference_summaryz);
        mSummaryN = a.getString(R.styleable.ExtSeekBarPreference_summaryn);
        setMin(a.getInt(R.styleable.ExtSeekBarPreference_min, mMin));
        setMax(a.getInt(R.styleable.ExtSeekBarPreference_max, mMax));
        setStep(a.getInt(R.styleable.ExtSeekBarPreference_step, mStep));
        setRatio(a.getInt(R.styleable.ExtSeekBarPreference_ratio, mRatio));
        a.recycle();
    }

    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mSummaryView = (TextView) view.findViewById(android.R.id.summary);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(toAbsProgress(mMax));
        seekBar.setProgress(toAbsProgress(mProgress));
        seekBar.setEnabled(isEnabled());
    }

    @Override
    protected Integer onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    @Override
    public CharSequence getSummary() {
        int progress = mProgress;
        CharSequence tmp = super.getSummary();
        if (mProgress > 0) {
            if (mSummaryP != null) {
                tmp = mSummaryP;
            }
        }
        else if (mProgress < 0) {
            if (mSummaryN != null) {
                progress = -progress;
                tmp = mSummaryN;
            }
        }
        else {
            if (mSummaryZ != null) {
                tmp = mSummaryZ;
            }
        }
        if (TextUtils.isEmpty(tmp)) {
            return null;
        }

        if (mRatio == 1) {
            return String.format(tmp.toString(), progress);
        }
        else {
            return String.format(tmp.toString(), ((float) progress / mRatio));
        }
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            final int realProgress = toRealProgress(seekBar.getProgress());
            if (realProgress == mProgress) {
                return;
            }

            if (callChangeListener(realProgress)) {
                mProgress = realProgress;
                persistInt(realProgress);
                //notifyChanged(); // NOTE can't notify self
                mSummaryView.setText(getSummary());
            }
            else {
                seekBar.setProgress(toAbsProgress(mProgress));
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mTrackingTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTrackingTouch = false;
    }

    public void setMax(int max) {
        if (max != mMax) {
            if (max < mMin) {
                throw new IllegalArgumentException("max value out of range");
            }
            mMax = max;
            if (mProgress > mMax) {
                mProgress = mMax;
                callChangeListener(mProgress);
            }
            notifyChanged();
        }
    }

    public void setMin(int min) {
        if (min != mMin) {
            if (mMax < min) {
                throw new IllegalArgumentException("min value out of range");
            }
            mMin = min;
            if (mProgress < mMin) {
                mProgress = mMin;
                callChangeListener(mProgress);
            }
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

        if (progress < mMin || mMax < progress) {
            throw new IllegalArgumentException("progress out of range");
        }

        if (progress == mProgress) {
            return;
        }

        if (callChangeListener(progress)) {
            mProgress = progress;
            persistInt(progress);
            notifyChanged();
        }
    }

    private int toRealProgress(int abs) {
        return abs * mStep + mMin;
    }

    private int toAbsProgress(int real) {
        return (real - mMin) / mStep;
    }
}
