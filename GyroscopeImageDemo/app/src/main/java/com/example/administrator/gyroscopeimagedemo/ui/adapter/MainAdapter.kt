package com.example.administrator.gyroscopeimagedemo.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.administrator.gyroscopeimagedemo.R
import com.example.administrator.gyroscopeimagedemo.utils.GyroscopeTransFormation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(val context: Context, val beans: ArrayList<String>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, p0, false)
        return MainViewHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(p0: MainViewHolder, p1: Int) {
        p0.setImage(beans[p0.layoutPosition])
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setImage(string: String) {
//            Glide.with(itemView.iv_adapter_image.context)
//                    .load(string)
//                    .into(itemView.iv_adapter_image)

            Picasso.get()
                    .load(string)
                    .transform(GyroscopeTransFormation(1080, 600))
                    .into(itemView.iv_adapter_image)
        }
    }
}