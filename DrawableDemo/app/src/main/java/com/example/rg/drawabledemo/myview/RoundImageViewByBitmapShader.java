package com.example.rg.drawabledemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.rg.drawabledemo.R;

/**
 * Created by RG on 2015/3/16.
 */
public class RoundImageViewByBitmapShader extends ImageView {

    int type=1;
    float radius=0;

    public RoundImageViewByBitmapShader(Context context) {
        this(context, null);
    }

    public RoundImageViewByBitmapShader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageViewByBitmapShader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewByXfermodeAttrs, defStyleAttr, 0);
        type=array.getInt(R.styleable.RoundImageViewByXfermodeAttrs_type, 1);
        radius=array.getDimensionPixelSize(R.styleable.RoundImageViewByXfermodeAttrs_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (type==2) {
            int width=Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(width, width);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Drawable drawable=getDrawable();
        if (drawable==null) {
            return;
        }
        Bitmap bmp=drawableToBitmap(drawable);
        BitmapShader shader=new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scaleWidth=getWidth()/drawable.getIntrinsicWidth();
        float scaleHeight=getHeight()/drawable.getIntrinsicHeight();
        float scale=Math.max(scaleHeight, scaleWidth);
        Matrix matrix=new Matrix();
        matrix.setScale(scale, scale);
        shader.setLocalMatrix(matrix);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        if (type==1) {
            RectF rectF=new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        else if (type==2) {
            canvas.drawCircle(getWidth()/2, getHeight()/2, getWidth()/2, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bmp=Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bmp;
    }
}
