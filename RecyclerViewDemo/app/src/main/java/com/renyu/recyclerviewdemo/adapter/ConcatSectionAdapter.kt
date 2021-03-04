package com.renyu.recyclerviewdemo.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renyu.recyclerviewdemo.databinding.LayoutConcatSectionBinding

class ConcatSectionAdapter(private val beans: ArrayList<ArrayList<String>>) : RecyclerView.Adapter<ConcatSectionAdapter.ConcatSectionViewHolder>() {
    private val scrollState: MutableMap<String, Parcelable?> by lazy { mutableMapOf() }

    private val viewPool by lazy { RecyclerView.RecycledViewPool() }

    class ConcatSectionViewHolder(private val binding: LayoutConcatSectionBinding) : RecyclerView.ViewHolder(binding.root) {
        val _bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcatSectionViewHolder {
        val binding = LayoutConcatSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatSectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConcatSectionViewHolder, position: Int) {
        holder._bind.tvConcatSection.text = "${holder.layoutPosition}"
        val _layoutManager = LinearLayoutManager(holder._bind.root.context, RecyclerView.HORIZONTAL, false)
        // 设置预加载的数量
        _layoutManager.initialPrefetchItemCount = 4
        holder._bind.rvConcatSection.apply {
            // 给相同视图类型的 RecyclerView 设置一个共享的 View Pool，提高了垂直方向滚动的性能
            setRecycledViewPool(viewPool)
            layoutManager = _layoutManager
            adapter = ConcatInnerAdapter(beans[holder.layoutPosition])
        }
        val key = holder.layoutPosition.toString()
        if (scrollState[key] != null) {
            holder._bind.rvConcatSection.layoutManager?.onRestoreInstanceState(scrollState[key])
        }
    }

    override fun getItemCount() = beans.size

    override fun onViewRecycled(holder: ConcatSectionViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition.toString()
        // 滑动位置的丢失，回收的时候保存滑动的位置，以及重新绑定时恢复滑动的位置
        scrollState[key] = holder._bind.rvConcatSection.layoutManager?.onSaveInstanceState()
    }
}