package com.renyu.threadstudydemo;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        // 先进先出
        Queue<String> mqueue = new PriorityQueue<>(5, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 大数在前面
                if (Integer.parseInt(o1) - Integer.parseInt(o2) > 0) {
                    return -1;
                }
                return 1;
            }
        });
        mqueue.offer("1");
        mqueue.offer("2");
        mqueue.offer("3");
        System.out.println(mqueue.poll());
        System.out.println(mqueue.poll());
        System.out.println(mqueue.poll());
        System.out.println(mqueue.poll());
        mqueue.peek();
        mqueue.remove();
    }
}
