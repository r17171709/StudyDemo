package com.renyu.kotlin.chapter2;

import com.renyu.kotlin.chapter2.a.APP;

public class NumberStudyJava {
    public static void main(String[] args) {
        APP app=new APP();
        com.renyu.kotlin.chapter2.b.APP app1=new com.renyu.kotlin.chapter2.b.APP();

        String[] arrays={"1", "2", "3", "4", "5"};
        outer:
        for (String array : arrays) {
            System.out.println("1. "+array);
            for (int i=0;i<3;i++) {
                System.out.println("1. "+i);
                if (i==2) {
                    break outer;
                }
            }
        }
    }
}
