package com.renyu.spannabledemo

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View

class User(val id: String, val name: String) {
    fun getSpannedName(): SpannableString {
        return SpannableString("@$name").also {
            it.setSpan(
                ForegroundColorSpan(Color.CYAN),
                0,
                it.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            it.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Log.d("TAGTAGTAG", name)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
                }
            }, 0, it.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}