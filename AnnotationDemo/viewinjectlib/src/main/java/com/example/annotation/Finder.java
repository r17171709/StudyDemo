package com.example.annotation;

import android.app.Activity;
import android.view.View;

/**
 * Created by RG on 2016/6/2.
 */
public enum Finder {

    Activity {
        @Override
        public View findViewById(Object activity, int id) {
            return ((Activity) activity).findViewById(id);
        }

        @Override
        public void setContentView(Object activity, int id) {
            ((Activity) activity).setContentView(id);
        }
    };

    public abstract View findViewById(Object activity, int id);

    public abstract void setContentView(Object activity, int id);
}
