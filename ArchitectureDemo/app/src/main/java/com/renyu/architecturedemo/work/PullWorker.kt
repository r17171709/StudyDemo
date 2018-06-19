package com.renyu.architecturedemo.work

import androidx.work.Data
import androidx.work.Worker

class PullWorker : Worker() {
    override fun doWork(): WorkerResult {
        val pullResult = "get data back"
        if (inputData.getBoolean("permiss", false)) {
            outputData = Data.Builder().putString("key_pulled_result", pullResult).build()
            return WorkerResult.SUCCESS
        }
        return WorkerResult.FAILURE
    }
}