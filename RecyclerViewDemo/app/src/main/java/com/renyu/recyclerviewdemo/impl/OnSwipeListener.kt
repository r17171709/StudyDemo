package com.renyu.recyclerviewdemo.impl

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by renyu on 2017/11/20.
 */
interface OnSwipeListener {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int)
}