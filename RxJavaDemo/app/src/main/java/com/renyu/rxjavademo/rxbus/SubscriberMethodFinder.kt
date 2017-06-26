package com.renyu.rxjavademo.rxbus

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.reflect.Modifier

/**
 * Created by Administrator on 2017/6/23.
 */
object SubscriberMethodFinder {

    val MODIFIERS_IGNORE: Int = Modifier.ABSTRACT.or(Modifier.STATIC).or(Modifier.SYNCHRONIZED)

    fun findUsingReflection(class_: Class<*>): List<SubscriberMethod> {
        var lists: MutableList<SubscriberMethod> = ArrayList()
        for (declaredMethod in class_.declaredMethods) {
            val modifiers: Int = declaredMethod.modifiers
            // 方法必须是public并且方法不能是abstract或者static或者synchronized
            if (modifiers.and(Modifier.PUBLIC)!= 0 && modifiers.and(SubscriberMethodFinder.MODIFIERS_IGNORE) == 0) {
                val parameterTypes: Array<Class<*>> = declaredMethod.parameterTypes
                // 只有一个入参
                if (parameterTypes.size==1) {
                    val subscribe = declaredMethod.getAnnotation(Subscribe::class.java)
                    // 判断是否为自定义注解对象
                    if (subscribe!=null) {
                        val threadMode: ThreadMode = subscribe.threadMode
                        val classType: Class<*> = parameterTypes[0]
                        lists.add(SubscriberMethod(getThreadMode(threadMode), classType, declaredMethod))
                    }
                }
            }
        }
        // 如果没有添加过
        if (lists.size==0) {
            throw RxbusException("Subscriber ${class_::class.java} and its super classes have no public methods with the @Subscribe annotation")
        }
        return lists
    }

    /**
     * 通过自身定义线程类型，获取Rxjava中的线程类型
     */
    fun getThreadMode(threadMode: ThreadMode): io.reactivex.Scheduler =
        when(threadMode) {
            ThreadMode.MAIN -> AndroidSchedulers.mainThread()
            ThreadMode.COMPUTATION -> Schedulers.computation()
            ThreadMode.IO -> Schedulers.io()
            ThreadMode.NEW_THREAD -> Schedulers.newThread()
            ThreadMode.SINGLE -> Schedulers.single()
            ThreadMode.TRAMPOLINE -> Schedulers.trampoline()
            else -> AndroidSchedulers.mainThread()
        }
}