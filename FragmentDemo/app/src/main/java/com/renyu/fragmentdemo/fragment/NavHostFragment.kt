package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.renyu.fragmentdemo.R

class NavHostFragment(private val ids: Int) : Fragment(R.layout.fragment_navhost) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 切换Button之后经过attach会重复执行 onViewCreated ，所以此处需要判断，不能重复添加
        if (childFragmentManager.findFragmentByTag("${name(ids)}1") == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.content, MultiStackChildFragment(1), "${name(ids)}1").commitNow()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = childFragmentManager.backStackEntryCount > 0
                if (isEnabled) {
                    childFragmentManager.popBackStackImmediate()
                } else {
                    // 交由上一级Fragment管理返回键
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    fun name(@IdRes id: Int) = when (id) {
        R.id.stack_11 -> "♥"
        R.id.stack_12 -> "♦"
        R.id.stack_13 -> "♠"
        R.id.stack_14 -> "♣"
        else -> ""
    }
}