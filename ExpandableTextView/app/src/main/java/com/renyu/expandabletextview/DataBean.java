package com.renyu.expandabletextview;

/**
 * Created by Clevo on 2016/9/13.
 */
public class DataBean {

    String text;
    boolean isCollapsed=true;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }
}
