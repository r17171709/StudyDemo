package com.renyu.asmdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        Thread.sleep(1000)
    }
}