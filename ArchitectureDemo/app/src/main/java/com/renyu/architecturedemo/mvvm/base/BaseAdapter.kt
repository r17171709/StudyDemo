package com.renyu.architecturedemo.mvvm.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.renyu.architecturedemo.mvvm.impl.EventListener
import java.util.*

abstract class BaseAdapter<T: ViewDataBinding, D>(val beans: ArrayList<D>, private val eventListener: EventListener) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    abstract fun initViews(): Int
    abstract fun dataVariableId(): Int
    abstract fun eventListenerVariableId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), initViews(), parent, false)
        return BaseViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = beans.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.viewDataBinding?.setVariable(dataVariableId(), beans[holder.layoutPosition])
        holder.viewDataBinding?.setVariable(eventListenerVariableId(), eventListener)
        holder.viewDataBinding?.executePendingBindings()
    }

    class BaseViewHolder : RecyclerView.ViewHolder {

        var viewDataBinding: ViewDataBinding? = null

        constructor(viewDataBinding: ViewDataBinding) : super(viewDataBinding.root) {
            this.viewDataBinding = viewDataBinding
        }
    }
}