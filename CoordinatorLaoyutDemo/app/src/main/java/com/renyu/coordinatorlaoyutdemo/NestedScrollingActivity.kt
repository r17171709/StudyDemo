package com.renyu.coordinatorlaoyutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renyu.coordinatorlaoyutdemo.adapter.VideoListAdapter

/**
 * 参考代码 https://github.com/sangxiaonian/EasyRefrush
 */
class NestedScrollingActivity : AppCompatActivity() {
    private var rv_nestedscrolling: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nestedscrolling)

        rv_nestedscrolling = findViewById(R.id.rv_nestedscrolling)
        rv_nestedscrolling!!.setHasFixedSize(true)
        rv_nestedscrolling!!.layoutManager = LinearLayoutManager(this)
        rv_nestedscrolling!!.adapter = VideoListAdapter()
    }
}