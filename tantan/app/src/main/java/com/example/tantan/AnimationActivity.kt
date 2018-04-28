package com.example.tantan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.SizeUtils
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        view_2.scaleX = Math.pow(0.8, 1.0).toFloat()
        view_2.scaleY = Math.pow(0.8, 1.0).toFloat()
        view_2.translationY = (1-0.8f)*SizeUtils.dp2px(300f)/2+SizeUtils.dp2px(30f)*0.64f

        view_3.scaleX = Math.pow(0.8, 2.0).toFloat()
        view_3.scaleY = Math.pow(0.8, 2.0).toFloat()
        view_3.translationY = (1-0.64f)*SizeUtils.dp2px(300f)/2+SizeUtils.dp2px(30f)*0.8f
    }
}