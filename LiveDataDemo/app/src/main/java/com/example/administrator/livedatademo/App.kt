package com.example.administrator.livedatademo

import android.support.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.facebook.drawee.backends.pipeline.Fresco
import com.renyu.commonlibrary.commonutils.ImagePipelineConfigUtils
import com.renyu.commonlibrary.network.HttpsUtils
import com.renyu.commonlibrary.network.Retrofit2Utils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        // 初始化网络请求
        val retrofit2Utils = Retrofit2Utils.getInstance("http://www.mocky.io/v2/")
        val baseBuilder = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
        //https默认信任全部证书
        val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)
        baseBuilder.hostnameVerifier { s, sslSession -> true }.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        retrofit2Utils.addBaseOKHttpClient(baseBuilder.build())
        retrofit2Utils.baseBuild()

        // 初始化工具库
        Utils.init(this)

        // 初始化fresco
        Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this))
    }
}