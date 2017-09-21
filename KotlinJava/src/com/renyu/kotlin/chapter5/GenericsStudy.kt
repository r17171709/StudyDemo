package com.renyu.kotlin.chapter5

fun main(args: Array<String>) {

    val a:A<Int> = A(12)
    println(a.getValue())
    a.printlnValue()
    a.printlnValue2(132)

    val b1: B<Any> = object : B<String> {}

    val from: Array<Int> = arrayOf(1, 2, 4)
    val to: Array<Any> = Array(3) {}
    copy(from, to)
    copy2(from, to)

    getElementOne(F())
    getElementTwo(F())


    val list: MutableList<Number> = MutableList<Number>(5) {
        0
    }
    list.add(1)
    val list2: MutableList<Int> = MutableList<Int>(5) {
        0
    }
    list.addAll(list2)

    var g=G<Number>()
    var g1=G<Int>()
    g=g1
    add(g1)
    add(g)
}

class A<T>(val t: T) {

    fun printlnValue() {
        println("$t")
    }

    fun getValue() : T {
        return t
    }

    fun <T> printlnValue2(t1: T) {
        println("$t1")
    }
}

interface B<out T>

fun copy(from: Array<out Any>, to: Array<Any>) {
    for (index in from.indices) {
        to[index]=from[index]
    }
}

fun copy2(from: Array<Int>, to: Array<in Int>) {
    for (index in from.indices) {
        to[index]=from[index]
    }
}

interface C<in T, out R> {
    fun a() : R
    fun b(params: T)
}

fun star1(c: C<String, *>) {
    c.b("123")
    c.a()
}

fun star2(c: C<*, Int>) {
    c.a()
}

fun star3(c: C<*, *>) {
    c.a()
}

fun <T> getValue(t: T) {}
fun <T> T.baseToString() : String {
    return this.toString()
}

abstract class D {
    abstract fun printlnD()
}

interface E {
    fun printlnE()
}

class F : D(), E {
    override fun printlnD() {

    }

    override fun printlnE() {

    }
}

fun <T> getElementTwo(t: T)
        where T: D,
              T: E {
    t.printlnD()
    t.printlnE()
}

fun <T: D> getElementOne(t: T) {
    t.printlnD()
}

class G<out T>
fun add(g: G<Number>) {}

class H<in T> {
    fun printlnValue(t: T) {}
}

abstract class I<out T> {
    abstract fun printlnValue() : T
}

class J : I<Number>() {
    override fun printlnValue(): Number {
        return 1
    }
}