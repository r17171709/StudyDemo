package com.example.clevo.dagger2demo.module;

import com.example.clevo.dagger2demo.ScopeDemo;
import com.example.clevo.dagger2demo.ScopeDemo2;
import com.example.clevo.dagger2demo.interfaces.ForModel1;
import com.example.clevo.dagger2demo.interfaces.ForModel2;
import com.example.clevo.dagger2demo.model.ModelC;
import com.example.clevo.dagger2demo.model.ModelD;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Clevo on 2016/7/11.
 */
@Module
public class TotalModule {

    int a;
    int b;

    public TotalModule(int a, int b) {
        this.a=a;
        this.b=b;
    }

    @ScopeDemo
    @Provides
//    @Named("ForModel1")
    @ForModel1
    public ModelD providesModelD1() {
        return new ModelD(a, b);
    }

    @ScopeDemo2
    @Provides
//    @Named("ForModel2")
    @ForModel2
    public ModelD providesModelD2() {
        return new ModelD(b, a);
    }

    @Provides
    public ModelD providesModelD3(ModelC modelC) {
        return new ModelD(modelC.getA(), modelC.getB());
    }
}
