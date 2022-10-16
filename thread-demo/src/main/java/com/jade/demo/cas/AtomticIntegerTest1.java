package com.jade.demo.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class AtomticIntegerTest1 {
    public static void main(String[] args) {

        // 初始值为A，版本号为1
        AtomicStampedReference<String> reference = new AtomicStampedReference<>("A", 1);

        new Thread(() -> {
            int stamp = reference.getStamp();
            System.out.println(Thread.currentThread().getName() + "当前版本号为：" + stamp);
            // 休眠1秒，让thread2也拿到初始的版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 模拟一次ABA操作
            reference.compareAndSet("A", "B", reference.getStamp(), reference.getStamp() + 1);
            reference.compareAndSet("B", "A", reference.getStamp(), reference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "线程完成了一次ABA操作");
        }, "thread1").start();

        new Thread(() -> {
            int stamp = reference.getStamp();
            System.out.println(Thread.currentThread().getName() + "当前版本号为：" + stamp);
            // 让thread2先睡眠2秒钟，确保thread1的ABA操作完成
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = reference.compareAndSet("A", "B", stamp, stamp + 1);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "线程修改值成功，当前值为：" + reference.getReference());
            } else {
                System.out.println(Thread.currentThread().getName() + "线程修改值失败，当前值为：" + reference.getReference() + "，版本号为：" + reference.getStamp());
            }
        }, "thread2").start();
    }
}