package com.renyu.kotlin.chapter2

import com.renyu.kotlin.chapter2.b.APP as BAPP
import com.renyu.kotlin.chapter2.a.APP

fun main(args: Array<String>) {
    // 数值常量字面值中的下划线
    val a: Int=10000
    val b: Int=10_000
    println(a == b)

    // 装箱与拆箱
    val boxA: Int? = a
    var anotherBoxA: Int? = a
    println(boxA == anotherBoxA)
    println(boxA === anotherBoxA)

    // 显式转换
    val c: Long = a.toLong()

    // 数组
    var array: Array<String> = arrayOf("1", "2", "3")
    array[0]
    println(array[0])
    array[1]="4"
    println(array[1])
    var array1 = arrayOfNulls<String>(5)
    var array2 = Array(5) {
        "$it+Demo"
    }
    for (s in array2) {
        println(s)
    }

    // 字符串
    val text1="Hello \n"
    var text= """
        |ABC""
        |1+2=${'\n'}
        |DEF
        |GHI
        """
    println(text.trimMargin())
    text="World"
    // 字符串模板
    val text12="Hello $text"
    println(text12)
    println("1+2=${1+2}")

    // 包冲突
    val appa: APP = APP()
    println(appa.value)
    val appb: BAPP = BAPP()
    println(appb.value)

    // if表达式
    var a1=10
    var b2=20
    var maxValue: Int = if (a1>b2) {
        println(a1)
        a1
    } else {
        println(b2)
        b2
    }
    println(maxValue)

    // when表达式
    println(getValue(5))

    var maxValue1: Int = when {
        (a1>b2) -> {
            a1
        } else -> {
            b2
        }
    }
    println(maxValue1)

    // 返回和跳转
    var array3: Array<String> = arrayOf("1", "2", "3", "4", "5")
    loop@ for (s in array3) {
        println("1. $s")
        for (i in  0..3) {
            println("2. $i")
            if (i==2) {
                break@loop
            }
        }
    }

    foo()

    printInfo(1 ,2)
    printInfo("1", "2")
}

fun getValue(value: Int) : String {
    return when(value) {
        1,3 -> "a"
        2 -> "b"
        "5".toInt() -> "e"
        else -> "other"
    }
}

fun foo() {
    var array: Array<String> = arrayOf("1", "2", "3", "4", "5")
    array.forEach {
        if (it == "2") {
            return@forEach
        }
        println("$it")
    }
    println("结束")
}

fun <T> printInfo(a: T, b: T) {
    println("$a + $b")
}