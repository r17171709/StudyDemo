package com.renyu.recyclerviewdemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.bean.SectionBean

/**
 * Created by renyu on 2017/10/31.
 */
class StickyHeader(val context: Context, val dividerDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.item_divider)) : RecyclerView.ItemDecoration() {

    var paint: Paint? = null

    init {
        paint = Paint()
        paint?.color = Color.BLACK
        paint?.textSize = 30.0f
        paint?.style = Paint.Style.FILL
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
        drawSection(c, parent)
    }

    private fun drawSection(c: Canvas?, parent: RecyclerView?) {
        val view0 = parent?.getChildAt(0)
        val view1 = parent?.getChildAt(1)

        if (view0 != null && view1 != null) {
            val left = view0.left
            val right = view1.right
            var top = 0
            var bottom = 100
            if (!(view0.tag as SectionBean).start && (view1.tag as SectionBean).start && view0?.bottom!! < 100) {
                bottom = view0?.bottom!!
                top = bottom - 100
            }
            dividerDrawable?.bounds = Rect(left, top, right, bottom)
            dividerDrawable?.draw(c)

            val fontMetrics: Paint.FontMetricsInt  = paint!!.fontMetricsInt
            val baseline = (dividerDrawable?.bounds.bottom + dividerDrawable?.bounds.top - fontMetrics.bottom - fontMetrics.top) / 2
            c?.drawText(((view0 as LinearLayout).getChildAt(0) as TextView).text.toString(), left.toFloat(), baseline.toFloat(), paint)
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        drawTop(c, parent)
    }

    private fun drawTop(c: Canvas?, parent: RecyclerView?) {
        for (i in 0 until parent?.childCount!!) {
            val child = parent?.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val bottom = child.top - params.topMargin + parent.getChildAt(i).translationY.toInt()
            val top = bottom - 100
            val left = child.left + parent.getChildAt(i).translationX.toInt()
            val right = child.right + parent.getChildAt(i).translationX.toInt()
            dividerDrawable?.bounds = Rect(left, top, right, bottom)
            if ((child.tag as SectionBean).start) {
                dividerDrawable?.draw(c)

                val fontMetrics: Paint.FontMetricsInt  = paint!!.fontMetricsInt
                val baseline = (dividerDrawable?.bounds.bottom + dividerDrawable?.bounds.top - fontMetrics.bottom - fontMetrics.top) / 2
                c?.drawText(((child as LinearLayout).getChildAt(0) as TextView).text.toString(), left.toFloat(), baseline.toFloat(), paint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if ((view?.tag as SectionBean).start) {
            outRect?.set(0, 100, 0 ,0)
        }
        else {
            outRect?.set(0, 0, 0 ,0)
        }
    }
}