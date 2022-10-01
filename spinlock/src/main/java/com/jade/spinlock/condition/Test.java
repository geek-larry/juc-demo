package com.jade.spinlock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        T1 t1 = new T1("线程1", lock, condition);
        t1.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        T2 t2 = new T2("线程2", lock, condition);
        t2.start();
    }
}

class T1 extends Thread {
    ReentrantLock lock;
    Condition condition;

    public T1(String name, ReentrantLock lock, Condition condition) {
        super(name);
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "成功获取锁,进入休眠");
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + "成功释放锁");
    }
}

class T2 extends Thread {
    ReentrantLock lock;
    Condition condition;

    public T2(String name, ReentrantLock lock, Condition condition) {
        super(name);
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "成功获取锁");
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "准备唤醒锁");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "成功唤醒锁");
            lock.unlock();

        }
    }
}


