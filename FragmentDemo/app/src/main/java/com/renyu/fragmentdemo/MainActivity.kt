package com.renyu.fragmentdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.fragmentdemo.databinding.ActivityMainBinding
import com.renyu.fragmentdemo.fragment.MainFragment1

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private val fragment by lazy { MainFragment1(R.layout.fragment_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.tvMain.setOnClickListener {
            if (!fragment.isAdded) {
                supportFragmentManager.beginTransaction().add(R.id.frame_main, fragment).commit()
            } else {
                if (fragment.isVisible) {
                    supportFragmentManager.beginTransaction().hide(fragment).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(fragment).commit()
                }
            }
        }
    }
}