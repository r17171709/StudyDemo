package com.rg.stickynavlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;

import com.rg.stickynavlayout.R;

/**
 * Created by RG on 2016/5/19.
 */
public class StickyNavLayout extends LinearLayout {

    Context context;

    //布局
    LinearLayout id_topview;
    LinearLayout id_indicatorview;
    ListView id_bottomview;

    //滚动相关属性
    int touchSlop;
    OverScroller scroller;
    VelocityTracker velocityTracker;
    int maxFling;
    int minFling;

    //顶部view的高度，此处用来作为临界点的值
    int mTopViewHeight;

    //ListView内容总高度
    int contentHeight=0;

    //ListView初始化高度
    int startListViewHeight=0;

    //悬浮view是否已经进入悬浮状态
    boolean isTopHidden=false;

    int lastY;

    //是否已经处理过临界点直接更改dispatchTouchEvent
    boolean isInControl=false;

    //是否通过其他事件进行滚动重置
    boolean isReset=false;

    public StickyNavLayout(Context context) {
        this(context, null);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        //设置排列方向
        setOrientation(VERTICAL);

        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
        maxFling=ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minFling=ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        scroller=new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        id_topview= (LinearLayout) findViewById(R.id.id_topview);
        id_indicatorview= (LinearLayout) findViewById(R.id.id_indicatorview);
        id_bottomview= (ListView) findViewById(R.id.id_bottomview);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //初始化ListView的高度
        startListViewHeight=getMeasuredHeight()-id_indicatorview.getMeasuredHeight()-id_topview.getMeasuredHeight();

        //listview在可以悬浮时的高度应该为 总高度-导航条高度
        LayoutParams params= (LayoutParams) id_bottomview.getLayoutParams();
        params.height=getMeasuredHeight()-id_indicatorview.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTopViewHeight=id_topview.getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y= (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                //由下往上滑动时候切换
                //获取可见范围内第一个view
                View viewItem=id_bottomview.getChildAt(id_bottomview.getFirstVisiblePosition());
                if (isTopHidden && (y-lastY)>0 && viewItem!=null && viewItem.getTop()==0 && !isInControl) {
                    isInControl=true;
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    MotionEvent event2=MotionEvent.obtain(ev);
                    dispatchTouchEvent(ev);
                    event2.setAction(MotionEvent.ACTION_DOWN);
                    return dispatchTouchEvent(event2);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y= (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(y-lastY)>touchSlop){
                    //获取可见范围内第一个view
                    View viewItem=id_bottomview.getChildAt(id_bottomview.getFirstVisiblePosition());
                    //viewGroup拦截条件：
                    //1. 顶部待隐藏view未隐藏
                    //2. 顶部待隐藏view已经隐藏，当前操作界面是listview的时候，往上滚到达临界点
                    if (!isTopHidden || (isTopHidden && (y-lastY)>0 && viewItem!=null && id_bottomview.getFirstVisiblePosition()==0 && viewItem.getTop()==0)) {
                        initVelocityTrackerIfNotExists();
                        velocityTracker.addMovement(ev);
                        lastY=y;
                        return true;
                    }
                    lastY=y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        velocityTracker.addMovement(event);
        int y= (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastY=y;
                return true;
            case MotionEvent.ACTION_MOVE:
                //内容高度不满足滑动条件时拒绝滑动
                if (contentHeight<startListViewHeight) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    dispatchTouchEvent(event);
                    return super.onTouchEvent(event);
                }
                scrollBy(0, -(y-lastY));
                //由上往下滑动时候切换
                if (getScrollY()==mTopViewHeight && (y-lastY)<0) {
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                    isInControl = false;
                }
                lastY=y;
                break;
            case MotionEvent.ACTION_CANCEL:
                recycleVelocityTracker();
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                //满足最小滑动条件，UP事件执行滑动操作
                velocityTracker.computeCurrentVelocity(1000, maxFling);
                int yVelocity= (int) velocityTracker.getYVelocity();
                if (Math.abs(yVelocity)>minFling) {
                    fling(yVelocity);
                }
                recycleVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void fling(int yVelocity) {
        scroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        //发生过滚动重置
        if (isReset) {
            super.scrollTo(x, y);
            isReset=false;
            isTopHidden=false;
            return;
        }
        //限定滚动在一定范围内
        if (y < 0) {
            y = 0;
        }
        //滚动距离不满足时候不能超过差值
        if (y>(contentHeight-startListViewHeight)) {
            y=contentHeight-startListViewHeight;
        }
        //滚动距离不能超过悬浮栏
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        //如果滚动距离与顶部view的高度一致，滚动距离达到最大，悬浮栏悬浮
        isTopHidden=getScrollY()==mTopViewHeight;
    }

    /**
     * 之前滚动的状态
     * @return  1:不满足滚动条件  2:滚动距离达不到topview的高度，悬浮栏达不到置顶条件  3.滚动距离足够，悬浮栏置顶
     */
    public int getState() {
        int state=-1;
        if (getScrollY()==0) {
            state=1;
        }
        if (getScrollY()<mTopViewHeight) {
            state=2;
        }
        if (getScrollY()>=mTopViewHeight) {
            state=3;
        }
        return state;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (velocityTracker==null) {
            velocityTracker=VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (velocityTracker!=null) {
            velocityTracker.recycle();
            velocityTracker=null;
        }
    }

    public void setContentHeight(int item) {
        this.contentHeight=item*dp2px(context, 50);
    }

    public void reset() {
        isReset=true;
        //之前滚动的状态
        int state=getState();
        //不满足滚动条件，则直接回到初始位置
        if (this.contentHeight<startListViewHeight) {
            scrollBy(0, -getScrollY());
            return;
        }
        //滚动距离不满足topview的高度，悬浮栏达不到置顶条件
        if (contentHeight-startListViewHeight<mTopViewHeight) {
            if (state==3) {
                //只有当前listview可显示高度超过内容高度，才进行调整
                if (startListViewHeight+getScrollY()>contentHeight) {
                    scrollTo(0, (contentHeight-startListViewHeight));
                }
            }
            else if (state==2) {
                //只有当前listview可显示高度超过内容高度，才进行调整
                if (startListViewHeight+getScrollY()>contentHeight) {
                    scrollBy(0, -(startListViewHeight+getScrollY()-contentHeight));
                }
            }
        }
        //如果新设置高度超过topview的高度，悬浮栏达到置顶条件，不做改动
        if (contentHeight-startListViewHeight>=mTopViewHeight) {

        }
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
