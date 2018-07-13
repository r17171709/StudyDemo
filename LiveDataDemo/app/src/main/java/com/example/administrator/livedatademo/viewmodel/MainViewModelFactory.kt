package com.example.administrator.livedatademo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.administrator.livedatademo.model.AutoRefresh

class MainViewModelFactory(private val autoRefresh: AutoRefresh) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (ViewModel::class.java.isAssignableFrom(modelClass)) {
            return MainViewModel(autoRefresh) as T
        }
        return super.create(modelClass)
    }
}