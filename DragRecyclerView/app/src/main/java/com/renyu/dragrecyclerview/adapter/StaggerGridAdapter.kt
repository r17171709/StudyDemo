package com.renyu.dragrecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.renyu.dragrecyclerview.R
import com.renyu.dragrecyclerview.bean.ImageBean
import kotlinx.android.synthetic.main.adapter_staggergrid.view.*

/**
 * Created by Administrator on 2020/6/18.
 */
class StaggerGridAdapter(private val beans: ArrayList<ImageBean>) :
    RecyclerView.Adapter<StaggerGridAdapter.StaggerGridViewHolder>() {
    class StaggerGridViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggerGridViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_staggergrid, null, false)
        return StaggerGridViewHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: StaggerGridViewHolder, position: Int) {
        holder.itemView.iv_stagger.setImageResource(beans[holder.adapterPosition].id)
        val params = holder.itemView.iv_stagger.layoutParams as LinearLayout.LayoutParams
        params.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20f)) / 2
        params.height =
            (beans[holder.adapterPosition].height * 1.0f / beans[holder.adapterPosition].width * params.width).toInt()
        holder.itemView.iv_stagger.layoutParams = params
    }
}