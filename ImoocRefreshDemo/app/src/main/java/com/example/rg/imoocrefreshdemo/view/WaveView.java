package com.example.rg.imoocrefreshdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.rg.imoocrefreshdemo.R;

/**
 * Created by RG on 2015/3/23.
 */
public class WaveView extends View {

    Bitmap bmp=null;
    Paint paint=null;
    Path path=null;
    Canvas canvas=null;
    Bitmap finalBmp=null;

    //3阶贝塞尔曲线2个控制点参考值
    float controlY, controlX, waveY;
    //贝塞尔曲线移动方向
    boolean rightMove=true;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        bmp= BitmapFactory.decodeResource(context.getResources(), R.mipmap.imooc_bg);
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        path=new Path();

        finalBmp=Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        canvas=new Canvas(finalBmp);

        controlY=17/16F*bmp.getHeight();
        waveY=7/8F*bmp.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width, height;

        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode==MeasureSpec.EXACTLY) {
            width=widthSize+getPaddingLeft()+getPaddingRight();
        }
        else {
            int temp=bmp.getWidth()+getPaddingLeft()+getPaddingRight();
            if (widthMode==MeasureSpec.AT_MOST) {
                width=Math.min(temp, widthSize);
            }
            else {
                width=temp;
            }
        }

        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode==MeasureSpec.EXACTLY) {
            height=heightSize+getPaddingTop()+getPaddingBottom();
        }
        else {
            int temp=bmp.getHeight()+getPaddingTop()+getPaddingBottom();
            if (heightMode==MeasureSpec.AT_MOST) {
                height=Math.min(temp, heightSize);
            }
            else {
                height=temp;
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvasDraw) {
        super.onDraw(canvas);

        path.reset();
        finalBmp.eraseColor(Color.parseColor("#00ffffff"));
        canvas.drawBitmap(bmp, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        path.moveTo(0, controlY);
        //贝塞尔曲线产生移动效果
        if (controlX>bmp.getWidth()*3) {
            rightMove=false;
        }
        else if (controlX<-bmp.getWidth()*3){
            rightMove=true;
        }
        //完全浸泡之后，重置
        if (controlY>0) {
            controlY-=1;
            waveY-=1;
        }
        else {
            controlY=17/16F*bmp.getHeight();
            waveY=7/8F*bmp.getHeight();
        }
        controlX=rightMove?controlX+10:controlX-10;
        path.cubicTo(controlX/2, waveY-(controlY-waveY), controlX/2+bmp.getWidth()/2, controlY, bmp.getWidth(), waveY);
        path.lineTo(bmp.getWidth(), bmp.getHeight());
        path.lineTo(0, bmp.getHeight());
        path.close();
        canvas.drawPath(path, paint);
        paint.setXfermode(null);

        canvasDraw.drawBitmap(finalBmp, getPaddingLeft(), getPaddingTop(), null);

        postInvalidate();
    }
}
