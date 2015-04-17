package com.example.rg.eventbusdemo;

/**
 * Created by RG on 2015/4/14.
 */
public class FirstEvent {

    String msg="";

    public FirstEvent(String msg) {
        this.msg=msg;
    }

    public void getValue() {
        System.out.println(msg);
    }
}
