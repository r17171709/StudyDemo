package com.renyu.listvisibilitydemo

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_main.view.*

/**
 * Created by Administrator on 2018/2/24 0024.
 */
class MainAdapter(private val beans: ArrayList<String>, private val context: Context, private val recyclerView: RecyclerView) : RecyclerView.Adapter<MainAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: MainHolder?, position: Int) {
        holder?.setText(beans[position])
    }

    /**
     * 获取当前ViewHolder，以便进行后续操作
     */
    fun getItem(position: Int): ListItem? {
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
        if (viewHolder is MainHolder) {
            return viewHolder
        }
        return null
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ListItem {
        override fun setPercent(percent: Int) {
            itemView.tv_percent.text = ""+percent
        }

        override fun setActive() {
            itemView.layout_main.setBackgroundColor(Color.YELLOW)
        }

        override fun deActive() {
            itemView.layout_main.setBackgroundColor(Color.WHITE)
        }

        fun setText(text: String) {
            itemView.tv_main.text = text
        }
    }
}