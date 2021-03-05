package com.renyu.fragmentdemo.fragment

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

class MainFragment32(@LayoutRes layoutId: Int, private val position: Int): Fragment(layoutId) {
    private var changeChildVisible = false

    override fun onResume() {
        super.onResume()
        check()
    }

    override fun onPause() {
        super.onPause()
        check()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        check()
    }

    private fun check() {
        if (isResumed && !isHidden && parentFragment!!.isVisible) {
            Log.d("TAGTAGTAG", "${this.javaClass.simpleName}  $position Visible")
        } else {
            Log.d("TAGTAGTAG", "${this.javaClass.simpleName}  $position InVisible")
        }
    }

    fun changeChildVisible(changeChildVisible: Boolean) {
        this.changeChildVisible = changeChildVisible
    }
}