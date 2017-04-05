package com.renyu.rangeseekbar.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.renyu.rangeseekbar.R;
import com.renyu.rangeseekbar.model.Thumb;

/**
 * Created by renyu on 2016/11/21.
 */

public class RangeSeekbar extends View {

    Context context;

    int mainLineColor=0;
    int mainLineSecondColor=0;
    int maxValue=0;
    //主轴线的宽度
    int mainLineWidth=0;
    //主轴线的高度
    int mainLineHeight=0;
    //thumb图片
    Bitmap image;
    //thumb半径
    int thumbRadius;
    //主轴线相关参数
    int mainLineStartX=0;
    int mainLineStopX=0;
    int mainLineStartY=0;
    int mainLineStopY=0;
    int tipMargin=0;
    int tipBottomMargin=0;
    // 底部提示文字位置
    int startTip=0;
    //顶部提示文字颜色
    int tipColor=0;
    //顶部提示文字大小
    int tipTextSize=0;
    //顶部提示文字
    String tipTextString;
    //底部提示文字颜色
    int tipBottomColor=0;
    //底部提示文字大小
    int tipBottomTextSize=0;

    //主轴线的Paint属性
    Paint mainLinePaint;
    //主轴线数值之间的Paint属性
    Paint mainLineSecondPaint;
    //Thumb的Paint属性
    Paint thumbPaint;
    //文字框的Paint属性
    Paint popTextPaint;
    Paint popBottomTextPaint;

    //2个Thumb
    Thumb thumbL;
    Thumb thumbR;
    //当前选中的thumb
    Thumb currentThumb;
    //左右margin
    int margin=0;

    float lastX=0;

    OnChangeListener listener;

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public RangeSeekbar(Context context) {
        this(context, null);
    }

    public RangeSeekbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;
        mainLinePaint=new Paint();
        mainLineSecondPaint=new Paint();
        thumbPaint=new Paint();
        popBottomTextPaint=new Paint();
        popTextPaint=new Paint();

        thumbL=new Thumb();
        thumbR=new Thumb();

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RangeSeekbar);
        mainLineColor=array.getColor(R.styleable.RangeSeekbar_mainLineColor, Color.BLACK);
        mainLineSecondColor=array.getColor(R.styleable.RangeSeekbar_mainLineSecondColor, Color.BLACK);
        image= BitmapFactory.decodeResource(context.getResources(), array.getResourceId(R.styleable.RangeSeekbar_image, R.mipmap.ic_round));
        thumbRadius=image.getWidth()/2;
        tipColor=array.getColor(R.styleable.RangeSeekbar_tipColor, Color.BLACK);
        tipTextSize=array.getDimensionPixelSize(R.styleable.RangeSeekbar_tipTextSize, 0);
        tipTextString=array.getString(R.styleable.RangeSeekbar_tipTextString);
        tipMargin=array.getDimensionPixelOffset(R.styleable.RangeSeekbar_tipMargin, 0);
        tipBottomColor=array.getColor(R.styleable.RangeSeekbar_tipBottomColor, Color.BLACK);
        tipBottomTextSize=array.getDimensionPixelSize(R.styleable.RangeSeekbar_tipBottomTextSize, 0);
        tipBottomMargin=array.getDimensionPixelSize(R.styleable.RangeSeekbar_tipBottomMargin, 0);
        maxValue=array.getInt(R.styleable.RangeSeekbar_maxValue, 100);
        array.recycle();

        //高度这里随便定义的
        mainLineHeight=dp2px(context, 5);

        //主轴默认配置
        mainLinePaint.setAntiAlias(true);
        mainLinePaint.setColor(mainLineColor);
        mainLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mainLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mainLinePaint.setStyle(Paint.Style.FILL);
        mainLinePaint.setStrokeWidth(mainLineHeight);

        //主轴选中配置
        mainLineSecondPaint.setAntiAlias(true);
        mainLineSecondPaint.setColor(mainLineSecondColor);
        mainLineSecondPaint.setStrokeJoin(Paint.Join.ROUND);
        mainLineSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        mainLineSecondPaint.setStyle(Paint.Style.FILL);
        mainLineSecondPaint.setStrokeWidth(mainLineHeight);

        //thumb配置
        thumbPaint.setAntiAlias(true);
        thumbPaint.setStyle(Paint.Style.FILL);

        //顶部提示文字配置
        popTextPaint.setAntiAlias(true);
        popTextPaint.setColor(tipColor);
        popTextPaint.setStyle(Paint.Style.FILL);
        popTextPaint.setTextSize(tipTextSize);
        popTextPaint.setTextAlign(Paint.Align.CENTER);

        //底部提示文字配置
        popBottomTextPaint.setAntiAlias(true);
        popBottomTextPaint.setColor(tipBottomColor);
        popBottomTextPaint.setStyle(Paint.Style.FILL);
        popBottomTextPaint.setTextSize(tipBottomTextSize);
        popBottomTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        int width=0;
        int height=0;

        if (widthMode==MeasureSpec.EXACTLY) {
            width=widthSize;
        }
        else {
            width=dp2px(context, 200);
        }

        if (heightMode==MeasureSpec.EXACTLY) {
            height=heightSize;
        }
        else {
            height=dp2px(context, 50);
        }

        super.setMeasuredDimension(width, height);

        //左右各留下个间隔
        margin=thumbRadius;

        //画笔位置
        mainLineWidth=getMeasuredWidth()-margin*2;
        mainLineStartX=margin;
        mainLineStopX=getMeasuredWidth()-margin;
        mainLineStartY=(height-mainLineHeight)/2;
        mainLineStopY=(height+mainLineHeight)/2;
        startTip=(height+mainLineHeight)/2+tipBottomMargin;

        //thumb的起始位置
        thumbL.setThumbX(mainLineStartX-thumbRadius);
        thumbL.setPress(false);
        thumbR.setThumbX(mainLineStopX-thumbRadius);
        thumbR.setPress(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制主轴
        canvas.drawRect(mainLineStartX, mainLineStartY, mainLineStopX, mainLineStopY, mainLinePaint);
        //绘制重叠轴
        canvas.drawRect(mainLineStartX+thumbL.getThumbX(),mainLineStartY, mainLineStartX+thumbR.getThumbX(), mainLineStopY, mainLineSecondPaint);

        //绘制Thumb
        canvas.drawBitmap(image, thumbL.getThumbX(), getMeasuredHeight()/2-thumbRadius, thumbPaint);
        canvas.drawBitmap(image, thumbR.getThumbX(), getMeasuredHeight()/2-thumbRadius, thumbPaint);

        //绘制顶部提示文字配置
        Rect rectAll=new Rect(getMeasuredWidth()/2-dp2px(context, 100), mainLineStartY-2*tipMargin,
                getMeasuredWidth()/2+dp2px(context, 100), mainLineStartY-tipMargin);
        float leftPercent= thumbL.getThumbX()/mainLineWidth;
        float rightPercent= thumbR.getThumbX()/mainLineWidth;
        if (leftPercent>rightPercent) {
            canvas.drawText("¥"+((int) (rightPercent*maxValue)/100+"00")+"-"+"¥"+((int) (leftPercent*maxValue)/100+"00"), rectAll.centerX(), getBaseline(rectAll), popTextPaint);
        }
        else {
            canvas.drawText("¥"+((int) (leftPercent*maxValue)/100+"00")+"-"+"¥"+((int) (rightPercent*maxValue)/100+"00"), rectAll.centerX(), getBaseline(rectAll), popTextPaint);
        }

        //绘制底部提示文字配置
        Rect tempL = new Rect();
        popBottomTextPaint.getTextBounds("0", 0, 1, tempL);
        Rect rectTipL=new Rect(mainLineStartX, startTip, mainLineStartX+tempL.width(),  startTip+tempL.height());
        canvas.drawText("0", rectTipL.centerX(), getBaseline(rectTipL), popBottomTextPaint);

        Rect tempR = new Rect();
        popBottomTextPaint.getTextBounds("不限", 0, 2, tempR);
        Rect rectTipR=new Rect(mainLineStopX-tempR.width(), startTip, mainLineStopX,  startTip+tempL.height());
        canvas.drawText("不限", rectTipR.centerX(), getBaseline(rectTipR), popBottomTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(thumbL.getThumbX()+thumbRadius-event.getX())<=thumbRadius) {
                    //选中ThumbL
                    thumbL.setPress(true);
                    currentThumb=thumbL;

                    lastX=event.getX();
                }
                if (Math.abs(thumbR.getThumbX()+thumbRadius-event.getX())<=thumbRadius) {
                    //选中ThumbR
                    thumbR.setPress(true);
                    currentThumb=thumbR;

                    lastX=event.getX();
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                if (currentThumb!=null) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentThumb!=null) {
                    float thumbX=currentThumb.getThumbX()+(event.getX()-lastX);
                    //2个临界点
                    if (thumbX<0) {
                        thumbX=0;
                    }
                    if (thumbX>mainLineWidth) {
                        thumbX=mainLineWidth;
                    }
                    currentThumb.setThumbX(thumbX);
                    lastX=event.getX();
                    postInvalidate();

                    if (listener!=null) {
                        float leftPercent= thumbL.getThumbX()/mainLineWidth;
                        float rightPercent= thumbR.getThumbX()/mainLineWidth;
                        if (leftPercent>rightPercent) {
                            listener.onValueChange(Integer.parseInt((int) (rightPercent*maxValue)/100+"00"), Integer.parseInt((int) (leftPercent*maxValue)/100+"00"));
                        }
                        else {
                            listener.onValueChange(Integer.parseInt((int) (leftPercent*maxValue)/100+"00"), Integer.parseInt((int) (rightPercent*maxValue)/100+"00"));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    currentThumb.setPress(false);
                    currentThumb=null;
                    lastX=0;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
        }
        return super.onTouchEvent(event);
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int getBaseline(Rect rectL) {
        Paint.FontMetricsInt fontMetrics = popTextPaint.getFontMetricsInt();
        return rectL.top + (rectL.bottom - rectL.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    }

    public interface OnChangeListener {
        void onValueChange(int thumbL, int thumbR);
    }

    public void setPercent(float minPercent, float maxPercent) {
        thumbL.setThumbX(margin+mainLineWidth*minPercent/100);
        thumbR.setThumbX(margin+mainLineWidth*maxPercent/100);
        invalidate();
    }
}
