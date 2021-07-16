package com.renyu.fragmentdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.renyu.fragmentdemo.fragment.MyFragment

class MyFragmentFactory(val arg1: String, val arg2: String, val layoutID: Int) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val clazz = loadFragmentClass(classLoader, className)
        if (clazz == MyFragment::class.java) {
            return MyFragment(arg1, arg2, layoutID)
        }
        // 如果要考虑恢复重建的场景，必须配套设置一个 FragmentFactory，不可直接使用默认FragmentFactory
        return super.instantiate(classLoader, className)
    }
}