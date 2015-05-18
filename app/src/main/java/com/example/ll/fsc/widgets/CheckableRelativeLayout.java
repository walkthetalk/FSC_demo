package com.example.ll.fsc.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.example.ll.fsc.R;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
    private int mCheckableChild = NO_ID;
    private ArrayList<Checkable> mCheckables = new ArrayList<>();

    public CheckableRelativeLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CheckableRelativeLayout, defStyle, 0);

        if (a.hasValue(R.styleable.CheckableRelativeLayout_checkableChildId)) {
            mCheckableChild = a.getResourceId(
                    R.styleable.CheckableRelativeLayout_checkableChildId, mCheckableChild);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childCount = this.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            findCheckableChildren(this.getChildAt(i));
        }
    }

    @Override
    public void setChecked(boolean checked) {
        View child = super.findViewById(mCheckableChild);
        if (child instanceof Checkable) {
            ((Checkable) child).setChecked(checked);
        }

        for (Checkable tmp : mCheckables) {
            tmp.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        View child = super.findViewById(mCheckableChild);
        if (child instanceof Checkable) {
            return ((Checkable) child).isChecked();
        }

        if (!mCheckables.isEmpty()) {
            return mCheckables.get(0).isChecked();
        }

        return false;
    }

    @Override
    public void toggle() {
        View child = super.findViewById(mCheckableChild);
        if (child instanceof Checkable) {
            ((Checkable) child).toggle();
        }

        for (Checkable tmp : mCheckables) {
            tmp.toggle();
        }
    }

    private void findCheckableChildren(View v) {
        if (v instanceof Checkable) {
            v.setClickable(false);
            v.setLongClickable(false);
            this.mCheckables.add((Checkable) v);
        }

        if (v instanceof ViewGroup) {
            final ViewGroup vg = (ViewGroup) v;
            final int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                findCheckableChildren(vg.getChildAt(i));
            }
        }
    }
}
