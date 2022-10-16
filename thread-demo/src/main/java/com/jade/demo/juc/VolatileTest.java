package com.jade.demo.juc;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class VolatileTest {
    private volatile static int INIT_VALUE = 0;
    private final static int LIMIT = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int value = INIT_VALUE;
            while (value < LIMIT) {
                if (value != INIT_VALUE) {
                    System.out.println("获取更新后的值：" + INIT_VALUE);
                    value = INIT_VALUE;
                }
            }
        }, "reader").start();

        new Thread(() -> {
            int value = INIT_VALUE;
            while (INIT_VALUE < LIMIT) {
                System.out.println("将值更新为：" + ++value);
                INIT_VALUE = value;
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "writer").start();
    }
}