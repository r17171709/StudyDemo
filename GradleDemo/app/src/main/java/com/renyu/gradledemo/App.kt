package com.renyu.gradledemo

import android.app.Application
import com.renyu.route_runtime.Route

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Route.init()
    }
}