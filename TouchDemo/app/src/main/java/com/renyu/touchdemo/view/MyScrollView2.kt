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

class MyScrollView2 : LinearLayout {
    private var scroller: Scroller? = null

    private var viewHeight = 0
    private var realHeight = 0

    private var lastX = 0f
    private var lastY = 0f
    private var currentX = 0f
    private var currentY = 0f

    private var touchSlop = 0
    private var minVelocity = 0
    private var maxVelocity = 0

    private var tracker: VelocityTracker? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        scroller = Scroller(context)
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        minVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
        maxVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        realHeight = childCount * dp2px(100f)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        currentX = ev.x
        currentY = ev.y

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(currentX - lastX) < abs(currentY - lastY) && abs(currentY - lastY) > touchSlop) {
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }

        lastX = currentX
        lastY = currentY

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (tracker == null) {
            tracker = VelocityTracker.obtain()
        }
        tracker!!.addMovement(event)

        currentX = event.x
        currentY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!scroller!!.isFinished) {
                    scroller!!.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                scroller!!.startScroll(0, scroller!!.finalY, 0, (lastY - currentY).toInt())
                postInvalidate()
            }
            MotionEvent.ACTION_UP -> {
                tracker!!.computeCurrentVelocity(1000, maxVelocity.toFloat())
                if (abs(tracker!!.yVelocity) > minVelocity) {
//                    Log.d("TAGTAGTAG", scrollY.toString() + "  " + -tracker!!.yVelocity)
                    scroller!!.fling(
                        0,
                        scrollY,
                        0,
                        -tracker!!.yVelocity.toInt(),
                        0,
                        0,
                        0,
                        (realHeight - viewHeight)
                    )
                    postInvalidate()
                }
                if (tracker != null) {
                    tracker!!.clear()
                    tracker!!.recycle()
                    tracker = null
                }
            }
        }

        lastX = currentX
        lastY = currentY

        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller!!.computeScrollOffset()) {
            Log.d("TAGTAGTAG", scroller!!.currY.toString())
            scrollTo(0, scroller!!.currY)
            postInvalidate()
        }
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}