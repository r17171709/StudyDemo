package com.renyu.videolistdemo

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.dueeeke.videoplayer.player.AndroidMediaPlayerFactory
import com.dueeeke.videoplayer.player.VideoViewConfig
import com.dueeeke.videoplayer.player.VideoViewManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Utils.init(this)

        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder().setPlayerFactory(AndroidMediaPlayerFactory.create())
                .build()
        )
    }
}