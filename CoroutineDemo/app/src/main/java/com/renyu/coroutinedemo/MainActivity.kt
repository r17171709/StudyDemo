package com.renyu.coroutinedemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class MainActivity() : AppCompatActivity(), CoroutineScope by MainScope() {
    private val vm by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    // 直接使用MainScope来代替
//    private val job by lazy {
//        Job()
//    }
//
//    // 在Activity中使用协程，需要实现CoroutineScope相应接口
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job // Activity的协程

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", "主线程ID:${mainLooper.thread.id}")

        // runBlocking
        // runBlocking启动的协程任务会阻断当前线程，直到该协程执行结束。当协程执行结束之后，页面才会被显示出来
//        test()

        // launch() 是CoroutineScope的一个扩展函数，CoroutineScope简单来说就是协程的作用范围。launch方法有三个参数：1.协程下上文；2.协程启动模式；3.协程体：block是一个带接收者的函数字面量，接收者是CoroutineScope
//        val job = GlobalScope.launch {
//            delay(1000)
//            Log.d("TAG", "协程执行结束")
//        }

        // suspend函数会将整个协程挂起，而不仅仅是这个suspend函数，也就是说一个协程中有多个挂起函数时，它们是顺序执行的
        // getUserInfo方法将协程挂起，协程中其后面的代码永远不会执行，只有等到getUserInfo挂起结束恢复后才会执行。同时协程挂起后不会阻塞其他线程的执行。setUserInfo没有被挂起所以会跟着getUserToken直接执行
//        GlobalScope.launch {
//            getUserInfo()
//            getUserToken()
//            setUserInfo()
//        }

        // async跟launch的用法基本一样，区别在于：async的返回值是Deferred，将最后一个封装成了该对象。async可以支持并发，此时一般都跟await一起使用
        // async是不阻塞线程的,也就是说getUserInfo和getUserToken是同时进行的
//        GlobalScope.launch {
//            val userinfo = GlobalScope.async {
//                getUserInfo()
//            }
//            val userToken = GlobalScope.async {
//                getUserToken()
//            }
//            val value = userinfo.await() + "  " + userToken.await()
//            Log.d("TAG", "协程执行结束$value")
//        }

        // Retrofit使用
//        vm.getHttpResult()

        // 结合具体的场景，比如结合 Lifecycle，需要使用 lifecycleScope 协程作用域：
//        lifecycleScope.launch {
//            whenResumed {
//                // 代表当前生命周期处于 Resumed 的时候才会执行(选择性使用)
//                getUserInfo()
//            }
//        }

//        lifecycleScope.launch {
//            // collect 函数是一个 suspend 方法，所以它必须发生在协程或者带有 suspend 的方法里面，这也是我为什么在一开始的时候启动了 lifecycleScope.launch
//            createFlow()
//                    // flowOn 使用的参数是协程对应的调度器，它实质改变的是协程对应的线程
//                    // Flow 的消费线程在我们启动协程指定调度器的时候就确认好了，对应着启动协程的调度器。比如在上面的代码中 lifecycleScope 启动的调度器是 Dispatchers.Main，那么 collect 方法就消费在主线程
//                    .flowOn(Dispatchers.IO)
//                    .catch {
//
//                    }
//                    // Flow 中的 onCompletion 对应这 RxJava 中的 onComplete 回调
//                    .onCompletion {
//
//                    }
//                    // 有点类似于懒加载，当我们触发 collect 方法的时候，数据才开始发射
//                    .collect {
//                        Log.d("TAG", "$it")
//                    }
//        }

//        vm.getHttpResult1().observe(this, { t -> t })

//        vm.getHttpRequest2()

//        vm.getHttpRequest3().observe(this, { t ->
//            run {
//                Log.d("TAGTAGTAG", "${Looper.myLooper() == Looper.getMainLooper()}")
//            }
//        })

        val job2 = launch(start = CoroutineStart.LAZY) {
            Log.d("TAG", "launch coroutine : ${Thread.currentThread().name}")
            doBackGround()
            Log.d("TAG", "launch coroutine end : ${Thread.currentThread().name}")
        }
        // 延迟到使用的时候才进行调用
        job2.start()

        lifecycleScope.launch {
            whenCreated {

            }
        }

        lifecycleScope.launchWhenCreated {

        }

        Log.d("TAG", "主线程执行结束")
    }

    override fun onDestroy() {
        super.onDestroy()
//        job.cancel()
        cancel()
    }

    private fun test() {
        runBlocking {
            repeat(8) {
                Log.d("TAG", "test")
                delay(1000)
            }
        }
    }

    private suspend fun getUserInfo(): String {
        delay(2000)
        Log.d("TAG", "getUserInfo")
        return "getUserInfo"
    }

    private suspend fun getUserToken(): String {
        delay(3000)
        Log.d("TAG", "getUserToken")
        return "getUserToken"
    }

    private fun setUserInfo() {
        Log.d("TAG", "setUserInfo")
    }

    private fun createFlow(): Flow<Int> = flow {
        for (i in 0 until 10) {
            // 在创建 Flow 对象的时候我们也需要调用 emit 方法发射数据
            emit(i)
        }
    }

    private suspend fun doBackGround() {
        withContext(Dispatchers.IO) {
            Thread.sleep(3000)
            Log.d("TAG", "background() called ${Thread.currentThread().name}")
        }
    }
}