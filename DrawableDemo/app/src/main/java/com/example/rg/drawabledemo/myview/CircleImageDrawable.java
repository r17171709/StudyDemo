package com.example.rg.drawabledemo.myview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by RG on 2015/3/16.
 */
public class CircleImageDrawable extends Drawable {

    Bitmap bmp=null;

    Paint paint=null;

    int width=0;

    public CircleImageDrawable(Bitmap bmp) {
        this.bmp=bmp;
        BitmapShader shader=new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        width=Math.min(bmp.getHeight(), bmp.getWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        return width;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(width/2, width/2, width/2, paint);
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
