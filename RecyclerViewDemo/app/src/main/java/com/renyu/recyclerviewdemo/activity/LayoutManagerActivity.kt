package com.renyu.recyclerviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.adapter.LayoutManagerAdapter
import com.renyu.recyclerviewdemo.layoutmanager.RepeatLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class LayoutManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_demo.layoutManager = RepeatLayoutManager(RecyclerView.VERTICAL)
        rv_demo.setHasFixedSize(true)
        rv_demo.adapter = LayoutManagerAdapter()
    }
}