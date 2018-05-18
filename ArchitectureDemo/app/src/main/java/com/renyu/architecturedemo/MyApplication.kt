package com.renyu.architecturedemo

import android.app.Application
import com.renyu.architecturedemo.dagger2.component.DaggerAppComponent
import com.renyu.architecturedemo.dagger2.module.AppModule
import okhttp3.OkHttpClient
import javax.inject.Inject

class MyApplication : Application() {
    @JvmField
    @Inject
    var client: OkHttpClient? = null

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().appModule(AppModule(this)).build().injectApp(this)
    }
}