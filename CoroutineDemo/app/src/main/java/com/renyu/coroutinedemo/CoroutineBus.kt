package com.renyu.coroutinedemo

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch


var channel2 = BroadcastChannel<Event<Any>>(Channel.BUFFERED)

inline fun <reified T> LifecycleOwner.receiveEvent(noinline block: suspend CoroutineScope.() -> Unit) {
    CoroutineBusScope(this).launch {
        for (bus in channel2.openSubscription()) {
            if (bus.event is T) {
                block()
            }
        }
    }
}

fun sendEvent(event: Event<Any>) {
    CoroutineBusScope().launch {
        channel2.send(event)
    }
}