package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.renyu.databindingdemo.databinding.ActivityCommonmethodBinding
import com.renyu.databindingdemo.model.Score
import kotlinx.android.synthetic.main.activity_commonmethod.*

class CommonMethodActivity : AppCompatActivity() {

    val score: Score by lazy {
        Score()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityCommonmethodBinding>(this, R.layout.activity_commonmethod)
        dataBindingUtil.act = this
        dataBindingUtil.score = score

        score.name.set("renyu")
    }

    fun showToastValue(value: String) {
        toast(value)
        layout_commonmethod.setBackgroundColor(Color.RED)
    }

}

fun AppCompatActivity.toast(value: String) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}