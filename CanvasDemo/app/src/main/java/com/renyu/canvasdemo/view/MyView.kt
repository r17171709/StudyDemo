package com.renyu.canvasdemo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.renyu.canvasdemo.R

class MyView : View {
    private val paint by lazy {
        Paint()
    }
    private val shader by lazy {
        LinearGradient(
            marginStart,
            height.toFloat() / 2,
            width - marginEnd,
            height.toFloat() / 2,
            intArrayOf(
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW
            ),
            null,
            Shader.TileMode.CLAMP
        )
    }

    private val paint2 by lazy {
        Paint()
    }

    private var color = Color.TRANSPARENT
    private var strokeWidth = 0f
    private var marginStart = 0f
    private var marginEnd = 0f

    private val rect = Rect(0, 100, 300, 200)

    private val string = "你好Canvas"

    private val fontMetrics by lazy {
        Paint.FontMetrics()
    }

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyViewAttrs)
        color = typedArray.getColor(R.styleable.MyViewAttrs_color, Color.TRANSPARENT)
        strokeWidth =
            typedArray.getDimensionPixelSize(R.styleable.MyViewAttrs_strokeWidth, 0).toFloat()
        marginStart =
            typedArray.getDimensionPixelSize(R.styleable.MyViewAttrs_marginStart, 0).toFloat()
        marginEnd = typedArray.getDimensionPixelSize(R.styleable.MyViewAttrs_marginEnd, 0).toFloat()
        typedArray.recycle()
        paint.isAntiAlias = true

        paint2.isAntiAlias = true
        paint2.color = Color.BLACK
        paint2.textSize = 25f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = color
        paint.shader = shader
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = strokeWidth
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        canvas.drawLine(
            marginStart,
            height.toFloat() / 2,
            width - marginEnd,
            height.toFloat() / 2,
            paint
        )

        canvas.drawRect(rect, paint)

        val tmp = Rect()
        paint.getTextBounds(string, 0, string.length, tmp)
        paint.getFontMetrics(fontMetrics)
        val distance = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        val offsetY = rect.centerY().toFloat() + distance
        val offsetX = rect.width().toFloat() / 2  - tmp.width()
        canvas.drawText(string, offsetX, offsetY, paint2)
    }
}