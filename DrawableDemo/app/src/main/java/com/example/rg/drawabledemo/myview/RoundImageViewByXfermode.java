package com.example.rg.drawabledemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.rg.drawabledemo.R;

/**
 * Created by RG on 2015/3/12.
 */
public class RoundImageViewByXfermode extends View {

    //设置圆角或者园
    int type=0;
    //设置圆角半径
    int radius=0;
    //背景图片
    Bitmap bmp=null;
    //视图宽度、高度
    int viewWidth=0;
    int viewHeight=0;

    public RoundImageViewByXfermode(Context context) {
        this(context, null);
    }

    public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageViewByXfermode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewByXfermodeAttrs, defStyleAttr, 0);
        type=array.getInt(R.styleable.RoundImageViewByXfermodeAttrs_type, 1);
        //最小圆角半径为10dip
        radius=array.getDimensionPixelSize(R.styleable.RoundImageViewByXfermodeAttrs_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        bmp= BitmapFactory.decodeResource(context.getResources(), array.getResourceId(R.styleable.RoundImageViewByXfermodeAttrs_src, R.mipmap.ic_launcher));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取宽度
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode==MeasureSpec.EXACTLY) {
            viewWidth=width;
        }
        else {
            int temp=bmp.getWidth()+getPaddingLeft()+getPaddingRight();
            if (widthMode==MeasureSpec.AT_MOST) {
                viewWidth=Math.min(temp, width);
            }
            else {
                viewWidth=temp;
            }
        }

        //获取高度
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode==MeasureSpec.EXACTLY) {
            viewHeight=height;
        }
        else {
            int temp=bmp.getHeight()+getPaddingLeft()+getPaddingRight();
            if (heightMode==MeasureSpec.AT_MOST) {
                viewHeight=Math.min(temp, height);
            }
            else {
                viewHeight=temp;
            }
        }

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap resultBitmap=null;
        if (type==1) {
            resultBitmap=drawRectF();
        }
        else if (type==2) {
            resultBitmap=drawCircle();
        }
        canvas.drawBitmap(resultBitmap, 0, 0, null);

    }

    private Bitmap drawCircle() {
        //选取最小值作为圆半径
        int scale=Math.min(viewHeight, viewWidth);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        //绘制一个圆形bitmap图片并返回
        Bitmap targetBimap=Bitmap.createBitmap(scale, scale, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(targetBimap);
        Bitmap currentBmp=Bitmap.createScaledBitmap(bmp, scale, scale, false);
        canvas.drawCircle(scale/2, scale/2, scale/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(currentBmp, 0, 0, paint);
        return targetBimap;
    }

    private Bitmap drawRectF() {
        Bitmap targetBitmap=Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(targetBitmap);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        RectF rectF=new RectF(0, 0, viewWidth, viewHeight);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        return targetBitmap;
    }
}
