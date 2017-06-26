package com.renyu.rxjavademo.rxbus

import io.reactivex.Scheduler
import java.lang.reflect.Method

/**
 * Created by Administrator on 2017/6/23.
 */

/**
 * @param threadMode 运行所在线程
 * @param eventClass 事件类型
 * @param method 事件执行方法
 */
data class SubscriberMethod(val threadMode: Scheduler, val eventClass: Class<*>, val method: Method)