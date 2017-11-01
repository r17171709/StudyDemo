package com.renyu.recyclerviewdemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find

/**
 * Created by renyu on 2017/9/22.
 */
class MainAdapter() : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    var context: Context? = null
    var beans: ArrayList<String>? = null

    constructor(context: Context, beans: ArrayList<String>) : this() {
        this.context = context
        this.beans = beans
    }

    override fun onBindViewHolder(holder: MainHolder?, position: Int) {
        holder?.tv_adapter!!.text = beans!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
        return if (beans == null) {
            0
        }
        else {
            beans!!.size
        }
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_adapter: TextView = itemView.find(R.id.tv_adapter)
    }
}