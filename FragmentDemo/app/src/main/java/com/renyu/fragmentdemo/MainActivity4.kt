package com.renyu.fragmentdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.fragmentdemo.databinding.ActivityMainBinding
import com.renyu.fragmentdemo.fragment.MyFragment

class MainActivity4 : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = MyFragmentFactory("Hello", "world", R.layout.fragment_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main, MyFragment::class.java, null).commit()
        binding!!.frameMain
    }
}