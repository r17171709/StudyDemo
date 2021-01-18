package com.renyu.constraintlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.renyu.constraintlayoutdemo.databinding.ActivityMotionlistBinding

class MotionListActivity : AppCompatActivity() {
    private val bind by lazy { ActivityMotionlistBinding.inflate(layoutInflater) }
    private val beans by lazy { ArrayList<String>() }
    private val adapter by lazy { MotionListAdapter(beans) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        beans.apply {
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
            add("1")
        }

        val layoutManager = LinearLayoutManager(this)
        bind.rvMotionlist.setHasFixedSize(true)
        bind.rvMotionlist.layoutManager = layoutManager
        bind.rvMotionlist.adapter = adapter
    }
}