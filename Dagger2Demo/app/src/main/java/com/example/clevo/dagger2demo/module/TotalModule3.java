package com.example.clevo.dagger2demo.module;

import com.example.clevo.dagger2demo.model.ModelB;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Clevo on 2016/7/20.
 */
@Module
public class TotalModule3 {

    @Provides
    public ModelB providesModelB() {
        return new ModelB(111, 1333);
    }
}
