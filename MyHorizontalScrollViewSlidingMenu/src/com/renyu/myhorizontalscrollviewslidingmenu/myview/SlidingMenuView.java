package com.renyu.myhorizontalscrollviewslidingmenu.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.renyu.myhorizontalscrollviewslidingmenu.R;

/**
 * Created by Administrator on 2014/9/12.
 */
public class SlidingMenuView extends HorizontalScrollView {

    private int screenWidth=0;
    private int menuWidth=0;
    private int halfMenuWidth=0;

    private int rightExtra=10;

    //只加载一次
    boolean isLoadOnce=false;
    //当前是否已经打开
    boolean isOpen=false;

    public SlidingMenuView(Context context) {
        super(context);
        init(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.SlidingMenuView);
        rightExtra=array.getDimensionPixelSize(R.styleable.SlidingMenuView_rightextra, 10);
        init(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        //获取屏幕宽度
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth=dm.widthPixels;
        //获取正文露出部分
        rightExtra=dip2px(rightExtra);
        //获取菜单栏宽度
        menuWidth=screenWidth-rightExtra;
        halfMenuWidth=menuWidth/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!isLoadOnce) {
            LinearLayout parent=(LinearLayout)getChildAt(0);
            ViewGroup menuLayout=(ViewGroup) parent.getChildAt(0);
            ViewGroup contentLayout=(ViewGroup) parent.getChildAt(1);
            menuLayout.getLayoutParams().width=menuWidth;
            contentLayout.getLayoutParams().width=screenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            this.scrollTo(menuWidth, 0);
            isLoadOnce=true;
        }
    }
    private int dip2px(float dipValue) {
        float scale=getResources().getDisplayMetrics().density;
        return (int) (dipValue*scale+0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP) {
            if(getScrollX()>halfMenuWidth) {
                this.smoothScrollTo(menuWidth, 0);
                isOpen=false;
            }
            else {
                this.smoothScrollTo(0, 0);
                isOpen=true;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断是否已经打开菜单
     * @return
     */
    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        this.smoothScrollTo(0, 0);
    }

    public void close() {
        this.smoothScrollTo(menuWidth, 0);
    }
}
