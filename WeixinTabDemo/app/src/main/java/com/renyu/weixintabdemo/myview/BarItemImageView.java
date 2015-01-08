package com.renyu.weixintabdemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.renyu.weixintabdemo.R;

/**
 * Created by ry on 2014/12/15.
 */
public class BarItemImageView extends ImageView {

    Bitmap selectImage=null;
    Bitmap normalImage=null;

    public void setNormalImage(Bitmap normalImage) {
        this.normalImage = normalImage;
    }

    public void setSelectImage(Bitmap selectImage) {
        this.selectImage = selectImage;
    }

    Context context=null;

    Paint paint=null;
    int alpha=0;

    public BarItemImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.barItemImage);
        selectImage=BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.barItemImage_selectImage, R.drawable.ic_launcher));
        normalImage=BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.barItemImage_normalImage, R.drawable.ic_launcher));
        a.recycle();
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint==null) {
            return;
        }
        paint.setAlpha(alpha);
        canvas.drawBitmap(selectImage, null, new Rect(0, 0, selectImage.getWidth(), selectImage.getHeight()), paint);
        paint.setAlpha(255-alpha);
        canvas.drawBitmap(normalImage, null, new Rect(0, 0, normalImage.getWidth(), normalImage.getHeight()), paint);
    }

    private void init(Context context) {
        this.context=context;
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);

    }

    public void setBarAlpha(int alpha) {
        this.alpha=alpha;
        //这边一定要通过刷新view去实现，不然没有效果
        invalidate();
    }

}
