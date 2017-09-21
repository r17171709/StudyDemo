package com.renyu.kotlin.chapter8

import kotlin.coroutines.experimental.startCoroutine

fun asyncCalcMd5(block: suspend () -> Unit) {
    block.startCoroutine(BaseContinuation())
}
