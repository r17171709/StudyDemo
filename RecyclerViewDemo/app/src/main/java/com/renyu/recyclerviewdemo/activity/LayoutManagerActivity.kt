package com.renyu.recyclerviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.adapter.LayoutManagerAdapter
import com.renyu.recyclerviewdemo.impl.SwipeCardCallback
import com.renyu.recyclerviewdemo.layoutmanager.SwipeCardLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class LayoutManagerActivity : AppCompatActivity() {
    private val beans by lazy { ArrayList<String>() }
    private val adapter by lazy { LayoutManagerAdapter(beans) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beans.apply {
            for (i in 0 until 30) {
                add("$i")
            }
        }

//        rv_demo.layoutManager = RepeatLayoutManager(RecyclerView.VERTICAL)
        rv_demo.layoutManager = SwipeCardLayoutManager()
        rv_demo.setHasFixedSize(true)
        rv_demo.adapter = adapter
        val callback = SwipeCardCallback(adapter, beans)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rv_demo)
    }
}