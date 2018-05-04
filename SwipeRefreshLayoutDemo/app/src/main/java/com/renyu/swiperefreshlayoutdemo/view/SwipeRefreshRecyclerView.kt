package com.renyu.swiperefreshlayoutdemo.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.renyu.swiperefreshlayoutdemo.R

class SwipeRefreshRecyclerView : BaseSwipeRefresh<RecyclerView> {
    override fun createScrollView(context: Context, attrs: AttributeSet?): RecyclerView {
        val recyclerView = RecyclerView(context, attrs)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getContext())
        recyclerView.id = R.id.customer_rv
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isFirstItemVisible() && isLastItemVisible()) {
                        setRefreshing(true, false)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        return recyclerView
    }

    override fun createEmptyView(context: Context, attrs: AttributeSet?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_empty, null, false)
        return view
    }

    override fun createFootView(context: Context, attrs: AttributeSet?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_loadmore, null, false)
        return view
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }
    constructor(context: Context) : super(context) {

    }

    fun setAdapter(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        mScrollView!!.adapter = adapter
        changeEmptyVisibilityState(adapter.itemCount == 0)
    }

    /**
     * 判断是否完全滑动到顶部
     */
    fun isFirstItemVisible() : Boolean {
        if ((mScrollView!!.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 0) {
            // 判断是否完全滑动到顶部
            return mScrollView!!.getChildAt(0).top >= mScrollView!!.top
        }
        return false
    }

    /**
     * 判断是否完全滑动到底部
     */
    fun isLastItemVisible() : Boolean {
        if ((mScrollView!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == mScrollView!!.adapter.itemCount - 1) {
            // 判断是否完全滑动到顶部
            return mScrollView!!.getChildAt(mScrollView!!.childCount-1).bottom <= mScrollView!!.bottom
        }
        return false
    }
}