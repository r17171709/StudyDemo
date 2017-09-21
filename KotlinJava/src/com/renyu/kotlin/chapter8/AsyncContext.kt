package com.renyu.kotlin.chapter8

import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor
import kotlin.coroutines.experimental.CoroutineContext

class AsyncContext : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return UIContinuation(continuation.context.fold(continuation) {
            continuation: Continuation<T>, element: CoroutineContext.Element ->
            if (element != this && element is ContinuationInterceptor) {
                element.interceptContinuation(continuation)
            }
            else {
                continuation
            }

        })
    }
}