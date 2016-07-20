package com.example.clevo.dagger2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.clevo.dagger2demo.component.DaggerTotalComponent;
import com.example.clevo.dagger2demo.component.TotalComponent;
import com.example.clevo.dagger2demo.interfaces.ForModel1;
import com.example.clevo.dagger2demo.interfaces.ForModel2;
import com.example.clevo.dagger2demo.model.ModelB;
import com.example.clevo.dagger2demo.model.ModelD;
import com.example.clevo.dagger2demo.module.TotalModule;
import com.example.clevo.dagger2demo.module.TotalModule3;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textview;

    @Inject
//    @Named("ForModel1")
    @ForModel1
    ModelD modelD;

    @Inject
//    @Named("ForModel2")
    @ForModel2
    ModelD modelD2;

    @Inject
//    @Named("ForModel1")
    @ForModel1
    ModelD modelD3;

    @Inject
//    @Named("ForModel2")
    @ForModel2
    ModelD modelD4;

    @Inject
    ModelD modelD5;

    @Inject
    Lazy<ModelD> modelD6;

    @Inject
    Provider<ModelD> modelD7;

    @Inject
    ModelB modelB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TotalComponent component=DaggerTotalComponent.builder().totalComponent2(((DemoApplication) getApplication()).component()).totalModule(new TotalModule(2, 3)).build();
        component.totalComponent3().inject3(this);
        Log.d("MainActivity", modelD.getA() + " " + modelD.getB());
        Log.d("MainActivity", modelD2.getA() + " " + modelD2.getB());
        Log.d("MainActivity", modelD5.getA() + " " + modelD5.getB());

        modelD=modelD;
        modelD3=modelD3;

        modelD2=modelD2;
        modelD4=modelD4;

        modelB=modelB;

        ModelD modelD6_=modelD6.get();
        ModelD modelD6__=modelD6.get();
        ModelD modelD7_=modelD7.get();
        ModelD modelD7__=modelD7.get();
    }
}
