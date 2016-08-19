package com.renyu.ninegridview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renyu.ninegridview.R;
import com.renyu.ninegridview.model.NineGridImageModel;
import com.renyu.ninegridview.myview.NineGridLayout;

import java.util.ArrayList;

/**
 * Created by Clevo on 2016/8/16.
 */
public class RVAdapter extends RecyclerView.Adapter {

    ArrayList<ArrayList<NineGridImageModel>> models;
    Context context;

    public RVAdapter(ArrayList<ArrayList<NineGridImageModel>> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.adapter_more, parent, false);
        return new RVMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((RVMoreViewHolder) holder).adapter_ninelayout.setImageData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RVMoreViewHolder extends RecyclerView.ViewHolder {

        NineGridLayout adapter_ninelayout;

        public RVMoreViewHolder(View itemView) {
            super(itemView);

            adapter_ninelayout= (NineGridLayout) itemView.findViewById(R.id.adapter_ninelayout);
        }
    }
}
