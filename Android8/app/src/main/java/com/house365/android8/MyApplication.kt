package com.house365.android8

import android.support.multidex.MultiDexApplication
import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by renyu on 2018/1/31.
 */
class MyApplication : MultiDexApplication() {
    val openClassNames by lazy {
        ArrayList<String>()
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {

            }

            override fun onActivityStarted(activity: Activity?) {
                openClassNames.add(activity!!.localClassName)
            }

            override fun onActivityDestroyed(activity: Activity?) {

            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

            }

            override fun onActivityStopped(activity: Activity?) {
                openClassNames.remove(activity!!.localClassName)
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

            }

        })
    }
}