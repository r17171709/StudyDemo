package com.renyu.recyclerviewdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by renyu on 2017/10/25.
 */
class GridDivider constructor(val context: Context, val dividerDrawable: Drawable) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        drawLeft(c, parent)
        drawTop(c, parent)
        drawRight(c, parent)
        drawBottom(c, parent)
    }

    private fun drawTop(c: Canvas?, parent: RecyclerView?) {
        if (parent != null) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.left - params.leftMargin + child.translationX.toInt()
                val right = child.right + params.rightMargin + child.translationX.toInt()
                val bottom = child.top - params.topMargin + child.translationY.toInt()
                val top = bottom - dividerDrawable?.intrinsicHeight
                dividerDrawable?.bounds = Rect(left, top, right, bottom)
                dividerDrawable?.draw(c)
            }
        }
    }

    private fun drawLeft(c: Canvas?, parent: RecyclerView?) {
        if (parent != null) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val right = child.left - params.leftMargin + child.translationX.toInt()
                val left = right - dividerDrawable?.intrinsicWidth
                val top = child.top - params.topMargin + child.translationY.toInt()
                val bottom = child.bottom + params.bottomMargin + child.translationY.toInt()
                dividerDrawable?.bounds = Rect(left, top, right, bottom)
                dividerDrawable?.draw(c)
            }
        }
    }

    private fun drawBottom(c: Canvas?, parent: RecyclerView?) {
        if (parent != null) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.left - params.leftMargin + child.translationX.toInt()
                val right = child.right + params.rightMargin + child.translationX.toInt()
                val top = child.bottom + params.bottomMargin + child.translationY.toInt()
                val bottom = top + dividerDrawable?.intrinsicHeight
                dividerDrawable?.bounds = Rect(left, top, right, bottom)
                dividerDrawable?.draw(c)
            }
        }
    }

    private fun drawRight(c: Canvas?, parent: RecyclerView?) {
        if (parent != null) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.right + params.rightMargin + child.translationX.toInt()
                val right = left + (dividerDrawable?.intrinsicWidth ?: 0)
                val top = child.top - params.topMargin + child.translationY.toInt()
                val bottom = child.bottom + params.bottomMargin + child.translationY.toInt()
                dividerDrawable?.bounds = Rect(left, top, right, bottom)
                dividerDrawable?.draw(c)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent != null) {
            val position = parent.getChildAdapterPosition(view)
            val manager = parent.layoutManager as GridLayoutManager
            when {
            // 左侧一排加顶上、左边、右边的线
                position % manager.spanCount == 0 -> outRect?.set(Rect(dividerDrawable?.intrinsicWidth, dividerDrawable?.intrinsicHeight, dividerDrawable?.intrinsicWidth, 0))
            // 其余加顶上的线、右边的线
                else -> outRect?.set(Rect(0, dividerDrawable?.intrinsicHeight, dividerDrawable?.intrinsicWidth, 0))
            }
        }
    }
}