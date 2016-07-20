package com.example.clevo.dagger2demo.component;

import com.example.clevo.dagger2demo.DemoApplication;
import com.example.clevo.dagger2demo.MainActivity;
import com.example.clevo.dagger2demo.model.ModelC;
import com.example.clevo.dagger2demo.module.TotalModule2;

import dagger.Component;

/**
 * Created by Clevo on 2016/7/11.
 */
@Component(modules = TotalModule2.class)
public interface TotalComponent2 {
    void inject2(DemoApplication application);

    ModelC getModelC();
}
