package com.renyu.observerdemo;

/**
 * Created by ry on 2014/12/15.
 */
public interface Subject {

    public void attach(Observer observer);

    public void detach(Observer observer);

    public void notice();
}
