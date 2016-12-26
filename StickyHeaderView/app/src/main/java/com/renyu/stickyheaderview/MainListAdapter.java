package com.renyu.stickyheaderview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by renyu on 2016/12/21.
 */

public class MainListAdapter extends BaseAdapter {

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    ArrayList<String> strings;
    Context context;

    public MainListAdapter(ArrayList<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainListViewHolder holder;
        if (convertView==null) {
            holder=new MainListViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_item, parent, false);
            holder.header_view= (TextView) convertView.findViewById(R.id.header_view);
            convertView.setTag(holder);
        }
        else {
            holder= (MainListViewHolder) convertView.getTag();
        }
        holder.header_view.setText(strings.get(position));
        convertView.setContentDescription(strings.get(position));
        if (position==0) {
            holder.header_view.setVisibility(View.VISIBLE);
            holder.header_view.setTag(FIRST_STICKY_VIEW);
        }
        else {
            if (!strings.get(position).equals(strings.get(position-1))) {
                holder.header_view.setVisibility(View.VISIBLE);
                holder.header_view.setTag(HAS_STICKY_VIEW);
            }
            else {
                holder.header_view.setVisibility(View.GONE);
                holder.header_view.setTag(NONE_STICKY_VIEW);
            }
        }
        return convertView;
    }

    public class MainListViewHolder {
        TextView header_view;
    }
}
