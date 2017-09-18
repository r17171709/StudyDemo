package com.example.customerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    ArrayList<String> strings;
    Context context;

    public MainAdapter(ArrayList<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {

        TextView tv_adapter_main;

        public MainHolder(View itemView) {
            super(itemView);
            tv_adapter_main=itemView.findViewById(R.id.tv_adapter_main);
        }
    }
}
