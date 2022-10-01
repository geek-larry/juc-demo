package com.jade.spinlock.service;

import com.jade.spinlock.config.OrderLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CallableDemo implements Callable<String> {

    @Autowired
    private OrderLock orderLock;

    final CountDownLatch latch;

    final int i;

    public CallableDemo(CountDownLatch latch, int i) {
        this.latch = latch;
        this.i = i;
    }

    @Override
    public String call() {
        String res = orderLock.service(i);
        latch.countDown();
        return res;
    }
}
