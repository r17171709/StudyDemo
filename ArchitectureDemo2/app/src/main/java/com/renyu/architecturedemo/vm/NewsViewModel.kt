package com.renyu.architecturedemo.vm

import androidx.lifecycle.MutableLiveData
import com.renyu.architecturedemo.bean.DataBean
import com.renyu.architecturedemo.repos.Repos
import com.renyu.architecturedemo.util.Resource

class NewsViewModel : BaseViewModel() {
    val data = MutableLiveData<Resource<DataBean>>()

    fun getNews() {
        getNetWorkData({
            Repos.getNews()
        }, data)
    }
}