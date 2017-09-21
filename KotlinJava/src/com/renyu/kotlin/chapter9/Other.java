package com.renyu.kotlin.chapter9;

import java.util.ArrayList;

public class Other {
    public static void main(String[] args) {
        ArrayList<Object> objects=new ArrayList<>();
        objects.add("123");
        for (Object object : objects) {
            if (object instanceof String) {
                ((String) object).length();
            }
        }
    }
}
