package com.renyu.kotlindemo.bus

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

data class EventData<T>(
    val coroutineScope: CoroutineScope,
    val eventDispatcher: CoroutineDispatcher,
    val onEvent: (T) -> Unit,
    val onException: ((Throwable) -> Unit)? = null
) {
    private val channels = Channel<T>()

    init {
        coroutineScope.launch {
            channels.consumeEach { value ->
                launch(eventDispatcher) {
                    if (onException != null) {
                        try {
                            onEvent(value)
                        } catch (e: Exception) {
                            onException.invoke(e)
                        }
                    } else {
                        onEvent(value)
                    }
                }
            }
        }
    }

    fun postEvent(value: Any) {
        if (!channels.isClosedForSend) {
            coroutineScope.launch {
                channels.send(value as T)
            }
        }
    }

    fun cancel() {
        channels.cancel()
    }
}