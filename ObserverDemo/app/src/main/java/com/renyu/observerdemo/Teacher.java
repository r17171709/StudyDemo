package com.renyu.observerdemo;

import java.util.Vector;

/**
 * Created by ry on 2014/12/15.
 */
public class Teacher implements Subject {

    private Vector<Observer> students=new Vector<>();

    //被观察数据
    private String phoneNum="";

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        notice();
    }

    @Override
    public void attach(Observer observer) {
        students.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        students.remove(observer);
    }

    @Override
    public void notice() {
        for (int i=0;i<students.size();i++) {
            Student student= (Student) students.get(i);
            student.update();
        }
    }
}
