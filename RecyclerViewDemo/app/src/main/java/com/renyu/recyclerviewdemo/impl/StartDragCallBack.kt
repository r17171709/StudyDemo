package com.renyu.recyclerviewdemo.impl

import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import org.jetbrains.anko.backgroundColor

/**
 * Created by renyu on 2017/11/17.
 */
class StartDragCallBack(private val onDragMoveListener: OnDragMoveListener, private val onSwipeListener: OnSwipeListener) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val up = ItemTouchHelper.UP
        val down = ItemTouchHelper.DOWN
        val left = ItemTouchHelper.LEFT
        val right = ItemTouchHelper.RIGHT
        val dragFlags = up or down
        val swipeFlags = left or right
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        onDragMoveListener.onMove(recyclerView, viewHolder, target)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        onSwipeListener.onSwiped(viewHolder, direction)
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.itemView?.backgroundColor = Color.BLUE
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        viewHolder?.itemView?.backgroundColor = android.R.color.holo_green_light

        viewHolder?.itemView?.alpha = 1f
        viewHolder?.itemView?.scaleX = 1f
        viewHolder?.itemView?.scaleY = 1f
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val radio = 1 - Math.abs(dX) / viewHolder?.itemView?.width?.toFloat()!!
            viewHolder?.itemView?.alpha = radio
            viewHolder?.itemView?.scaleX = radio
            viewHolder?.itemView?.scaleY = radio
        }
    }
}