package com.renyu.coordinatorlaoyutdemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

class SampleTitleBehavior : CoordinatorLayout.Behavior<View> {
    // 顶部TextView与Rv重叠时移动的距离
    private var deltaY = 0

    // 注意不要忘了重写两个参数的构造函数，否则无法在xml文件中使用该Behavior
    constructor() {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    /**
     * @param parent CoordinatorLayout
     * @param child 使用该Behavior的View
     * @param dependency 要监听的View。这里要监听RecyclerView
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (deltaY == 0) {
            deltaY = (dependency.y - child.height).toInt()
        }
        var dy = (dependency.y - child.height).toInt()
        dy = if (dy < 0) 0 else dy
        // 计算TextView移动的距离
        val finalY = -dy * 1.0f / deltaY * child.height
        // 负数向上，正数向下
        child.translationY = finalY
        child.alpha = 1 - dy * 1.0f / deltaY
        return true
    }
}