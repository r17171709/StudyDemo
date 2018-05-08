package com.renyu.swipetoloadlayoutdemo.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger
import com.blankj.utilcode.util.SizeUtils
import com.renyu.swipetoloadlayoutdemo.R

class MTRefreshHeaderView : LinearLayout, SwipeTrigger, SwipeRefreshTrigger {

    var iv_mt: ImageView? = null

    val animationDrawable: AnimationDrawable by lazy {
        iv_mt?.background as AnimationDrawable
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        iv_mt = findViewById(R.id.iv_mt)
    }

    override fun onReset() {

    }

    override fun onComplete() {
        animationDrawable.stop()
    }

    override fun onRelease() {

    }

    override fun onMove(p0: Int, p1: Boolean, p2: Boolean) {
        val percent = if (p0 * 1.0f / SizeUtils.dp2px(44f) > 1) 1f else p0 * 1.0f / SizeUtils.dp2px(44f)

        iv_mt?.scaleY = (0.3f + 0.7 * percent).toFloat()
        iv_mt?.scaleX = (0.3f + 0.7 * percent).toFloat()
    }

    override fun onPrepare() {
        if (!animationDrawable.isRunning) {
            animationDrawable.start()
        }

        iv_mt?.scaleY = 0.3f
        iv_mt?.scaleX = 0.3f
    }

    override fun onRefresh() {
        if (!animationDrawable.isRunning) {
            animationDrawable.start()
        }

        iv_mt?.scaleY = 1f
        iv_mt?.scaleX = 1f
    }
}