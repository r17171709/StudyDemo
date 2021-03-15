package com.renyu.recyclerviewdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SlideRecyclerView : RecyclerView {
    private var velocityTracker: VelocityTracker? = null
    private var viewConfiguration: ViewConfiguration? = null
    private var mScroller: Scroller? = null

    private var lastX = 0f
    private var downX = 0f
    private var downY = 0f

    // 滑动菜单宽度
    private var hideWidth = 0f

    // 是否正在滑动
    private var isMoving = false

    // 当前操作的Item
    private var mCurrentItem: View? = null
    // 之前操作的Item，考虑到scroller的操作延迟
    private var lastScrollItem: View? = null

    @JvmOverloads
    constructor(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        if (viewConfiguration == null) {
            viewConfiguration = ViewConfiguration.get(context)
        }
        mScroller = Scroller(context)
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(e)
        lastX = e!!.x
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }
                downX = e.x
                downY = e.y
                mCurrentItem = findChildViewUnder(e.x, e.y)
                if (lastScrollItem != null && mCurrentItem != lastScrollItem && lastScrollItem!!.scrollX > 0) {
                    mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, -lastScrollItem!!.scrollX, 0, abs(lastScrollItem!!.scrollX))
                    // 不要忘记刷新
                    invalidate()
                }
                hideWidth = if (mCurrentItem != null) {
                    300f
                } else {
                    -1f
                }

            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.computeCurrentVelocity(1000)
                val velocityX = abs(velocityTracker!!.xVelocity)
                val velocityY = abs(velocityTracker!!.yVelocity)
                // 从触摸到移动到当前位置这段移动距离
                val moveX = e.x - downX
                val moveY = e.y - downY
                val isMove = ((velocityX > velocityY && velocityX > viewConfiguration!!.scaledMinimumFlingVelocity) ||
                        (moveX > moveY && moveX > viewConfiguration!!.scaledTouchSlop)) && hideWidth > 0 && scrollState == SCROLL_STATE_IDLE
                if (isMove) {
                    isMoving = true
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (velocityTracker != null) {
                    velocityTracker!!.clear()
                    velocityTracker!!.recycle()
                    velocityTracker = null
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        velocityTracker?.addMovement(e)
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                if (isMoving) {
                    val dx = lastX - e.x
                    if (mCurrentItem != null && mCurrentItem!!.scrollX + dx >= 0 && mCurrentItem!!.scrollX + dx <= hideWidth) {
                        mCurrentItem!!.scrollBy(dx.toInt(), 0)
                    }
                    lastX = e.x
                    return true
                } else {
                    velocityTracker?.computeCurrentVelocity(1000)
                    val velocityX = abs(velocityTracker!!.xVelocity)
                    val velocityY = abs(velocityTracker!!.yVelocity)
                    // 从触摸到移动到当前位置这段移动距离
                    val moveX = abs(e.x - downX)
                    val moveY = abs(e.y - downY)
                    val isMove = ((velocityX > velocityY && velocityX > viewConfiguration!!.scaledMinimumFlingVelocity) ||
                            (moveX > moveY && moveX > viewConfiguration!!.scaledTouchSlop)) && hideWidth > 0 && scrollState == SCROLL_STATE_IDLE
                    if (isMove) {
                        val dx = lastX - e.x
                        if (mCurrentItem!!.scrollX + dx in 0.0..hideWidth.toDouble()) {
                            mCurrentItem!!.scrollBy(dx.toInt(), 0)
                        }
                        lastX = e.x
                        isMoving = true
                        return true
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isMoving) {
                    if (!mScroller!!.isFinished) {
                        mScroller!!.abortAnimation()
                        lastScrollItem!!.scrollTo(mScroller!!.finalX,0);
                    }
                    isMoving = false
                    velocityTracker?.computeCurrentVelocity(1000)
                    lastScrollItem = mCurrentItem
                    // 方向
                    if (velocityTracker!!.xVelocity >= viewConfiguration!!.scaledMinimumFlingVelocity) {
                        mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, -lastScrollItem!!.scrollX, 0, abs(lastScrollItem!!.scrollX))
                    } else if (velocityTracker!!.xVelocity <= -viewConfiguration!!.scaledMinimumFlingVelocity) {
                        mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, hideWidth.toInt() - lastScrollItem!!.scrollX, 0, abs(hideWidth.toInt() - lastScrollItem!!.scrollX))
                    } else if (lastScrollItem!!.scrollX > hideWidth / 2) {
                        mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, hideWidth.toInt() - lastScrollItem!!.scrollX, 0, abs(hideWidth.toInt() - lastScrollItem!!.scrollX))
                    } else {
                        mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, -lastScrollItem!!.scrollX, 0, abs(lastScrollItem!!.scrollX))
                    }
                    // 不要忘记刷新
                    invalidate()
                } else if (lastScrollItem != null && lastScrollItem!!.scrollX > 0) {
                    mScroller!!.startScroll(lastScrollItem!!.scrollX, 0, -lastScrollItem!!.scrollX, 0, abs(lastScrollItem!!.scrollX))
                    // 不要忘记刷新
                    invalidate()
                }

                if (velocityTracker != null) {
                    velocityTracker!!.clear()
                    velocityTracker!!.recycle()
                    velocityTracker = null
                }
            }
        }
        return super.onTouchEvent(e)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller!!.computeScrollOffset()) {
            if (isInWindow(lastScrollItem!!)) {
                lastScrollItem!!.scrollTo(mScroller!!.currX, 0)
                invalidate()
            } else {
                mScroller!!.abortAnimation()
                lastScrollItem!!.scrollTo(mScroller!!.finalX, 0)
                // 此处就不需要再次刷新了，已经直接到最终位置了
            }
        }
    }

    private fun isInWindow(view: View): Boolean {
        if (layoutManager is LinearLayoutManager) {
            val firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            val position = (layoutManager as LinearLayoutManager).getPosition(view)
            return position in firstVisibleItemPosition..lastVisibleItemPosition
        }
        return false
    }
}