package com.renyu.behaviordemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by renyu on 2016/12/11.
 */

public class PullToRefreshView extends LinearLayout implements NestedScrollingParent {

    Context context;

    boolean canScrollExtra=false;

    //每次移动的偏移量
    int deltaY=0;
    //是否屏蔽下拉事件
    boolean isIntercept=false;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context=context;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed<0) {
            int max=dp2Px(context, 200);
            int min=1;
            scrollDown(this, dyUnconsumed, min, max);
        }
        else if (dyUnconsumed>0) {
            int min=-dp2Px(context, 200);
            int max=0;
            scrollUp(this, dyUnconsumed, min, max);
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (getChildAt(1).getTop()>1) {
            int max=dp2Px(context, 200);
            int min=1;
            consumed[1]=scrollDown(this, dy, min, max);
        }
        else if (getChildAt(1).getTop()<-1) {
            int min=-dp2Px(context, 200);
            int max=0;
            consumed[1]=scrollUp(this, dy, min, max);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        if (canScrollExtra) {
            ValueAnimator valueAnimator=null;
            if (getChildAt(1).getTop()>0) {
                if (Math.abs(getChildAt(1).getTop())==1) {
                    return;
                }
                //下拉未到临界点
                if (getChildAt(1).getTop()<dp2Px(context, 200)/2 && getChildAt(1).getTop()>0) {
                    valueAnimator=ValueAnimator.ofInt(0, -dp2Px(context, 200)-getChildAt(0).getTop());
                }
                //下拉已过临界点，模拟刷新过程
                else if (getChildAt(1).getTop()>dp2Px(context, 200)/2) {
                    valueAnimator=ValueAnimator.ofInt(0, -dp2Px(context, 200)/2-getChildAt(0).getTop());
                    isIntercept=true;
                }
            }
            else if (getChildAt(1).getTop()<0) {
                if (Math.abs(getChildAt(1).getTop())==-1) {
                    return;
                }
                //上拉未到临界点
                if (getChildAt(1).getTop()>-dp2Px(context, 200)/2 && getChildAt(1).getTop()<0) {
                    valueAnimator=ValueAnimator.ofInt(0, -getChildAt(1).getTop());
                }
                //上拉已过临界点，模拟刷新过程
                else if (getChildAt(1).getTop()<-dp2Px(context, 200)/2) {
                    valueAnimator=ValueAnimator.ofInt(0, -dp2Px(context, 200)/2-getChildAt(1).getTop());
                    isIntercept=true;
                }
            }
            if (valueAnimator==null) {
                return;
            }
            valueAnimator.setDuration(400);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (getChildAt(1).getTop()>0) {
                        getChildAt(0).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                        getChildAt(1).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                        deltaY=Integer.parseInt(animation.getAnimatedValue().toString());
                    }
                    else if (getChildAt(1).getTop()<0) {
                        getChildAt(1).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                        getChildAt(2).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                        deltaY=Integer.parseInt(animation.getAnimatedValue().toString());
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    deltaY=0;
                    if (isIntercept) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (getChildAt(1).getTop()>0) {
                                    resetRefreshDown();
                                }
                                else if (getChildAt(1).getTop()<0) {
                                    resetRefreshUp();
                                }
                            }
                        }, 2000);
                    }
                }
            });
            valueAnimator.start();
        }

        canScrollExtra=false;
    }

    private void resetRefreshDown() {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0, -dp2Px(context, 200)-getChildAt(0).getTop());
        valueAnimator.setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getChildAt(0).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                getChildAt(1).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                deltaY=Integer.parseInt(animation.getAnimatedValue().toString());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                deltaY=0;
                isIntercept=false;
            }
        });
        valueAnimator.start();
    }

    private void resetRefreshUp() {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0, -getChildAt(1).getTop());
        valueAnimator.setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getChildAt(1).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                getChildAt(2).offsetTopAndBottom((Integer.parseInt(animation.getAnimatedValue().toString())-deltaY));
                deltaY=Integer.parseInt(animation.getAnimatedValue().toString());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                deltaY=0;
                isIntercept=false;
            }
        });
        valueAnimator.start();
    }

    private int scrollDown(LinearLayout childView, int y, int min, int max) {
        canScrollExtra=true;
        if (childView.getChildAt(1).getTop()-y>max) {
            childView.getChildAt(0).offsetTopAndBottom(max-childView.getChildAt(1).getTop());
            childView.getChildAt(1).offsetTopAndBottom(max-childView.getChildAt(1).getTop());
        }
        else if (childView.getChildAt(1).getTop()-y<min) {
            childView.getChildAt(0).offsetTopAndBottom(min-childView.getChildAt(1).getTop());
            childView.getChildAt(1).offsetTopAndBottom(min-childView.getChildAt(1).getTop());
        }
        else {
            childView.getChildAt(0).offsetTopAndBottom(-y);
            childView.getChildAt(1).offsetTopAndBottom(-y);
        }
        return y;
    }

    private int scrollUp(LinearLayout childView, int y, int min, int max) {
        canScrollExtra=true;
        if (childView.getChildAt(1).getTop()-y>max) {
            childView.getChildAt(2).offsetTopAndBottom(max-childView.getChildAt(1).getTop());
            childView.getChildAt(1).offsetTopAndBottom(max-childView.getChildAt(1).getTop());
        }
        else if (childView.getChildAt(1).getTop()-y<min) {
            childView.getChildAt(2).offsetTopAndBottom(min-childView.getChildAt(1).getTop());
            childView.getChildAt(1).offsetTopAndBottom(min-childView.getChildAt(1).getTop());
        }
        else {
            childView.getChildAt(2).offsetTopAndBottom(-y);
            childView.getChildAt(1).offsetTopAndBottom(-y);
        }
        return y;
    }

    private int dp2Px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept;
    }
}
