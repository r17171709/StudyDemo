package com.renyu.databindingdemo.view

import android.content.Context
import android.databinding.InverseBindingListener
import android.databinding.InverseBindingMethod
import android.databinding.InverseBindingMethods
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.MotionEvent

@InverseBindingMethods(InverseBindingMethod(type = CustomerRefreshLayout::class, attribute = "refresh", event = "displayAttrChanged"))
class CustomerRefreshLayout : AppCompatTextView {
    var isRefreshing = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setRefresh(refresh: Boolean) {
        if (isRefreshing == refresh) {
            return
        }
        isRefreshing = refresh
    }

    fun getRefresh(): Boolean {
        return isRefreshing
    }

    fun setDisplayAttrChanged(listener: InverseBindingListener) {
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isRefreshing = true
                    listener.onChange()
                }
                MotionEvent.ACTION_UP -> {
                    isRefreshing = false
                    listener.onChange()
                }
            }
            return@setOnTouchListener true
        }
    }
}