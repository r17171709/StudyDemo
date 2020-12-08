package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CanvasStackView : View {
    private val paint by lazy {
        Paint()
    }

    private val rect = Rect(0, 0, 400, 400)
    private val rectColor = Rect(300, 0, 600, 400)

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()

        canvas.clipRect(rect)
        canvas.drawColor(Color.BLUE)

        canvas.restore()

        paint.color = Color.BLACK
        canvas.drawRect(rectColor, paint)


    }
}