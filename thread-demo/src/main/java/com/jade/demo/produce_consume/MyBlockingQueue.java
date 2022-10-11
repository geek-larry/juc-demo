package com.jade.demo.produce_consume;

import java.util.LinkedList;

/**
 * @description:
 * @date: 2022/10/11
 **/
class MyBlockingQueue {

    private int maxSize;

    private LinkedList<Object> storage;

    public MyBlockingQueue(int size) {

        this.maxSize = size;

        storage = new LinkedList<>();

    }

    public synchronized void put() throws InterruptedException {

        while (storage.size() == maxSize) {

            wait();

        }

        storage.add(new Object());

        notifyAll();

    }

    public synchronized void take() throws InterruptedException {

        while (storage.size() == 0) {

            wait();

        }

        System.out.println(storage.remove());

        notifyAll();

    }

}
