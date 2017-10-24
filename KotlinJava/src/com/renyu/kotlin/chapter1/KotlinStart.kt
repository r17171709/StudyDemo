package com.renyu.kotlin.chapter1

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    // 变量与常量
    var a: Int = 3
    var a1 = 2
    var b = "b"
    val c = "c"
    var d: Int
    d=3
    // 使用字符串模板
    var value = "$a+$a1=${a+a1}"
    println(value)

    // 使用可空值
    var valueNull: String? = null
    println(valueNull)

    // 使用for循环遍历数组
    var arrays:Array<String> = arrayOf("a", "b", "c")
    for (array in arrays) {
        println(array)
    }
    for (index in arrays.indices) {
        println(index)
    }

    // 使用while循环
    var index = 0
    while (index<arrays.size) {
        println(arrays[index])
        index++
    }

    describe(1000)

    // 使用区间
    for (i in 0 until 100) {
        print(i)
    }
    println("")
    for (i in 0..100 step 2) {
        print(i)
    }
    println("")
    for (i in 9 downTo 0 step 3) {
        print(i)
    }

    // 使用集合
    var lists = listOf<String>("a", "b", "c")
    for (list in lists) {
        println(list)
    }
    if ("a" in lists) {
        println("yes")
    }

    // 使用数据类
    val params= hashMapOf<String, String>("key1" to "value1", "key2" to "value2")
    var data=RequestBean("http://www.baidu.com", params)
    println(data.component1())
    println(data.toString())

    for ((k, v) in params) {
        println("$k-$v")
    }

    println(params["key1"])
    params["key1"]="key3"
    println(params["key1"])

    // 函数默认值参数
    getTime()

    // 延迟属性
    val lazyClass=LazyClass()
    println(lazyClass.valueA)
    lazyClass.valueB="valueB"
    println(lazyClass.valueB)

    // 扩展函数
    lazyClass.toast("HelloA")

    // 创建单例
    Instance.printValue("HelloB")

    // Elvis操作符
    var file: File? = File("D:\\学习\\Speeding Up Your Android Gradle Builds (Google IO '17).mp4")
    println(file?.name ?: "empty")

    // let
//    file=null
    file?.let {
        println(file?.name)
        println(file?.length())
    }

    // with
    var lazyClass1 = with(lazyClass) {
        valueB = "valueBwith"
        this
    }
    println(lazyClass1.valueB)

    // apply
    lazyClass1 = lazyClass.apply {
        valueB = "valueBapply"
    }
    println(lazyClass1.valueB)
}

// 返回值为Int类型的函数
fun sun(a: Int, b: Int) : Int {
    return a+b
}

// 将表达式作为函数体并且自动推断返回值类型
fun min(a: Int, b: Int) = a-b

// 使用条件表达式
fun max(a: Int, b: Int) : Int {
    if (a>b) {
        return a
    }
    else {
        return b
    }
}

fun max1(a: Int, b: Int) = if (a>b) a else b

// 适用类型检测及自动类型转换
fun getStringLength(value: Any?) : Int? {
    if (value is String) {
        return value.length
    }
    return null
}

// 使用When表达式
fun describe(value: Any?) {
    when (value) {
        in 0..100 -> println("比101小")
        else -> println("比100大")
    }
}

// 使用数据类
data class RequestBean(val url: String, val params: HashMap<String, String>)

// 函数默认值参数
fun getTime(time: Date = Date()) {
    println(time.time)
}

// 延迟属性
class LazyClass {
    val valueA: String by lazy {
        "Hello World"
    }
    lateinit var valueB: String
}

// 扩展函数
fun LazyClass.toast(value: String) {
    println("$value+${this.valueA}")
}

// 创建单例
object Instance {
    fun printValue(value: String) {
        println(value)
    }
}