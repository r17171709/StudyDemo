package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentMainBinding

class MyFragment(val arg1: String, val arg2: String, val layoutId: Int) : Fragment(layoutId) {
    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding!!.tvFragmentMain.text = "$arg1 $arg2"
    }
}