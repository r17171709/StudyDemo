package com.renyu.listvisibilitydemo.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renyu.listvisibilitydemo.ListItem
import com.renyu.listvisibilitydemo.R
import kotlinx.android.synthetic.main.adapter_main.view.*

/**
 * Created by Administrator on 2018/3/1 0001.
 */
class MainAdapter2(private val beans: ArrayList<String>, private val context: Context, private val recyclerView: RecyclerView) : RecyclerView.Adapter<MainAdapter2.MainHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder2 {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return MainHolder2(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: MainHolder2?, position: Int) {
        holder?.setText(beans[position])
    }

    /**
     * 获取当前ViewHolder，以便进行后续操作
     */
    fun getItem(position: Int): ListItem? {
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
        if (viewHolder is MainHolder2) {
            return viewHolder
        }
        return null
    }

    class MainHolder2(itemView: View) : RecyclerView.ViewHolder(itemView), ListItem {
        override fun setActive() {
            itemView.layout_main.setBackgroundColor(Color.YELLOW)
        }

        override fun deActive() {
            itemView.layout_main.setBackgroundColor(Color.WHITE)
        }

        override fun setPercent(percent: Int) {
            itemView.tv_percent.text = ""+percent
        }

        fun setText(text: String) {
            itemView.tv_main.text = text
        }
    }
}