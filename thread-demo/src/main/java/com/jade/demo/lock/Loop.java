package com.jade.demo.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class Loop {
    private volatile String value = "a";
    private final Lock lock = new ReentrantLock();
    // 条件1
    private final Condition condition1 = lock.newCondition();
    // 条件2
    private final Condition condition2 = lock.newCondition();
    // 条件3
    private final Condition condition3 = lock.newCondition();

    public void printA() {
        try {
            lock.lock();
            while (!value.equals("a")) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + " print a");
            value = "b";
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        try {
            lock.lock();
            while (!value.equals("b")) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + " print b");
            value = "c";
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        try {
            lock.lock();
            while (!value.equals("c")) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + " print c");
            value = "a";
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Loop loop = new Loop();
        IntStream.rangeClosed(0, 2).forEach(i -> new Thread(loop::printA, "线程A").start());
        IntStream.rangeClosed(0, 2).forEach(i -> new Thread(loop::printB, "线程B").start());
        IntStream.rangeClosed(0, 2).forEach(i -> new Thread(loop::printC, "线程C").start());
    }
}
