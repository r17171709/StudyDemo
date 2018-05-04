package com.renyu.swiperefreshlayoutdemo.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

class CustomerSwipeRefreshLayout : SwipeRefreshLayout {

    // 设置可触发SwipeRefreshLayout判断滑动的全部View数组
    var swipeViews: Array<out View>? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context) : super(context)

    override fun canChildScrollUp(): Boolean {
        swipeViews?.forEach {
            // view可见并且view不能向下移动了，则直接返回false，将手势交给SwipeRefreshLayout进行下拉处理
            if (it.isShown && !it.canScrollVertically(-1)) {
                return false
            }
        }
        return true
    }
}