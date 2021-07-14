package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentMultistackparentBinding

class MultiStackParentFragment : Fragment(R.layout.fragment_multistackparent) {
    private val mStackList by lazy { ArrayList<NavHostFragment>() }
    private val mOrderStack by lazy { ArrayDeque<Int>() }

    private var binding: FragmentMultistackparentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMultistackparentBinding.bind(view)

        val navHostFragment1 = NavHostFragment(R.id.stack_11)
        val fragmentTransaction =
            childFragmentManager.beginTransaction()
                .add(R.id.inner_container, navHostFragment1, name(R.id.stack_11))
        mStackList.add(navHostFragment1)
        val navHostFragment2 = NavHostFragment(R.id.stack_12)
        fragmentTransaction.add(R.id.inner_container, navHostFragment2, name(R.id.stack_11))
        mStackList.add(navHostFragment2)
        val navHostFragment3 = NavHostFragment(R.id.stack_13)
        fragmentTransaction.add(R.id.inner_container, navHostFragment3, name(R.id.stack_11))
        mStackList.add(navHostFragment3)
        val navHostFragment4 = NavHostFragment(R.id.stack_14)
        fragmentTransaction.add(R.id.inner_container, navHostFragment4, name(R.id.stack_11))
        mStackList.add(navHostFragment4)
        fragmentTransaction.commitNow()

        binding!!.stack11.setOnClickListener(onClick(0))
        binding!!.stack12.setOnClickListener(onClick(1))
        binding!!.stack13.setOnClickListener(onClick(2))
        binding!!.stack14.setOnClickListener(onClick(3))
        binding!!.stack11.performClick()

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 手动管理返回栈
                    if (!mOrderStack.isNullOrEmpty()) {
                        mOrderStack.removeFirst()
                        if (mOrderStack.isNotEmpty()) {
                            val index = mOrderStack.first()
                            when (index) {
                                0 -> {
                                    binding!!.stack11.performClick()
                                }
                                1 -> {
                                    binding!!.stack12.performClick()
                                }
                                2 -> {
                                    binding!!.stack13.performClick()
                                }
                                3 -> {
                                    binding!!.stack14.performClick()
                                }
                            }
                            return
                        }
                    }
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })
    }

    private fun onClick(index: Int) = View.OnClickListener {
        mOrderStack.remove(index)
        mOrderStack.addFirst(index)
        val fragmentTransaction = childFragmentManager.beginTransaction()
        mStackList.forEachIndexed { i, navHostFragment ->
            if (index == i) {
                fragmentTransaction.attach(mStackList[i])
                    .setPrimaryNavigationFragment(mStackList[i])
            } else {
                fragmentTransaction.detach(mStackList[i])
            }
        }
        fragmentTransaction.commitNow()
    }

    fun name(@IdRes id: Int) = when (id) {
        R.id.stack_11 -> "♥"
        R.id.stack_12 -> "♦"
        R.id.stack_13 -> "♠"
        R.id.stack_14 -> "♣"
        else -> ""
    }
}