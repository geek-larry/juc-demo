package com.jade.demo.stop.volatiler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @date: 2022/10/11
 **/
class Consumer {

    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {

        this.storage = storage;

    }

    public boolean needMoreNums() {

        if (Math.random() > 0.97) {

            return false;

        }

        return true;

    }

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue storage = new ArrayBlockingQueue(8);

        Producer producer = new Producer(storage);

        Thread producerThread = new Thread(producer);

        producerThread.start();

        Thread.sleep(500);

        Consumer consumer = new Consumer(storage);

        while (consumer.needMoreNums()) {

            System.out.println(consumer.storage.take() + "被消费了");

            Thread.sleep(100);

        }

        System.out.println("消费者不需要更多数据了。");

        //一旦消费不需要更多数据了，我们应该让生产者也停下来，但是实际情况却停不下来

        producer.canceled = true;

        System.out.println(producer.canceled);

        producerThread.interrupt();

    }

}
