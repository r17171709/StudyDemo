package com.example.rg.imoocrefreshdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by RG on 2015/3/24.
 */
public class DynamicWaveView extends View {

    //水波纹速度
    int speedFirst=10;
    //总宽度
    int totalWidth=0;
    //所有y坐标合集
    float[] ys;

    Paint paint=null;

    //第一次绘制的第一点X坐标
    int startX=0;
    //全部Y的坐标
    float[] currentYs;
    //y最大值
    float offsetY=0;
    float temp=0;

    public DynamicWaveView(Context context) {
        this(context, null);
    }

    public DynamicWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        resetValues();
        for (int i=0;i<ys.length;i++) {
            canvas.drawLine(i, totalWidth-currentYs[i]-temp, i, totalWidth, paint);
        }
        if (temp<offsetY) {
            temp++;
        }
        else {
            temp=0;
        }
        startX+=speedFirst;
        if (startX>totalWidth) {
            startX=0;
        }

        postInvalidate();
    }

    /**
     * 重新绘制本次相关坐标
     */
    private void resetValues() {
        System.arraycopy(ys, totalWidth-startX, currentYs, 0, startX);
        System.arraycopy(ys, 0, currentYs, startX,  totalWidth-startX);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        totalWidth=w;
        ys=new float[totalWidth];
        currentYs=new float[totalWidth];
        //周期
        float w_= (float) (2*Math.PI/totalWidth);
        //最小幅度高度
        float min_y=0;
        for (int i=0;i<totalWidth;i++) {
            //y = Asin(wx+b)+h
            ys[i]= (float) (20*Math.sin(i*w_));
            if(i==0) {
                min_y=ys[i];
            }
            else {
                if (min_y>ys[i]) {
                    min_y=ys[i];
                }
            }
        }
        offsetY=totalWidth-min_y;
    }
}
