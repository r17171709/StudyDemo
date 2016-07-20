package com.example.clevo.dagger2demo.component;

import com.example.clevo.dagger2demo.ScopeDemo;
import com.example.clevo.dagger2demo.ScopeDemo2;
import com.example.clevo.dagger2demo.module.TotalModule;

import dagger.Component;

/**
 * Created by Clevo on 2016/7/11.
 */
@ScopeDemo
@ScopeDemo2
@Component(dependencies = TotalComponent2.class, modules = TotalModule.class)
public interface TotalComponent {
//    void inject(MainActivity activity);

    TotalComponent3 totalComponent3();
}
