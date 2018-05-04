package com.renyu.swiperefreshlayoutdemo.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

abstract class BaseSwipeRefresh<T : View> : FrameLayout {

    // 上拉加载更多页面的高度，用来做动画参数
    var mLoadLayoutHeight = 0
    var mShowLoadAnimator: ValueAnimator? = null
    var mHideLoadAnimator: ValueAnimator? = null

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }
    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    abstract fun createScrollView(context: Context, attrs: AttributeSet?): T
    abstract fun createEmptyView(context: Context, attrs: AttributeSet?): View
    abstract fun createFootView(context: Context, attrs: AttributeSet?): View

    // 滚动View，比如RecyclerView
    var mScrollView: T? = null
    // 空数据提示View
    var mEmptyView: View? = null
    // 上拉加载更多View
    var mFootView: View? = null
    // 下拉刷新View
    var mSwipeRefreshLayout: CustomerSwipeRefreshLayout? = null

    // 上拉加载更多事件回调
    var loadMoreListener: LoadMoreListener? = null

    var isLoadingMore = false

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mScrollView = createScrollView(context, attrs)
        mEmptyView = createEmptyView(context, attrs)
        mFootView = createFootView(context, attrs)
        mSwipeRefreshLayout = CustomerSwipeRefreshLayout(context, attrs)

        // SwipeRefreshLayout根布局View
        var mRootLayout = FrameLayout(context)
        mRootLayout!!.addView(mScrollView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        mRootLayout!!.addView(mEmptyView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        mSwipeRefreshLayout!!.addView(mRootLayout, ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT))
        addView(mSwipeRefreshLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addLoadMoreLayout()

        // 设置可触发SwipeRefreshLayout判断滑动的全部View数组
        mSwipeRefreshLayout!!.swipeViews = arrayOf(mScrollView!!, mEmptyView!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mLoadLayoutHeight = mFootView!!.measuredHeight
        mFootView!!.visibility = View.GONE

        initShowLoadAnimator()
        initHideLoadAnimator()
    }

    /**
     * 添加底部上拉加载更多视图
     */
    private fun addLoadMoreLayout() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, 240)
        params.gravity = Gravity.BOTTOM
        addView(mFootView, 1, params)
    }

    fun changeEmptyVisibilityState(visible: Boolean) {
        if (visible) mEmptyView!!.visibility = View.VISIBLE else mEmptyView!!.visibility = View.GONE
    }

    fun setOnRefreshListener(listener: SwipeRefreshLayout.OnRefreshListener) {
        mSwipeRefreshLayout!!.setOnRefreshListener(listener)
    }

    fun setOnLoadMoreListener(listener: LoadMoreListener) {
        this.loadMoreListener = listener
    }

    private fun initShowLoadAnimator() {
        mShowLoadAnimator = ValueAnimator.ofInt(0, mLoadLayoutHeight)
        mShowLoadAnimator?.addUpdateListener {
            val height = it.animatedValue as Int
            val layoutParams = mFootView!!.layoutParams
            layoutParams.height = height
            mFootView!!.layoutParams = layoutParams

            // 移动mScrollView
            mScrollView!!.translationY = -height.toFloat()
        }
        mShowLoadAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)

                isLoadingMore = true

                mFootView!!.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                loadMoreListener!!.onRefresh()
            }
        })
        mShowLoadAnimator?.duration = 300
    }

    private fun initHideLoadAnimator() {
        mHideLoadAnimator = ValueAnimator.ofInt(mLoadLayoutHeight, 0)
        mHideLoadAnimator?.addUpdateListener {
            val height = it.animatedValue as Int
            val layoutParams = mFootView!!.layoutParams
            layoutParams.height = height
            mFootView!!.layoutParams = layoutParams

            // 移动mScrollView
            mScrollView!!.translationY = -height.toFloat()
        }
        mHideLoadAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)

                isLoadingMore = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                mFootView!!.visibility = View.GONE
            }
        })
        mHideLoadAnimator?.duration = 300
    }

    fun setRefreshing(isShow: Boolean, isPullToRefresh: Boolean) {
        // 下拉刷新
        if (isPullToRefresh) {
            mSwipeRefreshLayout?.isRefreshing = isShow
        }
        // 上拉加载更多
        else {
            if (isShow) {
                // 正在刷新过程中不可以触发
                if (isLoadingMore) {
                    return
                }
                mShowLoadAnimator!!.start()
            }
            else {
                mHideLoadAnimator!!.start()
            }
        }
    }

    interface LoadMoreListener {
        fun onRefresh()
    }
}

