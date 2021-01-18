package com.renyu.constraintlayoutdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renyu.constraintlayoutdemo.databinding.AdapterMotionlistBinding

class MotionListAdapter(private val beans: ArrayList<String>) :
    RecyclerView.Adapter<MotionListAdapter.MotionListViewHolder>() {
    private val clickPosition by lazy { BooleanArray(beans.size) }

    class MotionListViewHolder(private val binding: AdapterMotionlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val motionContainer = binding.motionContainer
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotionListViewHolder {
        return MotionListViewHolder(
            AdapterMotionlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MotionListViewHolder, position: Int) {
        if (clickPosition[position]) {
            holder.motionContainer.progress = 1f
        } else {
            holder.motionContainer.progress = 0f
        }
        holder.itemView.setOnClickListener {
            clickPosition.fill(false)
            if (holder.motionContainer.progress == 1.0f) {
                holder.motionContainer.transitionToStart()
            } else if (holder.motionContainer.progress == 0.0f) {
                clickPosition[position] = true
                holder.motionContainer.transitionToEnd()
            }
            for (i in 0 until itemCount) {
                if (i != position) {
                    notifyItemChanged(i, "collapse")
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: MotionListViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads[0] == "collapse") {
                holder.motionContainer.transitionToStart()
            } else {
                holder.motionContainer.transitionToEnd()
            }
        }
    }

    override fun getItemCount() = beans.size
}