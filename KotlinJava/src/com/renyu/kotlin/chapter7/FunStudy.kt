package com.renyu.kotlin.chapter7

fun main(args: Array<String>) {

    val value = 0b1111 or 0b1100
    println(value)
    val value1 = 0b1111.or(0b1100)
    println(value1)

    println(3.min(2))
    println(3 min 2)

    a()
    a(3)

    a(value = 4)

    val count = justCount()
    count()
    count()
    count()

    var list = arrayOf(2, 3, 5)
    var list2 = arrayOf(1, *list, 4)

    d(2, 1, 2, 4, other = 1)

    println(f(0, 10, 0))
    println(f1(0, 10, 0))

    val filterList = list.filter { it>2 }
    for (i in filterList) {
        println(i)
    }

    g(1, 2) {_, _ ->
        println("没有值进来")
    }

    val filterListTemp = list.filter { return@filter it>2 }

    val filterListTemp2 = list.filter(fun(value: Int) : Boolean {return value>2})

    var sum=0
    list.forEach {
        value -> sum+=value
    }
    println(sum)

    val funH= (fun Int.(other: Int): Int {
        return this+other
    })
    h(1, 2, funH)
    h(1, 2) {
        other -> this+other
    }

    val map = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")
    for ((key, value) in map) {
        println("$key + $value")
    }
    map.map { entry: Map.Entry<String, String> -> { "${entry.key} + ${entry.value}"} } .forEach { println(it) }
    map.map { (key, value) -> { "$key + $value"} } .forEach { println(it) }
    val mapNew=map.map { it.value } .forEach { println(it) }

    k("123")

//    i(funValue = fun (it) { println("$it") })
    i {
        println("${it+100}")
    }

    val funValue: (Int) -> Unit = {
        println("${it+100}")
    }
    i(funValue)

    i {
        println("${it+100}")
        return
    }

    i1 {
        println("${it+100}")
        return@i1
    }
}

infix fun Int.min(value: Int) : Int {
    return if (this > value) value else this
}

fun a(value: Int = 2) {
    println("$value")
}

open class A {
    open fun a(value: Int = 1) {}
}

class B : A() {
    override fun a(value: Int) {
        super.a(value)
    }
}

fun c() = 1+1

fun d(name: Int, vararg value: Int, other: Int) {

}

fun e1() {
    val e1_value="e1"
    fun e2() {
        println(e1_value)
    }
}

tailrec fun f(start: Int, end: Int, result: Int) : Int {
    return if (start >= end) {
        result
    } else {
        f(start+1, end, result+start)
    }
}

fun f1(start: Int, end: Int, result: Int) : Int {
    var start_=start
    var result_=result
    while (start_ < end) {
        result_+=start_
        start_++
    }
    return result_
}

fun <T> g(value1: T, value2: T, funValue: (T, T) -> Unit) {
    funValue(value1, value2)
}

fun h(leftValue: Int, rightValue: Int, sum: Int.(other: Int) -> Int) {
    val value=leftValue.sum(rightValue)
    println("$value")
}

inline fun i(funValue: (Int) -> Unit) {
    println("i fun")
    funValue(3)
}

fun i1(funValue: (Int) -> Unit) {
    println("i fun")
    funValue(3)
}

inline fun j(crossinline runFun : () -> Unit) {
    val runnable = object : Runnable {
        override fun run() = runFun()
    }
}

inline fun <reified T> k(value1: T) {
    println(value1)
    val a="123"
    if (a is T) {
        println("String")
    }
    else{
        println("UNKNOWN")
    }
}

fun justCount():() -> Unit {
    var count = 0
    return {
        println(count++)
    }
}



