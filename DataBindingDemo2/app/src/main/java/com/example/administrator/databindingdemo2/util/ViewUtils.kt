package com.example.administrator.databindingdemo2.util

import android.databinding.BindingAdapter
import android.graphics.Color
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.administrator.databindingdemo2.impl.ClickEventImpl

class ViewUtils {
    companion object {
        @JvmStatic
        @BindingAdapter(value = "text")
        fun addLog(textView: TextView, string: String) {
            textView.text = "这是来自自定义的text:$string"
        }

        @JvmStatic
        @BindingAdapter(value = ["startText", "endText"])
        fun changeValue(textView: TextView, startText: String, endText: String) {
            textView.text = "这是来自自定义的text:$startText"
            Handler().postDelayed({
                textView.text = "这是来自自定义的text:$endText"
            }, 3000)
        }

        @JvmStatic
        @BindingAdapter(value = ["android:background"])
        fun changeSourceAttribute(textView: TextView, boolean: Boolean) {
            if (boolean) {
                textView.setBackgroundColor(Color.WHITE)
            }
            else {
                textView.setBackgroundColor(Color.BLACK)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["clickEventImpl", "inputValue"], requireAll = false)
        fun setOnClickEventImpl(textView: TextView, clickEventImpl: ClickEventImpl, inputValue: Boolean) {
            textView.setOnClickListener {
                clickEventImpl.clickEvent(textView, "$inputValue")
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["rvs"])
        fun <T : RecyclerView.ViewHolder> setRecyclerViews(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<T>) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = adapter
        }
    }
}