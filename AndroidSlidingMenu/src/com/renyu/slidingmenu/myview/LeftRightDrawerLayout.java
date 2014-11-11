package com.renyu.slidingmenu.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.nineoldandroids.view.ViewHelper;
import com.renyu.slidingmenu.R;
import com.renyu.slidingmenu.common.CommonUtils;

/**
 * Created by ry on 2014/11/11.
 */
public class LeftRightDrawerLayout extends HorizontalScrollView {

    Context context=null;

    int leftMenuWidth=0;
    int rightMenuWidth=0;

    boolean once=false;

    ViewGroup mainView=null;
    View leftView=null;
    View rightView=null;
    View content=null;

    public LeftRightDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.left_right_menu);
        leftMenuWidth=array.getDimensionPixelOffset(R.styleable.left_right_menu_left_width, 0);
        rightMenuWidth=array.getDimensionPixelOffset(R.styleable.left_right_menu_right_width, 0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mainView= (ViewGroup) getChildAt(0);
            leftView=mainView.getChildAt(0);
            rightView=mainView.getChildAt(2);
            content=mainView.getChildAt(1);

            leftView.getLayoutParams().width=leftMenuWidth;
            rightView.getLayoutParams().width=rightMenuWidth;
            content.getLayoutParams().width= CommonUtils.getScreenWH(context)[0];
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(leftMenuWidth, 0);
            once=true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (l-leftMenuWidth>rightMenuWidth) {
            scrollTo(leftMenuWidth+rightMenuWidth, 0);
        }
        if (l<0) {
            scrollTo(0, 0);
        }
        super.onScrollChanged(l, t, oldl, oldt);
        if (l<=leftMenuWidth) {
            ViewHelper.setTranslationX(content, l<0?-leftMenuWidth:-(leftMenuWidth-l));
        }
        else {
            ViewHelper.setTranslationX(content, l-leftMenuWidth>rightMenuWidth?rightMenuWidth:l-leftMenuWidth);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX()<leftMenuWidth/2) {
                    smoothScrollTo(0, 0);
                }
                else if (getScrollX()>=leftMenuWidth/2&&getScrollX()<leftMenuWidth) {
                    smoothScrollTo(leftMenuWidth, 0);
                }
                else if (getScrollX()>=leftMenuWidth&&getScrollX()<=leftMenuWidth+rightMenuWidth/2) {
                    smoothScrollTo(leftMenuWidth, 0);
                }
                else if (getScrollX()>=leftMenuWidth+rightMenuWidth/2) {
                    smoothScrollTo(leftMenuWidth+rightMenuWidth, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
