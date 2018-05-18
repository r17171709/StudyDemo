package com.renyu.architecturedemo.mvvm.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<T : ViewDataBinding, M : Any, VM : Any> : AppCompatActivity() {

    abstract fun initViews(): Int
    abstract fun variableId(): Int
    abstract fun model(): M
    abstract fun viewModel() : VM
    abstract fun initParams()
    abstract fun loadData()

    var viewDataBinding: T? = null
    var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, initViews())
        viewDataBinding?.setVariable(variableId(), model())

        viewModel = viewModel()

        initParams()
        loadData()
    }
}