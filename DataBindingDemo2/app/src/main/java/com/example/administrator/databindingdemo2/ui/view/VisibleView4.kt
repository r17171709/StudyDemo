package com.example.administrator.databindingdemo2.ui.view

import android.content.Context
import android.databinding.*
import android.util.AttributeSet
import android.view.View


@InverseBindingMethods(InverseBindingMethod(type = VisibleView4::class, attribute = "displayShow"))
class VisibleView4 : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    var listener: OnChangeListener? = null

    var displayShow = false

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
        fun changedisplayShow(view: VisibleView4, boolean: Boolean) {
            if (boolean) {
                view.visibility = View.VISIBLE
            }
            else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["displayShowAttrChanged"], requireAll = false)
        fun setListeners(view: VisibleView4, inverseBindingListener: InverseBindingListener) {
            view.listener = object : OnChangeListener {
                override fun change() {
                    view.displayShow = view.visibility == View.VISIBLE
                    inverseBindingListener.onChange()
                }
            }
        }
    }
}