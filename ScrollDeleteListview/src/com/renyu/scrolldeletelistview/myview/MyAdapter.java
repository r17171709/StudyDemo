package com.renyu.scrolldeletelistview.myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.renyu.scrolldeletelistview.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/9/18.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<String> lists=null;
    Context context=null;

    public MyAdapter(Context context, ArrayList<String> lists) {
        this.context=context;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position=i;
        MyHolder holder=null;
        if(view==null) {
            view= LayoutInflater.from(context).inflate(R.layout.adapter_deleteitem, null);
            holder=new MyHolder();
            holder.deleteitem_text=(TextView) view.findViewById(R.id.deleteitem_text);
            holder.deleteitem_layout=(DeleteItem) view.findViewById(R.id.deleteitem_layout);
            holder.clock=(Button) view.findViewById(R.id.clock);
            holder.delete=(Button) view.findViewById(R.id.delete);
            view.setTag(holder);
        }
        else {
            holder=(MyHolder) view.getTag();
        }
        holder.deleteitem_text.setText(lists.get(i));
        holder.clock.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click_clock" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click_delete" + position, Toast.LENGTH_SHORT).show();
            }
        });
        if (ParamsManager.openItem.containsKey(""+i)) {
            holder.deleteitem_layout.scrollState();
        }
        else {
            holder.deleteitem_layout.resetState();
        }
        return view;
    }
}

class MyHolder {
    TextView deleteitem_text=null;
    DeleteItem deleteitem_layout=null;
    Button clock=null;
    Button delete=null;
}