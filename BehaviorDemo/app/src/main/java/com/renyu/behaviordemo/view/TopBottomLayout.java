package com.renyu.behaviordemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by renyu on 2016/11/10.
 */

public class TopBottomLayout extends FrameLayout implements NestedScrollingParent {

    int viewY=0;

    ObjectAnimator animator;

    public TopBottomLayout(Context context) {
        super(context);
    }

    public TopBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (viewY==0 && child.getVisibility()==View.VISIBLE) {
            viewY= (int) (getMeasuredHeight()-child.getY());
        }
        return  (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        FloatingActionButton child=null;
        for (int i=0;i<getChildCount();i++) {
            if (getChildAt(i) instanceof FloatingActionButton) {
                child= (FloatingActionButton) getChildAt(i);
            }
        }
        if (child==null) {
            return;
        }
        if (dyConsumed>0) {
            hide(child);
        }
        else if (dyConsumed<0) {
            show(child);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    private void show(FloatingActionButton child) {
        if (animator!=null) {
            animator.cancel();
            animator=null;
        }
        animator=ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, 0).setDuration(500);
        animator.start();
    }

    private void hide(FloatingActionButton child) {
        if (animator!=null) {
            animator.cancel();
            animator=null;
        }
        animator=ObjectAnimator.ofFloat(child, View.TRANSLATION_Y, viewY).setDuration(500);
        animator.start();
    }
}
