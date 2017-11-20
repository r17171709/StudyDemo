package com.renyu.recyclerviewdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.impl.StartDragAndSwipeListener
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource

/**
 * Created by renyu on 2017/11/17.
 */
class DragSwipeAdapter(private val beans: ArrayList<String>, private val context: Context, private val startDragAndSwipeListener: StartDragAndSwipeListener) : RecyclerView.Adapter<DragSwipeAdapter.DragSwipeHolder>() {

    override fun getItemCount(): Int = beans.size

    override fun onBindViewHolder(holder: DragSwipeHolder?, position: Int) {
        holder?.iv_adapter?.imageResource = R.mipmap.ic_launcher
        holder?.tv_adapter?.text = beans[holder?.layoutPosition!!]
        holder?.iv_adapter?.setOnTouchListener { _, _ ->
            startDragAndSwipeListener?.onStartDrag(holder)
            true
        }
        holder?.iv2_adapter?.setOnTouchListener { _, _ ->
            startDragAndSwipeListener?.onStartSwipe(holder)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DragSwipeHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
        return DragSwipeHolder(view)
    }

    class DragSwipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_adapter: ImageView = itemView.find(R.id.iv_adapter)
        val tv_adapter: TextView = itemView.find(R.id.tv_adapter)
        val iv2_adapter: ImageView = itemView.find(R.id.iv2_adapter)
    }
}