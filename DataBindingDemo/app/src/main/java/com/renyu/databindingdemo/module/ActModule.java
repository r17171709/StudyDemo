package com.renyu.databindingdemo.module;

import com.renyu.databindingdemo.DaggerActivity;
import com.renyu.databindingdemo.model.User;

import dagger.Module;
import dagger.Provides;

@Module
public class ActModule {
    private DaggerActivity activity;

    public ActModule(DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    DaggerActivity provideDaggerActivity() {
        return activity;
    }

    @Provides
    User provideUser() {
        User user = new User();
        user.setBlog("BLOG");
        return user;
    }
}
