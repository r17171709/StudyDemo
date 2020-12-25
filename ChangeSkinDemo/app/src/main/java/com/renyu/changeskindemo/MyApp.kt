package com.renyu.changeskindemo

import android.app.Application
import com.renyu.changeskindemo.skin.SkinEngine

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinEngine.instance.init(this)
    }
}