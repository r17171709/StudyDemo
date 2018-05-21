package com.renyu.databindingdemo.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import android.widget.Toast
import com.renyu.databindingdemo.BR
import com.renyu.databindingdemo.MulTypeImpl
import com.renyu.databindingdemo.R

class User : BaseObservable(), MulTypeImpl {
    var id: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }
    var name: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
    var blog: String? = null
        @Bindable
        get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.blog)
        }

    fun click(view: View) {
        Toast.makeText(view.context, "value", Toast.LENGTH_LONG).show()
    }

    override fun getLayoutResouse(): Int {
        return if (id!!.toInt() > 10) {
            R.layout.adapter_rv2
        }
        else {
            R.layout.adapter_rv21
        }
    }
}