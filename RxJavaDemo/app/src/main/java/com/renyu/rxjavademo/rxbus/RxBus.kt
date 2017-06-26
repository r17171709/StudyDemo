package com.renyu.rxjavademo.rxbus

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.Disposable

/**
 * Created by Administrator on 2017/6/23.
 */
class RxBus private constructor(){

    private val mRelay: Relay<Any>
    private val mDisposableMap: MutableMap<Class<*>, MutableMap<Class<*>, Disposable>>

//    val subject: Subject<Any> = PublishSubject.create<Any>().toSerialized()
//    val publishProcessor: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()
//    val relay: Relay<Any> = PublishRelay.create<Any>().toSerialized()

    init {
        mRelay = PublishRelay.create<Any>().toSerialized()
        mDisposableMap = mutableMapOf()
    }

    /**
     * 注册事件
     * @param targetClass 观察者所在的类
     */
    fun regist(targetClass: Class<*>) {
        for (subscriberMethod in SubscriberMethodFinder.findUsingReflection(targetClass)) {
            addSubscriber(targetClass, subscriberMethod)
        }
    }

    /**
     * 添加事件
     * @param targetClass 观察者所在的类
     * @param subscriberMethod 事件模型
     */
    private fun addSubscriber(targetClass: Class<*>, subscriberMethod: SubscriberMethod) {
        synchronized(RxBus::class.java) {
            val eventClass: Class<*> = subscriberMethod.eventClass
            val disposable: Disposable = mRelay.ofType(eventClass).observeOn(subscriberMethod.threadMode).subscribe({
                subscriberMethod.method.invoke(targetClass.newInstance(), it)
            })
            var map: MutableMap<Class<*>, Disposable>
            if (mDisposableMap.get(targetClass)==null) {
                map = mutableMapOf()
                mDisposableMap.put(targetClass, map)
                map.put(eventClass, disposable)
            }
            else {
                map = mDisposableMap.get(targetClass)!!
                map.put(eventClass, disposable)
            }
        }
    }

    /**
     * 发射事件
     */
    fun post(obj: Any) {
        synchronized(RxBus::class.java) {
            if (mRelay.hasObservers()) mRelay.accept(obj)
        }
    }

    /**
     * 解绑事件
     */
    fun unregist(targetClass: Class<*>) {
        synchronized(RxBus::class.java) {
            if (mDisposableMap.get(targetClass) != null) {
                val map: MutableMap<Class<*>, Disposable> = mDisposableMap.get(targetClass)!!
                for ((key, value) in map) {
                    value.dispose()
                }
                mDisposableMap.remove(targetClass)
            }
            else {
                throw RxbusException("${targetClass::class.java} haven't registered RxBus")
            }
        }
    }

    /**
     * 解绑事件
     */
    fun unregist(targetClass: Class<*>, eventClass: Class<*>) {
        synchronized(RxBus::class.java) {
            if (mDisposableMap.get(targetClass) != null) {
                val map: MutableMap<Class<*>, Disposable> = mDisposableMap.get(targetClass)!!
                if (map.containsKey(eventClass) != null) {
                    map.get(eventClass)!!.dispose()
                    map.remove(eventClass)
                }
                else {
                    throw RxbusException("The event with type of ${eventClass::class.java} is not" +
                            " required in ${targetClass::class.java}")
                }
            }
            else {
                throw RxbusException("${targetClass::class.java} haven't registered RxBus")
            }
        }
    }

    private object Instance {
        val RXBUS = RxBus()
    }

    companion object {
        val default: RxBus
            get() = Instance.RXBUS
    }
}