package com.jade.demo.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class AtomticIntegerTest {
    private static final AtomicInteger value = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value.getAndIncrement()));
        Thread thread2 = new Thread(() -> IntStream.range(0, 500).forEach(i -> value.getAndIncrement()));

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(value);
    }
}
