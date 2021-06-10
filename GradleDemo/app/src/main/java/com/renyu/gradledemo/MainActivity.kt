package com.renyu.gradledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renyu.route.annotations.Destination
import com.renyu.route_runtime.Route

@Destination(url = "route://page-home", description = "首页")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Route.goto(this, "route://page-child?a=1&b=2")
    }
}