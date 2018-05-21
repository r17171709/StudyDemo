package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.renyu.databindingdemo.databinding.ActivityInversebindingBinding
import com.renyu.databindingdemo.model.Score
import kotlinx.android.synthetic.main.activity_inversebinding.*

class InverseBindingActivity : AppCompatActivity() {

    val score: Score by lazy {
        Score()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        score.isOK.set(false)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityInversebindingBinding>(this, R.layout.activity_inversebinding)
        dataBindingUtil.score = score

        Handler().postDelayed({
            view_inversebinding.visibility = View.VISIBLE
            toast(""+score.isOK.get())
        }, 3000)
    }
}