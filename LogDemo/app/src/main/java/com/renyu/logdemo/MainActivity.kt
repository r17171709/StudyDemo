package com.renyu.logdemo

import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val scroll by lazy {
        findViewById<ScrollView>(R.id.scroll)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.stop).setOnClickListener {
            LogcatExecutor.stopOutput()
        }

        LogcatExecutor.startOutput(object : LogcatExecutor.CallBack {
            override fun onLogOutput(log: String) {
                runOnUiThread {
                    findViewById<TextView>(R.id.content).text = log
                    scroll.post {
                        scroll.fullScroll(ScrollView.FOCUS_DOWN)
                    }
                }
            }
        })
    }
}