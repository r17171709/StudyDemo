package com.renyu.shoppingcartanimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/9/15.
 */
public class ActivityAdapter extends BaseAdapter {

    ArrayList<String> strs=null;
    Context context=null;

    public ActivityAdapter(Context context, ArrayList<String> strs) {
        this.context=context;
        this.strs=strs;
    }

    @Override
    public int getCount() {
        return strs.size();
    }

    @Override
    public Object getItem(int i) {
        return strs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdapterHolder holder=null;
        if(view==null) {
            view= LayoutInflater.from(context).inflate(R.layout.adapter_activity, null);
            holder=new AdapterHolder();
            holder.adapter_image=(ImageView) view.findViewById(R.id.adapter_image);
            view.setTag(holder);
        }
        else {
            holder= (AdapterHolder) view.getTag();
        }
        holder.adapter_image.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] locations=new int[2];
                view.getLocationInWindow(locations);
                ((ListViewActivity) context).createAnimateLayout();
                ((ListViewActivity) context).doAnimation(locations, view.getWidth(), view.getHeight());
            }
        });
        return view;
    }
}

class AdapterHolder {
    ImageView adapter_image=null;
}