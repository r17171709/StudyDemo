package com.renyu.swipetoloadlayoutdemo

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_weibo.*

class WeiboActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weibo)

        Utils.init(applicationContext)

        swipe_weibo.post {
            swipe_weibo.isRefreshing = true
        }
        swipe_weibo.setOnRefreshListener {
            Handler().postDelayed({
                swipe_weibo.isRefreshing = false
            }, 2000)
        }
    }
}