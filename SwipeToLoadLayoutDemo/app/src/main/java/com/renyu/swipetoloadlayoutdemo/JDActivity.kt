package com.renyu.swipetoloadlayoutdemo

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_jd.*

class JDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jd)

        Utils.init(applicationContext)

        swipe_jd.post {
            swipe_jd.isRefreshing = true
        }
        swipe_jd.setOnRefreshListener {
            Handler().postDelayed({
                swipe_jd.isRefreshing = false
            }, 2000)
        }
    }
}