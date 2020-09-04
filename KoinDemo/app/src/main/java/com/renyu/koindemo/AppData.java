package com.renyu.koindemo;

import android.app.Application;

/**
 * Created by Administrator on 2020/9/4.
 */
public class AppData {
    private Application application;

    public AppData(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }
}
