package com.renyu.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.renyu.circleimageview.R;

/**
 * Created by ry on 2015/1/6.
 */
public class CircleImageView extends ImageView {

    Context context=null;
    int border_thickness=0;
    int defaultColor=0xFFFFFFFF;
    int border_inside_color=0;
    int border_outside_color=0;
    int defaultHeight=0;
    int defaultWidth=0;
    //0 圆 1 矩形
    int circle_type=0;

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.roundedimageview);
        border_thickness=array.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
        border_inside_color=array.getColor(R.styleable.roundedimageview_border_inside_color, defaultColor);
        border_outside_color=array.getColor(R.styleable.roundedimageview_border_outside_color, defaultColor);
        circle_type=array.getInteger(R.styleable.roundedimageview_circle_type, 0);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //没有设置原始图片，直接忽略
        if (getDrawable()==null) {
            return;
        }
        //图片没有宽高，直接忽略
        if (getWidth()==0||getHeight()==0) {
            return;
        }
        this.measure(0, 0);
        //.9图直接忽略
        if (getDrawable().getClass()== NinePatchDrawable.class) {
            return;
        }
        Bitmap b=((BitmapDrawable) getDrawable()).getBitmap();
        defaultHeight=getHeight();
        defaultWidth=getWidth();
        //画圆半径
        int radius=0;
        if (defaultHeight>defaultWidth) {
            radius=defaultWidth/2-2*border_thickness;
        }
        else {
            radius=defaultHeight/2-2*border_thickness;
        }
        if (circle_type==0) {
            //画内圈
            drawCircleBorder(canvas, radius+border_thickness, border_inside_color);
            //画外圈
            drawCircleBorder(canvas, radius+2*border_thickness, border_outside_color);
            Bitmap bmp_=getCroppedRoundBitmap(b, radius);
            if (getWidth()<getHeight()) {
                canvas.drawBitmap(bmp_, 2*border_thickness, defaultHeight/2-radius, null);
            }
            else {
                canvas.drawBitmap(bmp_, defaultWidth/2-radius, 2*border_thickness, null);
            }
        }
        else if (circle_type==1) {
            Bitmap bmp_=getRoundRectBitmap(b, radius);
            canvas.drawBitmap(bmp_, 0, 0, null);
        }
    }

    //画圈
    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(border_thickness);
        paint.setColor(color);
        canvas.drawCircle(defaultWidth/2, defaultHeight/2, radius, paint);
    }

    //得到圆形图片
    private Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp=null;
        int diameter=radius*2;
        int bmpWidth=bmp.getWidth();
        int bmpHeight=bmp.getHeight();
        Bitmap squareBitmap=null;
        if (bmpWidth>bmpHeight) {
            squareBitmap=Bitmap.createBitmap(bmp, 0, (bmpWidth-bmpHeight)/2, bmpWidth, bmpHeight);
        }
        else if (bmpWidth>bmpHeight) {
            squareBitmap=Bitmap.createBitmap(bmp, 0, (bmpHeight-bmpWidth)/2, bmpWidth, bmpHeight);
        }
        else {
            squareBitmap=bmp;
        }
        if (squareBitmap.getWidth()!=diameter||squareBitmap.getHeight()!=diameter) {
            scaledSrcBmp=Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
        }
        else {
            scaledSrcBmp=squareBitmap;
        }
        Bitmap out=Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Canvas canvas=new Canvas(out);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(scaledSrcBmp.getWidth()/2, scaledSrcBmp.getHeight()/2, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect=new Rect(0, 0, scaledSrcBmp.getWidth(),scaledSrcBmp.getHeight());
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
        bmp = null;
        squareBitmap = null;
        scaledSrcBmp = null;
        return out;
    }

    private Bitmap getRoundRectBitmap(Bitmap bmp, int radius) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        RectF rectf=new RectF(0, 0, getWidth(), getHeight());
        Bitmap out=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(out);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectf, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        bmp = null;
        return out;
    }
}
