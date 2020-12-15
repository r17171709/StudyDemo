package com.renyu.threadstudydemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {
    private static final CountDownLatch countDownLatch = new CountDownLatch(3);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("A去上厕所");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A回来了");
            countDownLatch.countDown();
        });
        executorService.submit(() -> {
            System.out.println("B去上厕所");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B回来了");
            countDownLatch.countDown();
        });
        executorService.submit(() -> {
            System.out.println("C去上厕所");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C回来了");
            countDownLatch.countDown();
        });
        System.out.println("等待所有人来");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有人都来了");
        executorService.shutdown();
    }
}
