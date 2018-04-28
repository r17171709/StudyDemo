package com.example.tantan.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class AnimationView: RelativeLayout {
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        isChildrenDrawingOrderEnabled = true
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        isChildrenDrawingOrderEnabled = true
    }
    constructor(context: Context) : super(context) {
        isChildrenDrawingOrderEnabled = true
    }

    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        return childCount - 1 - i
    }
}