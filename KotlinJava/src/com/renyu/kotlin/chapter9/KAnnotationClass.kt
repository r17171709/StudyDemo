package com.renyu.kotlin.chapter9

fun main(args: Array<String>) {
    val class_ = Class.forName("com.renyu.kotlin.chapter9.K2AnnotationClass")
    val annotations = class_.getAnnotation(MyKAnnotation2::class.java)
    println("${annotations.kan.name} + ${annotations.kan.age}")
}

@MyKAnnotation(name = "Hello", age = 20)
class KAnnotationClass @MyKAnnotation(name = "Hello KAnnotationClass", age = 20) constructor(@field:MyKAnnotation(name = "Hello KAnnotationClass", age = 20) val value_: String) {
    @MyKAnnotation(name = "WORLD", age = 30)
    val value = 10
    @MyKAnnotation(age = 10)
    fun function() {}
}

@MyKAnnotation2(kan = MyKAnnotation("old", 100))
class K2AnnotationClass @MyKAnnotation(name = "Hello KAnnotationClass", age = 20) constructor(@field:MyKAnnotation(name = "Hello KAnnotationClass", age = 20) val value_: String)