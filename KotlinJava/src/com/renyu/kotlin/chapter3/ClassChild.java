package com.renyu.kotlin.chapter3;

public class ClassChild extends ClassParent {

    int value=1;

    private ClassChild() {

    }

    public ClassChild(int value) {
        this.value=value;
    }

    public void funChild() {
        System.out.println(value);
    }
}
