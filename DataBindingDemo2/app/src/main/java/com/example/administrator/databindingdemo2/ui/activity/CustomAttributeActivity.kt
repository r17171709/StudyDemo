package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityCustomattributeBinding
import com.example.administrator.databindingdemo2.model.DayNight
import com.example.administrator.databindingdemo2.util.MyHandlers

class CustomAttributeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityCustomattributeBinding>(this, R.layout.activity_customattribute)
        viewDataBinding.dayNight = DayNight(ObservableBoolean(false))
        viewDataBinding.handlers = MyHandlers()

        Handler().postDelayed({
            viewDataBinding.dayNight?.day?.set(true)
        }, 6000)
    }
}