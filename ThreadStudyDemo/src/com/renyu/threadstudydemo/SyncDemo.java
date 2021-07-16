package com.renyu.threadstudydemo;

import java.util.LinkedList;

public class SyncDemo {
    public static void main(String[] args) {
        BreadContainer breadContainer = new BreadContainer();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    breadContainer.addBread(new Bread());
                }
            }).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    breadContainer.takeBread();
                }
            }).start();
        }
    }
}

class BreadContainer {
    private LinkedList<Bread> breads = new LinkedList<>();

    public synchronized void addBread(Bread bread) {
        while (breads.size() == 10) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        breads.add(bread);
        System.out.println(Thread.currentThread().getName() + " add a bread = " + bread + ", size = " + breads.size());
    }

    public synchronized void takeBread() {
        while (breads.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Bread bread = breads.removeFirst();
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " take a bread = " + bread + ", size = " + breads.size());
    }
}

class Bread {

}