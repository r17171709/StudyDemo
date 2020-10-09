package com.renyu.videolistdemo

import android.os.Bundle
import android.transition.Transition
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videoplayer.player.AndroidMediaPlayer
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoViewManager
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var mVideoView: VideoView<AndroidMediaPlayer>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        ViewCompat.setTransitionName(player_container, VIEW_NAME_PLAYER_CONTAINER)

        addTransitionListener()
    }

    override fun onResume() {
        super.onResume()
        if (mVideoView != null) {
            mVideoView!!.resume()
        }
    }


    override fun onPause() {
        if (isFinishing) {
            // 移除Controller
            mVideoView!!.setVideoController(null)
            // 将VideoView置空，其目的是不执行 super.onPause(); 和 super.onDestroy(); 中的代码
            mVideoView = null
        }
        super.onPause()
        if (mVideoView != null) {
            mVideoView!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mVideoView != null) {
            mVideoView!!.release()
        }
    }

    override fun onBackPressed() {
        if (mVideoView == null || !mVideoView!!.onBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun addTransitionListener() {
        val transition = window.sharedElementEnterTransition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
                initVideoView()
            }

            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
            }

            override fun onTransitionCancel(transition: Transition?) {
                transition?.removeListener(this)
            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionResume(transition: Transition?) {

            }
        })
    }

    private fun initVideoView() {
        mVideoView = getVideoViewManager().get("seamless") as VideoView<AndroidMediaPlayer>
        // 详情页取消静音
        mVideoView!!.isMute = false
        Utils.removeViewFormParent(mVideoView)
        player_container.addView(mVideoView)
        val controller = StandardVideoController(this)
        mVideoView!!.setVideoController(controller)
        val title = intent.getStringExtra("title")
        controller.addDefaultControlComponent(title, false)
        // 区分无缝播放
        if (intent.getBooleanExtra("seamless_play", false)) {
            controller.setPlayState(mVideoView!!.currentPlayState)
            controller.setPlayerState(mVideoView!!.currentPlayerState)
        } else {
            val url = intent.getStringExtra("url")
            mVideoView!!.setUrl(url)
            mVideoView!!.start()
        }
    }

    private fun getVideoViewManager(): VideoViewManager {
        return VideoViewManager.instance()
    }
}

val VIEW_NAME_PLAYER_CONTAINER = "player_container"