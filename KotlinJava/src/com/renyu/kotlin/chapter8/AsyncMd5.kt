package com.renyu.kotlin.chapter8

import java.util.concurrent.Executors

class AsyncMd5 {

    private val service by lazy {
        Executors.newSingleThreadExecutor()
    }

    fun execute(funValue: () -> Unit) {
        service.execute { funValue() }
    }
}