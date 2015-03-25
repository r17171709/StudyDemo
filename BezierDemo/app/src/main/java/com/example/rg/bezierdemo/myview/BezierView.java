package com.example.rg.bezierdemo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.rg.bezierdemo.R;

/**
 * Created by RG on 2015/3/25.
 */
public class BezierView extends FrameLayout {

    Paint paint=null;
    Path path=null;

    //被拖拽的view的位置
    float startX=0;
    float startY=0;
    //手势拖拽view之后的位置
    float endX=0;
    float endY=0;
    //贝塞尔曲线中心控制点
    float anchorX=0;
    float anchorY=0;

    int heightOffset=0;
    int widthOffset=0;

    ImageView tipImageView=null;
    ImageView expImageView=null;

    //半径
    float radiu=20;

    //是否正在拖动
    boolean isTouch=false;
    //播放动画
    boolean isAnimate=false;

    //被拖动的imageview的坐标
    int orginalX=0;
    int orginalY=0;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setWillNotDraw(false);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.bezier_value, defStyleAttr, 0);
        heightOffset=a.getDimensionPixelOffset(R.styleable.bezier_value_heightOffset, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        widthOffset=a.getDimensionPixelOffset(R.styleable.bezier_value_widthOffset, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        a.recycle();

        startX=widthOffset;
        startY=heightOffset;
        endX=widthOffset;
        endY=heightOffset;
        anchorX=(endX+startX)/2;
        anchorY=(endY+startY)/2;

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        path=new Path();

        LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //添加被拖拽跟爆炸imageview
        tipImageView=new ImageView(context);
        tipImageView.setImageResource(R.mipmap.skin_tips_newmessage_ninetynine);
        tipImageView.setVisibility(View.VISIBLE);
        addView(tipImageView, params);
        expImageView=new ImageView(context);
        expImageView.setImageResource(R.drawable.tip_anim);
        expImageView.setVisibility(View.INVISIBLE);
        addView(expImageView, params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isTouch||isAnimate) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        }
        else {
            calculate();
            canvas.drawPath(path, paint);
            canvas.drawCircle(startX, startY, radiu, paint);
            canvas.drawCircle(endX, endY, radiu, paint);
        }
    }

    private void calculate() {
        //拖动点跟之前点距离
        float distance= (int) Math.sqrt(Math.pow(endX-startX, 2)+Math.pow(endY-startY, 2));
        //决定了爆炸的距离
        radiu=-distance/20+25;
        //这边的一系列值都是凭个人喜好随意设置的
        //半径过小，松开爆炸
        if (radiu<9) {
            isAnimate=true;

            tipImageView.setVisibility(View.INVISIBLE);

            expImageView.setVisibility(View.VISIBLE);
            expImageView.setX(endX-widthOffset);
            expImageView.setY(endY-heightOffset);
            ((AnimationDrawable) expImageView.getDrawable()).stop();
            ((AnimationDrawable) expImageView.getDrawable()).start();

            return;
        }

        float tempX= (float) (radiu*Math.sin(Math.atan((endY - startY) / (endX - startX))));
        float tempY= (float) (radiu*Math.cos(Math.atan((endY - startY) / (endX - startX))));

        float x1= startX-tempX;
        float y1= startY+tempY;

        float x2= endX-tempX;
        float y2= endY+tempY;

        float x3= endX+tempX;
        float y3= endY-tempY;

        float x4= startX+tempX;
        float y4= startY-tempY;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);

    }

    /**
     * 此处传入原页面上的imageview的坐标，使得拖动部分初始化直接盖在之前Imageview的位置上
     * @param orginalX_
     * @param orginalY_
     */
    public void touchDown(int orginalX_, int orginalY_) {

        isAnimate=false;

        tipImageView.setVisibility(View.VISIBLE);
        expImageView.setVisibility(View.INVISIBLE);

        orginalX=orginalX_;
        orginalY=orginalY_;
        //定义好起始位置
        startX=orginalX;
        startY=orginalY;
        endX=orginalX;
        endY=orginalY;
        anchorX=(endX+startX)/2;
        anchorY=(endY+startY)/2;

        //将拖动视图设置在起始位置上
        tipImageView.setX(startX-widthOffset/2);
        tipImageView.setY(startY-heightOffset/2);

        expImageView.setX(endX);
        expImageView.setY(endY);

        postInvalidate();
    }

    /**
     * 此处传入原页面上的imageview的移动坐标
     * @param offsetX
     * @param offsetY
     */
    public void touchMove(int offsetX, int offsetY) {
        isTouch=true;

        endX= orginalX+offsetX;
        endY= orginalY+offsetY;
        anchorX=(startX+endX)/2;
        anchorY=(startY+endY)/2;

        tipImageView.setX(endX-widthOffset/2);
        tipImageView.setY(endY-heightOffset/2);

        postInvalidate();
    }

    /**
     * 复原操作
     */
    public void touchUp() {
        isTouch=false;

        tipImageView.setX(startX-widthOffset/2);
        tipImageView.setY(startY-heightOffset/2);

        postInvalidate();
    }

}
