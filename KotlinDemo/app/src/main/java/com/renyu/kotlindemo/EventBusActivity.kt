package com.renyu.kotlindemo

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.renyu.kotlindemo.bean.Test
import com.renyu.kotlindemo.bus.EventBus
import kotlinx.coroutines.Dispatchers

class EventBusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_hello).setOnClickListener {
            EventBus.post(Test())
        }

        EventBus.register("demo", Dispatchers.Main, Test::class.java) {
            Toast.makeText(this, "收到回调", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.unregisterAllEvents()
    }
}