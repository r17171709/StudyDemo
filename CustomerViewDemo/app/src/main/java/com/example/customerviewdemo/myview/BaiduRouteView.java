package com.example.customerviewdemo.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;

import com.example.customerviewdemo.R;

/**
 * Created by Administrator on 2017/6/27.
 */

public class BaiduRouteView extends ViewGroup {

    // 是否在滑动范围内
    boolean isSlideTouch=false;
    // 最后一次滑动的坐标
    int lastY=0;
    int lastX=0;
    // 最大拖动距离
    int maxDistance=0;
    // 最小拖动距离
    int minDistance=0;
    // 最后一次显示的位置
    int lastShowPos=0;
    // 停止状态
    STATE state=STATE.CLOSE;
    enum STATE {
        OPEN, CLOSE
    }
    // 用以进行滑动判断的RecyclerView
    RecyclerView innerRv;
    // 突出部分的高度
    int outer_height=0;

    VelocityTracker velocityTracker;

    boolean isOnce=false;

    public BaiduRouteView(Context context) {
        this(context, null);
    }

    public BaiduRouteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.BaiduRouteViewStyle);
        outer_height=typedArray.getDimensionPixelSize(R.styleable.BaiduRouteViewStyle_outer_height, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i=0;i<getChildCount();i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        for (int j=0;j<getChildCount();j++) {
            if (j==0) {
                getChildAt(j).layout(0, 0, getMeasuredWidth(), getMeasuredHeight()-outer_height);
            }
            if (j==1) {
                int startY=getMeasuredHeight()-outer_height;
                if (!isOnce) {
                    isOnce=true;
                    lastShowPos=startY;
                }
                getChildAt(j).layout(0, startY, getMeasuredWidth(), startY+getChildAt(j).getMeasuredHeight());
                minDistance=getMeasuredHeight()-getChildAt(j).getMeasuredHeight();
                maxDistance=startY;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (velocityTracker==null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);
        int x= (int) ev.getX();
        int y= (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (checkTouch(x, y)) {
                    isSlideTouch=true;
                    lastY=y;
                    lastX=x;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果菜单被打开，则要额外的判断
                if (state==STATE.OPEN) {
                    // 没有滑动到顶部就释放拦截
                    if (innerRv!=null && !innerRv.canScrollVertically(-1) && lastY<y) {
                        isSlideTouch=true;
                    }
                    else {
                        isSlideTouch=false;
                        return super.onInterceptTouchEvent(ev);
                    }
                }
                if (state==STATE.CLOSE) {
                    velocityTracker.computeCurrentVelocity(1000);
                    int vx= (int) velocityTracker.getXVelocity();
                    int vy= (int) velocityTracker.getYVelocity();
                    // 横向滑动时间拦截
                    if (Math.abs(vx)>Math.abs(vy)) {
                        isSlideTouch=false;
                        return super.onInterceptTouchEvent(ev);
                    }
                    else {
                        if (checkTouch(x, y)) {
                            isSlideTouch = true;
                        }
                    }
                }
                if (isSlideTouch) {
                    lastY=y;
                    lastX=x;
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isSlideTouch=false;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                lastShowPos-=(lastY-y);
                if (lastShowPos<=minDistance) {
                    lastShowPos=minDistance;
                }
                if (lastShowPos>=maxDistance) {
                    lastShowPos=maxDistance;
                }
                getChildAt(1).setY(lastShowPos);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isSlideTouch=false;
                // 倾向于关闭
                if (lastShowPos>(maxDistance-minDistance)/2+minDistance) {
                    animScrll(maxDistance);
                }
                // 倾向于打开
                else {
                    animScrll(minDistance);
                }
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        lastY=y;
        lastX=x;
        return super.onTouchEvent(event);
    }

    private boolean checkTouch(int x, int y) {
        Rect rect=new Rect();
        getChildAt(1).getLocalVisibleRect(rect);
        int left = rect.left;
        int top = lastShowPos;
        int right = rect.right;
        int bottom = lastShowPos+rect.bottom;
        if (x>=left && x<=right && y>=top && y<bottom) {
            return true;
        }
        else {
            return false;
        }
    }

    private void animScrll(int end) {
        getChildAt(1).clearAnimation();
        ValueAnimator valueAnimator=ValueAnimator.ofInt(lastShowPos, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lastShowPos=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                getChildAt(1).setY(lastShowPos);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (lastShowPos==minDistance) {
                    state=STATE.OPEN;
                }
                if (lastShowPos>=maxDistance) {
                    state=STATE.CLOSE;
                }
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    public void setInnerRv(RecyclerView innerRv) {
        this.innerRv = innerRv;
    }
}
