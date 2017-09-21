package com.renyu.kotlin.chapter9

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyKAnnotation(val name: String = "Jack", val age: Int)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyKAnnotation2(val kan: MyKAnnotation)