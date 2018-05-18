package com.renyu.architecturedemo.dagger2.component;

import com.renyu.architecturedemo.MyApplication;
import com.renyu.architecturedemo.dagger2.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void injectApp(MyApplication application);
    OkHttpClient getOkHttpClient();
}
