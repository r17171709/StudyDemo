package com.renyu.dragrecyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.renyu.dragrecyclerview.R
import com.renyu.dragrecyclerview.bean.ImageBean
import com.renyu.dragrecyclerview.utils.OtherUtils
import kotlinx.android.synthetic.main.adapter_staggergrid3.view.*

/**
 * Created by Administrator on 2020/6/18.
 */
class StaggerGridAdapter3(private val beans: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER = 0
    private val CONTENT = 1

    class StaggerGridViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
    class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_header, null, false)
            return HeaderViewHolder(view)
        } else if (viewType == CONTENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_staggergrid3, null, false)
            return StaggerGridViewHolder(view)
        }
        throw Exception("error")
    }

    override fun getItemCount() = beans.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(holder.adapterPosition) == HEADER) {

        } else if (getItemViewType(holder.adapterPosition) == CONTENT) {
            val params = holder.itemView.iv_stagger3.layoutParams as LinearLayout.LayoutParams
            params.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20f)) / 2
            params.height =
                ((beans[holder.adapterPosition] as ImageBean).height * 1.0f / (beans[holder.adapterPosition] as ImageBean).width * params.width).toInt()
            holder.itemView.iv_stagger3.layoutParams = params
            OtherUtils.loadFresco(
                (beans[holder.adapterPosition] as ImageBean).url,
                SizeUtils.dp2px(200f).toFloat(),
                SizeUtils.dp2px(200f).toFloat(),
                holder.itemView.iv_stagger3
            )
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val lp = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        if (holder.itemViewType == HEADER) {
            lp.isFullSpan = true
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (beans[position] is String) {
            return HEADER
        } else if (beans[position] is ImageBean) {
            return CONTENT
        }
        return super.getItemViewType(position)
    }
}