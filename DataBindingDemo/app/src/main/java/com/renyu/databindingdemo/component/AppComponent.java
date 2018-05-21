package com.renyu.databindingdemo.component;

import android.content.SharedPreferences;

import com.renyu.databindingdemo.application.MyApp;
import com.renyu.databindingdemo.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject2(MyApp myApp);
    SharedPreferences sharedPreferences();
}
