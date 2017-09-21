package com.renyu.kotlin.chapter8

import javax.swing.SwingUtilities
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext

class UIContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    override fun resume(value: T) {
        SwingUtilities.invokeLater {
            continuation.resume(value)
        }
    }

    override fun resumeWithException(exception: Throwable) {
        SwingUtilities.invokeLater {
            continuation.resumeWithException(exception)
        }
    }
}