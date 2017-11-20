package com.renyu.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.bean.SectionBean
import org.jetbrains.anko.find

/**
 * Created by renyu on 2017/10/31.
 */
class StickyHeaderAdapter() : RecyclerView.Adapter<StickyHeaderAdapter.StickyHeaderHolder>() {

    var context: Context? = null
    var beans: ArrayList<String>? = null

    constructor(context: Context, beans: ArrayList<String>) : this() {
        this.context = context
        this.beans = beans
    }

    override fun onBindViewHolder(holder: StickyHeaderHolder?, position: Int) {
        holder?.tv_adapter!!.text = beans!![position]

        val section = SectionBean()
        if (position == 0) {
            section.start = true
        }
        else {
            section.start = beans!![position] != beans!![position-1]
        }
        holder?.layout_adapter.tag = section
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StickyHeaderHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return StickyHeaderHolder(view)
    }

    override fun getItemCount(): Int {
        return if (beans == null) {
            0
        }
        else {
            beans!!.size
        }
    }

    class StickyHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_adapter: TextView = itemView.find(R.id.tv_adapter)
        val layout_adapter: LinearLayout = itemView.find(R.id.layout_adapter)
    }
}