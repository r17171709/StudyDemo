package com.renyu.databindingdemo;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.renyu.databindingdemo.application.MyApp;
import com.renyu.databindingdemo.component.DaggerActComponent;
import com.renyu.databindingdemo.component.DaggerAppComponent;
import com.renyu.databindingdemo.model.User;
import com.renyu.databindingdemo.module.ActModule;
import com.renyu.databindingdemo.module.AppModule;

import javax.inject.Inject;

public class DaggerActivity extends AppCompatActivity {

    @Inject
    User user;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activtiy_dagger);

        // DaggerAppComponent.create()
        DaggerActComponent.builder()
                .appComponent(DaggerAppComponent.builder().appModule(new AppModule((MyApp) getApplication())).build())
                .actModule(new ActModule(this))
                .build()
                .inject(this);

        viewDataBinding.setVariable(BR.user, user);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.getBlog();
            }
        }, 3000);
    }
}
