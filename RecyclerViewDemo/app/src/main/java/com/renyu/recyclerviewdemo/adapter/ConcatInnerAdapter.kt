package com.renyu.recyclerviewdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renyu.recyclerviewdemo.databinding.LayoutConcatItemBinding

class ConcatInnerAdapter(private val beans: ArrayList<String>) : RecyclerView.Adapter<ConcatInnerAdapter.ConcatViewHolder>() {

    class ConcatViewHolder(binding: LayoutConcatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val _binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcatViewHolder {
        val binding = LayoutConcatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConcatViewHolder, position: Int) {
        holder._binding.ivConcatItem
    }

    override fun getItemCount() = beans.size
}