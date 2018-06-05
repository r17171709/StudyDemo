package com.renyu.coroutinedemo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import com.renyu.commonlibrary.baseact.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

class MainActivity : BaseActivity() {

    var job: Job = Job()

    override fun setStatusBarColor() = Color.BLACK

    override fun setStatusBarTranslucent() = 0

    override fun loadData() {
        launch(UI, parent = job) {
            showNetworkDialog("正在加载数据")

//            val result = withContext(CommonPool) {
//                val responseBody = httpHelper.okHttpUtils.syncGet("http://www.mocky.io/v2/5943e4dc1200000f08fcb4d4").body()
//                if (responseBody == null) {
//                    throw Exception("出现异常")
//                }
//                else {
//                    responseBody.string()
//                }
//            }

            val deferred = async(CommonPool) {
                val responseBody = httpHelper.okHttpUtils.syncGet("http://www.mocky.io/v2/5943e4dc1200000f08fcb4d4").body()
                if (responseBody == null) {
                    throw Exception("出现异常")
                }
                else {
                    responseBody.string()
                }
            }
            lifecycle.addObserver(CoroutineLifecycleListener(deferred))

            try {
//                tv_main.text = result
                tv_main.text = deferred.await()
            } catch (e: Exception) {

            } finally {
                dismissNetworkDialog()
            }
        }.invokeOnCompletion {
            if (it != null) {
                dismissNetworkDialog()
            }
        }
    }

    override fun initParams() {
    }

    override fun initViews() = R.layout.activity_main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

class CoroutineLifecycleListener<T>(val deferred: Deferred<T>) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine() {
        if (!deferred.isCancelled)
            deferred.cancel()
    }
}