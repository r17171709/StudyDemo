package com.renyu.dragrecyclerview.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Administrator on 2020/6/17.
 */
class MyItemTouchHelperCallBack constructor(private val callbackItemTouch: CallbackItemTouch) :
    ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled() = false

    override fun isItemViewSwipeEnabled() = false

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        callbackItemTouch.onMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}