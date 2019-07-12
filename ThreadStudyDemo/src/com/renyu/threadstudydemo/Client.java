package com.renyu.threadstudydemo;

public class Client {
    public static void main(String[] args) {
        ThreadPoolDemo1 threadPoolDemo1 = ThreadPoolDemo1.getInstance();
        for (int i = 0; i < 10; i++) {
            threadPoolDemo1.add(() -> System.out.println("HAHA"));
        }
    }
}
