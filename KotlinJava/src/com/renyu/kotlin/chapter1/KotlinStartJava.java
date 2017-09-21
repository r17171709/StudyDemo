package com.renyu.kotlin.chapter1;

public class KotlinStartJava {

    static String a2;

    public static void main(String[] args) {
        int a=2;
        int a1=3;
        String value=a+"+"+a1+"="+(a+a1);
        System.out.println(a2);
    }

    public int getStringLength(Object value) {
        if (value instanceof String) {
            return value.toString().length();
        }
        return 0;
    }
}
