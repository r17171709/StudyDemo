package com.renyu.workmanagerdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 构建单次运行的后台任务请求
        val request = OneTimeWorkRequest.Builder(WorkManagerTest::class.java).build()
        // 构建周期性运行的后台任务请求, 为了降低设备的性能功耗，运行周期间隔不得少于15分钟
        val request2 =
            PeriodicWorkRequest.Builder(WorkManagerTest::class.java, 15, TimeUnit.MINUTES).build()
        // 系统在合适的时间调用两个后台任务
        WorkManager.getInstance(this).enqueue(request2)
        // 任务唯一性
        // REPLACE：如果有现有的等待(未完成)使用相同的惟一名称，取消和删除它。然后插入现有的任务。
        // KEEP：如果有现有的等待(未完成)使用相同的惟一名称,则什么也不做。否则,插入现有的任务。
        // APPEND：如果有现有的等待(未完成)使用相同的惟一名称附加的, 新指定的工作作为一个孩子的所有的叶子工作序列。否则，插入现有的任务开始一个新的序列。
        WorkManager.getInstance(this).beginUniqueWork("onlyOne", ExistingWorkPolicy.KEEP, request)
            .enqueue()

        // setRequiresBatteryNotLow：是否为低电量时运行
        // setRequiredNetworkType：网络连接设置
        // setRequiresCharging：是否在充电情况下运行
        // setRequiresDeviceIdle：设备是否为空闲
        // setRequiresStorageNotLow：设备可用存储是否不低于临界阈值
        val constrains = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(false).build()
        val data = Data.Builder().putString("LiveDataTest", "有生命的数据监听··").build()
        val request3 =
            OneTimeWorkRequest.Builder(WorkManagerWithDataTest::class.java).addTag("test")
                .setInputData(data)
                .setConstraints(constrains)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS).build()
        WorkManager.getInstance(this).enqueue(request3)

        // 监听后台任务的状态变化
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request3.id)
            .observe(this,
                { t ->
                    run {
                        // 获取状态和数据
                        Log.d("TAG", "observe:${t?.state}")
                        Log.d("TAG", t?.outputData?.getString("LiveDataTest") ?: "空")
                    }
                })

        // WorkManager链式调用
//        WorkManager.getInstance(this).beginWith(request).then(request3).enqueue()

        // 通过id取消任务
//        WorkManager.getInstance(this).cancelWorkById(request.id)
        // 通过tag取消任务
//        WorkManager.getInstance(this).cancelAllWorkByTag("test")

    }
}