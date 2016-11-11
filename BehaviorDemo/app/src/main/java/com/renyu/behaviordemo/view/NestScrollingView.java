package com.renyu.behaviordemo.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by renyu on 2016/11/10.
 */

public class NestScrollingView extends View implements NestedScrollingChild {

    private static final String TAG = "NestScrollingView";

    private NestedScrollingChildHelper mChildHelper;

    private int[] mConsumed = new int[2];

    private int[] mOffset = new int[2];

    public NestScrollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestScrollingView(Context context) {
        this(context, null);
        init();
    }

    private void init() {
        mChildHelper = new NestedScrollingChildHelper(this);
        mChildHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.d(TAG, "-----------startNestedScroll 子View开始滚动---------------");
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.d(TAG, "-----------dispatchNestedScroll 子View把剩余的滚动距离传给父布局---------------");
        return mChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed, dxUnconsumed,dyUnconsumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.d(TAG, "-----------dispatchNestedPreScroll 子View把总的滚动距离传给父布局---------------");
        return mChildHelper.dispatchNestedPreScroll(dx,dy, consumed,offsetInWindow);
    }

    int startY=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                startY= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int extraY=startY-(int) event.getY();
                dispatchNestedPreScroll(0,extraY,mConsumed,mOffset);
                startY= (int) event.getY();

                //NestScrollingLayout移动之后的偏移量
                Log.d(TAG, "offset--x:" + mOffset[0] + ",offset--y:" + mOffset[1]);
                dispatchNestedScroll(50,50,50,50,mOffset);

                break;
            case MotionEvent.ACTION_UP:
                mChildHelper.stopNestedScroll();
                break;
            default:
                break;
        }
        return true;
    }

}
