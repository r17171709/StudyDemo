package com.renyu.swiperefreshlayoutdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import com.renyu.swiperefreshlayoutdemo.adapter.MainAdapter
import com.renyu.swiperefreshlayoutdemo.view.BaseSwipeRefresh
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: MainAdapter? = null

    val beans: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            Handler().postDelayed({
                for (i in 0..19) {
                    beans.add(0, "s$i")
                }
                adapter?.notifyDataSetChanged()

                rv_main.setRefreshing(false, true)
            }, 3000)
        })
        rv_main.setOnLoadMoreListener(object : BaseSwipeRefresh.LoadMoreListener {
            override fun onRefresh() {
                Handler().postDelayed({
                    for (i in 0..19) {
                        beans.add("e$i")
                    }
                    adapter?.notifyDataSetChanged()

                    rv_main.setRefreshing(false, false)
                }, 3000)
            }
        })
        for (i in 0..19) {
            beans.add("$i")
        }
        adapter = MainAdapter(this, beans)
        rv_main.setAdapter(adapter!!)
    }
}
