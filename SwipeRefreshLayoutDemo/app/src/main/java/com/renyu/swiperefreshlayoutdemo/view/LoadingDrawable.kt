package com.renyu.swiperefreshlayoutdemo.view

import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import kotlinx.coroutines.*

class LoadingDrawable(val sun: Drawable, val cloud: Drawable) : Drawable(), Animatable {
    private var scope: CoroutineScope? = null

    private var centerWidth = 0

    private var currentAngel = 0f

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)

        centerWidth = (bounds.right - bounds.left) * 2 / 5

        sun.setBounds(-centerWidth, -centerWidth, centerWidth, centerWidth)
        cloud.setBounds(-centerWidth, 0, centerWidth * 3 / 2, centerWidth * 3 / 2)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
        canvas.translate(centerWidth.toFloat(), centerWidth.toFloat())
        canvas.save()
        canvas.rotate(currentAngel)
        sun.draw(canvas)
        canvas.restore()
        cloud.draw(canvas)
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    override fun start() {
        startAnim()
    }

    override fun stop() {
        scope?.cancel()
        scope = null
    }

    override fun isRunning(): Boolean {
        scope?.let {
            return it.isActive
        } ?: return false
    }

    private fun startAnim() {
        if (scope == null) {
            scope = CoroutineScope(Job() + Dispatchers.Main)
            scope!!.launch {
                while (isActive) {
                    withContext(Dispatchers.IO) {
                        delay(20)
                    }
                    currentAngel += 4
                    if (currentAngel > 360) {
                        currentAngel = 0f
                    }
                    invalidateSelf()
                }
            }
        }
    }
}