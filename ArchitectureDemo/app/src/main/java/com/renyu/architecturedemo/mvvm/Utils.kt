package com.renyu.androidarchitecturedemo.mvvm

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.renyu.architecturedemo.mvvm.SearchAdapter

class Utils {
    companion object {
        @JvmStatic
        @BindingAdapter("adapter")
        fun setAdapter(recyclerView: RecyclerView, adapter: SearchAdapter) {
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }
}