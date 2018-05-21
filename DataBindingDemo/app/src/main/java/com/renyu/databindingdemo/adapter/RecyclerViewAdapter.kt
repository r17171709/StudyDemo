package com.renyu.databindingdemo.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.renyu.databindingdemo.BR
import com.renyu.databindingdemo.R
import com.renyu.databindingdemo.databinding.AdapterRvBinding
import com.renyu.databindingdemo.model.User

class RecyclerViewAdapter(private val beans: ArrayList<User>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<AdapterRvBinding>(LayoutInflater.from(parent.context), R.layout.adapter_rv, parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val dataBinding = DataBindingUtil.getBinding<AdapterRvBinding>(holder.itemView)
//        dataBinding?.user = beans[position]
        dataBinding?.setVariable(BR.user, beans[position])
        dataBinding?.executePendingBindings()
        holder.itemView.findViewById<LinearLayout>(R.id.layout_adapter_rv).setOnClickListener {
            Toast.makeText(holder.itemView.context, beans[position].blog, Toast.LENGTH_SHORT).show()
            beans[position].blog = "测试"
        }
    }

    class RecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}