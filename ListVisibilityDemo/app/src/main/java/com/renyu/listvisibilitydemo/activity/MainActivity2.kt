package com.renyu.listvisibilitydemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.renyu.listvisibilitydemo.R
import com.renyu.listvisibilitydemo.Utils
import com.renyu.listvisibilitydemo.adapter.MainAdapter2
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2018/3/1 0001.
 */
class MainActivity2 : AppCompatActivity() {
    // 滚动状态
    var scrollState: Int = RecyclerView.SCROLL_STATE_IDLE

    var lastPosition = -1

    // 当前第一个可见项位置
    private var tempFirstVisibleItem = 0
    // 当前第一个可见项顶部偏移量
    private var tempFirstVisibleItemTop = 0
    // 滚动方向，
    enum class ScrollDirection {
        Up, // 上拉
        Down // 下拉
    }
    // 当前方向
    var direction: ScrollDirection = ScrollDirection.Down

    var adapter: MainAdapter2? = null
    private val beans: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 0..19) {
            beans.add(""+i)
        }

        adapter = MainAdapter2(beans, this, rv_main)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.setHasFixedSize(true)
        rv_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                refreshPercent()
//                play()

                if (recyclerView != null) {
                    val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    detectedScrollDirection(recyclerView, firstVisibleItem)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollState = newState
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    refreshPercent()
//                    play()
                }
            }
        })
        rv_main.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View?) {
                val manager = rv_main.layoutManager as LinearLayoutManager
                val first = manager.findFirstVisibleItemPosition()
                val last = manager.findLastVisibleItemPosition()
                // 当前视图被移除时如果处于激活状态，则进行反激活
                if (direction == ScrollDirection.Up) {
                    println("onChildViewDetachedFromWindow:"+(first))
                    if (first == lastPosition) {
                        adapter?.getItem(first)?.deActive()
                        lastPosition = -1
                    }
                }
                else if (direction == ScrollDirection.Down) {
                    println("onChildViewDetachedFromWindow:"+(last))
                    if (last == lastPosition) {
                        adapter?.getItem(last)?.deActive()
                        lastPosition = -1
                    }
                }
                // 视图被销毁的时候去除点击事件
                view?.setOnClickListener {

                }
            }

            override fun onChildViewAttachedToWindow(view: View?) {
                val manager = rv_main.layoutManager as LinearLayoutManager
                val first = if (manager.findFirstVisibleItemPosition()<0) 0 else manager.findFirstVisibleItemPosition()
                println("onChildViewAttachedToWindow:"+(first + rv_main.indexOfChild(view)))
                // 视图出现的时候添加点击事件。在新视图激活的情况下，如果上一次激活的视图在可见范围内，则反激活
                view?.setOnClickListener {
                    if (Utils.getVisibilityPercents(view!!) == 100) {
                        if (lastPosition != -1) {
                            adapter?.getItem(lastPosition)?.deActive()
                        }
                        lastPosition = manager.findFirstVisibleItemPosition() + rv_main.indexOfChild(view)
                        adapter?.getItem(lastPosition)?.setActive()
                    }
                }
            }
        })
        rv_main.adapter = adapter
    }

    /**
     * 刷新当前全部可见项显示比例
     */
    private fun refreshPercent() {
        val manager = rv_main.layoutManager as LinearLayoutManager
        val first = manager.findFirstVisibleItemPosition()
        for ( i in 0 until rv_main.childCount) {
            val percent = Utils.getVisibilityPercents(rv_main.getChildAt(i))
            adapter?.getItem(first+i)?.setPercent(percent)
//            println(""+(first+i)+":"+percent)
        }
    }

    /**
     * 播放视频
     */
    fun play() {
        val manager = rv_main.layoutManager as LinearLayoutManager
        val first = manager.findFirstVisibleItemPosition()
        for (i in 0 until rv_main.childCount) {
            val percent = Utils.getVisibilityPercents(rv_main.getChildAt(i))
            if (percent == 100 && lastPosition != first+i) {
                adapter?.getItem(lastPosition)?.deActive()
                adapter?.getItem(first+i)?.setActive()
                lastPosition = first+i
            }
        }
    }

    /**
     * 判断滚动方向
     */
    fun detectedScrollDirection(recyclerView: RecyclerView, firstVisibleItem: Int) {
        val firstVisibleView = recyclerView.layoutManager.getChildAt(0)
        val top = firstVisibleView.top
        if (firstVisibleItem == tempFirstVisibleItem) {
            // 从上向下拉，RecyclerView渲染会调用onScrolled方法，因此这里可能存在不滑动的情况
            if (top>tempFirstVisibleItemTop) {
                direction = ScrollDirection.Down
            }
            else if (top<tempFirstVisibleItemTop) {
                direction = ScrollDirection.Up
            }
        }
        else {
            // 从上向下拉
            direction = if (firstVisibleItem < tempFirstVisibleItem) {
                ScrollDirection.Down
            } else {
                ScrollDirection.Up
            }
        }
        tempFirstVisibleItem = firstVisibleItem
        tempFirstVisibleItemTop = top
    }
}