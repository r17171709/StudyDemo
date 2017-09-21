package com.renyu.kotlin.chapter4

import com.renyu.kotlin.chapter3.Y

fun main(args: Array<String>) {
    val a=A()
    a.valueA
    a.valueB

    val b=B()
    b.valueC=200
    println(b.valueC)

    ANDROID
    C.XIAOMI
    C.HUAWEI
    D.XIAOMI
    D.HUAWEI

    val e = E()
    e.value="Hello LateInit"
    println(e.value)

    val g=G()
    g.a=1
    println(g.a)

    val i=I()
    i.printlnI1()

    printValue(K())

    println(I().printlnI())

    val list:List<String> = listOf("1", "2", "3")
    println(list.lastValue)

    println(D.printlnValue())
    println(D.ZHONGXING)

    M().addL(L())

    O().printlnValue(L())
}

class A {
    val valueA = 1
    var valueB = 2
    @JvmField var valueC = 3
}

class B {
    val valueA: Int
        get() = 1

    var valueB: Int = 12
        private set

    var valueC: Int = 10
        get() {
            return field
        }
        set(value) {
            field=value+1
        }
}

const val ANDROID: String = "1"

object C {
    const val XIAOMI: String = "2"
    val HUAWEI: String = "3"
}

class D {
    companion object {
        const val XIAOMI: String = "3"
        val HUAWEI: String = "4"
    }
}

fun D.Companion.printlnValue() {
    println(D.XIAOMI)
}

val D.Companion.ZHONGXING
    get() = "5"

class E {
    lateinit var value: String
}

interface F {
    fun getValue()
    fun getValue1() {
        println("getValue1")
    }
    val a: Int
    val b
    get() = "2"
    val c: Int
}

class G : F {
    override val c: Int
        get() = 100

    override fun getValue() {
        getValue1()
    }

    override var a: Int = 0
        get() = field
    set(value) {
        field = b.toInt()*10
    }
}

class H: Y() {
    val b=a
}

class I {
    fun printlnI() {
        println("printlnI")
    }
}

fun I.printlnI1() {
    println("printlnI1")
}

fun I.printlnI() {
    println("printlnI__")
}

open class J
class K: J()
fun J.printlnValue() {
    println("J")
}
fun K.printlnValue() {
    println("K")
}
fun printValue(j: J) {
    j.printlnValue()
}

val <T> List<T>.lastValue : T
        get() = this[this.size-1]

class L {
    fun a() {
        println("L")
    }
}

class M {
    fun a() {
        println("M")
    }

    fun L.printlnValue() {
        a()
//        this@M.a()
    }

    fun addL(l: L) {
        l.printlnValue()
    }
}

open class N {
    open fun L.b() {
        println("N")
    }

    open val L.num
        get() = 100

    fun printlnValue(l: L) {
        l.b()
        println(l.num)
    }
}

class O : N() {
    override fun L.b() {
        println("O")
    }

    override val L.num: Int
        get() = 200
}