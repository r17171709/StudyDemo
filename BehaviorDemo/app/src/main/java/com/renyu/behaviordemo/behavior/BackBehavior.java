package com.renyu.behaviordemo.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by renyu on 2016/11/7.
 */

public class BackBehavior extends CoordinatorLayout.Behavior<View> {

    public BackBehavior() {
        super();
    }

    public BackBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            int height= (int) dependency.getY();
            Log.d("BackBehavior", "height:" + height);
            child.setTranslationY(-height);
        }
        return true;
    }
}
