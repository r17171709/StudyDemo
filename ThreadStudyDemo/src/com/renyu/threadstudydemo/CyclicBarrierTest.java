package com.renyu.threadstudydemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
        System.out.println("所有人都来了");
    });

    public static void main(String[] args) {
        executorService.submit(() -> {
            System.out.println("A去上厕所");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A回来了");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("会议结束，A退出");
        });
        executorService.submit(() -> {
            System.out.println("B去上厕所");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B回来了");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("会议结束，B退出");
        });
        executorService.submit(() -> {
            System.out.println("C去上厕所");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C回来了");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("会议结束，C退出");
        });
        executorService.shutdown();
    }
}
