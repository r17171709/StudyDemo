package com.renyu.coordinatorlaoyutdemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewBehavior : CoordinatorLayout.Behavior<RecyclerView> {

    constructor()

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: RecyclerView,
        dependency: View
    ): Boolean {
        var y = dependency.translationY + dependency.height
        if (y < 0) {
            y = 0f
        }
        child.y = y
        return true
    }
}