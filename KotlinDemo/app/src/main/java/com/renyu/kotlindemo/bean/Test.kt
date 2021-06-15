package com.renyu.kotlindemo.bean

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class Test {
    val delegate1: String by Delegate1()
    var delegate2: Int by Delegate2()
}

class Delegate1 : ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "通过实现ReadOnlyProperty实现  name:${property.name}  thisRef:$thisRef"
    }
}

class Delegate2 : ReadWriteProperty<Any, Int> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return 20
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        println("委托属性  name:${property.name}  value:$value")
    }
}