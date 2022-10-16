package com.jade.demo.thread_local;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class ThreadLocalTest1 {

    private static final MyThreadLocal<String> threadLocal = new MyThreadLocal<String>() {
        @Override
        public String initalValue() {
            return "initalValue";
        }
    };
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            threadLocal.set("thread t1");
            try {
                TimeUnit.MICROSECONDS.sleep(random.nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            threadLocal.set("thread t2");
            try {
                TimeUnit.MICROSECONDS.sleep(random.nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2");

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
