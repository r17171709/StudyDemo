package com.example.clevo.heartlayout.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.clevo.heartlayout.R;

/**
 * Created by Clevo on 2016/6/13.
 */
public class HeartImageView extends ImageView {

    Bitmap bitmap_heart;

    public HeartImageView(Context context) {
        this(context, null);
    }

    public HeartImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bitmap_heart= BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap_heart.getWidth(), bitmap_heart.getHeight());
    }

    public void setColor(int color) {
        setImageBitmap(createColor(color));
    }

    private Bitmap createColor(int color) {
        int heartWidth=bitmap_heart.getWidth();
        int heartHeight=bitmap_heart.getHeight();
        Bitmap newBitmap=Bitmap.createBitmap(heartWidth, heartHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap_heart, 0, 0, paint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);
        canvas.setBitmap(null);
        return newBitmap;
    }
}
