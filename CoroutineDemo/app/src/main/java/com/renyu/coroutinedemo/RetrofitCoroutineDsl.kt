package com.renyu.coroutinedemo

import retrofit2.Call

class RetrofitCoroutineDsl<T> {
    var api: Call<T>? = null
    var onSuccess: ((T) -> Unit)? = null
    var onError: ((Throwable) -> Unit)? = null
    var onComplete: (() -> Unit)? = null


    fun onSuccess(block: (T) -> Unit) {
        onSuccess = block
    }

    fun onError(block: (Throwable) -> Unit) {
        onError = block
    }

    fun onComplete(block: () -> Unit) {
        onComplete = block
    }
}