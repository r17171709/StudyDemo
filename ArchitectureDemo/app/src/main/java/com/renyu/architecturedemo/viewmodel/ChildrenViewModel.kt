package com.renyu.architecturedemo.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.renyu.architecturedemo.viewmodel.data.Parent

class ChildrenViewModel(application: Application) : ViewModel() {
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val desp: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
    val parent: MutableLiveData<Parent> by lazy {
        MutableLiveData<Parent>()
    }
    private val family: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // 在通知观察者数据改变之前,改变LiveData中的值类型
    val parentInfo: LiveData<String> = Transformations.map(parent) { input -> "父母名字为${input?.papa+" "+input?.mama}" }

    // 与map对应的是switchMap()方法，这里就是打包。
    val familyInfo: LiveData<String>  = Transformations.switchMap(name) {
        family.value = "来自${it}的家庭"
        family
    }

    // 可以同时监听多个LiveData
    val mediatorLiveData: MediatorLiveData<String> = MediatorLiveData()
}