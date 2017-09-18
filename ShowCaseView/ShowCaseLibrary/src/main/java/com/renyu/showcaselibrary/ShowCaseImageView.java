package com.renyu.showcaselibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ShowCaseImageView extends ImageView {

    int mFocusBorderColor=Color.BLACK;
    int mFocusBorderSize=6;
    // 椭圆圆角范围
    RectF rectF;
    // 默认背景
    Bitmap mBitmap;
    // 高亮部分集合
    ArrayList<CalculatorBean> mCalculatorBeen;
    Path mPath;

    // 设置背景Paint
    Paint mBackgroundPaint;
    // 设置高亮点清除中心Paint
    Paint mErasePaint;
    // 设置高亮点Paint
    Paint mCircleBorderPaint;

    // 设置是否可以播放动画
    boolean mAnimationEnabled=false;
    // 当前步进值
    int mAnimCounter=0;
    // 当前步进趋势
    int mStep=1;
    // 最大步进值
    final int ANIM_COUNTER_MAX=20;
    // 最大变化范围
    float animMoveFactor=0.5f;

    public ShowCaseImageView(Context context) {
        this(context, null);
    }

    public ShowCaseImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowCaseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        // 3.0之后开启硬件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
        // 由于性能限制，onDraw()方法不被执行的解决方法
        setWillNotDraw(false);

        mBackgroundPaint=new Paint();
        mBackgroundPaint.setAntiAlias(true);

        mErasePaint=new Paint();
        mErasePaint.setAntiAlias(true);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mErasePaint.setAlpha(0xFF);

        mCircleBorderPaint=new Paint();
        mCircleBorderPaint.setAntiAlias(true);
        mCircleBorderPaint.setColor(mFocusBorderColor);
        mCircleBorderPaint.setStrokeWidth(mFocusBorderSize);
        mCircleBorderPaint.setStyle(Paint.Style.STROKE);
        mCircleBorderPaint.setStrokeJoin(Paint.Join.ROUND);
        mCircleBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mCircleBorderPaint.setPathEffect(new DashPathEffect(new float[] {10, 20}, 0));

        rectF=new RectF();

        mPath=new Path();
    }

    public void setmCalculatorBeen(ArrayList<CalculatorBean> mCalculatorBeen) {
        this.mCalculatorBeen = mCalculatorBeen;
    }

    public void setmAnimationEnabled(boolean mAnimationEnabled) {
        this.mAnimationEnabled = mAnimationEnabled;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null) {
            mBitmap= Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            // 把图片设置成半透明
            mBitmap.eraseColor(0xb2000000);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBackgroundPaint);
        if (mCalculatorBeen!=null) {
            for (CalculatorBean calculatorBean : mCalculatorBeen) {
                if (calculatorBean.getmFocusShape()==FocusShape.CIRCLE) {
                    drawCircle(canvas, calculatorBean);
                }
                else if (calculatorBean.getmFocusShape()==FocusShape.ROUNDED_RECTANGLE) {
                    drawRoundedRectangle(canvas, calculatorBean);
                }
            }
            if (mAnimationEnabled) {
                if (mAnimCounter==ANIM_COUNTER_MAX) {
                    mStep=-1;
                }
                else if (mAnimCounter==0) {
                    mStep=1;
                }
                mAnimCounter+=mStep;
                postInvalidate();
            }
        }
    }

    /**
     * 绘制圆形
     * @param canvas
     * @param calculatorBean
     */
    private void drawCircle(Canvas canvas, CalculatorBean calculatorBean) {
        // 绘制高亮
        canvas.drawCircle(calculatorBean.getmCircleCenterX(), calculatorBean.getmCircleCenterY(), calculatorBean.getmCircleRadius()+ mAnimCounter*animMoveFactor, mErasePaint);
        // 绘制其余部分
        mPath.reset();
        mPath.moveTo(calculatorBean.getmCircleCenterX(), calculatorBean.getmCircleCenterY());
        mPath.addCircle(calculatorBean.getmCircleCenterX(), calculatorBean.getmCircleCenterY(), calculatorBean.getmCircleRadius()+ mAnimCounter*animMoveFactor, Path.Direction.CW);
        canvas.drawPath(mPath, mCircleBorderPaint);
    }

    /**
     * 绘制圆角矩形
     * @param canvas
     * @param calculatorBean
     */
    private void drawRoundedRectangle(Canvas canvas, CalculatorBean calculatorBean) {
        // 绘制高亮
        int centerX=calculatorBean.getmCircleCenterX();
        int centerY=calculatorBean.getmCircleCenterY();
        float left=centerX-calculatorBean.getmFocusWidth()/2- mAnimCounter*animMoveFactor;
        float top=centerY-calculatorBean.getmFocusHeight()/2- mAnimCounter*animMoveFactor;
        float right=centerX+calculatorBean.getmFocusWidth()/2+ mAnimCounter*animMoveFactor;
        float bottom=centerY+calculatorBean.getmFocusHeight()/2+ mAnimCounter*animMoveFactor;
        canvas.drawRoundRect(new RectF(left, top, right, bottom), calculatorBean.getmCircleRadius(), calculatorBean.getmCircleRadius(), mErasePaint);
        // 绘制其余部分
        mPath.reset();
        mPath.moveTo(calculatorBean.getmCircleCenterX(), calculatorBean.getmCircleCenterY());
        mPath.addRoundRect(new RectF(left, top, right, bottom), calculatorBean.getmCircleRadius(), calculatorBean.getmCircleRadius(), Path.Direction.CW);
        canvas.drawPath(mPath, mCircleBorderPaint);
    }
}
