package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityBaseobservablesampleBinding
import com.example.administrator.databindingdemo2.model.Teacher2
import com.example.administrator.databindingdemo2.model.Teacher3

class BaseObservableSampleActivity : AppCompatActivity() {

    private val teacher2: Teacher2 by lazy {
        Teacher2(ObservableField("renyu"), ObservableField(30))
    }

    private val teacher3: Teacher3 by lazy {
        Teacher3()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewDataBinding = DataBindingUtil.setContentView<ActivityBaseobservablesampleBinding>(this, R.layout.activity_baseobservablesample)
        viewDataBinding.teacher2 = teacher2
        viewDataBinding.teacher3 = teacher3

        Handler().postDelayed({
            teacher2.name?.set("PQ")

            teacher3.name = "RGL"
        }, 4000)
    }
}