package com.renyu.coordinatorlaoyutdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renyu.coordinatorlaoyutdemo.R;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewListViewHolder> {
    @NonNull
    @Override
    public ViewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_videodetail, parent, false);
        return new ViewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewListViewHolder extends RecyclerView.ViewHolder {
        public ViewListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
