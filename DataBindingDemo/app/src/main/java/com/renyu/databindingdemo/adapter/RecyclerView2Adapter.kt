package com.renyu.databindingdemo.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.renyu.databindingdemo.BR
import com.renyu.databindingdemo.MulTypeImpl
import com.renyu.databindingdemo.RecyclerView2Activity
import com.renyu.databindingdemo.model.User

class RecyclerView2Adapter<T : ViewDataBinding, D: MulTypeImpl>(private val users: ArrayList<D>, private val listener: RecyclerView2Activity.RVListener): RecyclerView.Adapter<RecyclerView2Adapter.RecyclerViewHolder2<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder2<T> {
        val dataBindingUtil = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), viewType, parent, false)
        return RecyclerViewHolder2(dataBindingUtil)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: RecyclerViewHolder2<T>, position: Int) {
        val dataBindingUtil = DataBindingUtil.getBinding<T>(holder.itemView)
        dataBindingUtil?.setVariable(BR.user, users[position])
        dataBindingUtil?.setVariable(BR.listener, listener)
        dataBindingUtil?.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return users[position].getLayoutResouse()
    }

    class RecyclerViewHolder2<out T : ViewDataBinding>(dataBindingUtil: T) : RecyclerView.ViewHolder(dataBindingUtil.root)
}