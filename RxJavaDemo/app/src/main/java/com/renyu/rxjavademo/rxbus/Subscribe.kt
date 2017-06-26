package com.renyu.rxjavademo.rxbus

/**
 * Created by Administrator on 2017/6/23.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscribe(val threadMode: ThreadMode = ThreadMode.MAIN)