package com.renyu.coroutinedemo;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.renyu.commonlibrary.commonutils.ImagePipelineConfigUtils;
import com.renyu.commonlibrary.commonutils.Utils;
import com.renyu.commonlibrary.network.HttpsUtils;
import com.renyu.commonlibrary.network.Retrofit2Utils;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ExampleApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        String processName= Utils.getProcessName(android.os.Process.myPid());
        if (processName.equals(getPackageName())) {
            // 初始化网络请求
            Retrofit2Utils retrofit2Utils=Retrofit2Utils.getInstance("http://www.mocky.io/v2/");
            OkHttpClient.Builder baseBuilder=new OkHttpClient.Builder()
                    // 限制抓包
                    .proxy(Proxy.NO_PROXY)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS);
            //https默认信任全部证书
            HttpsUtils.SSLParams sslParams= HttpsUtils.getSslSocketFactory(null, null, null);
            baseBuilder.hostnameVerifier((s, sslSession) -> true).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            retrofit2Utils.addBaseOKHttpClient(baseBuilder.build());
            retrofit2Utils.baseBuild();

            // 初始化工具库
            com.blankj.utilcode.util.Utils.init(this);

            // 初始化fresco
            Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this));
        }
    }
}
