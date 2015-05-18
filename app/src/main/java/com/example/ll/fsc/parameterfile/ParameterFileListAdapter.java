package com.example.ll.fsc.parameterfile;

/**
 * Created by ll on 4/6/15.
 */
import android.database.Cursor;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ParameterFileListAdapter extends SimpleCursorAdapter {

    private final int mActId;
    private final int mCheckableId;
    private final int mIconWidgetId;
    private final int mActResId;
    private final int mNormalResId;

    private boolean mIsInActionMode = false;

    public ParameterFileListAdapter(Context context, int layout, Cursor c, String[] from,
                                    int[] to, int flags,
                                    int checkableWidgetId,
                                    int iconWidgetId, int actResId, int normalResId,
                                    int actId) {
        super(context, layout, c, from, to, flags);
        mActId = actId;
        mCheckableId = checkableWidgetId;
        mIconWidgetId = iconWidgetId;
        mActResId = actResId;
        mNormalResId = normalResId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View convertView = super.newView(context, cursor, parent);

        final ViewHolder holder = new ViewHolder(mTo.length);
        for (int i = 0; i < mTo.length; ++i) {
            holder.hold[i] = convertView.findViewById(mTo[i]);
        }
        holder.checkable = convertView.findViewById(mCheckableId);
        holder.drawable = (ImageView)convertView.findViewById(mIconWidgetId);

        convertView.setTag(holder);

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        for (int i = 0; i < mFrom.length; ++i) {
            String text = cursor.getString(mFrom[i]);
            if (text == null) {
                text = "";
            }

            View v = holder.hold[i];

            if (v instanceof TextView) {
                setViewText((TextView) v, text);
            } else if (v instanceof ImageView) {
                setViewImage((ImageView) v, text);
            } else {
                throw new IllegalStateException(v.getClass().getName() + " is not a " +
                        " view that can be bounds by this SimpleCursorAdapter");
            }
        }

        /**
         * NOTE: the checked state is set by parent laybout
         */
        holder.checkable.setVisibility(mIsInActionMode ? View.VISIBLE : View.GONE);

        holder.drawable.setImageResource(getItemId(cursor.getPosition()) == mActId ? mActResId : mNormalResId);

        return;
    }

    public void setActionMode(boolean enable) {
        mIsInActionMode = enable;
    }

    static public class ViewHolder {
        View hold[];
        View checkable;
        ImageView drawable;

        ViewHolder(int n) {
            hold = new View[n];
        }
    }
}
