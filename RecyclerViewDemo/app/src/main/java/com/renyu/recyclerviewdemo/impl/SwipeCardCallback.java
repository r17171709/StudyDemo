package com.renyu.recyclerviewdemo.impl;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.renyu.recyclerviewdemo.adapter.LayoutManagerAdapter;
import com.renyu.recyclerviewdemo.layoutmanager.SwipeCardLayoutManager;

import java.util.ArrayList;

public class SwipeCardCallback extends ItemTouchHelper.SimpleCallback {
    private RecyclerView.Adapter<LayoutManagerAdapter.LayoutManagerViewHolder> adapter;
    private ArrayList<String> beans;

    public SwipeCardCallback(RecyclerView.Adapter<LayoutManagerAdapter.LayoutManagerViewHolder> adapter, ArrayList<String> beans) {
        super(0, ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END);
        this.adapter = adapter;
        this.beans = beans;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        String tmp = beans.remove(viewHolder.getLayoutPosition());
        beans.add(0, tmp);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        int maxDistance = recyclerView.getWidth() / 2;
        double fraction = Math.sqrt(dX * dX + dY * dY) / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View view = recyclerView.getChildAt(i);
            // 3 2 1 0
            int level = recyclerView.getChildCount() - i - 1;
            if (level > 0 && level < SwipeCardLayoutManager.MAX_SHOW_COUNT) {
                float scale = (float) (1 - SwipeCardLayoutManager.SCALE_GAP * level + fraction * SwipeCardLayoutManager.SCALE_GAP);
                float tran = (float) (SwipeCardLayoutManager.TRANS_Y_GAP * level - fraction * SwipeCardLayoutManager.TRANS_Y_GAP);
                view.setTranslationY(tran);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }
        }
    }

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return 1000;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.2f;
    }
}
