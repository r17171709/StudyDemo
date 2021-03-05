package com.renyu.fragmentdemo.fragment

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

class MainFragment1(@LayoutRes layoutId: Int): Fragment(layoutId) {
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
        if (isResumed && !isHidden) {
            Log.d("TAGTAGTAG", "$this Visible")
        } else {
            Log.d("TAGTAGTAG", "$this InVisible")
        }
    }
}