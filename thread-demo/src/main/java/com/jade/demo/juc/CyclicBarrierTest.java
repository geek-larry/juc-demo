package com.jade.demo.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2, new Thread(() -> {
            System.out.println("发车，嘟嘟嘟");
        }));

        System.out.println("快上车来不及解释了");

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread() + "已上车");
                barrier.await();
                System.out.println("所有人已上车");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "Jane").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread() + "已上车");
                barrier.await();
                System.out.println("所有人已上车");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "Mike").start();
    }
}