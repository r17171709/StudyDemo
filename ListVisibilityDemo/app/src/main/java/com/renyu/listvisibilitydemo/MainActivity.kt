package com.renyu.listvisibilitydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: MainAdapter? = null
    private val beans: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    private val INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS = 70

    // 当前第一个可见项位置
    private var tempFirstVisibleItem = 0
    // 当前第一个可见项顶部偏移量
    private var tempFirstVisibleItemTop = 0
    // 滚动方向
    enum class ScrollDirection {
        Up, Down
    }
    // 当前方向
    var direction: ScrollDirection = ScrollDirection.Up
    // 滚动状态
    var scrollState: Int = 0
    var mCurrentItem: ListItemData = ListItemData(-1, null, false)
    var mPreActiveItem: ListItemData = ListItemData(-1, null, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beans.add("0")
        beans.add("1")
        beans.add("2")
        beans.add("3")
        beans.add("4")
        beans.add("5")
        beans.add("6")
        beans.add("7")
        beans.add("8")
        beans.add("9")
        beans.add("10")
        beans.add("11")
        beans.add("12")
        beans.add("13")
        beans.add("14")
        beans.add("15")
        beans.add("16")
        beans.add("17")
        beans.add("18")
        beans.add("19")

        adapter = MainAdapter(beans, this, rv_main)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.setHasFixedSize(true)
        rv_main.adapter = adapter
        rv_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (recyclerView != null) {
                    val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    detectedScrollDirection(recyclerView, firstVisibleItem)

                    when(scrollState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            stateIdle()
                        }
                        RecyclerView.SCROLL_STATE_SETTLING -> {
                            stateTouchScroll()
                        }
                        RecyclerView.SCROLL_STATE_DRAGGING -> {
                            stateTouchScroll()
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter!!.itemCount > 0) {
                    stateIdle()
                }

                scrollState = newState
            }
        })
    }

    private fun stateTouchScroll() {
        calculateActiveItem()
        // 如果当前的完全显示项与原先的完全显示项不一致，则执行激活与反激活配置
        if (mCurrentItem.changed && mPreActiveItem.index != mCurrentItem.index) {
            active()
        }
    }

    /**
     * 处理显示项发生变化
     */
    private fun calculateActiveItem() {
        val currentItemVisibilityPercents = Utils.getVisibilityPercents(mCurrentItem.view!!)
        val listItemData = when(direction) {
            ScrollDirection.Up -> findPreviewItem()
            ScrollDirection.Down -> findNextItem()
        }
        if (listItemData != null) {
            // 找到显示项之后，如果旧的显示项小于INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS并且新的显示项大于INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS的时候，则认定显示项发生变化
            val nextItemVisibilityPercents = Utils.getVisibilityPercents(listItemData.view!!)
//            Log.d("MainActivity", currentItemVisibilityPercents.toString()+" "+nextItemVisibilityPercents.toString())
            if (INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS in (currentItemVisibilityPercents + 1)..(nextItemVisibilityPercents - 1)) {
                setCurrentItem(listItemData)
            }
        }
    }

    /**
     * 查找下一个显示项
     */
    private fun findNextItem() : ListItemData? {
        val manager = rv_main.layoutManager as LinearLayoutManager
        if (mCurrentItem.index != -1) {
            // 刷新当前全部可见项显示比例
            refreshPercent()

            val nextItemIndex = mCurrentItem.index+1
            if (nextItemIndex < adapter!!.itemCount) {
                val currentViewPosition = rv_main.indexOfChild(mCurrentItem.view)
                val nextView = manager.getChildAt(currentViewPosition+1)
                if (nextView != null) {
                    return ListItemData(nextItemIndex, nextView)
                }
            }
        }
        return null
    }

    /**
     * 查找上一个显示项
     */
    private fun findPreviewItem() : ListItemData? {
        val manager = rv_main.layoutManager as LinearLayoutManager
        if (mCurrentItem.index != -1) {
            // 刷新当前全部可见项显示比例
            refreshPercent()

            val previewItemIndex = mCurrentItem.index-1
            if (previewItemIndex >= 0) {
                val currentViewPosition = rv_main.indexOfChild(mCurrentItem.view)
                val previewView = manager.getChildAt(currentViewPosition-1)
                if (previewView != null) {
                    return ListItemData(previewItemIndex, previewView)
                }
            }
        }
        return null
    }

    /**
     * 刷新当前全部可见项显示比例
     */
    private fun refreshPercent() {
        val manager = rv_main.layoutManager as LinearLayoutManager
        for (i in 0 until rv_main.indexOfChild(mCurrentItem.view)) {
            val temp = rv_main.indexOfChild(mCurrentItem.view) - 1 - i
            val position = mCurrentItem.index - 1 - i
            val percent = Utils.getVisibilityPercents(manager.getChildAt(temp))
            adapter?.getItem(position)?.setPercent(percent)
//            Log.d("MainActivity", "" + position + " " + percent)
        }
        for (i in 0 until rv_main.childCount - rv_main.indexOfChild(mCurrentItem.view)) {
            val position = mCurrentItem.index + i
            val percent = Utils.getVisibilityPercents(manager.getChildAt(rv_main.indexOfChild(mCurrentItem.view) + i))
            adapter?.getItem(position)?.setPercent(percent)
//            Log.d("MainActivity", "" + position + " " + percent)
        }
    }

    private fun stateIdle() {
        val firstVisibleItemPosition = (rv_main.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val lastVisibleItemPosition = (rv_main.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        calculateMostVisibleItem(firstVisibleItemPosition, lastVisibleItemPosition)
        if (mCurrentItem.index != -1) {
            refreshPercent()
        }
        // 如果当前的完全显示项与原先的完全显示项不一致，则执行激活与反激活配置
        if (mCurrentItem.changed && mPreActiveItem.index != mCurrentItem.index) {
            active()
        }
    }

    /**
     * 找到完全显示项
     */
    private fun calculateMostVisibleItem(firstVisibleItemPosition: Int, lastVisibleItemPosition: Int) {
        val mockCurrentItem = getMockCurrentItem(firstVisibleItemPosition, lastVisibleItemPosition)
        val maxVisibilityPercents = Utils.getVisibilityPercents(mockCurrentItem.view!!)
        when(direction) {
            ScrollDirection.Up -> {
                bottomToTopMostVisibleItem(mockCurrentItem, maxVisibilityPercents)
            }
            ScrollDirection.Down -> {
                topToBottomMostVisibleItem(mockCurrentItem, maxVisibilityPercents)
            }
        }
        if (mockCurrentItem.changed) {
            setCurrentItem(mockCurrentItem)
        }
    }

    /**
     * 设置当前完全显示项
     */
    private fun setCurrentItem(mockCurrentItem: ListItemData) {
        mCurrentItem.changed = true
        mCurrentItem.index = mockCurrentItem.index
        mCurrentItem.view = mockCurrentItem.view
    }

    /**
     * 激活与反激活原先与当前完全显示项
     */
    private fun active() {
        // 找到原先的完全显示项并执行反激活
        if (mPreActiveItem.index != -1) {
            adapter?.getItem(mPreActiveItem.index)?.deActive()
//            Log.d("MainActivity", "关闭"+mPreActiveItem.index)
        }
        // 找到当前的完全显示项并激活
        adapter?.getItem(mCurrentItem.index)?.setActive()
//        Log.d("MainActivity", "激活"+mCurrentItem.index)
        mCurrentItem.changed = false

        // 替换
        mPreActiveItem.changed = false
        mPreActiveItem.index = mCurrentItem.index
        mPreActiveItem.view = mCurrentItem.view
    }

    private fun bottomToTopMostVisibleItem(mockCurrentItem: ListItemData, maxVisibilityPercents: Int) {
        var changed = false
        val manager = rv_main.layoutManager as LinearLayoutManager
        var temp = maxVisibilityPercents
        // 由下而上循环查找第一个完全显示项
        val mostSize = rv_main.indexOfChild(mockCurrentItem.view)
        val temoIndex = manager.findLastVisibleItemPosition()
        for (i in 0..mostSize) {
            val currentView = manager.getChildAt(mostSize-i)
            val currentItemVisibilityPercents = Utils.getVisibilityPercents(currentView)
            if (currentItemVisibilityPercents>temp && currentItemVisibilityPercents>INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS) {
                temp = currentItemVisibilityPercents
                mockCurrentItem.view = currentView
                mockCurrentItem.index = temoIndex - i

                changed = true
            }
        }
        if (changed) {
            mockCurrentItem.changed = true
        }
    }

    private fun topToBottomMostVisibleItem(mockCurrentItem: ListItemData, maxVisibilityPercents: Int) {
        var changed = false
        val manager = rv_main.layoutManager as LinearLayoutManager
        var temp = maxVisibilityPercents
        val temoIndex = manager.findFirstVisibleItemPosition()
        for (i in rv_main.indexOfChild(mockCurrentItem.view) until rv_main.childCount) {
            val currentView = manager.getChildAt(i)
            val currentItemVisibilityPercents = Utils.getVisibilityPercents(currentView)
            if (currentItemVisibilityPercents>temp && currentItemVisibilityPercents>INACTIVE_LIST_ITEM_VISIBILITY_PERCENTS) {
                temp = currentItemVisibilityPercents
                mockCurrentItem.view = currentView
                mockCurrentItem.index = temoIndex+i

                changed = true
            }
        }
        if (changed) {
            mockCurrentItem.changed = true
        }
    }

    /**
     * 假定一个当前完全显示项
     */
    private fun getMockCurrentItem(firstVisibleItemPosition: Int, lastVisibleItemPosition: Int) : ListItemData {
        val manager = rv_main.layoutManager as LinearLayoutManager
        return when(direction) {
            // 从上往下拉的时候选择最后一个可见项
            ScrollDirection.Up -> {
                ListItemData(lastVisibleItemPosition,
                        manager.getChildAt(rv_main.childCount-1))
            }
            // 从下往上拉的时候选择第一个可见项
            ScrollDirection.Down -> {
                ListItemData(firstVisibleItemPosition,
                        manager.getChildAt(0))
            }
        }
    }

    /**
     * 判断滚动方向
     */
    fun detectedScrollDirection(recyclerView: RecyclerView, firstVisibleItem: Int) {
        // 当前最上方可见视图
        val firstVisibleView = recyclerView.layoutManager.getChildAt(0)
        val top = firstVisibleView.top
        if (firstVisibleItem == tempFirstVisibleItem) {
            // 从上向下拉，RecyclerView渲染会调用onScrolled方法，因此这里可能存在不滑动的情况
            if (top>tempFirstVisibleItemTop) {
                direction = ScrollDirection.Up
            }
            else if (top<tempFirstVisibleItemTop) {
                direction = ScrollDirection.Down
            }
        }
        else {
            // 从上向下拉
            direction = if (firstVisibleItem < tempFirstVisibleItem) {
                ScrollDirection.Up
            } else {
                ScrollDirection.Down
            }
        }
        tempFirstVisibleItem = firstVisibleItem
        tempFirstVisibleItemTop = top
    }
}

/**
 * 行信息数据封装
 * @param index 当前项在RecyclerView中的位置
 * @param view 当前项视图
 * @param changed 当前项是否发生显示上的变化
 */
data class ListItemData(var index: Int = -1, var view: View?, var changed: Boolean = false)