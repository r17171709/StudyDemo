package com.renyu.constraintlayoutdemo.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class KeyTriggerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    fun startAnim() {
        this.setBackgroundColor(Color.RED)
    }

    fun endAnim() {
        this.setBackgroundColor(Color.GREEN)
    }
}