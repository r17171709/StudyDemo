package com.example.rg.imoocrefreshdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.rg.imoocrefreshdemo.R;

/**
 * Created by RG on 2015/3/23.
 */
public class CustomView extends View {
    private PorterDuffXfermode porterDuffXfermode;// Xfermode
    private Paint paint;// 画笔
    private Bitmap bitmap;// 源图片
    private int width, height;// 控件宽高
    private Path path;// 画贝塞尔曲线需要用到
    private Canvas mCanvas;// 在该画布上绘制目标图片
    private Bitmap bg;// 目标图片

    private float controlX, controlY;// 贝塞尔曲线控制点，使用三阶贝塞尔曲线曲线，需要两个控制点，两个控制点都在该变量基础上生成
    private float waveY;// 上升的高度

    private boolean isIncrease;// 用于控制控制点水平移动

    private boolean isReflesh = true;// 是否刷新并产生填充效果，默认为true

    /**
     * @return 是否刷新
     */
    public boolean isReflesh() {
        return isReflesh;
    }

    /**
     * 提供接口设置刷新
     *
     * @param isReflesh
     */
    public void setReflesh(boolean isReflesh) {
        this.isReflesh = isReflesh;
        //重绘
        postInvalidate();
    }

    /**
     * @param context
     */
    public CustomView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化变量
     */
    private void init() {
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ffc9394a"));
        // 获得资源文件
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.imooc_bg);
        // 设置宽高为图片的宽高
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        // 初始状态值
        waveY = 7 / 8F * height;
        controlY = 17 / 16F * height;

        // 初始化Xfermode
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 初始化path
        path = new Path();
        // 初始化画布
        mCanvas = new Canvas();
        // 创建bitmap
        bg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 将新建的bitmap注入画布
        mCanvas.setBitmap(bg);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画目标图，存在bg上
        drawTargetBitmap();
        // 将目标图绘制在当前画布上，起点为左边距，上边距的交点
        canvas.drawBitmap(bg, getPaddingLeft(), getPaddingTop(), null);
        if (isReflesh) {
            // 重绘，使用boolean变量isReflesh进行控制，并对外提供访问的接口,默认为true且刷新
            invalidate();
        }
    }

    private void drawTargetBitmap() {
        // 重置path
        path.reset();
        // 擦除像素
        bg.eraseColor(Color.parseColor("#00ffffff"));

        // 当控制点的x坐标大于或等于终点x坐标时更改标识值
        if (controlX >= width + 1 / 2 * width) {
            isIncrease = false;
        }
        // 当控制点的x坐标小于或等于起点x坐标时更改标识值
        else if (controlX <= -1 / 2 * width) {
            isIncrease = true;
        }

        // 根据标识值判断当前的控制点x坐标是该加还是减
        controlX = isIncrease ? controlX + 10 : controlX - 10;
        if (controlY >= 0) {
            // 波浪上移
            controlY -= 1;
            waveY -= 1;
        } else {
            // 超出则重置位置
            waveY = 7 / 8F * height;
            controlY = 17 / 16F * height;
        }

        // 贝塞尔曲线的生成
        path.moveTo(0, waveY);
        // 两个控制点通过controlX，controlY生成
        path.cubicTo(controlX / 2, waveY - (controlY - waveY),
                (controlX + width) / 2, controlY, width, waveY);
        // 与下下边界闭合
        path.lineTo(width, height);
        path.lineTo(0, height);
        // 进行闭合
        path.close();

        // 以上画贝塞尔曲线代码参考自爱哥博客
        // http://blog.csdn.net/aigestudio/article/details/41960507

        mCanvas.drawBitmap(bitmap, 0, 0, paint);// 画慕课网logo
        paint.setXfermode(porterDuffXfermode);// 设置Xfermode
        mCanvas.drawPath(path, paint);// 画三阶贝塞尔曲线
        paint.setXfermode(null);// 重置Xfermode
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得宽高测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 保存测量结果
        int width, height;

        if (widthMode == MeasureSpec.EXACTLY) {
            // 宽度加左右内边距
            width = widthSize + getPaddingLeft() + getPaddingRight();
        } else {
            // 宽度加左右内边距
            width = this.width + getPaddingLeft() + getPaddingRight();
            ;
            if (widthMode == MeasureSpec.AT_MOST) {
                // 取小的那个
                width = Math.min(width, widthSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // 高度加左右内边距
            height = heightSize + getPaddingTop() + getPaddingBottom();
        } else {
            // 高度加左右内边距
            height = this.height + getPaddingTop() + getPaddingBottom();
            ;
            if (heightMode == MeasureSpec.AT_MOST) {
                // 取小的那个
                height = Math.min(height, heightSize);
            }

        }
        // 设置高度宽度为logo宽度和高度,实际开发中应该判断MeasureSpec的模式，进行对应的逻辑处理,这里做了简单的判断测量
        setMeasuredDimension(width, height);

    }

}