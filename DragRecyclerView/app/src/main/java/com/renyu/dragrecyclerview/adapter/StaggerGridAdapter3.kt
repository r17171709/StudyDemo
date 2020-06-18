package com.renyu.dragrecyclerview.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.renyu.dragrecyclerview.R
import com.renyu.dragrecyclerview.bean.ImageBean
import com.renyu.dragrecyclerview.utils.OtherUtils
import kotlinx.android.synthetic.main.adapter_staggergrid3.view.*

/**
 * Created by Administrator on 2020/6/18.
 */
class StaggerGridAdapter3(private val beans: ArrayList<ImageBean>) : RecyclerView.Adapter<StaggerGridAdapter3.StaggerGridViewHolder>() {
    class StaggerGridViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggerGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_staggergrid3, null, false)
        return StaggerGridViewHolder(view)
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: StaggerGridViewHolder, position: Int) {
        val params = holder.itemView.iv_stagger3.layoutParams as LinearLayout.LayoutParams
        params.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20f)) / 2
        params.height = (beans[holder.adapterPosition].height * 1.0f / beans[holder.adapterPosition].width * params.width).toInt()
        holder.itemView.iv_stagger3.layoutParams = params
        OtherUtils.loadFresco(beans[holder.adapterPosition].url, SizeUtils.dp2px(200f).toFloat(), SizeUtils.dp2px(200f).toFloat(), holder.itemView.iv_stagger3)
    }
}