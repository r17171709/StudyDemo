package com.renyu.spannabledemo

import android.text.SpanWatcher
import android.text.Spannable
import android.util.Log

class DemoSpanWatcher : SpanWatcher {
    override fun onSpanAdded(text: Spannable?, what: Any?, start: Int, end: Int) {
        Log.d("TAGTAGTAG", "onSpanAdded")
    }

    override fun onSpanRemoved(text: Spannable?, what: Any?, start: Int, end: Int) {
        Log.d("TAGTAGTAG", "onSpanRemoved")
    }

    override fun onSpanChanged(
        text: Spannable?,
        what: Any?,
        ostart: Int,
        oend: Int,
        nstart: Int,
        nend: Int
    ) {
        Log.d("TAGTAGTAG", "onSpanChanged")
    }
}