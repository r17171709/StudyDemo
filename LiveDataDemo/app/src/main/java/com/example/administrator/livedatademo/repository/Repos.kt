package com.example.administrator.livedatademo.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.administrator.livedatademo.model.Data1
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.renyu.commonlibrary.network.OKHttpHelper
import com.renyu.commonlibrary.network.OKHttpUtils

object Repos {
    fun getMainResponse(input: String) : LiveData<ArrayList<Data1>> {
        val temp = MutableLiveData<ArrayList<Data1>>()
        OKHttpHelper.getInstance().okHttpUtils.asyncGet(
                "http://www.mocky.io/v2/5b470ea0320000b332301d9f",
                object : OKHttpUtils.RequestListener {
            override fun onSuccess(string: String?) {
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Data1>>(){}.type
                val value = gson.fromJson<ArrayList<Data1>>(string!!, listType)
                temp.value = value
            }

            override fun onError() {
                temp.value = null
            }

            override fun onStart() {

            }
        })
        return temp
    }
}