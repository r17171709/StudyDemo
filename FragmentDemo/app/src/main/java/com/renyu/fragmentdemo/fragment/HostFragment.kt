package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentHostBinding

class HostFragment : Fragment(R.layout.fragment_host) {
    private var binding: ViewBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHostBinding.bind(view)

        (binding as FragmentHostBinding).single.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.content, SingleStackParentFragment()).commit()
        }
        (binding as FragmentHostBinding).multi.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.content, MultiStackParentFragment()).commit()
        }
    }
}