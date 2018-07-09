package com.example.administrator.databindingdemo2.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.databindingdemo2.BR
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.AdapterRecyclerviewBinding
import com.example.administrator.databindingdemo2.model.Teacher2
import com.example.administrator.databindingdemo2.util.MyHandlers

class RecyclerViewAdapter(private val teachers: ArrayList<Teacher2>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<AdapterRecyclerviewBinding>(LayoutInflater.from(p0.context), R.layout.adapter_recyclerview, p0, false)
        return RecyclerViewHolder(viewDataBinding)
    }

    override fun getItemCount() = teachers.size

    override fun onBindViewHolder(p0: RecyclerViewHolder, p1: Int) {
        p0.dataBinding?.setVariable(BR.teacher, teachers[p1])
        p0.dataBinding?.setVariable(BR.handlers, MyHandlers())
        p0.dataBinding?.executePendingBindings()
    }

    class RecyclerViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var dataBinding: ViewDataBinding? = viewDataBinding
    }
}