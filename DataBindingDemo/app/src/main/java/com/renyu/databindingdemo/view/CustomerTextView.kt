package com.renyu.databindingdemo.view

import android.content.Context
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.Toast

@BindingMethods(BindingMethod(type = AppCompatTextView::class, attribute = "toast", method = "showToast"))
class CustomerTextView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun showToast(value: String) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }
}