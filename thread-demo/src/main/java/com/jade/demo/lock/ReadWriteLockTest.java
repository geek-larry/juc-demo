package com.jade.demo.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @description: 使用ReadWriteLock的时候，当读线程数量远大于写线程数量的时候就会出现“写饥饿”现象。因为锁大概率都被读线程抢走了，写线程很难抢到锁，这将使得读写效率非常低下。
 * @date: 2022/10/16
 **/
public class ReadWriteLockTest {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    // 读锁
    private static final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    // 写锁
    private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    // 存放数据
    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                write();
            }
        }, "writer").start();
        new Thread(() -> {
            while (true) {
                read();
            }
        }, "reader").start();

    }

    public static void write() {
        try {
            writeLock.lock(); // 写锁
            TimeUnit.SECONDS.sleep(1);
            long value = System.currentTimeMillis();
            data.add(value);
            System.out.println(Thread.currentThread().getName() + " 写入value: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock(); // 释放写锁
        }
    }

    public static void read() {
        try {
            readLock.lock(); // 获取读锁
            TimeUnit.SECONDS.sleep(1);
            String value = data.stream().map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(Thread.currentThread().getName() + "读取data: " + value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }
}
