package com.renyu.viewpager2demo

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter : FragmentStateAdapter {
    private val mFragmentList by lazy {
        ArrayList<Fragment>()
    }

    // 数据源中fragment的hashcode 一对一 增删时注意保持一致
    private val mFragmentHashCodes by lazy {
        ArrayList<Long>()
    }

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    fun createFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
        mFragmentHashCodes.add(fragment.hashCode().toLong())
        notifyDataSetChanged()
    }

    fun removeFragment(fragment: Fragment) {
        mFragmentList.remove(fragment)
        mFragmentHashCodes.remove(fragment.hashCode().toLong())
        notifyDataSetChanged()
    }

    // 全局刷新
    fun totalUpdate() {
        val fragment = mFragmentList.removeAt(mFragmentList.size - 1)
        mFragmentHashCodes.remove(fragment.hashCode().toLong())
        val addFragment = ColorFragment(Color.GRAY)
        mFragmentList.add(0, addFragment)
        mFragmentHashCodes.add(addFragment.hashCode().toLong())
        notifyDataSetChanged()
    }

    override fun getItemCount() = mFragmentList.size

    override fun createFragment(position: Int) = mFragmentList[position]

    // 下面2个方法来解决ViewPager2动态删除Fragment显示问题
    override fun getItemId(position: Int): Long {
        return mFragmentList[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return mFragmentHashCodes.contains(itemId)
    }

}