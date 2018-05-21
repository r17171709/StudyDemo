package com.renyu.databindingdemo.view

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.util.AttributeSet
import android.view.View

class VisibleView : View {

    var listener: OnViewChangeListener? = null

    fun setOnViewChangeListener(listener1: OnViewChangeListener) {
        listener = listener1
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    interface OnViewChangeListener {
        fun onChange()
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        listener?.onChange()
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["display"], requireAll = false)
        fun setDisplay(view: VisibleView, isDisplay: Boolean) {
            view.visibility = if (isDisplay) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "display", event = "displayAttrChanged")
        fun getDisplay(view: VisibleView): Boolean {
            return view.visibility == View.VISIBLE
        }

        @JvmStatic
        @BindingAdapter(value = ["displayAttrChanged"], requireAll = false)
        fun setChangeListener(view: VisibleView, listener2: InverseBindingListener) {
            view.setOnViewChangeListener(object : OnViewChangeListener {
                override fun onChange() {
                    listener2.onChange()
                }
            })
        }
    }
}