package com.example.administrator.livedatademo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.administrator.livedatademo.adapter.MainAdapter
import com.example.administrator.livedatademo.model.AutoRefresh
import com.example.administrator.livedatademo.model.Data1
import com.example.administrator.livedatademo.repository.Repos

class MainViewModel(private val autoRefresh: AutoRefresh) : ViewModel() {

    val id = MutableLiveData<String>()
    var responses: LiveData<ArrayList<Data1>>? = null

    private var beans = ArrayList<Data1>()

    val adapter: MainAdapter by lazy {
        MainAdapter(beans)
    }

    init {
        responses = Transformations.switchMap(id) { input ->
            if (input == null) {
                MutableLiveData()
            } else {
                Repos.getMainResponse(input)
            }
        }
    }

    fun notifyDataSetChanged(temp: ArrayList<Data1>) {
        beans.addAll(temp)
        autoRefresh.value!!.set(true)
    }
}