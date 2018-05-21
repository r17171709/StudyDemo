package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.databindingdemo.databinding.ActivityChangetextBinding
import com.renyu.databindingdemo.model.Score

class ChangeTextActivity : AppCompatActivity() {

    val score: Score by lazy {
        Score()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityChangetextBinding>(this, R.layout.activity_changetext)
        dataBindingUtil.score = score

    }
}