package com.renyu.weixinedittext.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renyu.weixinedittext.BottomClass
import com.renyu.weixinedittext.MainActivity
import com.renyu.weixinedittext.R
import com.renyu.weixinedittext.TopClass
import kotlinx.android.synthetic.main.adapter_main.view.*

/**
 * Created by Administrator on 2018/3/5 0005.
 */
class MainAdapter(private val beans: ArrayList<Any>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var height = 0

    enum class TYPE(val value: Int) {
        TOP(0), NORMAL(1), BOTTOM(2)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            TYPE.NORMAL.value -> {
                val view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false)
                return MainNormalViewHolder(view)
            }
            TYPE.TOP.value -> {
                val view = LayoutInflater.from(context).inflate(R.layout.adapter_top, parent, false)
                return MainTopViewHolder(view)
            }
            TYPE.BOTTOM.value -> {
                val view = LayoutInflater.from(context).inflate(R.layout.adapter_bottom, parent, false)
                view.layoutParams.height = height
                return MainBottomViewHolder(view)
            }
        }
        throw Exception()
    }

    override fun getItemCount() = beans.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder != null) {
            when(getItemViewType(position)) {
                TYPE.NORMAL.value -> {
                    (holder as MainNormalViewHolder).setText(beans[position] as String)
                    holder.clickComment(holder.layoutPosition)
                }
                TYPE.TOP.value -> {}
                TYPE.BOTTOM.value -> {}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when(beans[position]) {
            is String -> return TYPE.NORMAL.value
            is TopClass -> return TYPE.TOP.value
            is BottomClass -> return TYPE.BOTTOM.value
        }
        return super.getItemViewType(position)
    }

    inner class MainNormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setText(text: String) {
            itemView.tv_title.text = text
        }

        fun clickComment(position: Int) {
            itemView.tv_comment.setOnClickListener {
                (context as MainActivity).showInputComment(itemView.tv_comment, position)
            }
        }
    }

    inner class MainTopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class MainBottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}