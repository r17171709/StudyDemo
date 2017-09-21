package com.renyu.kotlin.chapter9

fun main(args: Array<String>) {
    val aValue = A()
    val (name, age) = aValue
    println("$name + $age")
    println("${aValue.component1()} + ${aValue.component2()}")

    val (name1: String, age1: Int) = B().getDataB()
    println("$name1 + $age1")

    val map: Map<String, String> = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3", "key4" to "value4")
    for ((key, value) in map) {
        println("$key + $value")
    }
    map.mapValues {  }

    a {
        it -> it.name+" "+it.age
    }
    a {
        (name, age) -> name+" "+age
    }
    a {
        name+" "+age
    }

    var listParent: List<Parent>
    var listChild: List<Child> = mutableListOf()
    listParent=listChild

    val i=2
    if (i in 1..10) {
        println("查询到")
    }
    for (j in 1..10) {
        println(j)
    }
    for (j in 10 downTo 1) {
        println(j)
    }
    for (j in 1..10 step 2) {
        println(j)
    }
    for (j in 1 until 10) {
        println(j)
    }

    val obj: Any? = null
    when (obj) {
        is String -> {

        }
        is Int -> {

        }
        else -> {

        }
    }

    var objs: MutableList<Any?> = mutableListOf()
    objs.add("123")
    objs.add(null)
    for (obj in objs) {
        if (obj is String) {
            obj.length
        }

//        val value = obj as String

//        val value: String? = obj as String?
    }

//    val a: String = null
    val b: String? = null
//    b.length
    val length = b?.length ?: 0
    b?.let {
        val length = b.length
    }
}

fun check() : Int? {
    val b: String? = null
    val length = b?.length ?: return null
    val c: String? = null
    val length2 = c?.length ?: throw Exception()
    return length2
}

private operator fun A.component2(): Int {
    return age
}

class A {
    val name = "Hello"
    val age = 10
    operator fun component1(): String {
        return name;
    }
}

data class DataB(val name1: String, val age1: Int)

class B {
    fun getDataB() : DataB {
        return DataB("World", 20)
    }
}

fun a(funValue: (A) -> String) {
    println(funValue(A()))
}

open class Parent
class Child : Parent()

class C {
    inner class CIn {
        fun Int.a() {
            val b = { a: String -> println(this)}
//            val d = this@a
//            val c = this
//            val b = this@CIn
//            val a = this@C
        }
    }

    val sum = fun Int.(other: Int) : Int = this + other

    fun ca() {
        1.sum(2)
    }
}
