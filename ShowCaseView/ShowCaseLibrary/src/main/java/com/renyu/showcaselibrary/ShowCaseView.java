package com.renyu.showcaselibrary;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ShowCaseView {

    Queue<View> mQueue;
    View currentView;

    Activity context;

    public ShowCaseView(Activity context) {
        this.context = context;
    }

    public void addViews(ArrayList<View> views) {
        mQueue=new LinkedList<>();
        mQueue.addAll(views);
    }

    public void show() {
        if (!mQueue.isEmpty()) {
            currentView=mQueue.poll();
            ((ViewGroup) context.getWindow().getDecorView()).addView(currentView);
        }
    }

    public void dismiss() {
        if (currentView!=null) {
            ((ViewGroup) context.getWindow().getDecorView()).removeView(currentView);
        }
        show();
    }

    public void cancel() {
        if (!mQueue.isEmpty()) {
            mQueue.clear();
        }
        if (currentView!=null) {
            ((ViewGroup) context.getWindow().getDecorView()).removeView(currentView);
        }
    }
}
