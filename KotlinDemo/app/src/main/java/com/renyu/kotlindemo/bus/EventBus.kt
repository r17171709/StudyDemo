package com.renyu.kotlindemo.bus

import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

object EventBus : CoroutineScope {
    private val contextMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()

    fun <T> register(contextName: String, eventDispatcher: CoroutineDispatcher, eventClass: Class<T>, eventCallback: (T) -> Unit) {
        val eventDataMap = if (contextMap.contains(contextName)) {
            contextMap[contextName]
        } else {
            val tmp = mutableMapOf<Class<*>, EventData<*>>()
            contextMap[contextName] = tmp
            tmp
        }
        eventDataMap!![eventClass] = EventData(this, eventDispatcher, eventCallback)
    }

    fun post(eventClass: Any, delayTime: Long = 0) {
        if (delayTime > 0) {
            launch {
                delay(delayTime)
                postEvent(eventClass)
            }
        } else {
            postEvent(eventClass)
        }
    }

   private fun postEvent(eventClass: Any) {
        val tmp = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        tmp.putAll(contextMap)

        for ((_, eventDataMap) in tmp) {
            eventDataMap.keys.firstOrNull {
                it == eventClass.javaClass || it == eventClass.javaClass.superclass
            }?.let {
                eventDataMap[it]?.postEvent(eventClass)
            }
        }
    }

    fun unregisterAllEvents() {
        coroutineContext.cancelChildren()
        for ((_, eventDataMap) in contextMap) {
            eventDataMap.values.forEach {
                it.cancel()
            }
            eventDataMap.clear()
        }
        contextMap.clear()
    }

    fun unregister(contextName: String) {
        val tmp = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        tmp.putAll(contextMap)
        tmp[contextName]?.values?.forEach {
            it.cancel()
        }
        tmp[contextName]?.clear()
        contextMap.remove(contextName)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob()
}