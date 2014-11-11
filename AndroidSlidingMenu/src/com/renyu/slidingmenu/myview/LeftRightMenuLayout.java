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
 * Created by ry on 2014/11/7.
 */
public class LeftRightMenuLayout extends HorizontalScrollView {

    Context context=null;

    int leftMenuWidth=0;
    int rightMenuWidth=0;

    ViewGroup mainView=null;
    View leftMenu=null;
    View rightMenu=null;
    View content=null;

    boolean once=false;

    final static int NONENABLE_SCROLL=1;
    final static int ENABLE_SCROLL=2;
    private int state=0;

    public LeftRightMenuLayout(Context context, AttributeSet attrs) {
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
            leftMenu=mainView.getChildAt(0);
            content=mainView.getChildAt(1);
            rightMenu=mainView.getChildAt(2);

            leftMenu.getLayoutParams().width=leftMenuWidth;
            rightMenu.getLayoutParams().width=rightMenuWidth;
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
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX()<=leftMenuWidth) {
                    if (getScrollX()<=leftMenuWidth/2) {
                        smoothScrollTo(0, 0);
                    }
                    else {
                        smoothScrollTo(leftMenuWidth, 0);
                    }
                }
                else if(getScrollX()>leftMenuWidth) {
                    if (getScrollX()<=leftMenuWidth+rightMenuWidth/2) {
                        smoothScrollTo(leftMenuWidth, 0);
                    }
                    else {
                        smoothScrollTo(leftMenuWidth+rightMenuWidth, 0);
                    }
                }
                return true;

        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (l<=leftMenuWidth) {
            ViewHelper.setTranslationX(leftMenu, l);
        }
        else if(l>leftMenuWidth) {
            ViewHelper.setTranslationX(rightMenu, l-leftMenuWidth-rightMenuWidth);
        }
    }

    public void setScrollType(int state) {
        this.state=state;
    }
}
