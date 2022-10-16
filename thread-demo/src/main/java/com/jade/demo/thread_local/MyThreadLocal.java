package com.jade.demo.thread_local;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class MyThreadLocal<T> {

    private final Map<Thread, T> threadLocalMap = new HashMap<>();

    public void set(T t) {
        synchronized (this) {
            Thread key = Thread.currentThread();
            threadLocalMap.put(key, t);
        }
    }

    public T get() {
        synchronized (this) {
            Thread key = Thread.currentThread();
            T t = threadLocalMap.get(key);
            if (t == null) {
                return initalValue();
            } else {
                return t;
            }
        }
    }

    public T initalValue() {
        return null;
    }
}
