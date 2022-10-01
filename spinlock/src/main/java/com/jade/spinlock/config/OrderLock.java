package com.jade.spinlock.config;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class OrderLock {

    private int num;
    ReentrantLock lock = new ReentrantLock();
    Condition con = lock.newCondition();

    @SneakyThrows
    public String service(int i) {
        lock.lock();
        while (num != i) {
            con.await();
        }
        num++;
        // 业务逻辑

        con.notifyAll();
        lock.unlock();
        return "hello_" + i;
    }
}
