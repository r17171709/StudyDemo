package com.renyu.observerdemo;

/**
 * Created by ry on 2014/12/15.
 */
public class Student implements Observer {

    Teacher teacher=null;

    public Student(Teacher teacher) {
        this.teacher=teacher;
    }

    @Override
    public void update() {
        System.out.println(teacher.getPhoneNum());
    }
}
