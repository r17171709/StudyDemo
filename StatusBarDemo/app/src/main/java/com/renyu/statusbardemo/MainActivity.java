package com.renyu.statusbardemo;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.renyu.statusbardemo.activity.BaseActivity;

public class MainActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @Override
    public void initParams() {
        ((AppBarLayout)  findViewById(R.id.main_appbarlayout)).addOnOffsetChangedListener(this);
    }

    @Override
    public int initViews() {
        return R.layout.activity_main;
    }

    @Override
    public int setStatusBarColor() {
        return 0;
    }

    @Override
    public int setStatusBarTranslucent() {
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {

        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            findViewById(R.id.main_toolbar).setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        } else {
            findViewById(R.id.main_toolbar).setBackgroundColor(Color.TRANSPARENT);
        }

        ArgbEvaluator evaluator=new ArgbEvaluator();
        float percent=((float) -verticalOffset)/ appBarLayout.getTotalScrollRange();
        findViewById(R.id.main_toolbar).setBackgroundColor((Integer) evaluator.evaluate(percent, Color.TRANSPARENT, getResources().getColor(R.color.colorAccent)));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor((Integer) evaluator.evaluate(percent, Color.TRANSPARENT, getResources().getColor(R.color.colorAccent)));

    }
}
