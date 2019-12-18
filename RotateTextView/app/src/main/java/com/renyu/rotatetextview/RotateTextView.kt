package com.renyu.rotatetextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


/**
 * Created by Administrator on 2019/12/19.
 */
class RotateTextView : View {
    private val paint: Paint by lazy {
        val p = Paint()
        p.isAntiAlias = true
        p.textSize = 45f
        p.textAlign = Paint.Align.CENTER
        p
    }

    constructor(context: Context) : this(
        context,
        null,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        0
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(0f, measuredHeight.toFloat())
        canvas?.rotate(-90f)
        drawT(canvas)
        canvas?.translate(0f, -measuredHeight.toFloat())
    }

    private fun drawT(canvas: Canvas?) {
        val targetRect = Rect(0, 0, measuredHeight, measuredWidth)
        val fontMetrics = paint.fontMetricsInt
        val baseline =
            (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        canvas?.drawText("2019.01.01", targetRect.centerX().toFloat(), baseline.toFloat(), paint)
    }
}