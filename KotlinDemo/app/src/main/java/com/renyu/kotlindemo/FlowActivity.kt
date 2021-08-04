package com.renyu.kotlindemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

class FlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainScope().launch {
            flowDemo7()
        }
    }

    private suspend fun flowDemo() {
        val value = flow<Int> {
            (1..3).forEach {
                emit(it + 10)
            }
        }.onEach {
            delay(1000)
        }.transform<Int, String> { value: Int ->
            emit("Hello $value")
        }.onStart {
            Log.d("TAGTAGTAG", "Hello")
        }.onCompletion {
            Log.d("TAGTAGTAG", "ByeBye")
        }.reduce { accumulator, value ->
            Log.d("TAGTAGTAG", "$accumulator  $value")
            return@reduce "$accumulator  $value"
        }
//            .collect {
//            Log.d("TAGTAGTAG", it)
//        }
        Log.d("TAGTAGTAG", "final value $value")
    }

    private suspend fun flowDemo2() {
        flowOf(1, 2, 3, 4, 5).onEach {
            delay(1000)
        }.collect {
            Log.d("TAGTAGTAG", "Hello $it")
        }
    }

    private suspend fun flowDemo3() {
        listOf<String>("1", "2", "3", "4", "5").asFlow().onEach {
            delay(1000)
        }.collect {
            Log.d("TAGTAGTAG", "Hello $it")
        }
    }

    private suspend fun flowDemo4() {
        channelFlow<Int> {
            Log.d("TAGTAGTAG", "${Thread.currentThread().name}")
            for (i in 1..5) {
                delay(1000)
                send(i)
            }
        }.flowOn(Executors.newFixedThreadPool(5).asCoroutineDispatcher()).collect {
            Log.d("TAGTAGTAG", "Hello $it ${Thread.currentThread().name}")
        }
    }

    private suspend fun flowDemo5() {
        flow<String> {
            emit("")
            throw RuntimeException()
        }.catch {
            Log.d("TAGTAGTAG", "Exception")
        }.onCompletion { cause: Throwable? ->
            if (cause != null) {
                Log.d("TAGTAGTAG", "Flow completed exceptionally")
            } else {
                Log.d("TAGTAGTAG", "Done")
            }
        }.collect {

        }
    }

    private suspend fun flowDemo6() {
//        (1..5).asFlow().onEach {
//            if (it == 3) throw Exception("Error on $it")
//        }.retry(2) {
//            if (it is Exception) {
//                return@retry true
//            }
//            return@retry false
//        }.onEach {
//            Log.d("TAGTAGTAG", "Hello $it")
//        }.catch {
//            it.printStackTrace()
//        }.collect()

        (1..5).asFlow().onEach {
            if (it == 3) throw Exception("Error on $it")
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2
        }.onEach {
            Log.d("TAGTAGTAG", "Hello $it")
        }.catch {
            it.printStackTrace()
        }.collect()
    }

    private suspend fun flowDemo7() {
        flow<Int> {
            for (i in 1..5) {
                delay(1000)
                emit(i)
            }
        }.transform<Int, String> { value ->
            emit("Hello $value")
            delay(100)
            emit("byebye $value")
        }.buffer().collect {
            delay(1000)
            Log.d("TAGTAGTAG", it)
        }
    }
}