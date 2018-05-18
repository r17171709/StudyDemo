package com.renyu.architecturedemo.mvvm.impl

import android.view.View

interface EventListener {
    fun onClickListener(view: View, value: String)
}