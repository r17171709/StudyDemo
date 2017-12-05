package com.renyu.kotlin.chapter9

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaConstructor
import kotlin.reflect.jvm.javaMethod

fun main(args: Array<String>) {
    val arrays = arrayOf("1", "2", "3")
    for (array in arrays) {
        println(abc(array))
    }
    for (array in arrays) {
        println(array.bcd())
    }
    arrays.forEach {
        getValue(::abc, it)
        getValue(String::bcd, it)
    }

    ::xValue.set(24)
    println(::xValue.get())

    println(String::x1Value.get("author"))

    val funValue = ABC::yValue
    var abc = ABC()
    funValue.set(abc, 12)
    funValue.get(abc)
    println(abc.yValue)


//    val reflectStudyAClass = ReflectStudyA::class.java
//    var reflectStudyA: ReflectStudyA = reflectStudyAClass.getDeclaredConstructor().newInstance()
//    val aValueField = reflectStudyAClass.getDeclaredField("aValue")
//    aValueField.isAccessible = true
//    val aValue = aValueField.get(reflectStudyA)
//    val reflectStudyAMethod = reflectStudyAClass.getDeclaredMethod("toIntValue", String::class.java)
//    reflectStudyAMethod.isAccessible=true
//    reflectStudyAMethod.invoke(reflectStudyA, aValue)

    val reflectStudyAClass = ReflectStudyA::class
    for (constructor in reflectStudyAClass.constructors) {
        var reflectStudyA: ReflectStudyA = constructor.javaConstructor!!.newInstance()
        for (declaredMemberProperty in reflectStudyAClass.declaredMemberProperties) {
            if (declaredMemberProperty.name == "aValue") {
                declaredMemberProperty.isAccessible = true
                val aValue = declaredMemberProperty.get(reflectStudyA)
                for (function in reflectStudyAClass.functions) {
                    if (function.name == "toIntValue") {
                        function.isAccessible = true
                        function.javaMethod!!.invoke(reflectStudyA, aValue)
                    }
                }
            }
        }
    }

//    val reflectStudyBClass = ReflectStudyB::class
//    val reflectStudyB = reflectStudyBClass.createInstance()
//    val aValue_: KMutableProperty1<ReflectStudyB, String> = ReflectStudyB::aValue
//    val aValue = aValue_.get(reflectStudyB)
//    val toIntValueFun = ReflectStudyB::toIntValue
//    toIntValueFun.call(reflectStudyB, aValue)
//
//    var cde: CDE = getCDE(::CDE, "aaa")
//    cde.printValue()
}

fun abc(value: String) : Int {
    return value.toInt()
}

fun String.bcd() : Int {
    return this.toInt()
}

fun getValue(funValue: (String) -> Int, value: String) {
    println(funValue(value))
}

var xValue = 3

val String.x1Value: String
    get() = "Hello "+this

class ABC {
    var yValue = 3
}

class ReflectStudyB {
    var aValue = "234"
    fun toIntValue(value: String) {
        println(value)
    }
}

class CDE {
    var value: String? = null

    constructor(value: String) {
        this.value = value
    }

    fun printValue() {
        println(value)
    }
}

fun getCDE(cde: (String) -> CDE, value: String) : CDE {
    return cde(value)
}