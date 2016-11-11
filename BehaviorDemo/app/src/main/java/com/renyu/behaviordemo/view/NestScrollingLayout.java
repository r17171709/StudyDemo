package com.renyu.behaviordemo.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by renyu on 2016/11/10.
 */

public class NestScrollingLayout extends FrameLayout implements NestedScrollingParent {

    public NestScrollingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestScrollingLayout(Context context) {
        this(context, null);
    }
    
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }
    
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // 剩余没有消费的
        Log.d("NestScrollingLayout", "----父布局onNestedScroll----------------");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // 这里要消费的
        getChildAt(1).offsetTopAndBottom(-dy);

        consumed[1] = dy;
        Log.d("NestScrollingLayout", "----父布局onNestedPreScroll----------------");
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d("NestScrollingLayout", "----父布局onNestedFling----------------");
        return true;
    }
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)  {
        Log.d("NestScrollingLayout", "----父布局onNestedPreFling----------------");
        return true;
    }
}
