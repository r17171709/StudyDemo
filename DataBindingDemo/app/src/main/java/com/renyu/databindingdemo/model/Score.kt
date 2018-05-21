package com.renyu.databindingdemo.model

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt

class Score {
    val id: ObservableInt by lazy {
        ObservableInt()
    }

    val name: ObservableField<String> by lazy {
        ObservableField<String>()
    }

    val url: ObservableField<String> by lazy {
        ObservableField<String>()
    }

    val isOK: ObservableBoolean by lazy {
        ObservableBoolean()
    }
}