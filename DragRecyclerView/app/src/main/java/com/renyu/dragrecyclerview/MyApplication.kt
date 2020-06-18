package com.renyu.dragrecyclerview

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Administrator on 2020/6/18.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Utils.init(this)
        Fresco.initialize(this)
    }
}