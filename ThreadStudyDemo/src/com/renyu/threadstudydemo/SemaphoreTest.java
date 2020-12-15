package com.renyu.threadstudydemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private final static Semaphore semaphore = new Semaphore(0);
    public final static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("A去上厕所");
            try {
                Thread.sleep(4000);
                semaphore.release();
                System.out.println("A回来了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            System.out.println("B去上厕所");
            try {
                Thread.sleep(3000);
                semaphore.release();
                System.out.println("B回来了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            System.out.println("C去上厕所");
            try {
                Thread.sleep(2000);
                semaphore.release();
                System.out.println("C回来了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("等待所有人来");
        try {
            semaphore.acquire(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有人都来了");
        executorService.shutdown();
    }
}
