package com.renyu.dragrecyclerview.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.renyu.dragrecyclerview.MainActivity
import com.renyu.dragrecyclerview.R
import kotlinx.android.synthetic.main.adapter_recyclerview.view.*

/**
 * Created by Administrator on 2020/6/17.
 */
class RecyclerViewAdapter(private val beans: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {
    class RecyclerViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_recyclerview, null, false)
        return RecyclerViewViewHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.itemView.tv_1.text = beans[holder.layoutPosition]
        holder.itemView.tv_1.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (holder.itemView.context as MainActivity).startDrag(holder)
            }
            return@setOnTouchListener true
        }
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "${beans[holder.layoutPosition]}",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}