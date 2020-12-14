package com.renyu.coroutinedemo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class CoroutineBusScope : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + SupervisorJob()

    constructor(lifecycleOwner: LifecycleOwner, lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (lifecycleEvent == event) {
                    cancel()
                }
            }
        })
    }

    constructor() {}
}