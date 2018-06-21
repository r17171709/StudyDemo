package com.renyu.architecturedemo.dagger2.module;

import com.renyu.architecturedemo.HttpsUtils;
import com.renyu.architecturedemo.MyApplication;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public MyApplication provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public OkHttpClient providerOkHttpClient() {
        HttpsUtils.SSLParams sslParams= HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .hostnameVerifier((hostname, session) -> true)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }
}
