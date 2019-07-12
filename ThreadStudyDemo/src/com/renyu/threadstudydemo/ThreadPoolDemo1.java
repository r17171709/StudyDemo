package com.renyu.threadstudydemo;

import java.util.concurrent.*;

public class ThreadPoolDemo1 {
    private static volatile ThreadPoolDemo1 demo1;

    private LinkedBlockingQueue<Future<Integer>> taskQueue;

    private ThreadPoolExecutor executor;

    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQueue.put(new FutureTask<>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask<Integer> futureTask = null;
                try {
                    System.out.println("等待Task");
                    System.out.println("等待队列大小"+taskQueue.size());
                    futureTask = (FutureTask<Integer>) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask != null) {
                    System.out.println("开始执行Task");
                    executor.execute(futureTask);
                }
                System.out.println("线程池大小"+executor.getLargestPoolSize());
            }
        }
    };

    public static ThreadPoolDemo1 getInstance() {
        if (demo1 == null) {
            synchronized (ThreadPoolDemo1.class) {
                if (demo1 == null) {
                    demo1 = new ThreadPoolDemo1();
                }
            }
        }
        return demo1;
    }

    private ThreadPoolDemo1() {
        taskQueue = new LinkedBlockingQueue<>();
        executor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), handler);
        executor.execute(runnable);
    }

    public void add(Runnable runnable) {
        taskQueue.add(new FutureTask<>(runnable, null));
    }
}
