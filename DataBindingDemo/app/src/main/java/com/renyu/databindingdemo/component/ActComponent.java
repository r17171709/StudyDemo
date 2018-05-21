package com.renyu.databindingdemo.component;

import com.renyu.databindingdemo.DaggerActivity;
import com.renyu.databindingdemo.module.ActModule;
import com.renyu.databindingdemo.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = ActModule.class, dependencies = AppComponent.class)
public interface ActComponent {
    void inject(DaggerActivity daggerActivity);
}
