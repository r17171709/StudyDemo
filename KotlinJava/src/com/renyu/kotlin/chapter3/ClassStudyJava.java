package com.renyu.kotlin.chapter3;

public class ClassStudyJava {
    public static void main(String[] args) {
        ClassParent parent=new ClassChild(12);
        parent.funParent();
        ((ClassChild) parent).funChild();

        C.getInstance();
        C.getCompanionValue();
    }
}
