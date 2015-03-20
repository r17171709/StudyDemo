package com.example.rg.drawabledemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.rg.drawabledemo.R;

/**
 * Created by RG on 2015/3/16.
 */
public class ColorTrackView extends View {

    String text="";
    float text_size=0;
    int text_origin_color=0;
    int text_change_color=0;
    int direction=0;

    Paint paint=null;
    Rect rect=null;
    //文字长度
    int textWidth=0;
    //文字开始点
    int startX=0;
    int progress=0;

    int viewWidth=0;
    int viewHeight=0;

    public ColorTrackView(Context context) {
        this(context, null);
    }

    public ColorTrackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rect=new Rect();

        paint=new Paint();
        paint.setAntiAlias(true);

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ColorTrackViewAttrs, defStyleAttr, 0);
        text=array.getString(R.styleable.ColorTrackViewAttrs_text);
        text_size=array.getDimensionPixelSize(R.styleable.ColorTrackViewAttrs_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        text_origin_color=array.getColor(R.styleable.ColorTrackViewAttrs_text_origin_color, Color.WHITE);
        text_change_color=array.getColor(R.styleable.ColorTrackViewAttrs_text_change_color, Color.WHITE);
        direction=array.getInt(R.styleable.ColorTrackViewAttrs_direction, 1);

        array.recycle();

        paint.setColor(text_origin_color);
        paint.setTextSize(text_size);
        //得到文字的宽度，直接测量矩阵不够准确
        textWidth= (int) paint.measureText(text);
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode==MeasureSpec.EXACTLY) {
            viewWidth=width;
        }
        else {
            int temp=textWidth+getPaddingLeft()+getPaddingRight();
            if (widthMode==MeasureSpec.AT_MOST) {
                viewWidth=Math.min(temp, width);
            }
            else {
                viewWidth=temp;
            }
        }

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode==MeasureSpec.EXACTLY) {
            viewHeight=height;
        }
        else {
            int temp=rect.height()+getPaddingTop()+getPaddingBottom();
            if (heightMode==MeasureSpec.AT_MOST) {
                viewHeight=Math.min(temp, height);
            }
            else {
                viewHeight=temp;
            }
        }

        setMeasuredDimension(viewWidth, viewHeight);

        startX=getMeasuredWidth()/2-textWidth/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawClipText(canvas, progress);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText(text, startX, getHeight() / 2 + rect.height() / 2, paint);
    }

    /**
     * 绘制矩形范围内的文字，范围在startX+0至startX+textWidth范围内
     * @param canvas
     */
    private void drawClipText(Canvas canvas, int progress) {
        Paint paint1=new Paint();
        paint1.setTextSize(text_size);
        paint1.setColor(text_change_color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        if (direction==1) {
            canvas.clipRect(startX, 0, startX+textWidth*progress/100, getHeight());
        }
        else if (direction==2) {
            canvas.clipRect(startX+textWidth*progress/100, 0, startX+textWidth, getHeight());
        }
        canvas.drawText(text, startX, getHeight()/2+rect.height()/2, paint1);
        canvas.restore();
    }

    public void setRefresh(float refresh) {
        this.progress= (int) refresh;
        invalidate();
    }

    public void setDirection(int direction) {
        this.direction=direction;
    }
}
