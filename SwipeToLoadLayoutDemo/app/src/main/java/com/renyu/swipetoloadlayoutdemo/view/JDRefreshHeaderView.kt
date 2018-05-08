package com.renyu.swipetoloadlayoutdemo.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger
import com.blankj.utilcode.util.SizeUtils
import com.renyu.swipetoloadlayoutdemo.R

class JDRefreshHeaderView : RelativeLayout, SwipeTrigger, SwipeRefreshTrigger {

    var iv_jd_person: ImageView? = null
    var iv_jd_speed: ImageView? = null
    var iv_jd_box: ImageView? = null
    var tv_jd: TextView? = null

    val percentAnimation: AnimationDrawable by lazy {
        iv_jd_person?.background as AnimationDrawable
    }
    val twinkle: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.twinkle)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        iv_jd_person = findViewById(R.id.iv_jd_person)
        iv_jd_speed = findViewById(R.id.iv_jd_speed)
        iv_jd_box = findViewById(R.id.iv_jd_box)
        tv_jd = findViewById(R.id.tv_jd)
    }

    override fun onReset() {
        percentAnimation.stop()
        iv_jd_person?.scaleX = 0.4f
        iv_jd_person?.scaleY = 0.4f
        iv_jd_person?.alpha = 0.3f
        iv_jd_box?.scaleX = 0.5f
        iv_jd_box?.scaleY = 0.5f
        iv_jd_box?.alpha = 0.3f
        iv_jd_box?.visibility = View.VISIBLE
        iv_jd_speed?.visibility = View.GONE
        iv_jd_speed?.clearAnimation()
        tv_jd?.text = "下拉刷新..."
    }

    override fun onComplete() {
    }

    override fun onRelease() {
    }

    override fun onMove(p0: Int, p1: Boolean, p2: Boolean) {

        iv_jd_person?.pivotX = 1f
        iv_jd_person?.pivotY = 0.5f

        if (p0 >= SizeUtils.dp2px(80f)) {
            iv_jd_person?.scaleX = 1f
            iv_jd_person?.scaleY = 1f
            iv_jd_person?.alpha = 1f
            iv_jd_box?.scaleX = 1f
            iv_jd_box?.scaleY = 1f
            iv_jd_box?.alpha = 1f
            tv_jd?.text = "松开刷新..."
        }
        else if (p0 < SizeUtils.dp2px(80f) && p0 > 0) {
            val percent = p0 * 1.0f / SizeUtils.dp2px(80f)

            iv_jd_person?.scaleX = 0.4f + 0.6f*percent
            iv_jd_person?.scaleY = 0.4f + 0.6f*percent
            iv_jd_person?.alpha = 0.3f + 0.7f*percent
            iv_jd_box?.scaleX = 0.5f + 0.5f*percent
            iv_jd_box?.scaleY = 0.5f + 0.5f*percent
            iv_jd_box?.alpha = 0.3f + 0.7f*percent
            tv_jd?.text = "下拉刷新..."
        }
        else {
            iv_jd_person?.scaleX = 0.4f
            iv_jd_person?.scaleY = 0.4f
            iv_jd_person?.alpha = 0.3f
            iv_jd_box?.scaleX = 0.5f
            iv_jd_box?.scaleY = 0.5f
            iv_jd_box?.alpha = 0.3f
            tv_jd?.text = "下拉刷新..."
        }
    }

    override fun onPrepare() {
        iv_jd_speed?.visibility = View.GONE
    }

    override fun onRefresh() {
        iv_jd_person?.scaleX = 1f
        iv_jd_person?.scaleY = 1f
        iv_jd_person?.alpha = 1f
        iv_jd_box?.visibility = View.GONE
        iv_jd_speed?.visibility = View.VISIBLE
        iv_jd_speed?.startAnimation(twinkle)
        if (!percentAnimation.isRunning) {
            percentAnimation.start()
        }
        tv_jd?.text = "更新中..."
    }
}