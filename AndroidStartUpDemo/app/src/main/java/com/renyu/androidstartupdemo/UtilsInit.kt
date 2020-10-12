package com.renyu.androidstartupdemo

import android.content.Context
import androidx.startup.Initializer

class UtilsInit : Initializer<Unit> {
    override fun create(context: Context) {
//        Utils.init(context as Application)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return ArrayList()
    }
}