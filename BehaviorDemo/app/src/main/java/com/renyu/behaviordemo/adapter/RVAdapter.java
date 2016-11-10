package com.renyu.behaviordemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renyu.behaviordemo.R;

import java.util.ArrayList;

/**
 * Created by renyu on 2016/11/8.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    Context context;
    ArrayList<String> models;

    public RVAdapter(Context context, ArrayList<String> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_rv, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        holder.adapter_text.setText(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder {

        TextView adapter_text;

        public RVViewHolder(View itemView) {
            super(itemView);

            adapter_text= (TextView) itemView.findViewById(R.id.adapter_text);
        }
    }
}
