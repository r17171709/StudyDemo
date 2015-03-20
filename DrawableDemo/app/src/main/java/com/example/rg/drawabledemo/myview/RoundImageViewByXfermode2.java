package com.example.rg.drawabledemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.example.rg.drawabledemo.R;

import java.lang.ref.WeakReference;

/**
 * Created by RG on 2015/3/14.
 */
public class RoundImageViewByXfermode2 extends ImageView {

    //设置图片形状类型
    int type=1;
    //设置圆角图片半径
    float radius=0;
    //用来存储绘制图片的变量
    WeakReference<Bitmap> weakReference=null;
    Bitmap maskBitmap=null;

    public RoundImageViewByXfermode2(Context context) {
        this(context, null);
    }

    public RoundImageViewByXfermode2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageViewByXfermode2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewByXfermodeAttrs, defStyleAttr, 0);
        type=array.getInt(R.styleable.RoundImageViewByXfermodeAttrs_type, 1);
        radius=array.getDimensionPixelSize(R.styleable.RoundImageViewByXfermodeAttrs_radius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果是圆形，则将imageview强制设置宽高相同
        if (type==2) {
            //注意这边，是直接使用测量好的结果
            int temp=Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(temp, temp);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //得到弱引用中的缓存对象，如果为空，则进行绘制操作
        Bitmap bmp=weakReference==null||weakReference.get()==null?null:weakReference.get();
        if (bmp==null||bmp.isRecycled()) {
            Bitmap targetBmp=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasTarget=new Canvas(targetBmp);
            Drawable drawable=getDrawable();
            if (drawable!=null) {
                int imageWidth=drawable.getIntrinsicWidth();
                int imageHeight=drawable.getIntrinsicHeight();
                float scaleHeight=(float) getHeight()/imageHeight;
                float scaleWidth=(float) getWidth()/imageWidth;
                //注意此处，绘制出的图片一定要超过view的大小，否则进行交集操作时图片剪裁出来的效果会有所异常
                float scale=Math.max(scaleHeight, scaleWidth);
                drawable.setBounds(0, 0, (int) (imageWidth*scale), (int) (imageHeight*scale));
                drawable.draw(canvasTarget);
                Paint paint=new Paint();
                paint.setAntiAlias(true);
                paint.setFilterBitmap(false);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                if (maskBitmap==null) {
                    maskBitmap=getMaskBitmap();
                }
                canvasTarget.drawBitmap(maskBitmap, 0, 0, paint);
                paint.setXfermode(null);
                canvas.drawBitmap(targetBmp, 0, 0, paint);
                weakReference=new WeakReference<Bitmap>(targetBmp);
            }
        }
        //不为空，则直接进行绘制
        if (bmp!=null) {
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            canvas.drawBitmap(bmp, 0, 0, paint);
        }
    }

    private Bitmap getMaskBitmap() {
        System.out.println(getWidth()+" "+getHeight());
        Bitmap bmp=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        if (type==1) {
            RectF rectF=new RectF(0 ,0 , getWidth(), getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
        } else if (type==2) {
            canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2, paint);
        }
        return bmp;
    }
}
