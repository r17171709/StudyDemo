package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.databindingdemo.databinding.ActivityInversebinding2Binding
import com.renyu.databindingdemo.model.Score

class Inversebinding2Activity : AppCompatActivity() {

    val score: Score by lazy {
        Score()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityInversebinding2Binding>(this, R.layout.activity_inversebinding2)
        score.isOK.set(false)
        dataBindingUtil.score = score
    }
}