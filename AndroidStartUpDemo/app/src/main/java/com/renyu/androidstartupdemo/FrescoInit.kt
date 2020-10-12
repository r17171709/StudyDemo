package com.renyu.androidstartupdemo

import android.content.Context
import androidx.startup.Initializer
import com.facebook.drawee.backends.pipeline.Fresco

class FrescoInit : Initializer<Unit> {
    override fun create(context: Context) {
        Fresco.initialize(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return ArrayList()
    }
}