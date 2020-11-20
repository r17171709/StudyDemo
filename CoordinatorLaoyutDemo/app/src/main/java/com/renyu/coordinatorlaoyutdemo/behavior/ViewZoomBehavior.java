package com.renyu.coordinatorlaoyutdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.renyu.coordinatorlaoyutdemo.R;

public class ViewZoomBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private OverScroller overScroller;
    private ViewDragHelper viewDragHelper;

    private int scrolling_id;
    private View scrollingView;
    private View refChild;

    private int min_height;
    private int childOriginalHeight;

    private boolean canFullscreen;

    private FlingRunnable runnable;

    public ViewZoomBehavior() {
    }

    public ViewZoomBehavior(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.view_zoom_behavior, 0, 0);
        min_height = typedArray.getDimensionPixelOffset(R.styleable.view_zoom_behavior_min_height, 0);
        scrolling_id = typedArray.getResourceId(R.styleable.view_zoom_behavior_scrolling_id, 0);
        typedArray.recycle();
        overScroller = new OverScroller(context);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull ImageView child, int layoutDirection) {
        if (viewDragHelper == null) {
            viewDragHelper = ViewDragHelper.create(parent, 1.0f, callback);
            scrollingView = parent.findViewById(scrolling_id);
            refChild = child;
            childOriginalHeight = child.getMeasuredHeight();
            canFullscreen = true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (refChild.getBottom() <= childOriginalHeight && refChild.getBottom() >= min_height && canFullscreen) {
                return true;
            }
            return false;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return viewDragHelper.getTouchSlop();
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // dy>0 代表手指从屏幕上放往屏幕下方滑动
            // dy<0 代表手指从屏幕下方 往屏幕上方滑动
            if (dy == 0 || refChild == null) {
                return 0;
            }

            if (dy < 0 && refChild.getBottom() < min_height) {
                // 底部的最小值不能小于min_height
                return 0;
            } else if (dy > 0 && refChild.getBottom() > childOriginalHeight) {
                // 底部的最大值不能超过父容器的高度
                return 0;
            } else if (dy > 0 && scrollingView != null && scrollingView.canScrollVertically(-1)) {
                // 如果scrollingView还没有滑动到列表的最顶部，意味着列表还可以向下滑动，此时咱们应该让列表自行滑动，不做拦截
                return 0;
            }

            // 计算一下本次能够滑动的最大距离
            int maxConsumed = 0;
            if (dy > 0) {
                if (dy + refChild.getBottom() > childOriginalHeight) {
                    maxConsumed = childOriginalHeight - refChild.getBottom();
                } else {
                    maxConsumed = dy;
                }
            } else {
                if (dy + refChild.getBottom() < min_height) {
                    maxConsumed = min_height - refChild.getBottom();
                } else {
                    maxConsumed = dy;
                }
            }

            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) refChild.getLayoutParams();
            layoutParams.height = layoutParams.height + maxConsumed;
            refChild.setLayoutParams(layoutParams);
            return maxConsumed;
        }

        /**
         * 我们的手指 从屏幕上 离开的时候 会被调用
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (refChild.getBottom() > min_height && refChild.getBottom() < childOriginalHeight && yvel != 0) {
                if (runnable != null) {
                    refChild.removeCallbacks(runnable);
                    runnable = null;
                }
                runnable = new FlingRunnable();
                /**
                 * startX:开始的X值，由于我们不需要再水平方向滑动 所以为0
                 * startY:开始滑动时Y的起始值，那就是flingview的bottom值
                 * xvel:水平方向上的速度，实际上为0的
                 * yvel:垂直方向上的速度。即松手时的速度
                 * minX:水平方向上 滚动回弹的越界最小值，给0即可
                 * maxX:水平方向上 滚动回弹越界的最大值，实际上给0也是一样的
                 * minY：垂直方向上 滚动回弹的越界最小值，给0即可
                 * maxY:垂直方向上，滚动回弹越界的最大值，实际上给0 也一样
                 */
                overScroller.fling(0, refChild.getBottom(), (int) xvel, (int) yvel, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
                runnable.run();
            }
        }
    };

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull MotionEvent ev) {
        if (!canFullscreen || viewDragHelper == null) {
            return super.onTouchEvent(parent, child, ev);
        }
        viewDragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull ImageView child, @NonNull MotionEvent ev) {
        if (!canFullscreen || viewDragHelper == null) {
            return super.onInterceptTouchEvent(parent, child, ev);
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private class FlingRunnable implements Runnable {
        @Override
        public void run() {
            ViewGroup.LayoutParams layoutParams = refChild.getLayoutParams();
            int height = layoutParams.height;
            if (overScroller.computeScrollOffset() && height >= min_height && height <= childOriginalHeight) {
                int newHeight = Math.min(overScroller.getCurrY(), childOriginalHeight);
                if (newHeight != height) {
                    layoutParams.height = newHeight;
                    refChild.setLayoutParams(layoutParams);
                }
                // 这句等同于refChild.post(runnable);
                ViewCompat.postOnAnimation(refChild, this);
            } else {
                refChild.removeCallbacks(this);
            }
        }
    }
}
