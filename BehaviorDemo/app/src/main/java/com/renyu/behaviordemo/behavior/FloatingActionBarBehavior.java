package com.renyu.behaviordemo.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by renyu on 2016/11/7.
 */

public class FloatingActionBarBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    int viewY=0;

    ObjectAnimator animator;

    public FloatingActionBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        if (viewY==0 && child.getVisibility()==View.VISIBLE) {
            viewY= (int) (coordinatorLayout.getMeasuredHeight()-child.getY());
        }
        return  (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed>0) {
            hide(child);
        }
        else if (dyConsumed<0) {
            show(child);
        }
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
