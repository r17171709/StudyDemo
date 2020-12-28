package com.renyu.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.renyu.recyclerviewdemo.R

class LayoutManagerAdapter : RecyclerView.Adapter<LayoutManagerAdapter.LayoutManagerViewHolder>() {
    class LayoutManagerViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutManagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_layoutmanager, parent, false)
        return LayoutManagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: LayoutManagerViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_text).text = "$position"
    }

    override fun getItemCount() = 30
}