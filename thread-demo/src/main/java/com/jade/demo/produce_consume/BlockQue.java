package com.jade.demo.produce_consume;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @date: 2022/10/11
 **/
public class BlockQue {
    public static void main(String[] args) {

        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);

        Runnable producer = () -> {

            while (true) {

                try {
                    queue.put(new Object());
                    System.out.println(queue.size());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        };

        new Thread(producer).start();

        new Thread(producer).start();

        Runnable consumer = () -> {

            while (true) {

                try {
                    queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        };

        new Thread(consumer).start();

        new Thread(consumer).start();

    }
}
