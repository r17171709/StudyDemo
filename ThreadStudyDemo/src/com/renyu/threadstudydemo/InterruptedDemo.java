package com.renyu.threadstudydemo;

public class InterruptedDemo {
    public static void main(String[] args) {
//        A a = new A();
//        a.thread.start();

//        B b = new B();
//        b.thread.start();

        C c = new C();
        c.thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 线程依然存活，但是中断标记为false
//                a.thread.interrupt();
                // 线程依然存活，但是中断标记为true
//                b.thread.interrupt();
                c.thread.interrupt();
            }
        }).start();
    }


}

class A {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(thread.isAlive() + " " + thread.isInterrupted());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}

class B {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                System.out.println(thread.isAlive() + " " + thread.isInterrupted());
            }
        }
    });
}

class C {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (!thread.isInterrupted()) {
                    System.out.println(thread.isAlive() + " " + thread.isInterrupted());
                }
                System.out.println("   "+thread.isAlive() + " " + thread.isInterrupted());
            } catch (Exception e) {
                if (e instanceof InterruptedException) {
                    thread.interrupt();
                }
            }
        }
    });
}