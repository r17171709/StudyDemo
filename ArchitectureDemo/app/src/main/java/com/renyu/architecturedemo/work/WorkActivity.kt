package com.renyu.architecturedemo.work

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.work.WorkManager

class WorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PullUtils.schedulePull()
        WorkManager.getInstance().getStatusById(PullUtils.pullId!!).observe(this, Observer {
            val pulledResult = it?.outputData?.getString("key_pulled_result", "")
            println("返回结果为：$pulledResult")
        })

//        WorkManager.getInstance().cancelWorkById(PullUtils.pullId!!)
    }
}