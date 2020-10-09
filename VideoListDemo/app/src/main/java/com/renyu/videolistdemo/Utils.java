package com.renyu.videolistdemo;

import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

public class Utils {
    public static void removeViewFormParent(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(view);
        }
    }
}
