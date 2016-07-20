package com.example.clevo.dagger2demo.component;

import com.example.clevo.dagger2demo.MainActivity;
import com.example.clevo.dagger2demo.module.TotalModule3;

import dagger.Subcomponent;

/**
 * Created by Clevo on 2016/7/20.
 */
@Subcomponent(modules = TotalModule3.class)
public interface TotalComponent3 {
    void inject3(MainActivity activity);
}
