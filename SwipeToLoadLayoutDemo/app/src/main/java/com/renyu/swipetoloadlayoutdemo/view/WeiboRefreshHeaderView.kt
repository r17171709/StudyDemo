package com.renyu.swipetoloadlayoutdemo.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger
import com.blankj.utilcode.util.SizeUtils
import com.renyu.swipetoloadlayoutdemo.R

class WeiboRefreshHeaderView : LinearLayout, SwipeTrigger, SwipeRefreshTrigger {

    var pb_weibo: ProgressBar? = null
    var iv_weibo: ImageView? = null
    var tv_weibo: TextView? = null

    // 是否发生旋转
    var rotated = false

    private val rotate_up: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_up)
    }

    private val rotate_down: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_down)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        pb_weibo = findViewById(R.id.pb_weibo)
        iv_weibo = findViewById(R.id.iv_weibo)
        tv_weibo = findViewById(R.id.tv_weibo)
    }

    override fun onReset() {
        pb_weibo?.visibility = View.GONE
        iv_weibo?.visibility = View.VISIBLE
        tv_weibo?.text = "下拉刷新"
    }

    override fun onComplete() {
        tv_weibo?.text = "刷新完成"
        pb_weibo?.visibility = View.GONE
    }

    override fun onRelease() {

    }

    override fun onMove(p0: Int, p1: Boolean, p2: Boolean) {
        if (p0 > SizeUtils.dp2px(60f)) {
            if (!rotated) {
                rotated = true
                tv_weibo?.text = "释放更新"
                iv_weibo?.clearAnimation()
                iv_weibo?.startAnimation(rotate_up)
            }
        }
        else {
            if (rotated) {
                rotated = false
                tv_weibo?.text = "下拉刷新"
                iv_weibo?.clearAnimation()
                iv_weibo?.startAnimation(rotate_down)
            }
        }
    }

    override fun onPrepare() {

    }

    override fun onRefresh() {
        tv_weibo?.text = "加载中"
        iv_weibo?.clearAnimation()
        iv_weibo?.visibility = View.GONE
        pb_weibo?.visibility = View.VISIBLE
    }
}