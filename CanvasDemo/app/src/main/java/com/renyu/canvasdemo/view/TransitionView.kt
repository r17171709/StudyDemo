package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class TransitionView : View {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(100f, 100f)
        canvas.drawCircle(100f, 100f, 100f, Paint())
    }
}