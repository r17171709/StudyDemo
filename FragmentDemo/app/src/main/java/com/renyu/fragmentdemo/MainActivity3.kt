package com.renyu.fragmentdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.fragmentdemo.databinding.ActivityMain3Binding
import com.renyu.fragmentdemo.fragment.MainFragment3

class MainActivity3 : AppCompatActivity() {
    private var binding: ActivityMain3Binding? = null

    private val fragment1 by lazy { MainFragment3.getInstance(1) }
    private val fragment2 by lazy { MainFragment3.getInstance(2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.tvMain3.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment2.isAdded) {
                transaction.hide(fragment2)
            }
            if (!fragment1.isAdded) {
                transaction.add(R.id.frame_main3, fragment1).commit()
            } else {
                if (fragment1.isVisible) {
                    transaction.hide(fragment1).commit()
                } else {
                    transaction.show(fragment1).commit()
                }
            }
        }

        binding!!.tvMain31.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            if (fragment1.isAdded) {
                transaction.hide(fragment1)
            }
            if (!fragment2.isAdded) {
                transaction.add(R.id.frame_main3, fragment2).commit()
            } else {
                if (fragment2.isVisible) {
                    transaction.hide(fragment2).commit()
                } else {
                    transaction.show(fragment2).commit()
                }
            }
        }
    }
}