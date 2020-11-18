package com.renyu.spannabledemo

import android.text.Selection
import android.text.SpanWatcher
import android.text.Spannable
import kotlin.math.abs

/**
 * 光标点击处理，不可以进入文本内部
 */
class SelectionSpanWatcher : SpanWatcher {
    private var selStart = 0
    private var selEnd = 0
    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {

    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {

    }

    override fun onSpanChanged(
        text: Spannable?,
        what: Any?,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        if (what == Selection.SELECTION_START && selStart != nstart) {
            selStart = nstart
            text!!.getSpans(nstart, nend, User::class.java).firstOrNull()?.run {
                val spanStart = text.getSpanStart(this)
                val spanEnd = text.getSpanEnd(this)
                // 通过比较选择位置
                val index =
                    if (abs(spanStart - selStart) > abs(spanEnd - selStart)) spanEnd else spanStart
                Selection.setSelection(text, index)
            }
        }
    }
}