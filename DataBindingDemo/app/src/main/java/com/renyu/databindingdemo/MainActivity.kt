package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.renyu.databindingdemo.databinding.ActivityMainBinding
import com.renyu.databindingdemo.model.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val user = User()
        user.id = "1"
        user.name = "renyu"
        user.blog = "https://github.com/r17171709/android_demo"

//        dataBindingUtil.setVariable(BR.user, user)
        dataBindingUtil.user = user

        Handler().postDelayed({user.name = "pq"}, 2000)
    }
}
