package com.example.ll.fsc_demo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.example.ll.fsc_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class CheckableLayout extends RelativeLayout implements Checkable {
    private int mCheckableChild = NO_ID;
    private ArrayList<Checkable> mCheckables = new ArrayList<>();

    public CheckableLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public CheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CheckableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CheckableLayout, defStyle, 0);

        if (a.hasValue(R.styleable.CheckableLayout_checkableChild)) {
            mCheckableChild = a.getResourceId(
                    R.styleable.CheckableLayout_checkableChild, mCheckableChild);
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
