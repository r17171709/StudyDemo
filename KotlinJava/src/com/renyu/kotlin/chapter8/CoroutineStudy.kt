package com.renyu.kotlin.chapter8

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

fun main(args: Array<String>) {
    println("协程之前，当前线程处于${Thread.currentThread().name}")
    asyncCalcMd5 {
        println("开始协程，当前线程处于${Thread.currentThread().name}")
        val value = getValue("path")
        println("拿到数据$value，当前线程处于${Thread.currentThread().name}")
    }
    println("协程之后，当前线程处于${Thread.currentThread().name} ")
}

suspend fun getValue(path: String) = suspendCoroutine {
    continuation: Continuation<String> ->
        val continuationNew = UIContinuation(continuation)
        AsyncMd5().execute {
            val value = getMD5(path)
            continuationNew.resume(value)
        }

}

fun getMD5(path: String) : String {
    println("线程耗时操作，当前线程处于${Thread.currentThread().name}")
    Thread.sleep(3000)
    return ""+System.currentTimeMillis()
}