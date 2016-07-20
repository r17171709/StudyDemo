package com.example.clevo.dagger2demo;

import android.app.Application;

import com.example.clevo.dagger2demo.component.DaggerTotalComponent2;
import com.example.clevo.dagger2demo.component.TotalComponent2;
import com.example.clevo.dagger2demo.model.ModelC;
import com.example.clevo.dagger2demo.module.TotalModule2;

import javax.inject.Inject;

/**
 * Created by Clevo on 2016/7/6.
 */
public class DemoApplication extends Application {

    TotalComponent2 component2;

    @Inject
    ModelC modelC;

    @Override
    public void onCreate() {
        super.onCreate();

        component2=DaggerTotalComponent2.builder().totalModule2(new TotalModule2(1, 1)).build();
        component2.inject2(this);
    }

    public TotalComponent2 component() {
        return component2;
    }
}
