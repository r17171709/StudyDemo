package com.renyu.recyclerviewdemo.impl

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by renyu on 2017/11/20.
 */
interface OnDragMoveListener {
    fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?)
}