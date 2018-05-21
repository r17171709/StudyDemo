package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.databindingdemo.databinding.ActivityBindingmethodsBinding
import com.renyu.databindingdemo.model.User

class BindingMethodsActivity : AppCompatActivity() {

    val user: User by lazy {
        User()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityBindingmethodsBinding>(this, R.layout.activity_bindingmethods)
        dataBindingUtil.activity = Presenter()
        dataBindingUtil.user = user

        user.name = "renyu"
    }

    class Presenter {
        fun longClick(string: String) {
            println("longClick")
        }
    }
}