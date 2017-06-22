package com.example.renyu.frescodemo

import android.app.Application
import com.example.renyu.frescodemo.libs.ImagePipelineConfigUtils
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Administrator on 2017/5/31.
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化fresco
        Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this))
//        Fresco.initialize(this)
    }
}