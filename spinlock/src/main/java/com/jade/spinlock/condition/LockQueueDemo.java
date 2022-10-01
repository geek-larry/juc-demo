package com.jade.spinlock.condition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockQueueDemo {
    ReentrantLock lock = new ReentrantLock();
    LinkedList<String> list = new LinkedList<>();
    Condition condition1 = lock.newCondition();//生产者条件
    Condition condition2 = lock.newCondition();//消费者条件

    //定义一个方法用于生产者
    public void shengChan() {
        lock.lock();
        try {
            while (list.size() == 1) {
                //此时生产者开始等待
                condition1.await();
            }
            System.out.println("生产者开始生产");
            list.add("商品1");
            System.out.println("已经生产好了，可以消费");
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void xiaoFei() {
        lock.lock();
        try {
            while (list.size() == 0) {
                //消费者开始等待
                condition2.await();
            }
            System.out.println("消费者开始消费");
            list.removeFirst();
            System.out.println("消费完毕，请开始生产");
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        LockQueueDemo lockQueueDemo = new LockQueueDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(lockQueueDemo::shengChan).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(lockQueueDemo::xiaoFei).start();
        }
    }
}

