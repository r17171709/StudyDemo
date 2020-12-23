package com.renyu.coroutinedemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit,
                     error: (Throwable) -> Unit,
                     success: () -> Unit) {
    viewModelScope.launch(CoroutineExceptionHandler { _, e -> kotlin.error(e) }) {
        block.invoke(this)
    }
}

object Utils {
    private val retrofit = Retrofit.Builder().client(OkHttpClient.Builder().build()).baseUrl("https://run.mocky.io/").addConverterFactory(GsonConverterFactory.create()).build()
    val apiService = retrofit.create(ApiService::class.java)

    private val retrofit1 = Retrofit.Builder().client(OkHttpClient.Builder().build()).baseUrl("https://run.mocky.io/").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(LiveDataCallAdapterFactory()).build()
    val apiService1 = retrofit1.create(ApiService::class.java)
}

fun <T> ViewModel.retrofit(dsl: RetrofitCoroutineDsl<T>.() -> Unit) {
    viewModelScope.launch(context = CoroutineExceptionHandler { _, e -> } + Dispatchers.Main) {
        val retrofitCoroutineDsl = RetrofitCoroutineDsl<T>()
        retrofitCoroutineDsl.dsl()
        retrofitCoroutineDsl.api?.let {
            // 当 async 被用作根协程时，异常将会在您调用 .await 方法时被抛出，此处不是
            // 如果 async 抛出异常，launch 就会立即抛出异常，而不会调用 .await()
            val work = async(Dispatchers.IO) {
                try {
                    it.execute()
                } catch (e: Exception) {
                    retrofitCoroutineDsl.onError?.invoke(e)
                    null
                }
            }
            work.invokeOnCompletion { _ ->
                if (work.isCancelled) {
                    it.cancel()
                }
            }
            val response = work.await()
            retrofitCoroutineDsl.onComplete?.invoke()
            response?.let {
                if (response.isSuccessful) {
                    retrofitCoroutineDsl.onSuccess?.invoke(response.body()!!)
                } else {
                    retrofitCoroutineDsl.onError?.invoke(Exception(response.errorBody().toString()))
                }
            }
        }
    }
}

fun <T> ViewModel.retrofit2(dsl: RetrofitCoroutineDsl<T>.() -> Unit) {
    viewModelScope.launch {
        val retrofitCoroutineDsl = RetrofitCoroutineDsl<T>()
        retrofitCoroutineDsl.dsl()
        runCatching {
            withContext(Dispatchers.IO) {
                retrofitCoroutineDsl.api!!.execute()
            }
        }.onSuccess {
            if (it.isSuccessful) {
                retrofitCoroutineDsl.onSuccess?.invoke(it.body()!!)
            } else {
                retrofitCoroutineDsl.onError?.invoke(Exception(it.errorBody().toString()))
            }
        }.onFailure {
            // 如果协程还在运行，个别机型退出当前界面时，viewModel会通过抛出CancellationException，强行结束协程，与java中InterruptException类似，所以不必理会
            if (it !is CancellationException) {
                retrofitCoroutineDsl.onError?.invoke(it)
            }
        }
    }
}