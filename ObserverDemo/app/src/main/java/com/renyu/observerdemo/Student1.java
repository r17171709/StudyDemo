package com.renyu.observerdemo;

import java.util.*;

/**
 * Created by ry on 2014/12/15.
 */
public class Student1 implements java.util.Observer {

    @Override
    public void update(Observable observable, Object data) {
        Teacher1 teacher1= (Teacher1) observable;
        System.out.println(teacher1.getPhoneNum());
    }
}
