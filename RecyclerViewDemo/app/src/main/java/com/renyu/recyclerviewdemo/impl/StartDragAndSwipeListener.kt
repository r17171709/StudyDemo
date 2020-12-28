package com.renyu.recyclerviewdemo.impl

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by renyu on 2017/11/16.
 */
interface StartDragAndSwipeListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    fun onStartSwipe(viewHolder: RecyclerView.ViewHolder)
}