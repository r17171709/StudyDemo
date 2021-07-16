package com.renyu.threadstudydemo;

public class VolatileDemo {
    public static void main(String[] args) {
        new MyVolatileThread().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isReady = true;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }

    // volatile 保证可见性
//    private static boolean isReady = false;
    private static volatile boolean isReady = false;

    public static class MyVolatileThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isReady) {

            }
            System.out.println("thread end");
        }
    }
}
