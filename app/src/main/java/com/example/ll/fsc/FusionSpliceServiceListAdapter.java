package com.example.ll.fsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ll on 4/5/15.
 */
public class FusionSpliceServiceListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public FusionSpliceServiceListAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return FusionSpliceServiceInfo.FS_SVC_INFO.length;
    }

    @Override
    public Object getItem(int position) {
        return FusionSpliceServiceInfo.FS_SVC_INFO[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Only inflate the view if convertView is null
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.fragment_fs_svc_list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.fs_svc_title);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.fs_svc_subtitle);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.fs_svc_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the views fields as needed
        viewHolder.title.setText(FusionSpliceServiceInfo.FS_SVC_INFO[position].toolbarInfo.title);
        viewHolder.subtitle.setText(FusionSpliceServiceInfo.FS_SVC_INFO[position].toolbarInfo.subtitle);
        viewHolder.icon.setImageResource(FusionSpliceServiceInfo.FS_SVC_INFO[position].toolbarInfo.icon);

        return convertView;
    }

    static private class ViewHolder {
        TextView title;
        TextView subtitle;
        ImageView icon;
    }

}
