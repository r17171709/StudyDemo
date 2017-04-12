package com.renyu.statusbardemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static com.renyu.statusbardemo.utils.BarUtils.setColor;
import static com.renyu.statusbardemo.utils.BarUtils.setTranslucent;

/**
 * Created by renyu on 2017/4/11.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract void initParams();
    public abstract int initViews();
    public abstract int setStatusBarColor();
    public abstract int setStatusBarTranslucent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initViews()!=0) {
            setContentView(initViews());
        }

        // 设置沉浸式，二选一
        if (setStatusBarColor()!=0) {
            setColor(this, setStatusBarColor());
        }
        if (setStatusBarTranslucent()!=0) {
            setTranslucent(this);
        }

        initParams();
    }
}
