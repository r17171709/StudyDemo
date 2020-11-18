package com.renyu.spannabledemo

import android.text.Editable
import android.text.NoCopySpan
import android.text.SpannableStringBuilder
import android.text.Spanned

class NoCopySpanEditableFactory(private val span: NoCopySpan) : Editable.Factory() {
    override fun newEditable(source: CharSequence?): Editable {
        return SpannableStringBuilder.valueOf(source).apply {
            setSpan(span, 0, source!!.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }
}