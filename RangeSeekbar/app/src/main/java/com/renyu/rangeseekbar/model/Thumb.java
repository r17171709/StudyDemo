package com.renyu.rangeseekbar.model;

/**
 * Created by renyu on 2016/11/21.
 */

public class Thumb {
    float thumbX=0;
    boolean isPress=false;

    public float getThumbX() {
        return thumbX;
    }

    public void setThumbX(float thumbX) {
        this.thumbX = thumbX;
    }

    public boolean isPress() {
        return isPress;
    }

    public void setPress(boolean press) {
        isPress = press;
    }
}
