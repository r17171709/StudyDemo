package com.example.clevo.dagger2demo.module;

import com.example.clevo.dagger2demo.model.ModelC;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Clevo on 2016/7/11.
 */
@Module
public class TotalModule2 {

    int a;
    int b;

    public TotalModule2(int a, int b) {
        this.a=a;
        this.b=b;
    }

    @Provides
    public ModelC providesModelC1() {
        return new ModelC(a, b);
    }
}
