package com.jade.demo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class ReentrantLockTest {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(ReentrantLockTest::testTryLock, "thread1");
        thread1.start();
        TimeUnit.SECONDS.sleep(1);

        Thread thread2 = new Thread(ReentrantLockTest::testTryLock, "thread2");
        thread2.start();
    }
    public static void testTryLock() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始工作");
                while (true) { }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "没有获取到锁");
        }
    }
}
