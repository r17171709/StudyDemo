package com.renyu.architecturedemo.viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import com.renyu.architecturedemo.viewmodel.data.Parent

class ChildrenModelActivity : AppCompatActivity() {

    private val nameViewModel by lazy {
//        ViewModelProviders.of(this).get(ChildrenViewModel::class.java)
        // ViewModel带参数
        ViewModelProviders.of(this, ChildrenModelFactory(Utils.getApp())).get(ChildrenViewModel::class.java)
    }

    private val myChildrenViewModel by lazy {
        MyChildrenViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nameViewModel.desp.observe(this, Observer {
            var desp = ""
            it?.forEach {
                desp += it
            }
            println("新增成员性格描述$desp")
        })
        nameViewModel.name.observe(this, Observer {
            println("新增成员姓名$it")
        })
        nameViewModel.parentInfo.observe(this, Observer {
            println(it)
        })
        myChildrenViewModel.observe(this, Observer {
            println("学费$it")
        })
        nameViewModel.familyInfo.observe(this, Observer {
            println(it)
        })
        nameViewModel.mediatorLiveData.observe(this, Observer {
            println(it)
        })
        nameViewModel.mediatorLiveData.addSource(nameViewModel.name) {
            nameViewModel.mediatorLiveData.value = "欢迎你$it"
        }
        Handler().postDelayed({
            val desps = mutableListOf("开心", "活泼")
            // 同步
            nameViewModel.name.value = "ABC"
            // 异步
            nameViewModel.desp.postValue(desps)
            nameViewModel.parent.value = Parent("A", "B")
            myChildrenViewModel.setMyValue(1000)
        }, 1000)

    }
}