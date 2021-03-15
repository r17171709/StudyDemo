package com.renyu.recyclerviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.adapter.DragSwipeAdapter
import com.renyu.recyclerviewdemo.view.SlideRecyclerView

/**
 * 参考文章：https://github.com/YoungTime/SlideRecyclerView
 */
class SlideActivity : AppCompatActivity() {
    private val beans: ArrayList<String> by lazy { ArrayList<String>() }
    private val adapter: DragSwipeAdapter by lazy { DragSwipeAdapter(beans, this@SlideActivity, null) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

        for (i in 0 until 30) {
            beans.add("0")
        }

        val rvSlide = findViewById<SlideRecyclerView>(R.id.rv_slide)
        val manager = LinearLayoutManager(this)
        rvSlide.setHasFixedSize(true)
        rvSlide.layoutManager = manager
        rvSlide.adapter = adapter
    }
}