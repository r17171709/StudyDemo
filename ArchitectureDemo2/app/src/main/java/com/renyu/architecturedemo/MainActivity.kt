package com.renyu.architecturedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.renyu.architecturedemo.bean.DataBean
import com.renyu.architecturedemo.databinding.ActivityMainBinding
import com.renyu.architecturedemo.util.BaseObserver2
import com.renyu.architecturedemo.util.Resource
import com.renyu.architecturedemo.vm.NewsViewModel

class MainActivity : AppCompatActivity() {

    private val vm: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        vm.data.observe(this, object : BaseObserver2<DataBean>(this) {
            override fun onError(tResource: Resource<DataBean>?) {

            }

            override fun onSucess(tResource: Resource<DataBean>?) {
                viewDataBinding.setVariable(
                    BR.value,
                    "${tResource?.data?.key} = ${tResource?.data?.value}"
                )
            }
        })
        vm.getNews()
    }
}
