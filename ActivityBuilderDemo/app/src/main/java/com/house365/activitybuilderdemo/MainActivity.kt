package com.house365.activitybuilderdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.house365.annotation.annotation.BuilderActivity

@BuilderActivity(routerValue = "MainActivity")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SecondActivity_Builder.build().beans(RequestBeans("Hello")).beans2(ArrayList<String>()).start(this)
    }
}
