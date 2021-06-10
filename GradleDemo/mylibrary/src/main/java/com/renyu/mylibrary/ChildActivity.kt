package com.renyu.mylibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.route.annotations.Destination

@Destination(url = "route://page-child", description = "子页面")
class ChildActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
    }
}