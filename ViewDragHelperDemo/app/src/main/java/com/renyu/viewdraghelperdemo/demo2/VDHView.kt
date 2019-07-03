package com.renyu.viewdraghelperdemo.demo2

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
                return child == tv1 || child == tv2 || child == tv3
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                if (lastReleasePosition == -1) {
                    vdh!!.settleCapturedViewAt(sourcePoints[selectPosition].x, sourcePoints[selectPosition].y)
                    invalidate()
                } else {
                    vdh!!.settleCapturedViewAt(sourcePoints[lastReleasePosition].x, sourcePoints[lastReleasePosition].y)
                    invalidate()
                }
                selectPosition = -1
                lastReleasePosition = -1
            }

            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                switchPositionIfNeeded(changedView)
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }
        })
    }

    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null

    private var vdh: ViewDragHelper? = null

    private val sourcePoints: ArrayList<Point> by lazy {
        ArrayList<Point>()
    }

    private var downX = 0
    private var downY = 0
    private var downTime = 0L

    // 当前选中的位置
    private var selectPosition = -1
    // 最终释放的位置
    private var lastReleasePosition = -1

    // 完成位置处理
    private var isFinishInitData = false

    // 切换是否结束
    private var isEndChange = true

    override fun onFinishInflate() {
        super.onFinishInflate()

        for (i in 0 until childCount) {
            sourcePoints.add(Point())
        }
        tv1 = getChildAt(0) as TextView
        tv2 = getChildAt(1) as TextView
        tv3 = getChildAt(2) as TextView
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        // 在调用bringChildToFront之后会进行重绘
        if (isFinishInitData) {
            return
        }

        for (i in 0 until sourcePoints.size) {
            sourcePoints[i].x = getChildAt(i).left
            sourcePoints[i].y = getChildAt(i).top
        }
        isFinishInitData = true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            downX = ev.x.toInt()
            downY = ev.y.toInt()
            downTime = System.currentTimeMillis()
            bringToFrontWhenTouchDown(downX, downY)
        }
        return super.dispatchTouchEvent(ev)
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

    private fun bringToFrontWhenTouchDown(downX: Int, downY: Int) {
        val view = getViewByDownPoint(downX, downY)
        if (view != null && indexOfChild(view) != childCount - 1) {
            // 重排视图并将当前选中的视图置于最上层。请注意LinearLayout与FrameLayout上该方法的区别
            bringChildToFront(view)
        }
    }

    private fun getViewByDownPoint(downX: Int, downY: Int): View? {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view.left < downX && view.right > downX && view.top < downY && view.bottom > downY) {
                return view
            }
        }
        return null
    }

    private fun switchPositionIfNeeded(changedView: View) {
        if (!isEndChange) {
            return
        }
        val centerX = changedView.left + changedView.width / 2
        val centerY = changedView.top + changedView.height / 2
        when (changedView) {
            tv1 -> {
                Log.d("DEMO", "当前0")
                selectPosition = 0
            }
            tv2 -> {
                Log.d("DEMO", "当前1")
                selectPosition = 1
            }
            tv3 -> {
                Log.d("DEMO", "当前2")
                selectPosition = 2
            }
        }
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view == changedView) {
                continue
            }
            if (view.left < centerX && view.right > centerX && view.top < centerY && view.bottom > centerY) {
                when (view) {
                    tv1 -> {
                        Log.d("DEMO", "准备替换0")
                        lastReleasePosition = 0
                    }
                    tv2 -> {
                        Log.d("DEMO", "准备替换1")
                        lastReleasePosition = 1
                    }
                    tv3 -> {
                        Log.d("DEMO", "准备替换2")
                        lastReleasePosition = 2
                    }
                }
                animTo(view, sourcePoints[selectPosition].x - view.left, sourcePoints[selectPosition].y - view.top)
                isEndChange = true
                break
            }
        }
    }

    private fun animTo(view: View, moveDstX: Int, moveDstY: Int) {
        view.offsetLeftAndRight(moveDstX)
        view.offsetTopAndBottom(moveDstY)
    }
}