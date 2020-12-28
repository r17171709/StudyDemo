package com.renyu.recyclerviewdemo.layoutmanager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RepeatLayoutManager extends RecyclerView.LayoutManager {
    private int orientation = RecyclerView.HORIZONTAL;

    public RepeatLayoutManager(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return orientation == RecyclerView.HORIZONTAL;
    }

    @Override
    public boolean canScrollVertically() {
        return orientation == RecyclerView.VERTICAL;
    }

    /**
     * 定义初始布局
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        // 将所有Item分离至scrap
        detachAndScrapAttachedViews(recycler);
        layoutChunk(recycler);
    }

    private void layoutChunk(RecyclerView.Recycler recycler) {
        if (orientation == RecyclerView.HORIZONTAL) {
            int start = getPaddingStart();
            for (int i = 0; ; i++) {
                if (start > getWidth() - getPaddingEnd()) {
                    break;
                }
                // 从缓存中获取子View
                View view = recycler.getViewForPosition(i);
                addView(view);
                // 测量子View，并将子View的margin也考虑进来。通常使用此函数
                measureChildWithMargins(view, 0, 0);
                int top = getPaddingTop();
                int end = start + getDecoratedMeasuredWidth(view);
                int bottom = top + getDecoratedMeasuredHeight(view);
                layoutDecorated(view, start, top, end, bottom);
                start = end;
            }
        }
        if (orientation == RecyclerView.VERTICAL) {
            int top = getPaddingTop();
            for (int i = 0; ; i++) {
                if (top > getHeight() - getPaddingBottom()) {
                    break;
                }
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int start = getPaddingLeft();
                int end = start + getDecoratedMeasuredWidth(view);
                int bottom = top + getDecoratedMeasuredHeight(view);
                layoutDecorated(view, start, top, end, bottom);
                top = bottom;
            }
        }
    }

    /**
     * 横向滚动
     *
     * @param dx       同ViewGroup的scrollBy的方向
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 测量 布局
        fillHorizontal(recycler, dx > 0);
        offsetChildrenHorizontal(-dx);
        // 回收
        releaseChildView(recycler, dx > 0);
        return dx;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fillVertical(recycler, dy > 0);
        offsetChildrenVertical(-dy);
        releaseChildView(recycler, dy > 0);
        return dy;
    }

    private void fillHorizontal(RecyclerView.Recycler recycler, boolean isFillEnd) {
        if (getChildCount() <= 0) {
            return;
        }
        if (isFillEnd) {
            // 填充尾部
            // getChildAt获取当前可见范围内的View。此可见范围，是由是否发生回收来决定
            View anchorView = getChildAt(getChildCount() - 1);
            // getPosition方法可以返回传入的View在整个ItemCount中的位置
            int anchorPosition = getPosition(anchorView);
            for (; anchorView.getRight() < getWidth() - getPaddingEnd(); ) {
                // 滚动到最后面之后，循环添加前面的
                int position = (anchorPosition + 1) % getItemCount();
                View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int start = anchorView.getRight();
                int top = getPaddingTop();
                // 获取包含Decorate在内的View的宽度
                int end = start + getDecoratedMeasuredWidth(view);
                // 获取包含Decorate在内的View的高度
                int bottom = top + getDecoratedMeasuredHeight(view);
                // 重新布局
                layoutDecorated(view, start, top, end, bottom);
                anchorView = view;
            }
        } else {
            // 填充头部
            View anchorView = getChildAt(0);
            int anchorPosition = getPosition(anchorView);
            for (; anchorView.getLeft() > getPaddingStart(); ) {
                int position = anchorPosition - 1;
                if (position < 0) {
                    // 滚动到最前面之后，循环添加
                    position = getItemCount() + position;
                }
                View view = recycler.getViewForPosition(position);
                addView(view, 0);
                measureChildWithMargins(view, 0, 0);
                int end = anchorView.getLeft();
                int start = end - getDecoratedMeasuredWidth(view);
                int top = getPaddingTop();
                int bottom = top + getDecoratedMeasuredHeight(view);
                layoutDecorated(view, start, top, end, bottom);
                anchorView = view;
            }
        }
    }

    private void fillVertical(RecyclerView.Recycler recycler, boolean isFillEnd) {
        if (isFillEnd) {
            View anchorView = getChildAt(getChildCount() - 1);
            int anchorPosition = getPosition(anchorView);
            for (; anchorView.getBottom() < getHeight() - getPaddingBottom(); ) {
                int position = (anchorPosition + 1) % getItemCount();
                View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int start = getPaddingLeft();
                int end = start + getDecoratedMeasuredWidth(view);
                int top = anchorView.getBottom();
                int bottom = top + getDecoratedMeasuredHeight(view);
                layoutDecorated(view, start, top, end, bottom);
                anchorView = view;
            }
        } else {
            View anchorView = getChildAt(0);
            int anchorPosition = getPosition(anchorView);
            for (; anchorView.getTop() > getPaddingTop(); ) {
                int position = anchorPosition - 1;
                if (position < 0) {
                    position = position + getItemCount();
                }
                View view = recycler.getViewForPosition(position);
                addView(view, 0);
                measureChildWithMargins(view, 0, 0);
                int start = getPaddingLeft();
                int end = start + getDecoratedMeasuredWidth(view);
                int bottom = anchorView.getTop();
                int top = bottom - getDecoratedMeasuredHeight(view);
                layoutDecorated(view, start, top, end, bottom);
                anchorView = view;
            }
        }
    }

    private void releaseChildView(RecyclerView.Recycler recycler, boolean isFillEnd) {
        if (isFillEnd) {
            // 回收头部
            for (int i = 0; ; i++) {
                View view = getChildAt(i);
                if (view == null) {
                    return;
                }
                boolean needRecycler = orientation == RecyclerView.HORIZONTAL ? view.getRight() < getPaddingStart() : view.getBottom() < getPaddingTop();
                // 不在可见范围内的直接回收。此可见范围，是由是否发生回收来决定。getChildCount的数量会随着addView的添加而增多，如果没有发生回收即调用removeAndRecycleView，getChildCount不会减少
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        } else {
            // 回收尾部
            for (int i = getChildCount() - 1; ; i++) {
                View view = getChildAt(i);
                if (view == null) {
                    return;
                }
                boolean needRecycler = orientation == RecyclerView.HORIZONTAL ? view.getLeft() >= getWidth() - getPaddingEnd() : view.getTop() > getHeight() - getPaddingBottom();
                if (needRecycler) {
                    // 将指定的View直接回收加至recyclerPool
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        }
    }
}
