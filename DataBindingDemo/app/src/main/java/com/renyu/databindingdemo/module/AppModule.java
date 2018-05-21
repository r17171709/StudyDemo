package com.renyu.databindingdemo.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.renyu.databindingdemo.application.MyApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private MyApp myApp;

    public AppModule(MyApp myApp) {
        this.myApp = myApp;
    }

    @Provides
    @Singleton
    public MyApp provideMyApp() {
        return myApp;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences() {
        return myApp.getSharedPreferences("Demo", Context.MODE_PRIVATE);
    }
}
