package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.tan

/**
 * 参考文章：https://blog.csdn.net/iasxk/article/details/17411381
 */
class CircleView : View {
    private val paint by lazy { Paint() }
    private val paint2 by lazy { Paint() }
    private val paint3 by lazy { Paint() }

    // 转圈刷新进度
    var rotatePercent = 0f

    // X消失刷新进度
    var xPercent = 0f

    // 跳跃的点刷新进度
    var pointPercent = 0f

    // 伸展的√刷新进度
    var gouPercent = 0f

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f

        paint2.isAntiAlias = true
        paint2.color = Color.RED
        paint2.style = Paint.Style.FILL_AND_STROKE
        paint2.strokeWidth = 8f

        paint3.isAntiAlias = true
        paint3.color = Color.BLUE
        paint3.style = Paint.Style.FILL_AND_STROKE
        paint3.strokeWidth = 8f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制圆弧
        val position = floatArrayOf(0f, 0.75f)
        val color = intArrayOf(Color.GREEN, Color.WHITE)
        val shader = SweepGradient(
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2, color, position
        )
        paint.shader = shader
        val radius = if (measuredWidth > measuredHeight) measuredHeight / 2 else measuredWidth / 2
//        canvas.rotate(
//            -90f,
//            measuredWidth.toFloat() / 2,
//            measuredHeight.toFloat() / 2
//        )
        canvas.save()
        canvas.translate(measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2)
        canvas.rotate(-90f + 360 * rotatePercent)
        canvas.translate(-measuredWidth.toFloat() / 2, -measuredHeight.toFloat() / 2)
        canvas.drawCircle(
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2,
            radius.toFloat(),
            paint
        )
        canvas.restore()

        // 绘制X
        val rightLength = measuredWidth.toFloat() / 4 - measuredWidth.toFloat() * (xPercent / 2) / 4
        val rightXX = measuredWidth.toFloat() / 2 + rightLength
        val rightTopXY =
            measuredHeight.toFloat() / 2 - rightLength
        val rightBottomXY =
            measuredHeight.toFloat() / 2 + rightLength

        val leftLength = measuredWidth.toFloat() / 4 + measuredWidth.toFloat() * (xPercent / 2) / 4
        val leftXX = leftLength
        val leftTopXY = leftLength
        val leftBottomXY = measuredHeight.toFloat() - leftLength

        val path = Path()
        path.moveTo(leftXX, leftTopXY)
        path.lineTo(rightXX, rightBottomXY)
        path.moveTo(leftXX, leftBottomXY)
        path.lineTo(rightXX, rightTopXY)
        canvas.drawPath(path, paint2)

        // 绘制点
        val topPoint =
            measuredHeight.toFloat() / 2 - pointPercent * measuredHeight.toFloat() / 4
        canvas.drawCircle(
            measuredWidth.toFloat() / 2,
            topPoint,
            if (topPoint == measuredHeight.toFloat() / 2) 0f else 8f,
            paint3
        )

        // 绘制√
        val gouRightLength = (gouPercent / 2) * measuredWidth.toFloat() / 4
        val rightgouX = measuredWidth.toFloat() / 2 + gouRightLength
        val rightgouY =
            measuredHeight.toFloat() / 2 - gouRightLength / tan(Math.toRadians(30.toDouble()))
        canvas.drawLine(
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2,
            rightgouX,
            rightgouY.toFloat(),
            paint2
        )

        val leftgouX = measuredWidth.toFloat() / 2 - gouRightLength
        val leftgouY =
            measuredHeight.toFloat() / 2 - gouRightLength / tan(Math.toRadians(60.toDouble()))
        canvas.drawLine(
            measuredWidth.toFloat() / 2,
            measuredHeight.toFloat() / 2,
            leftgouX,
            leftgouY.toFloat(),
            paint2
        )
    }
}