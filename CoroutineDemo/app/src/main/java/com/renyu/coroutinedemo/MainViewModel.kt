package com.renyu.coroutinedemo

import androidx.annotation.MainThread
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class MainViewModel : ViewModel() {
    private val result = MediatorLiveData<Resource<TestResponse>>()

    fun testDsl(dsl: DSLBean.() -> Unit) {
        val bean = DSLBean()
        bean.dsl()
        bean.dslFunction?.invoke()
    }

    fun testParentChild1() {
        viewModelScope.launch {
            viewModelScope.launch {
                println("HelloChild1")
                println("HelloChild2")
            }
            println("HelloParent")
            println("HelloParent2")
        }
        println("HelloOuter")
    }

    /**
     * 如果子协程是正常的取消（即CancellationException），那么对于父协程是没有影响的
     */
    fun testParentChild2() {
        viewModelScope.launch {
            val child = viewModelScope.launch {
                println("HelloChild1")
                println("HelloChild2")
            }
            println("HelloParent")
            child.cancel()
            println("HelloParent2")
        }
        println("HelloOuter")
    }

    /**
     * 这样非CancellationException导致的子协程地取消，也会导致父协程的取消
     */
    fun testParentChild3() {
        viewModelScope.launch {
            viewModelScope.launch(context = CoroutineExceptionHandler { _, e -> e.printStackTrace() }) {
                println("HelloChild1")
                println("HelloChild2")
                throw Exception("")
            }
            println("HelloParent")
            println("HelloParent2")
        }
        println("HelloOuter")
    }

    fun testParentChild4() {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)
        scope.launch {
            try {
                coroutineScope {
                    async {
                        throw Exception("")
                    }.await()
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

    fun getHttpResult() {
        launch(block = {
            val result = async {
                Utils.apiService.test()
            }
            val value = result.await()
            value
        }, error = {

        }, success = {

        })
    }

    fun getHttpResult1(): LiveData<Resource<TestResponse>> {
        val apipResponse = Utils.apiService1.test1()
        result.addSource(apipResponse) {
            result.removeSource(apipResponse)
            if (it != null) {
                when (it) {
                    is ApiSuccessResponse -> setValue(Resource.success(it.body))
                    is ApiErrorResponse -> setValue(Resource.error(it.errorMessage, null))
                    is ApiEmptyResponse -> {
                    }
                }
            }
        }
        return result
    }

    fun getHttpRequest2() {
        retrofit2<TestResponse> {
            api = Utils.apiService.test2()

            onSuccess {
                it
            }

            onError {
                it
            }

            onComplete {

            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<TestResponse>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun getHttpRequest3() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(Utils.apiService.test()))
        } catch (e: Exception) {
            emit(Resource.error(e.message!!, null))
        }
    }

    fun getHttpRequest4(): LiveData<Resource<TestResponse>> {
        return retrofit3<TestResponse>(context = SupervisorJob() + Dispatchers.Main.immediate) {
            api = Utils.apiService.test2()

            onSuccess {
                it
            }

            onError {
                it
            }

            onComplete {

            }
        }
    }

    private val channel = Channel<String>()
    val liveDataValue = liveData<String> {
        for (result in channel) {
            emit(result)
        }
    }

    fun updateChannel() {
        viewModelScope.launch {
            delay(3000)
            channel.send("Hello")
        }
    }

    /**
     * 重新封装了一下
     */
    private fun <T> Channel<T>.coroutineLiveData() {
        val liveDataValue = liveData<T> {
            for (result in this@coroutineLiveData) {
                emit(result)
            }
        }
    }
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}