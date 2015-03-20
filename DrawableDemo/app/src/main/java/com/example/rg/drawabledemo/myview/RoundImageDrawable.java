package com.example.rg.drawabledemo.myview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by RG on 2015/3/16.
 */
public class RoundImageDrawable extends Drawable {

    Bitmap bmp=null;
    RectF rectF=null;

    Paint paint=null;

    public RoundImageDrawable(Bitmap bmp) {
        this.bmp=bmp;
        paint=new Paint();
        paint.setAntiAlias(true);
        BitmapShader shader=new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF=new RectF(left, top, right, bottom);
    }

    @Override
    public int getIntrinsicHeight() {
        return bmp.getHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return bmp.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, 30, 30, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
