package com.renyu.recyclerviewdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by renyu on 2017/11/1.
 */
class Divider(val context: Context, val drawable: Drawable, val orientation: Int = LinearLayoutManager.VERTICAL) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        if (parent != null) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                var left = 0
                var right = 0
                var top = 0
                var bottom = 0
                if (orientation == LinearLayoutManager.VERTICAL) {
                    left = parent.paddingLeft + params.leftMargin + child.translationX.toInt()
                    right = parent.width - params.rightMargin + child.translationX.toInt()
                    top = child.bottom + params.bottomMargin + child.translationY.toInt()
                    bottom = top + drawable.intrinsicHeight
                }
                else {
                    left = child.right + params.rightMargin + child.translationX.toInt()
                    right = left + drawable.intrinsicWidth
                    top = child.top - params.topMargin + child.translationY.toInt()
                    bottom = child.bottom + params.bottomMargin + child.translationY.toInt()
                }
                drawable.bounds = Rect(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect?.set(Rect(0, 0, 0 ,drawable?.intrinsicHeight))
        }
        else {
            outRect?.set(Rect(0, 0 ,drawable?.intrinsicWidth, 0))
        }
    }
}