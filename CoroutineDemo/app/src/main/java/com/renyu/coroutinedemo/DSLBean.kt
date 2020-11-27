package com.renyu.coroutinedemo

class DSLBean {
    var dslFunction: (() -> Unit)? = null

    fun dslFunction(func: (() -> Unit)) {
        this.dslFunction = func
    }
}