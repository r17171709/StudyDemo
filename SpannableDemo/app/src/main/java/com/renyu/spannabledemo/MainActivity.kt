package com.renyu.spannabledemo

import android.os.Bundle
import android.text.Selection
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.renyu.spannabledemo.databinding.ActivityMainBinding

/**
 * 参考文章 https://www.jianshu.com/p/83176fb89aed
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            val user = User("renyu", "renyu")
            val at = user.getSpannedName().apply {
                setSpan(user, 0, user.getSpannedName().length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            (binding.tvInput.text as SpannableStringBuilder).append(at).append(" ")
        }
        binding.buttonRead.setOnClickListener {
            val text = binding.tvInput.text!!
            text.getSpans(0, text.length, User::class.java).forEach {
                Log.d("TAGTAGTAG", it.name)
            }
        }
        binding.tvInput.setEditableFactory(NoCopySpanEditableFactory(SelectionSpanWatcher()))
        binding.tvInput.setDelKeyEventListener {
            val text = binding.tvInput.text!!
            val selectionStart = Selection.getSelectionStart(text)
            val selectionEnd = Selection.getSelectionEnd(text)
            text.getSpans(selectionStart, selectionEnd, User::class.java).firstOrNull {
                text.getSpanEnd(it) == selectionStart
            }?.run {
                val spanStart = text.getSpanStart(this)
                val spanEnd = text.getSpanEnd(this)
                Selection.setSelection(text, spanStart, spanEnd)
            }
        }
        binding.tvInput.movementMethod = LinkMovementMethod.getInstance()
    }
}