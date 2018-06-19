package com.renyu.architecturedemo.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class ChildrenModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ChildrenModelFactory? = null

        fun getInstance(application: Application) : ChildrenModelFactory {
            if (instance == null) {
                instance = ChildrenModelFactory(application)
            }
            return instance as ChildrenModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChildrenViewModel::class.java)) return ChildrenViewModel(application) as T
        return super.create(modelClass)
    }
}