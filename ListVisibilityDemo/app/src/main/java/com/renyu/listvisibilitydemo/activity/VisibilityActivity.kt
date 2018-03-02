package com.renyu.listvisibilitydemo.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.listvisibilitydemo.R
import kotlinx.android.synthetic.main.activity_visibility.*

/**
 * Created by Administrator on 2018/3/1 0001.
 */
class VisibilityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visibility)

        view_visibility.post {
            val rect = Rect()
            view_visibility.getLocalVisibleRect(rect)
            println(rect)
        }
    }
}