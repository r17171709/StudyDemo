package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CanvasTransformationView : View {
    private val paint by lazy {
        Paint()
    }

    private val rect = Rect(0, 0, 100, 100)

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

        paint.color = Color.BLUE
        canvas.drawRect(rect, paint)

        paint.color = Color.RED
        // 移动到100，100的位置
        canvas.translate(100f, 100f)
        canvas.drawRect(rect, paint)

        paint.color = Color.YELLOW
        canvas.translate(100f, 100f)
        // 在200，200的位置上进行缩放
        canvas.scale(0.5f, 0.5f)
        canvas.drawRect(rect, paint)

        paint.color = Color.GRAY
        // 因为已经进行了画布缩放，所以实际上无法到300，300的位置
        canvas.translate(100f, 100f)
        canvas.rotate(30f)
        canvas.drawRect(rect, paint)

        paint.color = Color.GREEN
        // 因为已经进行了画布旋转，所以会在新位置基础上移动
        canvas.translate(100f, 100f)
        canvas.drawRect(rect, paint)
    }
}