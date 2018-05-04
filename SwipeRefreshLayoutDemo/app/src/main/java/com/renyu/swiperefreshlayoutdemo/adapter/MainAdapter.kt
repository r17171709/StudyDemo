package com.renyu.swiperefreshlayoutdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renyu.swiperefreshlayoutdemo.R
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(private val context: Context, private val beans: ArrayList<String>): RecyclerView.Adapter<MainAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.setText(beans[position])
    }

    class MainHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setText(text: String) {
            itemView.tv_main_adapter.text = text
        }
    }
}