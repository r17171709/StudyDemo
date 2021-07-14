package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentMultistackchildBinding

class MultiStackChildFragment(private val depth: Int) :
    Fragment(R.layout.fragment_multistackchild) {
    private var binding: FragmentMultistackchildBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMultistackchildBinding.bind(view)

        binding!!.textView.text = "$depth"

        binding!!.button20.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.content, MultiStackChildFragment(depth + 1))
                .addToBackStack("$depth").commit()
        }
        binding!!.button21.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}