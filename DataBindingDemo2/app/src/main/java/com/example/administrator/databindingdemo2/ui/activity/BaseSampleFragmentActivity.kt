package com.example.administrator.databindingdemo2.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.ui.fargment.BaseSampleFragment

class BaseSampleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basesamplefragment)

        supportFragmentManager.beginTransaction().replace(R.id.layout_basesamplefragment, BaseSampleFragment(), "tag").commitAllowingStateLoss()
    }
}