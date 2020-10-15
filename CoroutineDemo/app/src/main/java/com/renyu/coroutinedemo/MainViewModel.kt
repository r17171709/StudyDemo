package com.renyu.coroutinedemo

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.async

class MainViewModel : ViewModel() {
    private val result = MediatorLiveData<Resource<TestResponse>>()

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

    @MainThread
    private fun setValue(newValue: Resource<TestResponse>) {
        if (result.value != newValue) {
            result.value = newValue
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