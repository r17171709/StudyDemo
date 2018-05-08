package com.renyu.swipetoloadlayoutdemo

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_mt.*

class MTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mt)

        Utils.init(applicationContext)

        swipe_mt.setOnRefreshListener {
            Handler().postDelayed({
                swipe_mt.isRefreshing = false
            }, 2000)
        }
    }
}