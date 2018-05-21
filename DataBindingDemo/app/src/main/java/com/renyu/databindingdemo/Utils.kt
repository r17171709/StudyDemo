package com.renyu.databindingdemo

import android.databinding.BindingAdapter
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.renyu.databindingdemo.adapter.RecyclerViewAdapter

class Utils {
    companion object {
        @JvmStatic
        @BindingAdapter("scorevalue")
        fun loadScoreValue(textView: TextView, id: Int) {
            Handler().postDelayed({textView.text = "$id"}, 2000)
        }

        @JvmStatic
        @BindingAdapter("scorevalue2")
        fun loadScoreValue2(textView: TextView, value: String) {
            Handler().postDelayed({textView.text = value}, 2000)
        }

        @JvmStatic
        fun showTextValue(value: String) : String {
            return "changeAfterValue$value"
        }

        @JvmStatic
        @BindingAdapter(value = ["customeradapter"])
        fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerViewAdapter) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }
}