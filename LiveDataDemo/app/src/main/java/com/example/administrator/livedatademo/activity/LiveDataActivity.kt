package com.example.administrator.livedatademo.activity

import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.administrator.livedatademo.R
import com.example.administrator.livedatademo.databinding.ActivityLivedataBinding
import com.example.administrator.livedatademo.model.Data1
import com.example.administrator.livedatademo.model.NetWorkState

class LiveDataActivity : AppCompatActivity() {

    val state: NetWorkState by lazy {
        NetWorkState(applicationContext)
    }

    val id: MutableLiveData<String> = MutableLiveData()

    private val realResult: MediatorLiveData<Data1> = MediatorLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLivedataBinding>(this, R.layout.activity_livedata)

        state.observe(this, Observer<Boolean> {
            Toast.makeText(this, if (it!=null && it) "网络连接成功" else "网络连接失败", Toast.LENGTH_LONG).show()
        })

        val data1 = Transformations.switchMap(id) {
            val temp: MutableLiveData<Data1> = MutableLiveData()
            Thread(Runnable {
                Thread.sleep(4000)
                temp.postValue(Data1(it))
            }).start()
            temp
        }
        data1.observe(this, Observer<Data1> { t -> Log.d(LiveDataActivity::class.java.simpleName, t?.name) })


        val data11 = Transformations.map(id) {
            if (it != null) {
                return@map Data1(it)
            }
            Data1("")
        }
        data11.observe(this, Observer<Data1> {
            Log.d(LiveDataActivity::class.java.simpleName, it?.name)
        })

        id.value = "测试姓名"

        val cacheSource = loadCache()
        realResult.addSource(cacheSource) {
            realResult.removeSource(cacheSource)
            if (TextUtils.isEmpty(it?.name)) {
                val apiResponse = loadFromNetwork()
                realResult.addSource(apiResponse) {
                    realResult.removeSource(apiResponse)
                    if (TextUtils.isEmpty(it?.name)) {
                        // 网络请求失败
                        realResult.value = null
                    }
                    else {
                        realResult.value = it
                    }
                }
            }
            else {
                realResult.value = it
            }
        }
        realResult.observe(this, Observer<Data1> { t -> Log.d(LiveDataActivity::class.java.simpleName, "onChanged ${t?.name}") })
    }

    private fun loadCache() : LiveData<Data1> {
        val temp = MutableLiveData<Data1>()
        Thread(Runnable {
            Thread.sleep(3000)
            temp.postValue(Data1(""))
        }).start()
        return temp
    }

    private fun loadFromNetwork() : LiveData<Data1> {
        val temp = MutableLiveData<Data1>()
        Thread(Runnable {
            Thread.sleep(3000)
            temp.postValue(Data1("cs"))
        }).start()
        return temp
    }
}