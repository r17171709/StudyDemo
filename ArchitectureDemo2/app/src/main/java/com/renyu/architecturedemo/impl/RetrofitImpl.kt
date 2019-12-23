package com.renyu.architecturedemo.impl

import com.renyu.architecturedemo.bean.DataBean
import com.renyu.architecturedemo.bean.ValueResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitImpl {
    @GET("v2/5dfc33843100003700d2bdb1")
    fun getNews(): Call<ValueResponse<DataBean>>
}