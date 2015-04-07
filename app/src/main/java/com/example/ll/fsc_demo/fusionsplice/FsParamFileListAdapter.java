package com.example.ll.fsc_demo.fusionsplice;

/**
 * Created by ll on 4/6/15.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ll.fsc_demo.R;
import com.example.ll.fsc_demo.database.FsDbHelper;

/*
public class FsParamFileListAdapter extends CursorAdapter {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_ACTIVATED = 1;
    private static final int TYPE_COUNT = TYPE_ACTIVATED + 1;

    private LayoutInflater mInflater;

    public FsParamFileListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return FsParamContent.getCount();
    }

    @Override
    public Object getItem(int position) {
        return FsParamContent.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return FsParamContent.isActivated(position) ? TYPE_ACTIVATED : TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public View newView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        // Only inflate the view if convertView is null
        if (convertView == null) {
            if (mInflater != null) {
                switch (type) {
                    case TYPE_NORMAL: {
                        convertView = mInflater.inflate(R.layout.fragment_fs_parameterfile_list, parent, false);

                        ViewHolderNormal viewHolderNormal = new ViewHolderNormal();
                        viewHolderNormal.id = (TextView) convertView.findViewById(R.id.fs_parameterfile_number);
                        viewHolderNormal.name = (TextView) convertView.findViewById(R.id.fs_parameterfile_name);
                        viewHolderNormal.mode = (TextView) convertView.findViewById(R.id.fs_parameterfile_mode);
                        viewHolderNormal.fiberType = (TextView) convertView.findViewById(R.id.fs_parameterfile_fiber_type);

                        convertView.setTag(viewHolderNormal);
                        break;
                    }
                    case TYPE_ACTIVATED: {
                        convertView = mInflater.inflate(R.layout.fragment_fs_parameterfile_list_activated, parent, false);

                        ViewHolderActivated viewHolderActivated = new ViewHolderActivated();
                        viewHolderActivated.id = (TextView) convertView.findViewById(R.id.fs_parameterfile_number);
                        viewHolderActivated.name = (TextView) convertView.findViewById(R.id.fs_parameterfile_name);
                        viewHolderActivated.mode = (TextView) convertView.findViewById(R.id.fs_parameterfile_mode);
                        viewHolderActivated.fiberType = (TextView) convertView.findViewById(R.id.fs_parameterfile_fiber_type);

                        convertView.setTag(viewHolderActivated);
                        break;
                    }
                }
            } else {
                Log.i("........", "" + null);
            }
        }
        // Set the views fields as needed
        FsParamFile item = (FsParamFile) getItem(position);

        switch (type) {
            case TYPE_NORMAL: {
                ViewHolderNormal viewHolderNormal = (ViewHolderNormal)convertView.getTag();
                viewHolderNormal.id.setText(String.valueOf(item.id));
                viewHolderNormal.name.setText(item.name);
                viewHolderNormal.mode.setText(String.valueOf(item.mode));
                viewHolderNormal.fiberType.setText(String.valueOf(item.fiberType));
                break;
            }
            case TYPE_ACTIVATED: {
                ViewHolderActivated viewHolderActivated = (ViewHolderActivated)convertView.getTag();
                viewHolderActivated.id.setText(String.valueOf(item.id));
                viewHolderActivated.name.setText(item.name);
                viewHolderActivated.mode.setText(String.valueOf(item.mode));
                viewHolderActivated.fiberType.setText(String.valueOf(item.fiberType));
                break;
            }
        }

        return convertView;
    }

    static private class ViewHolderNormal {
        TextView id;
        TextView name;
        TextView mode;
        TextView fiberType;
    }

    static private class ViewHolderActivated {
        TextView id;
        TextView name;
        TextView mode;
        TextView fiberType;
    }
}
*/
