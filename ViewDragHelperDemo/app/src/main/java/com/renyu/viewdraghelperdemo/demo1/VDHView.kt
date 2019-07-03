package com.renyu.viewdraghelperdemo.demo1

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.customview.widget.ViewDragHelper

class VDHView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        vdh = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child == tv1 || child == tv2
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                if (releasedChild == tv2) {
                    vdh!!.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y)
                    invalidate()
                }
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                vdh!!.captureChildView(tv3!!, pointerId)
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
            }
        })
        vdh!!.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null

    private var vdh: ViewDragHelper? = null

    private var mAutoBackOriginPos: Point = Point()

    override fun onFinishInflate() {
        super.onFinishInflate()

        tv1 = getChildAt(0) as TextView
        tv2 = getChildAt(1) as TextView
        tv3 = getChildAt(2) as TextView
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        mAutoBackOriginPos.x = tv2!!.left
        mAutoBackOriginPos.y = tv2!!.top
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val shouldIntercept = vdh!!.shouldInterceptTouchEvent(ev!!)
        Log.d("DEMO", shouldIntercept.toString())
        return shouldIntercept
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        vdh!!.processTouchEvent(event!!)
        return true
    }

    override fun computeScroll() {
        if (vdh!!.continueSettling(true)) {
            invalidate()
        }
    }
}