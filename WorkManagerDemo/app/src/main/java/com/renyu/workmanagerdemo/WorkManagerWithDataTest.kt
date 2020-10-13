package com.renyu.workmanagerdemo

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkManagerWithDataTest(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        // 获取数据
        val data = Data.Builder().putString(
            "LiveDataTest",
            "LiveDataTest修改后的信息---${inputData.getString("LiveDataTest")}"
        ).build()
        // 任务运行结果，发送数据
        return Result.success(data)
    }
}