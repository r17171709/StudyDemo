package com.renyu.kotlin.chapter3

fun main(args: Array<String>) {
    var b2=B2("a", "b", "c")
    println(b2.value)
    var b3=B3("")

    println(C.instance.getValue())
    println(C.getCompanionValue())

    val q = Q("Hello")
    println(q.toString())
    val r: Q = q.copy(value = "World")
    println(r.toString())
    r.component1()

    val (value1, value2, value3) = R(value = "0", value1 = "1", value2 = "2")
    println("$value1, $value2, $value3")

    val r2 = Triple("0", "1", "2")
    println("${r2.first}, ${r2.second}, ${r2.third}")
    println("${r2.component1()}, ${r2.component2()}, ${r2.component3()}")

    choiceClass(S.BeanA("a"))
    choiceClass(S.BeanB("b"))

    val t1=T.T1()
    t1.printValue()
    val t2=T().T2()
    t2.printValue()

    val u=U()
    u.addImpl1(impl = object : Impl1 {
        override fun printValue1() {

        }

        override fun printValue2() {

        }
    })

    val v=V.Left
    for (value in W.values()) {
        println("${value.name}--${value.ordinal}")
    }
    W.left.getDirection()
    X.READING.printValue()
    println(W.valueOf("left").ordinal)

    z()

    a1.printlnValue()

    efg1("1")("2")("3")
}

class A {

}

class B(value: String) {
    var value_:String? = null
    var value__:String = value
    init {
        value_=value
    }
}

class B2(val value: String) {
    constructor(value: String, value1: String) : this(value) {
        println("constructor2")
    }
    constructor(value: String, value1: String, value2: String) : this(value, value1) {
        println("constructor3")
    }
    init {
        println("init")
    }
}

class B3 {
    var value_:String? = null
    constructor(value: String) {
        value_=value
    }
}

class C private constructor() {
    companion object Factory{
        @JvmStatic val instance: C=C()

        @JvmStatic fun getCompanionValue() {
            println("getCompanionValue")
        }
    }

    fun getValue() {
        println("getValue")
    }
}

open class SuperClass {
    constructor(value: String)
    constructor(value: String, value1: String)
}
open class SuperClassA(val value: String)


class D(value1: String, val value2: String) : SuperClassA(value1)

class E : SuperClass {
    constructor(value: String) : super(value)
    constructor(value: String, value1: String) : super(value, value1)
}
class E2 : SuperClass {
    constructor(value: String) : super(value)
    constructor(value: String, value1: String) : this(value)
}

open class SuperClassB {
    open fun getValue() : String {
        return "1"
    }
}
open class F : SuperClassB() {
    final override fun getValue() : String {
        return "1"
    }
}

class G : F()

class I

open class SuperClassC {
    open var i:I? = null
    open var a: String = "123"
    open val b: String = "456"
    open val c: String = "789"
}

class H : SuperClassC() {
    override var a: String
        get() = super.a
        set(value) {}

    override val b: String
        get() = super.b

    override var c: String
        get() = super.c
        set(value) {}

    override var i: I?
        get() = super.i
        set(value) {}
}

class K(override var i: I?, override var a: String, override val b: String, override val c: String) : SuperClassC()

open class M {
    open fun getValue() : String {
        return "1"
    }
}

interface N {
    fun getValue() : String {
        return "2"
    }
}

class J : M(), N {
    override fun getValue(): String {
        super<M>.getValue()
        super<N>.getValue()
        return "J"
    }
}

abstract class O : M() {
    override abstract fun getValue(): String
}

abstract class P : N {
    override abstract fun getValue(): String
}

data class Q(val value: String)

data class R(val value: String, val value1: String, val value2: String)

sealed class S {
    open fun printInfo(value: String) {
        println(value)
    }
    data class BeanA(val value: String) : S()
    data class BeanB(val value: String) : S()
    object isNULL : S()
}

fun choiceClass(s: S) {
    when(s) {
        is S.BeanA -> s.printInfo(s.value)
        is S.BeanB -> s.printInfo(s.value)
        S.isNULL -> s.printInfo("is null")
    }
}

class T {
    private val VALUE="value"
    class T1 {
        fun printValue() {
            println("拿不到VALUE")
        }
    }
    inner class T2 {
        fun printValue() {
            this@T.VALUE
            println(VALUE)
        }
    }
}

class U {
    private var impl: Impl1? = null
    fun addImpl1(impl: Impl1) {
        this.impl=impl
    }
}

interface Impl1 {
    fun printValue1()
    fun printValue2()
}

enum class V {
    Left, top, right, bottom
}

enum class W(val intValue:Int) {
    left(1), top(2), right(3), bottom(4);

    fun getDirection() = intValue
}

enum class X {
    WRITTING {
        override fun printValue() {
            println("WRITTING")
        }
    },
    READING {
        override fun printValue() {
            println("READING")
        }
    };

    abstract fun printValue()
}

open class Y {
    internal val a="abc"
}

fun z() {
    val obj = object  {
        val a="11"
        val b="22"
        fun printlnValue() {
            println("hello")
        }
    }

    obj.printlnValue()
    println("${obj.a} + ${obj.b}")
}

class Z {
    private fun b()=object {
        val c="123"
    }

    val d=b().c
}

object a1 : SuperClass("123"), N {
    fun printlnValue() {
        println("123")
    }

    override fun getValue(): String {
        return super.getValue()
    }
}

fun efg(a: String, b: String, c: String) {
    println("$a + $b + $c")
}

fun efg1(a: String) = fun(b: String) = fun(c: String) {
    efg(a, b, c)
}