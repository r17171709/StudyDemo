package com.renyu.recyclerviewdemo.layoutmanager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    public final static int MAX_SHOW_COUNT = 4;
    public final static float SCALE_GAP = 0.05f;
    public final static int TRANS_Y_GAP = 45;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        for (int i = getItemCount() - MAX_SHOW_COUNT; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            measureChildWithMargins(view, 0, 0);
            addView(view);
            int start = (getWidth() - getDecoratedMeasuredWidth(view)) / 2;
            int top = (getHeight() - getDecoratedMeasuredHeight(view)) / 2;
            int end = start + getDecoratedMeasuredWidth(view);
            int bottom = top + getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, start, top, end, bottom);

            // 3 2 1 0
            int level = getItemCount() - i - 1;
            if (level > 0) {
                float scale = 1 - SCALE_GAP * level;
                float tran = TRANS_Y_GAP * level;
                view.setTranslationY(tran);
                view.setScaleX(scale);
                view.setScaleY(scale);
            } else {

            }
        }
    }
}
