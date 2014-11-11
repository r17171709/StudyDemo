package com.renyu.slidingmenu.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.renyu.slidingmenu.R;
import com.renyu.slidingmenu.common.CommonUtils;

/**
 * Created by ry on 2014/11/7.
 */
public class LeftMenuLayout2 extends HorizontalScrollView {

    Context context=null;

    boolean once=false;

    int menuWidth=0;

    boolean isOpenMenu=false;

    LinearLayout mainView=null;
    View menu=null;
    View content=null;

    public LeftMenuLayout2(Context context) {
        super(context);
    }

    public LeftMenuLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.left_menu);
        menuWidth=a.getDimensionPixelSize(R.styleable.left_menu_width, 100);
        a.recycle();
    }

    public LeftMenuLayout2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mainView= (LinearLayout) getChildAt(0);

            menu=mainView.getChildAt(0);
            content=mainView.getChildAt(1);

            menu.getLayoutParams().width=menuWidth;
            content.getLayoutParams().width= CommonUtils.getScreenWH(context)[0];
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(menuWidth, 0);
            once=true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX()>menuWidth/2) {
                    this.smoothScrollTo(menuWidth, 0);
                    isOpenMenu=false;
                }
                else {
                    this.smoothScrollTo(0, 0);
                    isOpenMenu=true;
                }
                //关键在返回true，不然没有效果
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //仿QQ特效
        float scale = l * 1.0f / menuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

        ViewHelper.setScaleX(menu, leftScale);
        ViewHelper.setScaleY(menu, leftScale);
        ViewHelper.setAlpha(menu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(menu, menuWidth * scale * 0.6f);

        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight() / 2);
        ViewHelper.setScaleX(content, rightScale);
        ViewHelper.setScaleY(content, rightScale);

        //保持菜单栏不运动
        //ViewHelper.setTranslationX(menu, l);
    }

    /**
     * 设置背景图片资源
     * @param resId
     */
    public void setBackgroundRes(int resId) {
        mainView.setBackgroundResource(resId);
    }

    private void openMenu() {
        this.smoothScrollTo(0, 0);
        isOpenMenu=true;
    }

    private void closeMenu() {
        this.smoothScrollTo(menuWidth, 0);
        isOpenMenu=false;
    }

    /**
     * 手动控制菜单打开关闭
     */
    public void toggle() {
        if (isOpenMenu) {
            closeMenu();
        }
        else {
            openMenu();
        }
    }
}
