package com.renyu.fragmentdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.renyu.fragmentdemo.databinding.ActivityMain2Binding
import com.renyu.fragmentdemo.fragment.MainFragment1

class MainActivity2 : AppCompatActivity() {
    private var binding: ActivityMain2Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.main2Vp.adapter = VPAdapter(supportFragmentManager)
    }

    class VPAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 5

        override fun getItem(position: Int) = MainFragment1(R.layout.fragment_main)
    }
}