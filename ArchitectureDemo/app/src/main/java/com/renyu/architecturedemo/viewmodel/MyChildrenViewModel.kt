package com.renyu.architecturedemo.viewmodel

import android.arch.lifecycle.LiveData

class MyChildrenViewModel : LiveData<Int>() {
    override fun onActive() {
        println("缴纳学费了")
        super.onActive()
    }

    override fun onInactive() {
        println("学费缴纳结束了")
        super.onInactive()
    }

    fun setMyValue(price: Int) {
        value = price
    }
}