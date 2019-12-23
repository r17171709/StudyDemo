package com.renyu.architecturedemo.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renyu.architecturedemo.bean.ValueResponse
import com.renyu.architecturedemo.util.NetworkException
import com.renyu.architecturedemo.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.await

open class BaseViewModel : ViewModel() {
    fun <T> getNetWorkData(
        block: () -> Call<ValueResponse<T>>,
        liveData: MutableLiveData<Resource<T>>
    ) =
        viewModelScope.launch {
            try {
                liveData.postValue(Resource.loading())
                val value = block().await()
                if (value.code != 1) {
                    val exception = NetworkException(value.code, value.msg)
                    liveData.postValue(Resource.error(exception))
                } else {
                    liveData.postValue(Resource.sucess(value.data))
                }
            } catch (e: Exception) {
                liveData.postValue(Resource.error(NetworkException(-1, e.message)))
            }
        }
}