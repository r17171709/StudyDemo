package com.renyu.constraintlayoutdemo.helper

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.hypot

/**
 * 提供了getViews()方法获取所引用的所有view
 * 提供了view的onLayout()/onMeasure()等流程方法执行前后的回调
 * 布局文件中，声明要包含的view的id
 */
class CircularRevealHelper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {
    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        val views = getViews(container)
        views.forEach {
            val anim = ViewAnimationUtils.createCircularReveal(
                it, it.width / 2, it.height / 2,
                0f,
                hypot(it.width.toDouble() / 2, it.height.toDouble() / 2).toFloat()
            )
            anim.duration = 1000
            anim.start()
        }
    }
}