package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.renyu.fragmentdemo.R

class SingleStackParentFragment : Fragment(R.layout.fragment_singlestackparent) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        childFragmentManager.beginTransaction()
            .replace(R.id.stack_1, SingleStackChildFragment(1)).commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.stack_2, SingleStackChildFragment(1)).commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.stack_3, SingleStackChildFragment(1)).commit()
        childFragmentManager.beginTransaction()
            .replace(R.id.stack_4, SingleStackChildFragment(1)).commit()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object :
            OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                isEnabled = childFragmentManager.backStackEntryCount != 0
                if (isEnabled) {
                    childFragmentManager.popBackStack()
                } else {
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                callback.isEnabled = true
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                callback.isEnabled = false
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                callback.remove()
                lifecycle.removeObserver(this)
            }
        })
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