package com.example.rg.androidstudiodemo.jni;

/**
 * Created by RG on 2015/4/3.
 */
public class MathKit {

    public static native int square(int num);

    static {
        System.loadLibrary("jniDemo");
    }
}
