package com.renyu.touchdemo.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.widget.LinearLayout
import android.widget.Scroller
import kotlin.math.abs

class MyScrollView : LinearLayout {
    private var slop = 0
    private var maxFlingVelocity = 0
    private var minFlingVelocity = 0

    private var lastX = 0f
    private var lastY = 0f

    private var viewHeight = 0
    private var realHeight = 0

    private lateinit var scroller: Scroller
    private var velocityTracker: VelocityTracker? = null
    private var fling = false
    private var flingY = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        slop = ViewConfiguration.get(context).scaledTouchSlop
        maxFlingVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
        minFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

        scroller = Scroller(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        realHeight = childCount * dp2px(100f)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val currentX = ev.x
        val currentY = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> return super.onInterceptTouchEvent(ev)
            MotionEvent.ACTION_MOVE -> {
                if (currentY - lastY > slop && currentY > currentX) {
                    return true
                }
            }
        }
        lastX = currentX
        lastY = currentY
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        fling = false
        flingY = 0
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(event)

        val currentX = event.x
        val currentY = event.y
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                scrollBy(0, (lastY - currentY).toInt())
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker!!.computeCurrentVelocity(1000, maxFlingVelocity.toFloat())
                val yVelocity = velocityTracker!!.yVelocity
                if (abs(yVelocity) > minFlingVelocity) {
                    fling = true
                    scroller.fling(
                        0,
                        0,
                        0,
                        yVelocity.toInt(),
                        Int.MIN_VALUE,
                        Int.MAX_VALUE,
                        Int.MIN_VALUE,
                        Int.MAX_VALUE
                    )
                    invalidate()
                }
//                velocityTracker!!.recycle()
            }
        }
        lastX = currentX
        lastY = currentY
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        if (y < 0) {
            super.scrollTo(x, 0)
            return
        }
        if ((realHeight - viewHeight) < y) {
            super.scrollTo(x, realHeight - viewHeight)
            return
        }
        super.scrollTo(x, y)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset() && fling) {
            scrollBy(0, -(scroller.currY - flingY))
            flingY = scroller.currY
            postInvalidate()
        } else {
            fling = false
            flingY = 0
        }
        Log.d("TAGTAGTAG", scroller.currY.toString())
    }

    override fun onDetachedFromWindow() {
        velocityTracker!!.recycle()
        super.onDetachedFromWindow()
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}