package com.renyu.architecturedemo.mvvm.vm

import com.renyu.architecturedemo.mvvm.model.SearchBeans

class DetailViewModel constructor(val beans: SearchBeans) {
    fun update(value: String) {
        beans.value = value
    }
}