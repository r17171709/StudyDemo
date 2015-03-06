package com.renyu.androidpulllayout.myview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.renyu.androidpulllayout.R;

/**
 * Created by ry on 2015/3/4.
 */
public class PullLayout extends ScrollView {

    ImageView headview_image=null;
    ImageView content_image=null;
    EyeView headview_eye=null;

    int scaledTouchSlop=0;
    int lastY=0;
    boolean mDragging=false;
    //滑动过程中手再次触摸，直接响应action_up
    boolean isActionCancel=false;
    //topview的高度，便于使用
    int topViewHeight=0;
    //下拉偏移量
    int deltaY=0;
    boolean isOpen=false;

    ObjectAnimator animator=null;

    public PullLayout(Context context) {
        super(context);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        scaledTouchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        headview_eye=(EyeView) findViewById(R.id.headview_eye);
        headview_eye.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headview_eye.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                headview_eye.setRadius(headview_eye.getMeasuredWidth());
            }
        });
        headview_image=(ImageView) findViewById(R.id.headview_image);
        headview_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("headview_image");
            }
        });
        content_image=(ImageView) findViewById(R.id.content_image);
        content_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("content_image");
            }
        });
        headview_image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headview_image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                scrollTo(0, headview_image.getHeight());
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        topViewHeight=headview_image.getMeasuredHeight();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_DOWN) {
            lastY= (int) ev.getY();
            isActionCancel=false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (animator!=null&&animator.isRunning()) {
            isActionCancel=true;
        }
        if (isActionCancel) {
            return true;
        }
        //如果遇到多手指触摸，立即人为将本次滑动事件替换成up事件
        if (ev.getActionIndex()!=0&&getScrollY()<topViewHeight) {
            ev.setAction(MotionEvent.ACTION_UP);
            isActionCancel=true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getY()-lastY)>scaledTouchSlop) {
                    mDragging=true;
                }
                if (mDragging) {
                    if (getScrollY()!=0) {
                        deltaY=0;
                        lastY= (int) ev.getY();
                    }
                    //滑动到顶部的时候
                    else {
                        //正在下拉
                        deltaY=(int) ev.getY()-lastY;
                        if (deltaY>0) {
                            System.out.println("123123123");
                            animatePull(deltaY/5);
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mDragging) {
                    mDragging=false;
                    //下拉松开之后将其复原；介于顶部与topview之间情况
                    if (getScrollY()<topViewHeight) {
                        if (deltaY!=0) {
                            reset();
                        }
                        else {
                            toggle();
                        }
                        //为了阻止惯性滑动，必须return true
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t>topViewHeight) {
            return;
        }
        else if (!mDragging&&t!=topViewHeight) {
            scrollTo(0, topViewHeight);
        }
        else {
            animateScroll(t);
        }
    }

    private void animateScroll(int t) {
        headview_eye.setRadius(headview_eye.getMeasuredWidth()*t/topViewHeight);
    }

    /**
     * 下拉过程，执行topview动画效果
     * @param t view放大到的高度
     */
    private void animatePull(int t) {
        scrollTo(0, -t);
        headview_image.getLayoutParams().height=t+topViewHeight;
        headview_image.requestLayout();
    }

    private void reset() {
        animator=ObjectAnimator.ofInt(this, "moveReset", deltaY/5, 0);
        animator.setDuration(150);
        animator.start();
        deltaY=0;
    }

    /**
     * 构建自定义属性动画参数
     * @param move
     */
    public void setMoveReset(int move) {
        animatePull(move);
    }

    private void toggle() {
        if (isOpen) {
            close();
        }
        else {
            open();
        }
    }

    private void open() {
        animator=ObjectAnimator.ofInt(this, "moveScroll", getScrollY(), (int) (getScrollY() / 2.2f), 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mDragging=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDragging=false;
                isOpen=true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(400);
        animator.start();
    }

    private void close() {
        animator=ObjectAnimator.ofInt(this, "moveScroll", getScrollY(), topViewHeight);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mDragging=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDragging=false;
                isOpen=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(250);
        animator.start();
    }

    public void setMoveScroll(int t) {
        scrollTo(0, t);
    }
}
