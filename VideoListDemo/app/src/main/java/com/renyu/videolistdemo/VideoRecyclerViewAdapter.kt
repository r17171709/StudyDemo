package com.renyu.videolistdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dueeeke.videocontroller.component.PrepareView

class VideoRecyclerViewAdapter(
    val videos: ArrayList<VideoBean>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mPlayerContainer = view.findViewById<FrameLayout>(R.id.player_container)
        val mTitle = view.findViewById<TextView>(R.id.tv_title)
        val mPrepareView = view.findViewById<PrepareView>(R.id.prepare_view)
        val mThumb = mPrepareView.findViewById<ImageView>(R.id.thumb)

        init {
            view.tag = this
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_video, parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val videoBean = videos[position]
        Glide.with(holder.mThumb.context).load(videoBean.thumb)
            .placeholder(android.R.color.darker_gray).into(holder.mThumb)
        holder.mTitle.text = videoBean.title
        holder.mTitle.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount() = videos.size

    fun addData(videoList: ArrayList<VideoBean>) {
        val size = videos.size
        videos.addAll(videoList)
        // 使用notifyDataSetChanged会导致正在播放的视频中断
        notifyItemRangeChanged(size, videos.size)
    }
}