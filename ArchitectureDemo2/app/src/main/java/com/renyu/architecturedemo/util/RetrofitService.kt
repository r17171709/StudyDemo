package com.renyu.architecturedemo.util

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {
    const val BASE_URL = "http://www.mocky.io/"

    private val builder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .callTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(builder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)
}