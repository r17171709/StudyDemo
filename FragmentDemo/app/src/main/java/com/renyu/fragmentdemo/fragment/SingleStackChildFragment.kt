package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentSinglestackchildBinding

class SingleStackChildFragment(private val depth: Int) :
    Fragment(R.layout.fragment_singlestackchild) {
    private var binding: ViewBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSinglestackchildBinding.bind(view)
        (binding as FragmentSinglestackchildBinding).button4.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(id, SingleStackChildFragment(depth + 1)).addToBackStack(name(id))
                .commit()
        }
        (binding as FragmentSinglestackchildBinding).button4.text =
            "${name(id)} ${getCount(depth)}"
    }

    fun name(@IdRes id: Int) = when (id) {
        R.id.stack_1 -> "♥"
        R.id.stack_2 -> "♦"
        R.id.stack_3 -> "♠"
        R.id.stack_4 -> "♣"
        else -> ""
    }

    fun getCount(value: Int): String = when (value) {
        11 -> "J"
        12 -> "Q"
        13 -> "K"
        1 -> "A"
        else -> value.toString()
    }
}