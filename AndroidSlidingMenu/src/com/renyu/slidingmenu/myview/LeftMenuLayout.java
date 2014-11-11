package com.renyu.slidingmenu.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2014/10/14.
 */
public class LeftMenuLayout extends ViewGroup {

    Scroller scroller=null;

    int startX=0;
    int startY=0;
    //最小滑动距离
    int scaledEdgeSlop=0;
    int tempX=0;
    //菜单视图宽度
    int menuWidth=0;

    public LeftMenuLayout(Context context) {
        super(context);
        init(context);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        scroller=new Scroller(context);
        scaledEdgeSlop=ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int totalWidth=0;
        for (int num=0;num<getChildCount();num++) {
            View view=getChildAt(num);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            totalWidth+=view.getMeasuredWidth();
            if (num==0) {
                menuWidth=view.getMeasuredWidth();
            }
        }
        setMeasuredDimension(totalWidth, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        int startX=0;
        for (int num=0;num<getChildCount();num++) {
            View view=getChildAt(num);
            view.layout(startX, 0, startX+view.getMeasuredWidth(), view.getMeasuredHeight());
            startX+=view.getMeasuredWidth();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println(ev.getAction());
        tempX= (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getX();
                startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX= (int) ev.getX();
                int moveY= (int) ev.getY();
                if (Math.abs(startX-moveX)>scaledEdgeSlop&&Math.abs(startY-moveY)<scaledEdgeSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX= (int) (event.getX()-tempX);
                tempX= (int) event.getX();
                //System.out.println(getScrollX()-deltaX);
                if(getScrollX()-deltaX>=menuWidth) {
                    scrollTo(menuWidth, 0);
                }
                else if (getScrollX()-deltaX<=0) {
                    scrollTo(0, 0);
                }
                else {
                    scrollBy(-deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX()>menuWidth/2) {
                    scroller.startScroll(getScrollX(), 0, menuWidth-getScrollX(), 0, 200);
                }
                else {
                    scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 200);
                }
                postInvalidate();
                break;
        }
        return true;
    }
}
