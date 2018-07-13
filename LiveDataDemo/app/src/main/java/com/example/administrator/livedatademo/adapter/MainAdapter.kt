package com.example.administrator.livedatademo.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.livedatademo.BR
import com.example.administrator.livedatademo.R
import com.example.administrator.livedatademo.databinding.AdapterMainBinding
import com.example.administrator.livedatademo.model.Data1

class MainAdapter(private val beans: ArrayList<Data1>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<AdapterMainBinding>(LayoutInflater.from(parent.context), R.layout.adapter_main, parent, false)
        return MainViewHolder(viewDataBinding)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(BR.data1, beans[position])
        holder.viewDataBinding.executePendingBindings()
    }

    class MainViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        val viewDataBinding: ViewDataBinding = viewDataBinding
    }
}