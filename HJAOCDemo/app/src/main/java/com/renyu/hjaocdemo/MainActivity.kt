package com.renyu.hjaocdemo

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.renyu.hjaocdemo.databinding.ActivityMainBinding
import com.renyu.hjlibrary.singleclick.SingleClick

class MainActivity : AppCompatActivity(), EventListener {
    override fun onViewClick(view: View) {
        MainActivity.clockValue(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewDataBinding.listener = this
    }

    companion object {
        @SingleClick
        fun clockValue(view: View) {
            println("onViewClick")
        }

    }
}
