package com.renyu.coordinatorlaoyutdemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SampleHeaderBehavior : CoordinatorLayout.Behavior<TextView> {
    // 上一个可见的Item位置
    private var lastPosition = -1

    // 向上滑动是否到达边界
    private var upReach = false
    // 向下滑动
    private var downReach = false

    constructor()

    constructor(context: Context, attributeSet: AttributeSet)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: TextView,
        ev: MotionEvent
    ): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                upReach = false
                downReach = false
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (target is RecyclerView) {
            // RV中第一个可见的Item
            val firstPosition = (target.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstPosition == 0 && lastPosition > firstPosition) {
                upReach = true
            }
            // 仅第一个可见Item进行判断，其他交由父控件处理
            if (canScroll(child, dy) && firstPosition == 0) {
                var finalY = child.translationY - dy
                if (finalY < -child.height) {
                    finalY = -child.height.toFloat()
                } else if (finalY > 0) {
                    finalY = 0f
                }
                child.translationY = finalY
                consumed[1] = dy
            }
            lastPosition = firstPosition
        }
    }

    private fun canScroll(child: TextView, dy: Int): Boolean {
        // 当TextView滑出屏幕外之后，事件不再处理
        if (!upReach && child.translationY == -child.height.toFloat() && dy > 0) {
            return false
        }
        if (upReach) {
            return false
        }
        return true
    }
}