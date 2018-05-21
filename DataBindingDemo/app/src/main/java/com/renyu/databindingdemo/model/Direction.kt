package com.renyu.databindingdemo.model

import android.databinding.ObservableArrayMap
import android.databinding.ObservableField
import android.databinding.ObservableInt

class Direction {
    val direction: ObservableArrayMap<String, Any> by lazy {
        ObservableArrayMap<String, Any>()
    }
    val choice: ObservableField<String> by lazy {
        ObservableField<String>()
    }
    val id: ObservableInt by lazy {
        ObservableInt()
    }
}