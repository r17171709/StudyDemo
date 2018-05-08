package com.renyu.swipetoloadlayoutdemo.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger
import com.blankj.utilcode.util.SizeUtils
import com.eftimoff.androipathview.PathView
import com.renyu.swipetoloadlayoutdemo.R

class ElemeRefreshHeaderView : LinearLayout, SwipeTrigger, SwipeRefreshTrigger {

    var pathView_ele: PathView? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        pathView_ele = findViewById(R.id.pathView_ele)
    }

    override fun onReset() {

    }

    override fun onComplete() {
        pathView_ele?.setPercentage(1f)
    }

    override fun onRelease() {
    }

    override fun onMove(p0: Int, p1: Boolean, p2: Boolean) {
        val percent = 1 - (SizeUtils.dp2px(58f) - p0) * 1.0f / SizeUtils.dp2px(58f)
        val value = if (percent >= 1) 1f else percent
        pathView_ele?.setPercentage(value)
    }

    override fun onPrepare() {
        pathView_ele?.setPercentage(0f)
    }

    override fun onRefresh() {
        pathView_ele?.setPercentage(1f)
    }
}