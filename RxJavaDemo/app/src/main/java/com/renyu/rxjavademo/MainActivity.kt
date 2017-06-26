package com.renyu.rxjavademo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.renyu.rxjavademo.bean.ExampleBean
import com.renyu.rxjavademo.rxbus.RxBus
import com.renyu.rxjavademo.rxbus.Subscribe
import com.renyu.rxjavademo.rxbus.ThreadMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxBus.default.regist(MainActivity::class.java)

        btn_rxbus.setOnClickListener {
            RxBus.default.post(ExampleBean("123"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        RxBus.default.unregist(MainActivity::class.java)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(exampleBean: ExampleBean) {
        Log.d("MainActivity", "receive ${exampleBean.string}")
    }

    /**
     * 测试Annotion
     */
    fun testAnnotation() {
        val class_ = Class.forName("com.renyu.rxjavademo.MainActivity")
        for (declaredMethod in class_.declaredMethods) {
            val subscribe = declaredMethod.getAnnotation(Subscribe::class.java)
            if (subscribe!=null) {
                Log.d("MainActivity", "值：${subscribe.threadMode == ThreadMode.MAIN}")
            }
        }
    }
}
