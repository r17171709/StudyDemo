package com.example.administrator.databindingdemo2.ui.view

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.util.AttributeSet
import android.view.View

class VisibleView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var listener: OnChangeListener? = null

    var mDisplayShow = false

    interface OnChangeListener {
        fun change()
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        listener?.change()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("displayShow")
        fun changedisplayShow(view: VisibleView, boolean: Boolean) {
            if (boolean) {
                view.visibility = View.VISIBLE
            }
            else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "displayShow")
        fun getVisib(view: VisibleView) : Boolean {
            return view.mDisplayShow
        }

        @JvmStatic
        @BindingAdapter(value = ["displayShowAttrChanged"], requireAll = false)
        fun setListeners(view: VisibleView, inverseBindingListener: InverseBindingListener) {
            view.listener = object : OnChangeListener {
                override fun change() {
                    view.mDisplayShow = view.visibility == View.VISIBLE
                    inverseBindingListener.onChange()
                }
            }
        }
    }
}