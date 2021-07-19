package com.renyu.coroutinedemo

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {
    val sharedFlow = MutableSharedFlow<String>()

    // 有默认值则第一次会返回默认值
    private val _stateFlow = MutableStateFlow<String>("defaultValue")
    val stateFlow: StateFlow<String> = _stateFlow

    val stateFlow2: StateFlow<String> = flow<String> {
        emit("newFlow")
    }
        // stateIn 是专门将数据流转换为 StateFlow 的运算符
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "defaultFlow"
        )

    val liveData1: LiveData<String> by lazy {
        liveData {
            emit("LiveData1")
            emit("LiveData2")
        }
    }

    // flow 转换成 livedata
    val liveData2 = _stateFlow.asLiveData()

    // livedata 转换成 flow
    val stateFlow3 = liveData2.asFlow()

    fun sendSharedData() {
        viewModelScope.launch {
            sharedFlow.emit("1")
            sharedFlow.emit("2")
        }
    }

    fun sendStateData() {
        viewModelScope.launch {
            _stateFlow.value = "newValue"
        }
    }
}