package com.renyu.threadstudydemo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    public static void main(String[] args) {
        AppleContainer container = new AppleContainer();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    container.addApple(new Apple());
                }
            }).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    container.takeApple();
                }
            }).start();
        }
    }
}

class AppleContainer {
    LinkedList<Apple> apples = new LinkedList<>();

    private Lock lock = new ReentrantLock();
    private Condition providerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();

    public void takeApple() {
        lock.lock();
        while (apples.size() == 0) {
            try {
                consumerCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Apple apple = apples.removeFirst();
        providerCondition.signalAll();
        System.out.println(Thread.currentThread().getName() + " take a apple = " + apple + ", size = " + apples.size());
        lock.unlock();
    }

    public void addApple(Apple apple) {
        lock.lock();
        while (apples.size() == 10) {
            try {
                providerCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apples.add(apple);
        consumerCondition.signalAll();
        System.out.println(Thread.currentThread().getName() + " add a apple = " + apple + ", size = " + apples.size());
        lock.unlock();
    }
}

class Apple {}
