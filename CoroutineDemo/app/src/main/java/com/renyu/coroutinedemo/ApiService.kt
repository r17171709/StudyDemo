package com.renyu.coroutinedemo

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("v3/4135bb48-c283-4f16-9602-f6ddd244e5a1")
    suspend fun test(): TestResponse

    @GET("v3/4135bb48-c283-4f16-9602-f6ddd244e5a1")
    fun test1(): LiveData<ApiResponse<TestResponse>>

    @GET("v3/4135bb48-c283-4f16-9602-f6ddd244e5a1")
    fun test2(): Call<TestResponse>
}