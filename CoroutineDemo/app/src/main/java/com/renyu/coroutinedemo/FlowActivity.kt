package com.renyu.coroutinedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    private val vm by lazy { ViewModelProvider(this)[FlowViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 处于 STARTED 状态时会开始收集流，并且在 RESUMED 状态时保持收集，最终在进入 STOPPED 状态时结束收集过程
        lifecycleScope.launch {
            // 与 launchWhenX 不同的是，repeatOnLifecycle 会在生命周期所有者退出该状态时停止协程，而不是生命周期所有者被销毁时才会被取消
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.sharedFlow.collect {
                    Toast.makeText(this@FlowActivity, "$it", Toast.LENGTH_LONG).show()
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.collect {
                    Toast.makeText(this@FlowActivity, "$it", Toast.LENGTH_LONG).show()
                }
            }
        }

        vm.liveData1.observe(this,
            Observer<String> { t ->
                Toast.makeText(this@FlowActivity, "$t", Toast.LENGTH_LONG).show()
            })
    }

    override fun onResume() {
        super.onResume()

//        vm.sendStateData()
    }
}