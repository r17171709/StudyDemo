package com.renyu.architecturedemo.work

import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.work.OneTimeWorkRequest

class PullUtils {
    companion object {

        var pullId: UUID? = null

        fun schedulePull() {
            // 任务约束条件
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(true).build()
            // 重复执行的任务
            val pullRequest = PeriodicWorkRequest.Builder(PullWorker::class.java, 15, TimeUnit.MINUTES).setInputData(Data.Builder().putBoolean("permiss", true).build()).build()
//            val pullRequest = OneTimeWorkRequest.Builder(PullWorker::class.java).setInputData(Data.Builder().putBoolean("permiss", true).build()).build()
            WorkManager.getInstance().enqueue(pullRequest)

            // 链式任务
//            val workContinuation1 = WorkManager.getInstance().beginWith(pullRequest).then(pullRequest)
//            val workContinuation2 = WorkManager.getInstance().beginWith(pullRequest).then(pullRequest)
//            WorkContinuation.combine(workContinuation1, workContinuation2).then(pullRequest)

            pullId = pullRequest.id
        }
    }

}