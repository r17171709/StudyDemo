package com.renyu.observerdemo;

import java.util.Observable;

/**
 * Created by ry on 2014/12/15.
 */
public class Teacher1 extends Observable {

    private String phoneNum="";

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        //通知观察者发生改变了
        setChanged();
        notifyObservers();
    }
}
