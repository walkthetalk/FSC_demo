package com.example.ll.fsc_demo.parameterfile;

/**
 * Created by ll on 4/6/15.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ll.fsc_demo.R;
import com.example.ll.fsc_demo.database.FsDbHelper;
import com.example.ll.fsc_demo.fusionsplice.FsParamFile;

public class ParameterFileListAdapter extends SimpleCursorAdapter {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_ACTIVATED = 1;
    private static final int TYPE_COUNT = TYPE_ACTIVATED + 1;

    private final int mActId;
    private final int mActLayout;
    private final int mCheckableId;
    private final CheckedInfo mCheckedInfo;

    private boolean mIsInActionMode = false;

    /**
     * it is same as it in base class.
     */
    private LayoutInflater mInflater;

    public ParameterFileListAdapter(Context context, int layout, Cursor c, String[] from,
                                    int[] to, int flags, int actId, int actLayout, int checkableId, CheckedInfo checkedInfo) {
        super(context, layout, c, from, to, flags);
        mActId = actId;
        mActLayout = actLayout;
        mCheckableId = checkableId;
        mCheckedInfo = checkedInfo;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (mCursor == null) {
            Log.e("ADAPTER", " no cursor");
            return TYPE_NORMAL;
        }

        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }

        return getItemViewType(mCursor);
    }

    private int getItemViewType(Cursor cursor) {
        if (mActId == cursor.getInt(mRowIDColumn)) {
            return TYPE_ACTIVATED;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int type = getItemViewType(cursor);
        View convertView;
        switch (type) {
            case TYPE_NORMAL:
                convertView = super.newView(context, cursor, parent);
                break;
            case TYPE_ACTIVATED:
                convertView = mInflater.inflate(mActLayout, parent, false);
                break;
            default:
                throw new IllegalStateException("invalid parameterfilelistadapter view type " + type);
        }

        ViewHolder holder = new ViewHolder(mTo.length);
        for (int i = 0; i < mTo.length; ++i) {
            holder.hold[i] = convertView.findViewById(mTo[i]);
        }
        holder.checkable = convertView.findViewById(mCheckableId);
        Log.i("HOLDER.CHECKABLE ", holder.checkable == null ? " IS NULL" : "NOT NULL");

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
         * NOTE: we should ensure {@link Cursor.getPosition} is stable.
         */
        holder.checkable.setVisibility(mIsInActionMode ? View.VISIBLE : View.GONE);
        ((Checkable) holder.checkable).setChecked(mCheckedInfo.isItemChecked(cursor.getPosition()));

        return;
    }

    public void setActionMode(boolean enable) {
        mIsInActionMode = enable;
    }

    static private class ViewHolder {
        View hold[];
        View checkable;

        ViewHolder(int n) {
            hold = new View[n];
        }
    }

    public interface CheckedInfo {
        public boolean isItemChecked(int position);
    }
}
