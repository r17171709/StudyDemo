package com.renyu.androidpulllayout.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ry on 2015/3/5.
 */
public class EyeView extends ImageView {

    Bitmap bmp=null;
    Paint paint=null;

    public EyeView(Context context) {
        super(context);
    }

    public EyeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setDrawingCacheEnabled(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint=new Paint();
        paint.setAntiAlias(true);
    }

    public EyeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (bmp!=null) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(bmp, 0, 0, paint);
            paint.setXfermode(null);
        }
    }

    public void setRadius(int radius) {
        bmp=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
        invalidate();
    }
}
