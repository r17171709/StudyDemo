package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RoundImageView : AppCompatImageView {
    private val paint by lazy { Paint() }
    private val rect by lazy { RectF() }
    private val path by lazy { Path() }
    private val mRadii by lazy { floatArrayOf(25f, 25f, 25f, 25f, 25f, 25f, 25f, 25f) }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        // 重点
        canvas.saveLayer(rect, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)

        paint.reset()
        path.reset()

        paint.isAntiAlias = true
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

        path.addRoundRect(rect, mRadii, Path.Direction.CCW)
        canvas.drawPath(path, paint)

        paint.xfermode = null
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
        path.reset()
        path.addRoundRect(rect, mRadii, Path.Direction.CCW)
        canvas.drawPath(path, paint)
    }
}