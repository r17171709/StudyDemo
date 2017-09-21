package com.renyu.kotlin.chapter6

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main(args: Array<String>) {
    val a2=A2(A1())
    a2.printValue()

    B().value="HI"
    println(B().value)

    val c=C()
    println(c.value)
    println(c.value)

    val d=D()
    d.name="212"
    d.name="213"

    var e=E()
    e.name=1
    println(e.name)
    e.name=0
    println(e.name)

    val f=F(mapOf("name" to "abc", Pair("age", "30")))
    println("${f.name} + ${f.age}")

    g {
        "g"
    }

//    println(H().value)
//    println(H().value1)
//    println(H().value2)
    println(H().value3)
    println(H().value4)
}

interface A {
    fun printValue()
}

class A1 : A {
    override fun printValue() {
        println("A1")
    }
}

class A2(a: A) : A by a

class Delegate1 {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) : String {
        return "$thisRef + ${property.name}"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef + ${property.name} + $value")
    }
}

class Delegate2 : ReadWriteProperty<B, String> {
    override fun getValue(thisRef: B, property: KProperty<*>): String {
        return "$thisRef + ${property.name}"
    }

    override fun setValue(thisRef: B, property: KProperty<*>, value: String) {
        println("$thisRef + ${property.name} + $value")
    }
}

class B {
    var value: String by Delegate1()
}

class C {
    val value: String by lazy {
        println("C")
        "1"
    }
}

class D {
    var name: String by Delegates.observable("no name") { kProperty, s, s1 ->
        run {
            println("$s + $s1")
        }
    }
}

class E {
    var name: Int by Delegates.vetoable(0) {
        property, oldValue, newValue -> newValue>oldValue
    }
}

class F(user: Map<String, String>) {
    val name: String by user
    val age: String by user
}

fun g(lazyFun: () -> String) {
    val lazyFunTemp by lazy(lazyFun)
    println(lazyFunTemp)
}

var extra3=false
var extra4=false

class Delegate3<T>(val lambdaFun: () -> T) : ReadOnlyProperty<H, T> {
    override fun getValue(thisRef: H, property: KProperty<*>): T {
        return lambdaFun()
    }
}

class H {
//    val value: String by h(this, "value") {
//        "$extra3"
//    }
//
//    val value1: String by h(this, "value1") {
//        "$extra4"
//    }
//
//    val value2: String by Delegate3 {
//        "324"
//    }
//
    val value3: String by ResourceLoader {
        "$extra3"
    }

    val value4: String by ResourceLoader {
        "$extra4"
    }
}

fun <T> h(h: H, type: String, lambdaFun: () -> T) : Delegate3<T> {
    if (type == "value") {
        extra4=true
    }
    return Delegate3(lambdaFun)
}

class ResourceLoader<T>(val lambdaFun: () -> T) {
    operator fun provideDelegate(thisRef: H, property: KProperty<*>) : ReadOnlyProperty<H, T> {
        if (property.name == "value3") {
            extra4=true
        }
        return Delegate3(lambdaFun)
    }
}
