package com.renyu.architecturedemo.mvvm.vm

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import com.renyu.architecturedemo.mvvm.net.NetRequest
import org.greenrobot.eventbus.EventBus

class SearchViewModel {
    fun getRemoteValue() {
        NetRequest.getInstance().getRequestInfo().subscribe({
            val type = object : TypeToken<List<SearchBeans>>() {}.type
            val value = Gson().fromJson<List<SearchBeans>>(it, type)
            EventBus.getDefault().post(value)
        }, {

        }, {

        })
    }
}