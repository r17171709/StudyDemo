package com.renyu.recyclerviewdemo.impl

import android.support.v7.widget.RecyclerView

/**
 * Created by renyu on 2017/11/20.
 */
interface OnSwipeListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int)
}