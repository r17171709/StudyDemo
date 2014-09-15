package com.renyu.arrorprogressbar.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.renyu.arrorprogressbar.R;

/**
 * Created by Administrator on 2014/9/15.
 */
public class MyLayoutProgress extends RelativeLayout {

    ProgressBar layout_progress=null;
    ImageView layout_image=null;

    int width=0;
    int height=0;
    int left=0;

    public MyLayoutProgress(Context context) {
        super(context);
    }
    public MyLayoutProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyLayoutProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //优先执行onFinishInflate，然后才是onMeasure onLayout,所以不可以在此获取控件高度
        layout_progress=(ProgressBar) findViewById(R.id.layout_progress);
        layout_image=(ImageView) findViewById(R.id.layout_image);
    }

    public void setProgress(int process) {
        layout_progress.setProgress(process);
        System.out.println(width+" "+height+" "+left);
        int imageLeft=width*process/100+left;
        LayoutParams arrowParams=(LayoutParams)layout_image.getLayoutParams();
        arrowParams.leftMargin=imageLeft;
        layout_image.setLayoutParams(arrowParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0;i<getChildCount();i++) {
            //测量每个控件的宽高
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params= (MarginLayoutParams) getChildAt(i).getLayoutParams();
            System.out.println(getChildAt(i).getMeasuredWidth()+" "+getChildAt(i).getMeasuredHeight()+" "+params.leftMargin+" "+params.rightMargin);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i=0;i<getChildCount();i++) {
        	//此处第一个child为进度条的宽高
            if(i==0) {
                width=getChildAt(i).getWidth();
                height=getChildAt(i).getHeight();
                left=getChildAt(i).getLeft();
            }
        }
    }
}
