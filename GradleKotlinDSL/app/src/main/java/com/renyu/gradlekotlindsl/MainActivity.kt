package com.renyu.gradlekotlindsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// 参考
// https://github.com/81813780/kotlin-dsl-gradle-sample
// https://www.cnblogs.com/mengdd/archive/2020/01/15/android-gradle-migrate-from-groovy-to-kotlin.html
// https://juejin.im/post/6864349303843307534
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}