package com.renyu.recyclerviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.renyu.recyclerviewdemo.view.StickyHeader
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by renyu on 2017/10/31.
 */
class StickyHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lists = ArrayList<String>()
        for (i in 0..6) {
            lists.add("111")
        }
        for (i in 0..6) {
            lists.add("222")
        }
        for (i in 0..6) {
            lists.add("333")
        }

        rv_demo.setHasFixedSize(true)
        rv_demo.layoutManager = LinearLayoutManager(this)
        rv_demo.addItemDecoration(StickyHeader(this))
        rv_demo.adapter = StickyHeaderAdapter(this, lists)
    }
}