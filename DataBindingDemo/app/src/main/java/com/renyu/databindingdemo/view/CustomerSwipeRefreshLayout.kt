package com.renyu.databindingdemo.view

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView

class CustomerSwipeRefreshLayout : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    companion object {
        var isRefreshing = false

        @JvmStatic
        @BindingAdapter(value = ["refresh"], requireAll = false)
        fun setRefresh(view: CustomerSwipeRefreshLayout, refresh: Boolean) {
            if (isRefreshing == refresh) {
                return
            }
            isRefreshing = refresh
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "refresh", event = "displayAttrChanged")
        fun getRefresh(view: CustomerSwipeRefreshLayout): Boolean {
            return isRefreshing
        }

        @JvmStatic
        @BindingAdapter(value = ["displayAttrChanged"], requireAll = false)
        fun setChangeListener(view: CustomerSwipeRefreshLayout, listener2: InverseBindingListener) {
            view.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isRefreshing = true
                        listener2.onChange()
                    }
                    MotionEvent.ACTION_UP -> {
                        isRefreshing = false
                        listener2.onChange()
                    }
                }
                return@setOnTouchListener true
            }
        }
    }
}