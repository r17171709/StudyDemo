package com.renyu.kotlin.chapter8

import kotlinx.coroutines.*
import java.lang.Exception

/**
 * 参考文章：https://juejin.cn/post/6935472332735512606/
 */
fun main() {
    // C1,C2不会互相影响，完全独立
//    GlobalScope.launch {
//        // 协程C1
//        GlobalScope.launch {
//            // 协程C2
//            println(" main c2")
//            print(1/0)
//        }
//        Thread.sleep(1000)
//        println(" main c1")
//    }

    // C2和C3是C1的子协程，C2和C3异常会取消C1
//    GlobalScope.launch {
//        // 协程C1
//        coroutineScope {
//            launch {
//                // 协程C2
//                print(1/0)
//                Thread.sleep(1000)
//                println(" main c2")
//            }
//            launch {
//                // 协程C3
//                Thread.sleep(2000)
//                println(" main c3")
//            }
//        }
//        Thread.sleep(1000)
//        println(" main c1")
//    }

    // C2和C3是C1的子协程，C2和C3异常不会取消C1
//    GlobalScope.launch {
//        // 协程C1
//        supervisorScope {
//            launch {
//                // 协程C2
//                Thread.sleep(1000)
//                print(1/0)
//                println(" main c2")
//            }
//            launch {
//                // 协程C3
//                Thread.sleep(2000)
//                println(" main c3")
//            }
//        }
//        println(" main c1")
//    }


//    CoroutineScope(Job()).launch {
//        try {
//            print(1/0)
//        } catch (e: Exception) {
//
//        }
//    }

//    CoroutineScope(Job()).launch {
//        try {
//            // 无法通过外部try-catch子句进行处理
//            launch {
//                print(1 / 0)
//            }
//        } catch (e: Exception) {
//
//        }
//    }

    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Handler $throwable in CoroutineExceptionHandler")
    }
//    CoroutineScope(Job()).launch {
//        // 给子协程设置CoroutineExceptionHandler是没有效果的，我们必须给顶级协程设置，或者初始化Scope时设置才有效
//        launch(coroutineExceptionHandler) {
//            print(1 / 0)
//        }
//    }
    // 为了使CoroutineExceptionHandler起作用，必须将其设置在CoroutineScope或顶级协程中
//    CoroutineScope(Job() + coroutineExceptionHandler).launch {
//        launch {
//            print(1 / 0)
//        }
//    }

    // 从launch开始的协程的返回类型是Job，它只是协程的一种表示形式，没有返回值
    // 如果我们需要协程的某些结果，则必须使用async，它返回Deferred，这是一种特殊的Job，另外还保存一个结果值
    // 如果异步协程失败，则将该异常封装在Deferred返回类型中，并在我们调用suspend函数.await()来检索其结果值时将其重新抛出
    val deferred = CoroutineScope(SupervisorJob()).async {
        print(1 / 0)
    }
//    CoroutineScope(SupervisorJob()).launch {
//        try {
//            deferred.await()
//        } catch (e: Exception) {
//
//        }
//    }
//    CoroutineScope(SupervisorJob() + coroutineExceptionHandler).launch {
//        deferred.await()
//    }

//    CoroutineScope(Job()).launch {
//        // 范围函数coroutineScope {} 重新抛出其失败的子协程的异常，而不是将其传播到Job层次结构中，这使我们能够使用try-catch处理失败的协程的异常
//        try {
//            coroutineScope {
//                launch {
//                    print(1 / 0)
//                }
//            }
//        } catch (e: Exception) {
//
//        }
//    }

    // 范围函数supervisorScope {}在Job层次结构中添加了一个新的独立子范围，并将SupervisorJob作为这个scope的'job'。这个新作用域不会在“Job层次结构”中传播其异常，因此它必须自行处理其异常


//    Thread.sleep(4000)
//    println("main")

    val a = CoroutineBuilder<String>().apply {
        onSuccess {
            println("onSuccess")
        }

        onError {
            println("onError")
        }

        onRequest {
            println("onRequest")
            return@onRequest "onRequest"
        }
    }
    CoroutineScope(Job()).rxLaunch(a)
}

fun CoroutineScope.rxLaunch(builder: CoroutineBuilder<String>) {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        builder.onError?.invoke(throwable)
    }
    launch(coroutineExceptionHandler) {
        val value = builder.onRequest?.invoke()
        value?.let {
            builder.onSuccess?.invoke(it)
        }
    }
}

class CoroutineBuilder<T> {
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
    var onRequest: (suspend () -> T)? = null

    fun onSuccess(block: (T) -> Unit) {
        onSuccess = block
    }

    fun onError(block: (Throwable) -> Unit) {
        onError = block
    }

    fun onRequest(block: suspend () -> T) {
        onRequest = block
    }
}