package com.example.administrator.databindingdemo2.ui.view

import android.content.Context
import android.databinding.*
import android.util.AttributeSet
import android.view.View


class VisibleView3 : View {
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
        fun changedisplayShow(view: VisibleView3, boolean: Boolean) {
            if (boolean) {
                view.visibility = View.VISIBLE
            }
            else {
                view.visibility = View.GONE
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "displayShow", event = "displayShowAttrChanged_random")
        fun getVisib(view: VisibleView3) : Boolean {
            return view.mDisplayShow
        }

        @JvmStatic
        @BindingAdapter(value = ["displayShowAttrChanged_random"], requireAll = false)
        fun setListeners(view: VisibleView3, inverseBindingListener: InverseBindingListener) {
            view.listener = object : OnChangeListener {
                override fun change() {
                    view.mDisplayShow = view.visibility == View.VISIBLE
                    inverseBindingListener.onChange()
                }
            }
        }
    }
}