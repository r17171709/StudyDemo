package com.rg.annotationdemo;

import com.rg.annotationdemo.MainActivity;

public class MainViewBinding {
    public void bind(MainActivity activity) {
        activity.textview = activity.findViewById(R.id.textview);
    }
}
