package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.databindingdemo.databinding.ActivityScoreBinding
import com.renyu.databindingdemo.model.Score

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val score = Score()
        score.id.set(1)
        score.name.set("renyu")
        score.url.set("https://github.com/r17171709/android_demo")

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityScoreBinding>(this, R.layout.activity_score)
        dataBindingUtil.score = score
    }
}