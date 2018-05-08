package com.renyu.swipetoloadlayoutdemo

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_eleme.*

class ElemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleme)

        Utils.init(applicationContext)

        swipe_eleme.setOnRefreshListener {
            Handler().postDelayed({
                swipe_eleme.isRefreshing = false
            }, 2000)
        }
    }
}