package com.renyu.videolistdemo

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videocontroller.component.*
import com.dueeeke.videoplayer.player.AndroidMediaPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoViewManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mVideoView: VideoView<AndroidMediaPlayer>? = null
    private var mController: StandardVideoController? = null
    private var mTitleView: TitleView? = null

    // 当前播放位置
    private var mCurPos = -1

    // 上一次播放位置
    private var mLastPos = mCurPos

    private val mVideos by lazy {
        ArrayList<VideoBean>()
    }
    private val adapter by lazy {
        VideoRecyclerViewAdapter(mVideos, object : VideoRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVideoView()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        rv.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {

            }

            override fun onChildViewDetachedFromWindow(view: View) {
                val playerContainer = view.findViewById<FrameLayout>(R.id.player_container)
                val v = playerContainer.getChildAt(0)
                if (v == mVideoView && !mVideoView!!.isFullScreen) {
                    releaseVideoView()
                }
            }
        })
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoPlayVideo(recyclerView)
                }
            }

            private fun autoPlayVideo(recyclerView: RecyclerView) {
                val count = recyclerView.childCount
                // 遍历RecyclerView子控件,如果mPlayerContainer完全可见就开始播放
                for (i in 0 until count) {
                    val itemView = recyclerView.getChildAt(i) ?: continue
                    val viewHolder = itemView.tag as VideoRecyclerViewAdapter.VideoHolder
                    val rect = Rect()
                    viewHolder.mPlayerContainer.getLocalVisibleRect(rect)
                    if (rect.top == 0 && rect.bottom == viewHolder.mPlayerContainer.height) {
                        startPlay(viewHolder.layoutPosition)
                        break
                    }
                }
            }
        })
        initData()
    }

    private fun initData() {
        mVideos.addAll(DataUtil.getVideoList())
        adapter.notifyDataSetChanged()
        // 自动播放第一个
        rv.post {
            startPlay(0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (mLastPos == -1) {
            return
        }
        startPlay(mLastPos)
    }

    override fun onPause() {
        super.onPause()
        releaseVideoView()
    }

    private fun initVideoView() {
        mVideoView = VideoView(this)
        mVideoView!!.setOnStateChangeListener(object : VideoView.OnStateChangeListener {
            override fun onPlayerStateChanged(playerState: Int) {

            }

            override fun onPlayStateChanged(playState: Int) {
                if (playState == VideoView.STATE_IDLE) {
                    // 监听VideoViewManager释放，重置状态
                    Utils.removeViewFormParent(mVideoView)
                    mLastPos = mCurPos
                    mCurPos = -1
                } else if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    // 播放完成之后自动播放下一个
                    val nextPlay = mCurPos + 1
                    releaseVideoView()
                    val count = rv.childCount
                    for (i in 0 until count) {
                        val itemView = rv.getChildAt(i) ?: continue
                        val viewHolder = itemView.tag as VideoRecyclerViewAdapter.VideoHolder
                        val rect = Rect()
                        viewHolder.mPlayerContainer.getLocalVisibleRect(rect)
                        if (rect.top == 0 && rect.bottom == viewHolder.mPlayerContainer.height) {
                            if (viewHolder.layoutPosition == nextPlay) {
                                rv.postDelayed({
                                    startPlay(nextPlay)
                                }, 300)
                                break
                            }
                        }
                    }
                }
            }
        })
        mController = StandardVideoController(this)
        mController!!.addControlComponent(ErrorView(this))
        mController!!.addControlComponent(CompleteView(this))
        mTitleView = TitleView(this)
        mController!!.addControlComponent(mTitleView)
        mController!!.addControlComponent(VodControlView(this))
        mController!!.addControlComponent(GestureView(this))
        mController!!.setEnableOrientation(true)
        mVideoView!!.setVideoController(mController)
    }

    private fun startPlay(position: Int) {
        if (mCurPos == position) {
            return
        }
        if (mCurPos != -1) {
            releaseVideoView()
        }
        val videoBean = mVideos[position]
        mVideoView!!.setUrl(videoBean.url)
        mTitleView!!.setTitle(videoBean.title)
        val itemView = rv.layoutManager!!.findViewByPosition(position) ?: return
        val viewHolder = itemView.tag as VideoRecyclerViewAdapter.VideoHolder
        // 把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController!!.addControlComponent(viewHolder.mPrepareView, true)
        Utils.removeViewFormParent(mVideoView)
        viewHolder.mPlayerContainer.addView(mVideoView, 0)
        // 播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        getVideoViewManager().add(mVideoView, "list")
        mVideoView!!.start()
        // 列表页设置静音
        mVideoView!!.isMute = true
        mCurPos = position
    }

    private fun releaseVideoView() {
        mVideoView!!.release()
        if (mVideoView!!.isFullScreen) {
            mVideoView!!.stopFullScreen()
        }
        // 重置方向
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        mCurPos = -1
    }

    private fun getVideoViewManager(): VideoViewManager {
        return VideoViewManager.instance()
    }
}