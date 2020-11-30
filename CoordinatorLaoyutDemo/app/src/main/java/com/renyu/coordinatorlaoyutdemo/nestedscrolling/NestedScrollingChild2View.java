package com.renyu.coordinatorlaoyutdemo.nestedscrolling;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

import static androidx.core.view.ViewCompat.TYPE_TOUCH;

public class NestedScrollingChild2View extends LinearLayout implements NestedScrollingChild2 {
    private NestedScrollingChildHelper helper = new NestedScrollingChildHelper(this);

    private int mMinFlingVelocity = 0;
    private int mMaxFlingVelocity = 0;
    private VelocityTracker tracker;

    private Scroller scroller;

    private int lastX;
    private int lastY;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    // 是否执行惯性滚动
    private boolean fling;
    // 上一次滚动的数据
    private int mLastFlingX;
    private int mLastFlingY;
    private int[] mScrollConsumed = new int[2];

    public NestedScrollingChild2View(Context context) {
        this(context, null);
    }

    public NestedScrollingChild2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingChild2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setNestedScrollingEnabled(true);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

        scroller = new Scroller(context);
    }

    /**
     * 开始滑动前调用，在惯性滑动和触摸滑动前都会进行调用，此方法一般在 onInterceptTouchEvent或者onTouch中，通知父类方法开始滑动
     * 会调用父类方法的 onStartNestedScroll onNestedScrollAccepted 两个方法
     *
     * @param axes 滑动方向
     * @param type 开始滑动的类型
     * @return 有父视图并且开始滑动，则返回true 实际上就是看parent的 onStartNestedScroll 方法
     */
    @Override
    public boolean startNestedScroll(int axes, int type) {
        return helper.startNestedScroll(axes, type);
    }

    /**
     * 子控件在开始滑动前，通知父控件开始滑动，同时由父控件先消耗滑动时间
     * 在子View的onInterceptTouchEvent或者onTouch中，调用该方法通知父View滑动的距离
     * 最终会调用父view的 onNestedPreScroll 方法
     *
     * @param dx             水平方向嵌套滑动的子控件想要变化的距离 dx<0 向右滑动 dx>0 向左滑动 （保持和 RecycleView 一致）
     * @param dy             垂直方向嵌套滑动的子控件想要变化的距离 dy<0 向下滑动 dy>0 向上滑动 （保持和 RecycleView 一致）
     * @param consumed       父控件消耗的距离，父控件消耗完成之后，剩余的才会给子控件，子控件需要使用consumed来进行实际滑动距离的处理
     * @param offsetInWindow 子控件在当前window的偏移量
     * @param type           滑动类型，ViewCompat.TYPE_NON_TOUCH fling效果,ViewCompat.TYPE_TOUCH 手势滑动
     * @return 表示父控件进行了滑动消耗，需要处理 consumed 的值，false表示父控件不对滑动距离进行消耗，可以不考虑consumed数据的处理，此时consumed中两个数据都应该为0
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    /**
     * 在dispatchNestedPreScroll 之后进行调用
     * 当滑动的距离父控件消耗后，父控件将剩余的距离再次交个子控件，
     * 子控件再次消耗部分距离后，又继续将剩余的距离分发给父控件,由父控件判断是否消耗剩下的距离。
     * 如果四个消耗的距离都是0，则表示没有可以消耗的了，会直接返回false，否则会调用父控件的 onNestedScroll 方法，父控件继续消耗剩余的距离
     *
     * @param dxConsumed     水平方向嵌套滑动的子控件滑动的距离(消耗的距离)    dx<0 向右滑动 dx>0 向左滑动 （保持和 RecycleView 一致）
     * @param dyConsumed     垂直方向嵌套滑动的子控件滑动的距离(消耗的距离)    dy<0 向下滑动 dy>0 向上滑动 （保持和 RecycleView 一致）
     * @param dxUnconsumed   水平方向嵌套滑动的子控件未滑动的距离(未消耗的距离)dx<0 向右滑动 dx>0 向左滑动 （保持和 RecycleView 一致）
     * @param dyUnconsumed   垂直方向嵌套滑动的子控件未滑动的距离(未消耗的距离)dy<0 向下滑动 dy>0 向上滑动 （保持和 RecycleView 一致）
     * @param offsetInWindow 子控件在当前window的偏移量
     * @param type
     * @return 如果返回true, 表示父控件又继续消耗了
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    /**
     * 子控件停止滑动，例如手指抬起，惯性滑动结束
     *
     * @param type 停止滑动的类型 TYPE_TOUCH，TYPE_NON_TOUCH
     */
    @Override
    public void stopNestedScroll(int type) {
        helper.stopNestedScroll(type);
    }

    /**
     * 设置当前子控件是否支持嵌套滑动，如果不支持，那么父控件是不能够响应嵌套滑动的
     *
     * @param enabled
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
    }

    /**
     * 当前子控件是否支持嵌套滑动
     *
     * @return
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return helper.isNestedScrollingEnabled();
    }

    /**
     * 判断当前子控件是否拥有嵌套滑动的父控件
     *
     * @param type
     * @return
     */
    @Override
    public boolean hasNestedScrollingParent(int type) {
        return helper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 停止惯性滚动
        fling = false;
        mLastFlingX = 0;
        mLastFlingY = 0;

        if (lastX == -1 || lastY == -1) {
            lastY = (int) event.getRawY();
            lastX = (int) event.getRawX();
        }

        if (tracker == null) {
            tracker = VelocityTracker.obtain();
        }
        tracker.addMovement(event);

        // 此处需要注意传递的是RawY与RawX，同时NestedScroll的type类型不要传错
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                lastX = (int) event.getRawX();
                // 此方法确定开始滑动的方向和类型
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_MOVE:
                int currentY = (int) (event.getRawY());
                int currentX = (int) (event.getRawX());
                int dy = lastY - currentY;
                int dx = lastX - currentX;
                // 滑动开始前通知父控件，确认是否需要消耗一部分滑动
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, TYPE_TOUCH)) {
                    // 如果父控件需要消耗，则处理父控件消耗的部分数据
                    dy -= consumed[1];
                    dx -= consumed[0];
                }
                // 剩余部分自己去消耗
                // 此处由于不需要自身移动，所以自己消耗的部分其实都是0
                int consumedX = 0;
                int consumedY = 0;
                // 将剩余的部分再次还给父控件
                dispatchNestedScroll(consumedX, consumedY, dx - consumedX, dy - consumedY, null, ViewCompat.TYPE_TOUCH);
                lastY = currentY;
                lastX = currentX;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 当手指抬起时，结束嵌套滑动传递，并判断是否产生了fling效果
                stopNestedScroll(TYPE_TOUCH);

                tracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                int xVelocity = (int) tracker.getXVelocity();
                int yVelocity = (int) tracker.getYVelocity();
                // 判断是否需要惯性滑动
                fling(xVelocity, yVelocity);
                if (tracker != null) {
                    tracker.clear();
                }
                lastY = -1;
                lastX = -1;
                break;
        }
        return true;
    }

    private void fling(int xVelocity, int yVelocity) {
        if (Math.abs(xVelocity) < mMinFlingVelocity) {
            xVelocity = 0;
        }
        if (Math.abs(yVelocity) < mMinFlingVelocity) {
            yVelocity = 0;
        }
        if (xVelocity == 0 && yVelocity == 0) {
            return;
        }
        // 通知父控件开始惯性滚动
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH);
        xVelocity = Math.max(-mMaxFlingVelocity, Math.min(xVelocity, mMaxFlingVelocity));
        yVelocity = Math.max(-mMaxFlingVelocity, Math.min(yVelocity, mMaxFlingVelocity));

        fling = true;
        scroller.fling(0, 0, xVelocity, yVelocity, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset() && fling) {
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            int dx = mLastFlingX - x;
            int dy = mLastFlingY - y;
            mLastFlingX = x;
            mLastFlingY = y;
            //在子控件处理fling之前，先判断父控件是否消耗
            if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, null, ViewCompat.TYPE_NON_TOUCH)) {
                //计算父控件消耗后，剩下的距离
                dx -= mScrollConsumed[0];
                dy -= mScrollConsumed[1];
            }
            //因为之前默认向父控件传递的竖直方向，所以这里子控件也消耗剩下的竖直方向
            int hResult = 0;
            int vResult = 0;
            // 子控件水平fling 消耗的距离
            int leaveDx = 0;
            // 父控件竖直fling 消耗的距离
            int leaveDy = 0;

            //在父控件消耗完之后，子控件开始消耗
            if (dx != 0) {
                leaveDx = 0;
                // 得到子控件消耗后剩下的水平距离
                hResult = dx - leaveDx;
            }
            if (dy != 0) {
                leaveDy = 0;
                // 得到子控件消耗后剩下的竖直距离
                vResult = dy - leaveDy;
            }
            //将最后剩余的部分，再次还给父控件
            dispatchNestedScroll(leaveDx, leaveDy, hResult, vResult, null, ViewCompat.TYPE_NON_TOUCH);
            postInvalidate();
        } else {
            stopNestedScroll(ViewCompat.TYPE_NON_TOUCH);
            fling = false;
            mLastFlingX = 0;
            mLastFlingY = 0;
        }
    }
}
